package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.SMS.SendSmsLib;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.wallet.NetworkConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 币种配置表(TwCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Service("twCoinService")
@Slf4j
public class TwCoinServiceImpl extends ServiceImpl<TwCoinDao, TwCoin> implements TwCoinService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TwAdminLogService twAdminLogService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwAddressService twAddressService;

    @Autowired
    private TwUserService twUserService;

    @Override
    public IPage<TwCoin> listpage(PageParam pageParam) {
        Page<TwCoin> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public List<TwCoin> lists( String userCode, int companyId, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
//        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
//        Long uidToken = tokenService.getUIDToken(xHeaderToken);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("txstatus",1);
        queryWrapper.eq("company_id",companyId);

        List<TwCoin> twCoinList = this.list(queryWrapper);

        /*QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_code", userCode);
        TwUser twUser = twUserService.getOne(queryWrapper2);

        for(TwCoin twCoin : twCoinList) {
            if(twCoin.getCzline().equals(NetworkConst.ETH)) {
                twCoin.setCzaddress(twAddressService.createAddress(twUser.getId(), 60, twCoin.getId()));
            }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                twCoin.setCzaddress(twAddressService.createAddress(twUser.getId(), 195, twCoin.getId()));
            }else {
                twCoin.setCzaddress(twAddressService.createAddress(twUser.getId(), 0, twCoin.getId()));
            }
        }*/
        /*for(TwCoin twCoin : twCoinList) {
            if(StringUtils.isNotEmpty(twCoin.getCzaddress1()) && StringUtils.isNotEmpty(twCoin.getCzaddress2())) {
                String[] addresses = {twCoin.getCzaddress(), twCoin.getCzaddress1(), twCoin.getCzaddress2()};
                Random random = new Random();
                int index = random.nextInt(addresses.length);
                twCoin.setCzaddress(addresses[index]);
            }
        }*/
        return twCoinList;
    }

    @Override
    public boolean addOrUpdate(TwCoin twCoin, HttpServletRequest request)  {
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);

        TwAdminLog twAdminLog = new TwAdminLog();
        twAdminLog.setCompanyId(byId.getCompanyId());
        twAdminLog.setAdminId((int) byId.getEmployeeId());
        twAdminLog.setAdminUsername(byId.getActualName());
        twAdminLog.setAction("修改地址成功");
        long timestampInSeconds = Instant.now().getEpochSecond();
        twAdminLog.setCreateTime((int) timestampInSeconds);
        twAdminLog.setRemark("修改地址成功用户："+byId.getActualName() +"修改地址为"+twCoin.getCzaddress());
        twAdminLogService.save(twAdminLog);
//        twCoin.setUpdateTime(new Date());

        int companyId = byId.getCompanyId();
        List<EmployeeEntity> alladmin = employeeService.getAlladmin(companyId);
        if(alladmin.size() > 0){
            for(EmployeeEntity list:alladmin){
                String phone = list.getPhone();
                if(StringUtils.isNotEmpty(phone)){
                    try {
                        SendSmsLib.phoneaddress(phone,twCoin.getCzaddress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    log.info("地址修改验证码发送{]:"+phone);
                }
            }
        }
        log.info("修改地址成功用户name{},地址{}",byId.getActualName(),twCoin.getCzaddress());
        return this.saveOrUpdate(twCoin);
    }

    @Override
    public boolean updateStatus(int id, Integer status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCoin one = this.getOne(queryWrapper);
        one.setStatus(status);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwCoin find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }
}
