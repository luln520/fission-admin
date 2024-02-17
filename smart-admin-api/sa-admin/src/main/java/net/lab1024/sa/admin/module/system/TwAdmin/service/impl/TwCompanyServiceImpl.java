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
import net.lab1024.sa.admin.module.system.department.domain.entity.DepartmentEntity;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.manager.EmployeeManager;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_company(公司表)】的数据库操作Service实现
* @createDate 2024-02-15 17:42:54
*/
@Service
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

        this.saveOrUpdate(twCompany);

        // 校验名称是否重复
        EmployeeEntity employeeEntity = employeeDao.getByLoginName(twCompany.getCompanyAccount(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("账号重复");
        }

        EmployeeEntity entity  = new EmployeeEntity();
        // 设置密码 默认密码
        String password = twCompany.getCompanyPwd();
        entity.setLoginPwd(getEncryptPwd(password));
        entity.setLoginName(twCompany.getCompanyAccount());
        entity.setCompanyId(twCompany.getId());
        entity.setDeletedFlag(Boolean.FALSE);
        entity.setAdministratorFlag(Boolean.TRUE);
        entity.setDisabledFlag(Boolean.FALSE);
        employeeManager.saveEmployee(entity, null);

        return ResponseDTO.ok(password);
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




