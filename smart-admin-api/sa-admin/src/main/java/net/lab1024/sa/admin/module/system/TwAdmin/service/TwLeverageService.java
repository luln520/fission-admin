package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_leverage(杠杆倍数)】的数据库操作Service
* @createDate 2024-01-27 12:45:43
*/
public interface TwLeverageService extends IService<TwLeverage> {
    IPage<TwLeverage> listpage(LeverVo leverVo);

    boolean addOrUpdate(TwLeverage twLeverage);

    boolean delete(int id);

    TwLeverage find(int id);

    ResponseDTO<List<TwLeverage>> getTwLeverage(String symbol);

}
