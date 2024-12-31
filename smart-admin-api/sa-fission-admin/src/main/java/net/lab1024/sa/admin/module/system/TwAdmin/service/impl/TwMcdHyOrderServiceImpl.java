package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdHyorderMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.common.util.DateUtil;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@Slf4j
public class TwMcdHyOrderServiceImpl extends ServiceImpl<TwMcdHyorderMapper, TwMcdHyOrder>
        implements TwMcdHyOrderService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwMockUserCoinService twMockUserCoinService;

    @Autowired
    private TwHysettingService twHysettingService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Override
    public IPage<TwMcdHyOrder> listpage(TwHyorderVo twHyorderVo, HttpServletRequest request) {
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwMcdHyOrder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwMcdHyOrder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
                twHyorderVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
                return objectPage;
            }else{
                Page<TwMcdHyOrder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
                if(inviteType == 1){
                    twHyorderVo.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public ResponseDTO creatorder(int orderId, int uid, BigDecimal ctzed, String language) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid); // 添加查询条件
        TwUser twUser = twUserService.getOne(queryWrapper);
        Integer userType = twUser.getUserType();
        if(userType == 1){
            return userOrder(orderId, twUser, ctzed, language) ;
        }
        if(userType == 2){
            return mockUserOrder(orderId, twUser, ctzed, language) ;
        }

        return null;
    }

    @Override
    public ResponseDTO<List<TwMcdHyOrder>> followHyorder(int uid) {
        List<TwMcdHyOrder> list1 = new ArrayList<>();
        QueryWrapper<TwMcdHyOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("main_order_no", 0);
        queryWrapper.in("status", 0, 1, 2);
        queryWrapper.eq("uid", uid);
        queryWrapper.orderByDesc("id");
        List<TwMcdHyOrder> list = this.list(queryWrapper);
        for(TwMcdHyOrder twHyorder:list){
            Date selltime = twHyorder.getSelltime();
            int t = DateUtil.dateToSeconds(selltime);
            twHyorder.setTimeResidue(t);

            list1.add(twHyorder);
        }

        return ResponseDTO.ok(list1);
    }

    @Override
    public void settlement(Integer orderId) {
        Instant now = Instant.now();

        // 将当前时间戳减去12个小时
        Instant twelveHoursAgo = now.minusSeconds(12 * 60 * 60);
        int nowtime = (int) twelveHoursAgo.getEpochSecond();
        QueryWrapper<TwMcdHyOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("id", orderId).or().eq("main_order_id", orderId);
        List<TwMcdHyOrder> list = this.baseMapper.selectList(queryWrapper);

        for(TwMcdHyOrder twHyorder : list) {
            long startTime = System.currentTimeMillis();
            log.info("开始执行合约任务，编号: {}", twHyorder.getOrderNo());

            Integer companyId = twHyorder.getCompanyId();
            QueryWrapper<TwHysetting> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("company_id",twHyorder.getCompanyId());
            TwHysetting twHysetting = twHysettingService.getOne(queryWrapper1);
            String hyYlid = twHysetting.getHyYlid();
            String hyKsid = twHysetting.getHyKsid();
            String[] winarr = hyYlid.split("\\|");
            String[] lossarr = hyKsid.split("\\|");


            String coinname = twHyorder.getCoinname();
            String symbol = coinname.toLowerCase().replace("/", "");
            String url = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" + symbol;
            BigDecimal newprice = getnewprice(url);
            // 创建 Random 对象
            Random random = new Random();
            // 生成在[0.1, 0.9999]范围内的随机数
            BigDecimal randnum = new BigDecimal(Double.toString(0.1 + (0.9999 - 0.1) * random.nextDouble()));

            BigDecimal buyprice = twHyorder.getBuyprice();
            Integer hyzd = twHyorder.getHyzd();  //合约方向
            Integer kongyk = twHyorder.getKongyk(); //单控设置
            String uid = twHyorder.getUid().toString();
            Integer uuid = twHyorder.getUid();
            String username = twHyorder.getUsername();
            BigDecimal num = twHyorder.getNum();
            BigDecimal hybl = twHyorder.getHybl();
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
            BigDecimal ylnum = num.multiply(hybl.divide(new BigDecimal(100), mathContext));
            BigDecimal money = num.add(ylnum);  //盈利金额
            BigDecimal sellprice = twHyorder.getSellprice();

            QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("userid", uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);

            //买涨
            if (hyzd == 1) {
                if (kongyk != 0) {   //已控
                    if (kongyk == 1) {  //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = newprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }
                        //增加资产
                        twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        this.baseMapper.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid, username, money,companyId);

                        log.info("买涨已控盈利1=================================");
                    }

                    if (kongyk == 2) { //亏损
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }
                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(num);
                        this.baseMapper.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid, username, num,companyId);

                        log.info("买涨已控亏损2=================================");
                    }
                }

                if (kongyk == 0) {   //未控
                    boolean isWinArray = false;
                    boolean isLoseArray = false;
                    for (String win : winarr) {
                        if (win.equals(uid)) {
                            isWinArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }
                    for (String win : lossarr) {
                        if (win.equals(uid)) {
                            isLoseArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }

                    if (isWinArray) {
                        //如果有指定盈利ID，则按盈利结算
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = newprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }
                        //增加资产
                        twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        this.baseMapper.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid, username, money,companyId);

                        log.info("买涨指定盈利3=================================");
                    }

                    if (isLoseArray) {
                        //买涨,指定亏损,结算价格要低于买入价格
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(num);
                        this.baseMapper.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid, username, num,companyId);

                        log.info("买涨指定亏损4=================================");
                    }

                    if (!isWinArray && !isLoseArray) {
                        //如果未指定盈利和亏损，则按单控的计算

                        if (kongyk == 1) {  //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = newprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }
                            //增加资产
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            this.baseMapper.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);

                            log.info("买涨指定盈利5=================================");

                        }

                        if (kongyk == 2) { //亏损
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }
                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(2);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(num);
                            this.baseMapper.updateById(twHyorder);


                            //写财务日志
                            addlog(uuid, username, num,companyId);

                            log.info("买涨指定亏损6=================================");
                        }

                        if (kongyk == 0) {
                            if (buyprice.compareTo(newprice) < 0) {   //盈利
                                twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                //写财务日志
                                addlog(uuid, username, money,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(ylnum);
                                this.baseMapper.updateById(twHyorder);
                                log.info("买涨指定盈利7=================================");

                            } else if (newprice.compareTo(buyprice) == 0) {
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid, username, num,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                this.baseMapper.updateById(twHyorder);
                                log.info("买涨指定亏损8=================================");
                            } else if (newprice.compareTo(buyprice) < 0) {   //亏损
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid, username, num,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                this.baseMapper.updateById(twHyorder);
                                log.info("买涨指定亏损9=================================");
                            }

                        }
                    }
                }

            }
            //买跌
            if (hyzd == 2) {

                if (kongyk != 0) {
                    if (kongyk == 1) { //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }

                        //增加资产
                        twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        this.baseMapper.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid, username, money,companyId);
                    }

                    if (kongyk == 2) { //亏损
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(num);
                        this.baseMapper.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid, username, num,companyId);
                        log.info("合约指定亏损成功======================================");
                    }
                }

                if (kongyk == 0) {
                    boolean isWinArray = false;
                    boolean isLoseArray = false;
                    for (String win : winarr) {
                        if (win.equals(uid)) {
                            isWinArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }
                    for (String win : lossarr) {
                        if (win.equals(uid)) {
                            isLoseArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }
                    if (isWinArray) { //如果有指定盈利ID，则按盈利结算
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }
                        //增加资产
                        twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        this.baseMapper.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid, username, money,companyId);
                    }

                    if (isLoseArray) { //如果有指定亏损ID，则按亏损结算
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(num);
                        this.baseMapper.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid, username, num,companyId);
                    }

                    if (!isWinArray && !isLoseArray) { //如果未指定盈利和亏损，则按单控的计算
                        if (kongyk == 1) { //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }

                            //增加资产
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            this.baseMapper.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);
                        }

                        if (kongyk == 2) { //亏损
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(2);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(num);
                            this.baseMapper.updateById(twHyorder);


                            //写财务日志
                            addlog(uuid, username, num,companyId);
                            log.info("合约指定亏损成功======================================");
                        }


                        if (kongyk == 0) {
                            if (buyprice.compareTo(newprice) < 0) {   //亏损
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid, username, num,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                this.baseMapper.updateById(twHyorder);

                            } else if (newprice.compareTo(buyprice) == 0) {
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid, username, num,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                this.baseMapper.updateById(twHyorder);

                            } else if (newprice.compareTo(buyprice) < 0) {   //盈利
                                twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                //写财务日志
                                addlog(uuid, username, money,companyId);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(ylnum);
                                this.baseMapper.updateById(twHyorder);
                            }
                        }
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;
            log.info("当前执行线程是: {}, 执行的合约编号是: {}, 总耗时: {}", Thread.currentThread().getName(), twHyorder.getOrderNo(), timeElapsed);
        }
    }

    public ResponseDTO userOrder(int orderId, TwUser twUser , BigDecimal ctzed, String language){

        Integer uid = twUser.getId();
        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwCompany company = twCompanyService.getById(twUser.getCompanyId());
        int inviteType = company.getInviteType();
        String invite = "";
        if(inviteType == 1){
            EmployeeEntity byInvite = employeeService.getById(Long.valueOf(twUser.getInvit1()));//获取代理人信息
            if(byInvite == null){
                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id", twUser.getInvit1()); // 添加查询条件
                TwUser puser = twUserService.getOne(queryWrapper4);
                invite = puser.getInvit1();
            }
            invite = String.valueOf(byInvite.getEmployeeId());
        }

        //获取会员资产
        QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid); // 添加查询条件
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

        if(twUser.getRzstatus() != 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("请先完成实名认证！");
            }else{
                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
            }
        }

        if(twUser.getBuyOn() == 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("您的账户已被禁止交易，请联系客服！");
            }else{
                return ResponseDTO.userErrorParam("Your account has been banned from trading, please contact customer service！");
            }
        }

//            if(ctzed.compareTo(twHysetting.getHyMin()) < 0){
//                ResponseDTO.userErrorParam("不能小于最低投资额度");
//            }

//            BigDecimal hySxf = twHysetting.getHySxf();

//            Integer companyId = twUser.getCompanyId();
//            QueryWrapper<TwCompany> queryWrapper2 = new QueryWrapper<>();
//            queryWrapper2.eq("id", companyId); // 添加查询条件
        BigDecimal hyFee = company.getHyFee();
        BigDecimal tmoneys= ctzed.add(hyFee);
        if(twUserCoin.getUsdt().compareTo(tmoneys) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient balance！");
            }
        }

        TwMcdHyOrder twMcdHyOrder = this.baseMapper.selectById(orderId);
        if(twMcdHyOrder == null) {
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("当前订单已失效!");
            }else{
                return ResponseDTO.userErrorParam("The current order has expired！");
            }
        }

        String symbol = twMcdHyOrder.getCoinname().toLowerCase().replace("/", "");
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        Map<String, Object> map = CommonUtil.executeGet(str);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());
        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

        TwMcdHyOrder twHyorder = new TwMcdHyOrder();
        try {
            BeanUtils.copyProperties(twHyorder, twMcdHyOrder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        twHyorder.setId(null);
        twHyorder.setMainOrderNo(twMcdHyOrder.getOrderNo());
        twHyorder.setNum(ctzed);
        twHyorder.setBuytime(DateUtil.stract12());
        this.save(twHyorder);
        //扣除USDT额度
        twUserCoinService.decre(uid,tmoneys,twUserCoin.getUsdt());

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUserCode(twUser.getUserCode());
        twBill.setUid(uid);
        twBill.setUsername(twUser.getUsername());
        twBill.setNum(ctzed);
        twBill.setCompanyId(twUser.getCompanyId());
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(3);
        twBill.setPath(twUser.getPath());
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("购买"+ twMcdHyOrder.getCoinname() + "秒合约");
        twBillService.save(twBill);
        if(language.equals("zh")){
            return ResponseDTO.ok(orderNo);
        }else{
            return ResponseDTO.ok(orderNo);
        }
    }
    public ResponseDTO mockUserOrder(int orderId, TwUser twUser , BigDecimal ctzed, String language){
        Integer uid = twUser.getId();

        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwCompany company = twCompanyService.getById(twUser.getCompanyId());
        int inviteType = company.getInviteType();
        String invite = "";
        if(inviteType == 1){
            EmployeeEntity byInvite = employeeService.getById(Long.valueOf(twUser.getInvit1()));//获取代理人信息
            if(byInvite == null){
                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id", twUser.getInvit1()); // 添加查询条件
                TwUser puser = twUserService.getOne(queryWrapper4);
                invite = puser.getInvit1();
            }
            invite = String.valueOf(byInvite.getEmployeeId());
        }

        //获取会员资产
        QueryWrapper<TwMockUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid); // 添加查询条件
        TwMockUserCoin twMockUserCoin = twMockUserCoinService.getOne(queryWrapper1);

//        if(twUser.getRzstatus() != 2){
//            if(language.equals("zh")){
//                return ResponseDTO.userErrorParam("请先完成实名认证！");
//            }else{
//                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
//            }
//        }

        if(twUser.getBuyOn() == 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("您的账户已被禁止交易，请联系客服！");
            }else{
                return ResponseDTO.userErrorParam("Your account has been banned from trading, please contact customer service！");
            }
        }

//            if(ctzed.compareTo(twHysetting.getHyMin()) < 0){
//                ResponseDTO.userErrorParam("不能小于最低投资额度");
//            }

//            BigDecimal hySxf = twHysetting.getHySxf();

//            Integer companyId = twUser.getCompanyId();
//            QueryWrapper<TwCompany> queryWrapper2 = new QueryWrapper<>();
//            queryWrapper2.eq("id", companyId); // 添加查询条件
        BigDecimal hyFee = company.getHyFee();
        BigDecimal tmoneys= ctzed.add(hyFee);
        if(twMockUserCoin.getUsdt().compareTo(tmoneys) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient balance！");
            }
        }

        TwMcdHyOrder twMcdHyOrder = this.baseMapper.selectById(orderId);
        if(twMcdHyOrder == null) {
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("当前订单已失效!");
            }else{
                return ResponseDTO.userErrorParam("The current order has expired！");
            }
        }

        String symbol = twMcdHyOrder.getCoinname().toLowerCase().replace("/", "");
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        Map<String, Object> map = CommonUtil.executeGet(str);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);



        TwMcdHyOrder twHyorder = new TwMcdHyOrder();
        try {
            BeanUtils.copyProperties(twMcdHyOrder, twHyorder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        twHyorder.setId(null);
        twHyorder.setMainOrderNo(twMcdHyOrder.getOrderNo());
        twHyorder.setNum(ctzed);
        twHyorder.setBuytime(DateUtil.stract12());
        this.save(twHyorder);
        //扣除USDT额度
        twMockUserCoinService.decre(uid,tmoneys,twMockUserCoin.getUsdt());

//        //创建财务日志
//        TwBill twBill = new TwBill();
//        twBill.setUserCode(twUser.getUserCode());
//        twBill.setUid(uid);
//        twBill.setUsername(twUser.getUsername());
//        twBill.setNum(ctzed);
//        twBill.setCompanyId(twUser.getCompanyId());
//        twBill.setCoinname("usdt");
//        twBill.setAfternum(twUserCoinService.afternum(uid));
//        twBill.setType(3);
//        twBill.setPath(twUser.getPath());
//        twBill.setDepartment(twUser.getDepatmentId());
//        twBill.setAddtime(new Date());
//        twBill.setSt(2);
//        twBill.setRemark("购买"+ ccoinname + "秒合约");
//        twBillService.save(twBill);
        if(language.equals("zh")){
            return ResponseDTO.ok(orderNo);
        }else{
            return ResponseDTO.ok(orderNo);
        }
    }

    public BigDecimal getnewprice(String url){
        Map<String, Object> map = CommonUtil.executeGet(url);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
        return close;
    }

    public void addlog(int uid, String username,BigDecimal money,int companyId){
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uid);
        TwUser twUser = twUserService.getOne(queryWrapper);


        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(username);
        twBill.setUserCode(twUser.getUserCode());
        twBill.setNum(money);
        twBill.setCompanyId(companyId);
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setPath(twUser.getPath());
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(4);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("合约出售");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setPath(twUser.getPath());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNotice.setAccount(username);
        twNotice.setCompanyId(twUser.getCompanyId());
        twNotice.setTitle("秒合约交易");
        twNotice.setTitleEn("second contract trading");
        twNotice.setContent("秒合约已平仓，请注意查收");
        twNotice.setContentEn("The second contract has been closed, please check it carefully");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }
}
