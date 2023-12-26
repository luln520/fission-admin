package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwIssue;
import net.lab1024.sa.admin.module.dao.TwIssueDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwIssueService;

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
