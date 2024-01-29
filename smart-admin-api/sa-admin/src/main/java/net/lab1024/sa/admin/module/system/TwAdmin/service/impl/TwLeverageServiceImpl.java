package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwLeverageMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverageService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_leverage(杠杆倍数)】的数据库操作Service实现
* @createDate 2024-01-27 12:45:43
*/
@Service
public class TwLeverageServiceImpl extends ServiceImpl<TwLeverageMapper, TwLeverage>
    implements TwLeverageService {

    @Override
    public IPage<TwLeverage> listpage(LeverVo leverVo) {
        Page<TwLeverage> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, leverVo));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwLeverage twLeverage) {
        twLeverage.setCreateTime(new Date());
        return this.saveOrUpdate(twLeverage);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwLeverage find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO<List<TwLeverage>> getTwLeverage(String symbol) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("symbol",symbol);
        return ResponseDTO.ok(this.list(queryWrapper));
    }

}




