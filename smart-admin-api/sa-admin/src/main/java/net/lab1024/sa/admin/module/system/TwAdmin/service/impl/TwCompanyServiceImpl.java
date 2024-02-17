package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCompanyMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_company(公司表)】的数据库操作Service实现
* @createDate 2024-02-15 17:42:54
*/
@Service
public class TwCompanyServiceImpl extends ServiceImpl<TwCompanyMapper, TwCompany> implements TwCompanyService {

    @Override
    public IPage<TwCompany> listpage(CompanyVo companyVo) {
        Page<TwCompany> objectPage = new Page<>(companyVo.getPageNum(), companyVo.getPageSize());
        objectPage.setRecords(this.baseMapper.listpage(objectPage, companyVo));
        return objectPage;
    }

    @Override
    public TwCompany detail(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean addOrUpdate(TwCompany twCompany) {
            return this.saveOrUpdate(twCompany);
    }

    @Override
    public boolean updateStatus(int id, Integer status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCompany one = this.getOne(queryWrapper);
        one.setStatus(status);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCompany one = this.getOne(queryWrapper);
        one.setIsDel(2);
        return this.updateById(one);
    }
}




