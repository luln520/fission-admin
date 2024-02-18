package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwLeverSetMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverSetService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_lever_set(杠杆止盈止损设置)】的数据库操作Service实现
* @createDate 2024-01-27 12:45:59
*/
@Service
public class TwLeverSetServiceImpl extends ServiceImpl<TwLeverSetMapper, TwLeverSet>
    implements TwLeverSetService {


    @Override
    public IPage<TwLeverSet> listpage(LeverVo leverVo) {
        Page<TwLeverSet> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, leverVo));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwLeverSet twLeverSet) {
        twLeverSet.setCreateTime(new Date());
        return this.saveOrUpdate(twLeverSet);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwLeverSet find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO<List<TwLeverSet>> getTwLeverSet(String symbol, int type,int companyId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("symbol",symbol);
        queryWrapper.eq("type",type);
        queryWrapper.eq("company_id",companyId);
        return ResponseDTO.ok(this.list(queryWrapper));
    }
}




