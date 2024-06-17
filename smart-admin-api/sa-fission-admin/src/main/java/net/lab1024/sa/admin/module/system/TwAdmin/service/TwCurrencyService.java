package net.lab1024.sa.admin.module.system.TwAdmin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrency;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;

/**
* @author 1
* @description 针对表【tw_currency(货币表)】的数据库操作Service
* @createDate 2024-04-10 16:25:03
*/
public interface TwCurrencyService extends IService<TwCurrency> {

    IPage<TwCurrency> listpage(CompanyVo companyVo);

    ResponseDTO lists(int companyId);

    TwCurrency detail(int id);

    ResponseDTO addOrUpdate(TwCurrency twCurrency);


    boolean delete(int id);
}
