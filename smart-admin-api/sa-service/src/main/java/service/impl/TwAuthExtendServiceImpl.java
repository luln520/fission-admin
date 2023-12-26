package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwAuthExtend;
import net.lab1024.sa.admin.module.dao.TwAuthExtendDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAuthExtendService;

import javax.annotation.Resource;

/**
 * (TwAuthExtend)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
@Service("twAuthExtendService")
public class TwAuthExtendServiceImpl extends ServiceImpl<TwAuthExtendDao, TwAuthExtend> implements TwAuthExtendService {

}
