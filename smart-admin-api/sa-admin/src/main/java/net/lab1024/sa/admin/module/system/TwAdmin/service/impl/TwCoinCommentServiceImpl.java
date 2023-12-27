package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinCommentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwCoinComment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:50
 */
@Service("twCoinCommentService")
public class TwCoinCommentServiceImpl extends ServiceImpl<TwCoinCommentDao,TwCoinComment> implements TwCoinCommentService {

}
