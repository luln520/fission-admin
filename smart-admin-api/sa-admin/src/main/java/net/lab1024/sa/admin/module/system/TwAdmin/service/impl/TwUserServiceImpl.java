package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserQianbaoDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户信息表(TwUser)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */




@Service("twUserService")
public class TwUserServiceImpl extends ServiceImpl<TwUserDao, TwUser> implements TwUserService {

    @Override
    public Integer countAllUsers() {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }
}
