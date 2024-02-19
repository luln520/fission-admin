package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_company(公司表)】的数据库操作Service
* @createDate 2024-02-15 17:42:54
*/
public interface TwCompanyService extends IService<TwCompany> {
    IPage<TwCompany> listpage(CompanyVo companyVo);

    TwCompany detail(int id);

    ResponseDTO addOrUpdate(TwCompany twCompany);

    TwCompany companyDomain(String  domain);

    boolean updateStatus(int id, Integer status);

    boolean delete(int id);
}
