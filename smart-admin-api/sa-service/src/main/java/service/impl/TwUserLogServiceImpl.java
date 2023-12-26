package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUserLog;
import net.lab1024.sa.admin.module.dao.TwUserLogDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserLogService;

import javax.annotation.Resource;

/**
 * 用户记录表(TwUserLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Service("twUserLogService")
public class TwUserLogServiceImpl extends ServiceImpl<TwUserLogDao, TwUserLog> implements TwUserLogService {

}
