package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
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
public class TwCoinServiceImpl extends ServiceImpl<TwCoinDao, TwCoin> implements TwCoinService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwAddressService twAddressService;

    @Override
    public IPage<TwCoin> listpage(PageParam pageParam) {
        Page<TwCoin> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public List<TwCoin> lists( int companyId, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("txstatus",1);
        queryWrapper.eq("company_id",companyId);

        List<TwCoin> twCoinList = this.list(queryWrapper);


        for(TwCoin twCoin : twCoinList) {
            if(twCoin.getCzline().equals(NetworkConst.ETH)) {
                twCoin.setCzaddress(twAddressService.createAddress(uidToken.intValue(), 60, twCoin.getId()));
            }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                twCoin.setCzaddress(twAddressService.createAddress(uidToken.intValue(), 195, twCoin.getId()));
            }else {
                twCoin.setCzaddress(twAddressService.createAddress(uidToken.intValue(), 0, twCoin.getId()));
            }
        }
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
    public boolean addOrUpdate(TwCoin twCoin) {
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
