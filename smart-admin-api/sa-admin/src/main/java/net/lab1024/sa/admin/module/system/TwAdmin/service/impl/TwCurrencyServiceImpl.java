package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCurrencyMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrency;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCurrencyService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 1
* @description 针对表【tw_currency(货币表)】的数据库操作Service实现
* @createDate 2024-04-10 16:25:03
*/
@Service
public class TwCurrencyServiceImpl extends ServiceImpl<TwCurrencyMapper, TwCurrency>
implements TwCurrencyService {

    @Override
    public IPage<TwCurrency> listpage(CompanyVo companyVo) {
            Page<TwCurrency> objectPage = new Page<>(companyVo.getPageNum(), companyVo.getPageSize());
            objectPage.setRecords(this.baseMapper.listpage(objectPage, companyVo));
            return objectPage;
    }

    @Override
    public ResponseDTO lists(int companyId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("company_id",companyId);
        return ResponseDTO.ok(this.list(queryWrapper));
    }

    @Override
    public TwCurrency detail(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO addOrUpdate(TwCurrency twCurrency) {
        if(twCurrency.getId() == null){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("currency",twCurrency.getCurrency());
            queryWrapper.eq("company_id",twCurrency.getCompanyId());
            TwCurrency one = this.getOne(queryWrapper);
            if(one != null){
                return ResponseDTO.userErrorParam("货币已存在");
            }
            twCurrency.setCreateTime(new Date());
            this.save(twCurrency);
            return ResponseDTO.ok();
        }else{
            this.updateById(twCurrency);
            return ResponseDTO.ok();
        }
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }
}
