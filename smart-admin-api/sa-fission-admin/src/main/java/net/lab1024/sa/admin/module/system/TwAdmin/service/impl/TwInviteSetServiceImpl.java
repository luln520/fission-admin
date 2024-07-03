package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwInviteSetMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwInviteSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwInviteSetService;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_invite_set(邀请规则设置)】的数据库操作Service实现
* @createDate 2024-07-03 14:47:22
*/
@Service
public class TwInviteSetServiceImpl extends ServiceImpl<TwInviteSetMapper, TwInviteSet>
    implements TwInviteSetService {

    @Override
    public IPage<TwInviteSet> listpage(PageParam pageParam) {
        Page<TwInviteSet> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwInviteSet twInviteSet) {
        twInviteSet.setCreateTime(new Date());
        return this.saveOrUpdate(twInviteSet);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwInviteSet find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO<List<TwInviteSet>> getTwLeverSet(int companyId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("company_id",companyId);
        queryWrapper.orderByAsc("sort");
        return ResponseDTO.ok(this.list(queryWrapper));
    }
}




