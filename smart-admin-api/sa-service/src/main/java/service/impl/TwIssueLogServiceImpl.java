package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwIssueLog;
import net.lab1024.sa.admin.module.dao.TwIssueLogDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwIssueLogService;

import javax.annotation.Resource;

/**
 * 认购记录表(TwIssueLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@Service("twIssueLogService")
public class TwIssueLogServiceImpl extends ServiceImpl<TwIssueLogDao, TwIssueLog> implements TwIssueLogService {

}
