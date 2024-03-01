package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCompanyMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.department.domain.entity.DepartmentEntity;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.manager.EmployeeManager;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_company(公司表)】的数据库操作Service实现
* @createDate 2024-02-15 17:42:54
*/
@Service
@Slf4j
public class TwCompanyServiceImpl extends ServiceImpl<TwCompanyMapper, TwCompany> implements TwCompanyService {

    private static final String PASSWORD_SALT_FORMAT = "smart_%s_admin_$^&*";

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeManager employeeManager;
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
    public ResponseDTO addOrUpdate(TwCompany twCompany) {

        // 校验名称是否重复
        if(twCompany.getId() == null){
            EmployeeEntity employeeEntity = employeeDao.getByLoginName(twCompany.getCompanyAccount(), null);
            if (null != employeeEntity) {
                return ResponseDTO.userErrorParam("账号重复");
            }
        }
        String encryptPwd = getEncryptPwd(twCompany.getCompanyPwd());
        twCompany.setCompanyPwd(encryptPwd);
        TwCompany twCompany1 = SmartBeanUtil.copy(twCompany, TwCompany.class);

        if(twCompany.getId() == null){
            EmployeeEntity entity  = new EmployeeEntity();
            // 设置密码 默认密码
            entity.setLoginPwd(encryptPwd);
            entity.setLoginName(twCompany.getCompanyAccount());
            entity.setCompanyId(twCompany1.getId());
            entity.setDeletedFlag(Boolean.FALSE);
            entity.setActualName(twCompany.getCompanyAccount());
            entity.setAdministratorFlag(Boolean.TRUE);
            entity.setDisabledFlag(Boolean.FALSE);
            entity.setCreateTime(LocalDateTime.now());

            List<Long> roleIdList = new ArrayList<>();
            roleIdList.add(1L);
            employeeManager.saveEmployee(entity, roleIdList);

            this.save(twCompany1);
        }else{
            this.updateById(twCompany);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.like("login_name",twCompany.getCompanyAccount());
            EmployeeEntity one = employeeManager.getOne(queryWrapper);
            // 设置密码 默认密码
            one.setLoginPwd(encryptPwd);
            one.setLoginName(twCompany.getCompanyAccount());
            one.setActualName(twCompany.getCompanyAccount());
            one.setUpdateTime(LocalDateTime.now());

            List<Long> roleIdList = new ArrayList<>();
            roleIdList.add(1L);
            employeeManager.updateEmployee(one,roleIdList);
        }

        return ResponseDTO.ok();
    }

    @Override
    public TwCompany companyDomain(String  domain) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("company_domain",domain);
        return this.getOne(queryWrapper);
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

    /**
     * 获取 加密后 的密码
     *
     * @param password
     * @return
     */
    public static String getEncryptPwd(String password) {
        return DigestUtils.md5Hex(String.format(PASSWORD_SALT_FORMAT, password));
    }
}




