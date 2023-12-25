package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBborderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBborder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBborderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
