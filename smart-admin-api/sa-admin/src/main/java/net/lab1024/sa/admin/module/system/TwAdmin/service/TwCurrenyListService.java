package net.lab1024.sa.admin.module.system.TwAdmin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrenyList;
import net.lab1024.sa.common.common.domain.ResponseDTO;

/**
* @author 1
* @description 针对表【tw_curreny_list(货币汇率总表)】的数据库操作Service
* @createDate 2024-05-02 14:38:59
*/
public interface TwCurrenyListService extends IService<TwCurrenyList> {

    ResponseDTO add();
}
