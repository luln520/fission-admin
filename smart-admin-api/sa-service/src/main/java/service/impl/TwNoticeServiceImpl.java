package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwNotice;
import net.lab1024.sa.admin.module.dao.TwNoticeDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwNoticeService;

import javax.annotation.Resource;

/**
 * 通知表(TwNotice)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Service("twNoticeService")
public class TwNoticeServiceImpl extends ServiceImpl<TwNoticeDao, TwNotice> implements TwNoticeService {

}
