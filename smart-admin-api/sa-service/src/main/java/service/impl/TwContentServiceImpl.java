package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwContent;
import net.lab1024.sa.admin.module.dao.TwContentDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwContentService;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Service("twContentService")
public class TwContentServiceImpl extends ServiceImpl<TwContentDao, TwContent> implements TwContentService {

}
