package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_lever_set(杠杆止盈止损设置)】的数据库操作Service
* @createDate 2024-01-27 12:45:59
*/
public interface TwLeverSetService extends IService<TwLeverSet> {
    IPage<TwLeverSet> listpage(LeverVo leverVo);

    boolean addOrUpdate(TwLeverSet twLeverSet);

    boolean delete(int id);

    TwLeverSet find(int id);

    ResponseDTO<List<TwLeverSet>> getTwLeverSet(String symbol,int type,int companyId);
}
