package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TeamVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserTeamService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 1
* @description 针对表【tw_user_team(会员团队)】的数据库操作Service实现
* @createDate 2024-06-12 17:35:11
*/
@Service
public class TwUserTeamServiceImpl extends ServiceImpl<TwUserTeamMapper, TwUserTeam>
    implements TwUserTeamService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    private TwRechargeDao twRechargeDao;

    @Autowired
    private TwMyzcDao twMyzcDao;

    @Autowired
    @Lazy
    private TwUserService twUserService;

    @Autowired
    private TwHyorderDao twHyorderDao;

    @Autowired
    private TwLeverOrderMapper twLeverOrderMapper;

    @Override
    public IPage<TwUserTeam> listpage(TeamVo teamVo, HttpServletRequest request) {
        List<TwUserTeam> list1 = new ArrayList<>();
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
            List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
            for(TwUserTeam twUserTeam:listpage){
                Integer uid = twUserTeam.getUid();
                TwUser byId1 = twUserService.getById(uid);

                if(byId1 == null){
                    twUserTeam.setUserCode(uid.toString());
                }else{
                    String userCode = byId1.getUserCode();
                    twUserTeam.setUserCode(userCode);
                }


                BigDecimal recharge = new BigDecimal(0);
                BigDecimal myzc = new BigDecimal(0);
                QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
                queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
                if (rechargeResult.isEmpty()) {
                    recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object rechargeResultObject = rechargeResult.get(0).get("recharge");
                if (rechargeResultObject instanceof BigDecimal) {
                    recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (rechargeResultObject instanceof Long) {
                    recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (rechargeResultObject instanceof Integer) {
                    recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
                queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
                if (myzcResult.isEmpty()) {
                    myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object myzcResultObject = myzcResult.get(0).get("myzc");
                if (myzcResultObject instanceof BigDecimal) {
                    myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (myzcResultObject instanceof Long) {
                    myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (myzcResultObject instanceof Integer) {
                    myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }



                BigDecimal winHyorder = new BigDecimal(0);
                BigDecimal lossHyorder = new BigDecimal(0);
                BigDecimal winLeverOrder = new BigDecimal(0);
                BigDecimal lossLeverOrder = new BigDecimal(0);
                BigDecimal numHyOrder = new BigDecimal(0);
                BigDecimal numLeverOrder = new BigDecimal(0);

                QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                        .eq("is_win", 1)
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> winHyorderResult = this.twHyorderDao.selectMaps(queryWrapper);
                if (winHyorderResult.isEmpty()) {
                    winHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object winHyorderObject = winHyorderResult.get(0).get("winHyorder");
                if (winHyorderObject instanceof BigDecimal) {
                    winHyorder =  ((BigDecimal) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (winHyorderObject instanceof Long) {
                    winHyorder =  BigDecimal.valueOf((Long) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (winHyorderObject instanceof Integer) {
                    winHyorder =  BigDecimal.valueOf((Integer) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    winHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }


                QueryWrapper<TwHyorder> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.select("IFNULL(SUM(ploss), 0) as lossHyorder")
                        .eq("is_win", 2)
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> lossHyorderResult = this.twHyorderDao.selectMaps(queryWrapper1);
                if (lossHyorderResult.isEmpty()) {
                    lossHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object lossHyorderObject = lossHyorderResult.get(0).get("lossHyorder");
                if (lossHyorderObject instanceof BigDecimal) {
                    lossHyorder =  ((BigDecimal) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (lossHyorderObject instanceof Long) {
                    lossHyorder =  BigDecimal.valueOf((Long) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (lossHyorderObject instanceof Integer) {
                    lossHyorder =  BigDecimal.valueOf((Integer) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    lossHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }


                QueryWrapper<TwHyorder> queryWrapper7 = new QueryWrapper<>();
                queryWrapper7.select("IFNULL(SUM(num), 0) as numHyOrder")
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> numHyorderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                if (numHyorderResult.isEmpty()) {
                    numHyOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object numHyorderObject = numHyorderResult.get(0).get("numHyOrder");
                if (numHyorderObject instanceof BigDecimal) {
                    numHyOrder =  ((BigDecimal) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (numHyorderObject instanceof Long) {
                    numHyOrder =  BigDecimal.valueOf((Long) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (numHyorderObject instanceof Integer) {
                    numHyOrder =  BigDecimal.valueOf((Integer) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    numHyOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }


                QueryWrapper<TwLeverOrder> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.select("IFNULL(SUM(ploss), 0) as winLeverOrder")
                        .eq("is_win", 1)
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> winLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper2);
                if (winLeverOrderResult.isEmpty()) {
                    winLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object winLeverOrderResultObject = winLeverOrderResult.get(0).get("winLeverOrder");
                if (winLeverOrderResultObject instanceof BigDecimal) {
                    winLeverOrder =  ((BigDecimal) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (winLeverOrderResultObject instanceof Long) {
                    winLeverOrder =  BigDecimal.valueOf((Long) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (winLeverOrderResultObject instanceof Integer) {
                    winLeverOrder =  BigDecimal.valueOf((Integer) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    winLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }


                QueryWrapper<TwLeverOrder> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.select("IFNULL(SUM(ploss), 0) as lossLeverOrder")
                        .eq("is_win", 2)
                        .like("path", uid)
                        .eq("status", 2);


                List<Map<String, Object>> lossLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper3);
                if (lossLeverOrderResult.isEmpty()) {
                    lossLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object lossLeverOrderResultObject = lossLeverOrderResult.get(0).get("lossLeverOrder");
                if (lossLeverOrderResultObject instanceof BigDecimal) {
                    lossLeverOrder =  ((BigDecimal) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (lossLeverOrderResultObject instanceof Long) {
                    lossLeverOrder =  BigDecimal.valueOf((Long) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (lossLeverOrderResultObject instanceof Integer) {
                    lossLeverOrder =  BigDecimal.valueOf((Integer) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    lossLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }



                QueryWrapper<TwHyorder> queryWrapper8 = new QueryWrapper<>();
                queryWrapper8.select("IFNULL(SUM(num), 0) as numLeverOrder")
                        .like("path", uid)
                        .eq("status", 2);

                List<Map<String, Object>> numLeverOrderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                if (numLeverOrderResult.isEmpty()) {
                    numLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object numLeverOrderObject = numLeverOrderResult.get(0).get("numHyOrder");
                if (numLeverOrderObject instanceof BigDecimal) {
                    numLeverOrder =  ((BigDecimal) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (numLeverOrderObject instanceof Long) {
                    numLeverOrder =  BigDecimal.valueOf((Long) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (numLeverOrderObject instanceof Integer) {
                    numLeverOrder =  BigDecimal.valueOf((Integer) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 处理其他可能的类型
                    numLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }


                BigDecimal totalWinOrder = winHyorder.add(winLeverOrder);  //用户总盈利
                BigDecimal totalLossOrder = lossHyorder.add(lossLeverOrder);  //用户总亏损
                BigDecimal totalnumOrder = numHyOrder.add(numLeverOrder);  //用户总投注

                twUserTeam.setRecharge(recharge);
                twUserTeam.setMyzc(myzc);
                twUserTeam.setTotalnumOrder(totalnumOrder);
                twUserTeam.setTotalWinOrder(totalWinOrder);
                twUserTeam.setTotalLossOrder(totalLossOrder);

                list1.add(twUserTeam);

            }
            objectPage.setRecords(list1);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
                teamVo.setDepartmentId(byId.getDepartmentId());
                List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
                for(TwUserTeam twUserTeam:listpage){
                    Integer uid = twUserTeam.getUid();
                    TwUser byId1 = twUserService.getById(uid);
                    if(byId1 == null){
                        twUserTeam.setUserCode(uid.toString());
                    }else{
                        String userCode = byId1.getUserCode();
                        twUserTeam.setUserCode(userCode);
                    }


                    BigDecimal recharge = new BigDecimal(0);
                    BigDecimal myzc = new BigDecimal(0);
                    QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
                    queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
                    if (rechargeResult.isEmpty()) {
                        recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object rechargeResultObject = rechargeResult.get(0).get("recharge");
                    if (rechargeResultObject instanceof BigDecimal) {
                        recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (rechargeResultObject instanceof Long) {
                        recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (rechargeResultObject instanceof Integer) {
                        recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
                    queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
                    if (myzcResult.isEmpty()) {
                        myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object myzcResultObject = myzcResult.get(0).get("myzc");
                    if (myzcResultObject instanceof BigDecimal) {
                        myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (myzcResultObject instanceof Long) {
                        myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (myzcResultObject instanceof Integer) {
                        myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }



                    BigDecimal winHyorder = new BigDecimal(0);
                    BigDecimal lossHyorder = new BigDecimal(0);
                    BigDecimal winLeverOrder = new BigDecimal(0);
                    BigDecimal lossLeverOrder = new BigDecimal(0);
                    BigDecimal numHyOrder = new BigDecimal(0);
                    BigDecimal numLeverOrder = new BigDecimal(0);

                    QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                            .eq("is_win", 1)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> winHyorderResult = this.twHyorderDao.selectMaps(queryWrapper);
                    if (winHyorderResult.isEmpty()) {
                        winHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object winHyorderObject = winHyorderResult.get(0).get("winHyorder");
                    if (winHyorderObject instanceof BigDecimal) {
                        winHyorder =  ((BigDecimal) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winHyorderObject instanceof Long) {
                        winHyorder =  BigDecimal.valueOf((Long) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winHyorderObject instanceof Integer) {
                        winHyorder =  BigDecimal.valueOf((Integer) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        winHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwHyorder> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.select("IFNULL(SUM(ploss), 0) as lossHyorder")
                            .eq("is_win", 2)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> lossHyorderResult = this.twHyorderDao.selectMaps(queryWrapper1);
                    if (lossHyorderResult.isEmpty()) {
                        lossHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object lossHyorderObject = lossHyorderResult.get(0).get("lossHyorder");
                    if (lossHyorderObject instanceof BigDecimal) {
                        lossHyorder =  ((BigDecimal) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossHyorderObject instanceof Long) {
                        lossHyorder =  BigDecimal.valueOf((Long) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossHyorderObject instanceof Integer) {
                        lossHyorder =  BigDecimal.valueOf((Integer) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        lossHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwHyorder> queryWrapper7 = new QueryWrapper<>();
                    queryWrapper7.select("IFNULL(SUM(num), 0) as numHyOrder")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> numHyorderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                    if (numHyorderResult.isEmpty()) {
                        numHyOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object numHyorderObject = numHyorderResult.get(0).get("numHyOrder");
                    if (numHyorderObject instanceof BigDecimal) {
                        numHyOrder =  ((BigDecimal) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numHyorderObject instanceof Long) {
                        numHyOrder =  BigDecimal.valueOf((Long) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numHyorderObject instanceof Integer) {
                        numHyOrder =  BigDecimal.valueOf((Integer) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        numHyOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwLeverOrder> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.select("IFNULL(SUM(ploss), 0) as winLeverOrder")
                            .eq("is_win", 1)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> winLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper2);
                    if (winLeverOrderResult.isEmpty()) {
                        winLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object winLeverOrderResultObject = winLeverOrderResult.get(0).get("winLeverOrder");
                    if (winLeverOrderResultObject instanceof BigDecimal) {
                        winLeverOrder =  ((BigDecimal) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winLeverOrderResultObject instanceof Long) {
                        winLeverOrder =  BigDecimal.valueOf((Long) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winLeverOrderResultObject instanceof Integer) {
                        winLeverOrder =  BigDecimal.valueOf((Integer) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        winLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwLeverOrder> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.select("IFNULL(SUM(ploss), 0) as lossLeverOrder")
                            .eq("is_win", 2)
                            .like("path", uid)
                            .eq("status", 2);


                    List<Map<String, Object>> lossLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper3);
                    if (lossLeverOrderResult.isEmpty()) {
                        lossLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object lossLeverOrderResultObject = lossLeverOrderResult.get(0).get("lossLeverOrder");
                    if (lossLeverOrderResultObject instanceof BigDecimal) {
                        lossLeverOrder =  ((BigDecimal) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossLeverOrderResultObject instanceof Long) {
                        lossLeverOrder =  BigDecimal.valueOf((Long) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossLeverOrderResultObject instanceof Integer) {
                        lossLeverOrder =  BigDecimal.valueOf((Integer) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        lossLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }



                    QueryWrapper<TwHyorder> queryWrapper8 = new QueryWrapper<>();
                    queryWrapper8.select("IFNULL(SUM(num), 0) as numLeverOrder")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> numLeverOrderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                    if (numLeverOrderResult.isEmpty()) {
                        numLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object numLeverOrderObject = numLeverOrderResult.get(0).get("numHyOrder");
                    if (numLeverOrderObject instanceof BigDecimal) {
                        numLeverOrder =  ((BigDecimal) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numLeverOrderObject instanceof Long) {
                        numLeverOrder =  BigDecimal.valueOf((Long) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numLeverOrderObject instanceof Integer) {
                        numLeverOrder =  BigDecimal.valueOf((Integer) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        numLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    BigDecimal totalWinOrder = winHyorder.add(winLeverOrder);  //用户总盈利
                    BigDecimal totalLossOrder = lossHyorder.add(lossLeverOrder);  //用户总亏损
                    BigDecimal totalnumOrder = numHyOrder.add(numLeverOrder);  //用户总投注

                    twUserTeam.setRecharge(recharge);
                    twUserTeam.setMyzc(myzc);
                    twUserTeam.setTotalnumOrder(totalnumOrder);
                    twUserTeam.setTotalWinOrder(totalWinOrder);
                    twUserTeam.setTotalLossOrder(totalLossOrder);

                    list1.add(twUserTeam);

                }
                objectPage.setRecords(list1);
                return objectPage;
            }else{
                Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
                if(inviteType == 1){
                    teamVo.setEmployeeId(byId.getEmployeeId());
                }
                List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
                for(TwUserTeam twUserTeam:listpage){
                    Integer uid = twUserTeam.getUid();
                    TwUser byId1 = twUserService.getById(uid);
                    if(byId1 == null){
                        twUserTeam.setUserCode(uid.toString());
                    }else{
                        String userCode = byId1.getUserCode();
                        twUserTeam.setUserCode(userCode);
                    }


                    BigDecimal recharge = new BigDecimal(0);
                    BigDecimal myzc = new BigDecimal(0);
                    QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
                    queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
                    if (rechargeResult.isEmpty()) {
                        recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object rechargeResultObject = rechargeResult.get(0).get("recharge");
                    if (rechargeResultObject instanceof BigDecimal) {
                        recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (rechargeResultObject instanceof Long) {
                        recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (rechargeResultObject instanceof Integer) {
                        recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
                    queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
                    if (myzcResult.isEmpty()) {
                        myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object myzcResultObject = myzcResult.get(0).get("myzc");
                    if (myzcResultObject instanceof BigDecimal) {
                        myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (myzcResultObject instanceof Long) {
                        myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (myzcResultObject instanceof Integer) {
                        myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }



                    BigDecimal winHyorder = new BigDecimal(0);
                    BigDecimal lossHyorder = new BigDecimal(0);
                    BigDecimal winLeverOrder = new BigDecimal(0);
                    BigDecimal lossLeverOrder = new BigDecimal(0);
                    BigDecimal numHyOrder = new BigDecimal(0);
                    BigDecimal numLeverOrder = new BigDecimal(0);

                    QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                            .eq("is_win", 1)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> winHyorderResult = this.twHyorderDao.selectMaps(queryWrapper);
                    if (winHyorderResult.isEmpty()) {
                        winHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object winHyorderObject = winHyorderResult.get(0).get("winHyorder");
                    if (winHyorderObject instanceof BigDecimal) {
                        winHyorder =  ((BigDecimal) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winHyorderObject instanceof Long) {
                        winHyorder =  BigDecimal.valueOf((Long) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winHyorderObject instanceof Integer) {
                        winHyorder =  BigDecimal.valueOf((Integer) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        winHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwHyorder> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.select("IFNULL(SUM(ploss), 0) as lossHyorder")
                            .eq("is_win", 2)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> lossHyorderResult = this.twHyorderDao.selectMaps(queryWrapper1);
                    if (lossHyorderResult.isEmpty()) {
                        lossHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object lossHyorderObject = lossHyorderResult.get(0).get("lossHyorder");
                    if (lossHyorderObject instanceof BigDecimal) {
                        lossHyorder =  ((BigDecimal) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossHyorderObject instanceof Long) {
                        lossHyorder =  BigDecimal.valueOf((Long) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossHyorderObject instanceof Integer) {
                        lossHyorder =  BigDecimal.valueOf((Integer) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        lossHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwHyorder> queryWrapper7 = new QueryWrapper<>();
                    queryWrapper7.select("IFNULL(SUM(num), 0) as numHyOrder")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> numHyorderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                    if (numHyorderResult.isEmpty()) {
                        numHyOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object numHyorderObject = numHyorderResult.get(0).get("numHyOrder");
                    if (numHyorderObject instanceof BigDecimal) {
                        numHyOrder =  ((BigDecimal) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numHyorderObject instanceof Long) {
                        numHyOrder =  BigDecimal.valueOf((Long) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numHyorderObject instanceof Integer) {
                        numHyOrder =  BigDecimal.valueOf((Integer) numHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        numHyOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwLeverOrder> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.select("IFNULL(SUM(ploss), 0) as winLeverOrder")
                            .eq("is_win", 1)
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> winLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper2);
                    if (winLeverOrderResult.isEmpty()) {
                        winLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object winLeverOrderResultObject = winLeverOrderResult.get(0).get("winLeverOrder");
                    if (winLeverOrderResultObject instanceof BigDecimal) {
                        winLeverOrder =  ((BigDecimal) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winLeverOrderResultObject instanceof Long) {
                        winLeverOrder =  BigDecimal.valueOf((Long) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (winLeverOrderResultObject instanceof Integer) {
                        winLeverOrder =  BigDecimal.valueOf((Integer) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        winLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    QueryWrapper<TwLeverOrder> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.select("IFNULL(SUM(ploss), 0) as lossLeverOrder")
                            .eq("is_win", 2)
                            .like("path", uid)
                            .eq("status", 2);


                    List<Map<String, Object>> lossLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper3);
                    if (lossLeverOrderResult.isEmpty()) {
                        lossLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object lossLeverOrderResultObject = lossLeverOrderResult.get(0).get("lossLeverOrder");
                    if (lossLeverOrderResultObject instanceof BigDecimal) {
                        lossLeverOrder =  ((BigDecimal) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossLeverOrderResultObject instanceof Long) {
                        lossLeverOrder =  BigDecimal.valueOf((Long) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (lossLeverOrderResultObject instanceof Integer) {
                        lossLeverOrder =  BigDecimal.valueOf((Integer) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        lossLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }



                    QueryWrapper<TwHyorder> queryWrapper8 = new QueryWrapper<>();
                    queryWrapper8.select("IFNULL(SUM(num), 0) as numLeverOrder")
                            .like("path", uid)
                            .eq("status", 2);

                    List<Map<String, Object>> numLeverOrderResult = this.twHyorderDao.selectMaps(queryWrapper7);
                    if (numLeverOrderResult.isEmpty()) {
                        numLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }

                    Object numLeverOrderObject = numLeverOrderResult.get(0).get("numHyOrder");
                    if (numLeverOrderObject instanceof BigDecimal) {
                        numLeverOrder =  ((BigDecimal) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numLeverOrderObject instanceof Long) {
                        numLeverOrder =  BigDecimal.valueOf((Long) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else if (numLeverOrderObject instanceof Integer) {
                        numLeverOrder =  BigDecimal.valueOf((Integer) numLeverOrderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 处理其他可能的类型
                        numLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }


                    BigDecimal totalWinOrder = winHyorder.add(winLeverOrder);  //用户总盈利
                    BigDecimal totalLossOrder = lossHyorder.add(lossLeverOrder);  //用户总亏损
                    BigDecimal totalnumOrder = numHyOrder.add(numLeverOrder);  //用户总投注

                    twUserTeam.setRecharge(recharge);
                    twUserTeam.setMyzc(myzc);
                    twUserTeam.setTotalnumOrder(totalnumOrder);
                    twUserTeam.setTotalWinOrder(totalWinOrder);
                    twUserTeam.setTotalLossOrder(totalLossOrder);
                    list1.add(twUserTeam);
                }
                objectPage.setRecords(list1);
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public IPage<TwUser> teamlist(TeamVo teamVo, HttpServletRequest request) {
        List<TwUser> list1 = new ArrayList<>();
        Page<TwUser> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
        for (TwUser twUser : baseMapper.teamlist(objectPage, teamVo)) {
            BigDecimal recharge = new BigDecimal(0);
            BigDecimal myzc = new BigDecimal(0);
            Integer uid = twUser.getId();

            QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                    .eq("uid", uid)
                    .eq("status", 2);

            List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
            if (rechargeResult.isEmpty()) {
                recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            Object rechargeResultObject = rechargeResult.get(0).get("recharge");
            if (rechargeResultObject instanceof BigDecimal) {
                recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (rechargeResultObject instanceof Long) {
                recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (rechargeResultObject instanceof Integer) {
                recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                // 处理其他可能的类型
                recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                    .eq("userid", uid)
                    .eq("status", 2);

            List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
            if (myzcResult.isEmpty()) {
                myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            Object myzcResultObject = myzcResult.get(0).get("myzc");
            if (myzcResultObject instanceof BigDecimal) {
                myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (myzcResultObject instanceof Long) {
                myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (myzcResultObject instanceof Integer) {
                myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                // 处理其他可能的类型
                myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            twUser.setRecharge(recharge);
            twUser.setMyzc(myzc);

            list1.add(twUser);
        }

        objectPage.setRecords(list1);
        return objectPage;
    }
}




