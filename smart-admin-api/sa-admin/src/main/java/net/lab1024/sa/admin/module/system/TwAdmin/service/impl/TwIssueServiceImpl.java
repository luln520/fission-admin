package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwIssueDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwIssueLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssue;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssueLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwIssueLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwIssueService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 认购发行表(TwIssue)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:47
 */
@Service("twIssueService")
public class TwIssueServiceImpl extends ServiceImpl<TwIssueDao, TwIssue> implements TwIssueService {

}
