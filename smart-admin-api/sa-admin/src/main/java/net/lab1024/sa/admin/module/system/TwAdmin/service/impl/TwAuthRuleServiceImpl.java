package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthRuleDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthRule;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthRuleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwAuthRule)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:36
 */
@Service("twAuthRuleService")
public class TwAuthRuleServiceImpl extends ServiceImpl<TwAuthRuleDao, TwAuthRule> implements TwAuthRuleService {

}
