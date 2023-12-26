package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwFooter;
import net.lab1024.sa.admin.module.dao.TwFooterDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwFooterService;

import javax.annotation.Resource;

/**
 * (TwFooter)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
@Service("twFooterService")
public class TwFooterServiceImpl extends ServiceImpl<TwFooterDao, TwFooter> implements TwFooterService {

}
