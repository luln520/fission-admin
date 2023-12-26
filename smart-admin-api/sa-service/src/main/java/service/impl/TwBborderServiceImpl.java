package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwBborder;
import net.lab1024.sa.admin.module.dao.TwBborderDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwBborderService;

import javax.annotation.Resource;

/**
 * 币币交易记录(TwBborder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:54
 */
@Service("twBborderService")
public class TwBborderServiceImpl extends ServiceImpl<TwBborderDao, TwBborder> implements TwBborderService {

}
