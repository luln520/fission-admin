package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUserQianbao;
import net.lab1024.sa.admin.module.dao.TwUserQianbaoDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserQianbaoService;

import javax.annotation.Resource;

/**
 * 用户钱包表(TwUserQianbao)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Service("twUserQianbaoService")
public class TwUserQianbaoServiceImpl extends ServiceImpl<TwUserQianbaoDao, TwUserQianbao> implements TwUserQianbaoService {

}
