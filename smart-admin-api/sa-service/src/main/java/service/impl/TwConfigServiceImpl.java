package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwConfig;
import net.lab1024.sa.admin.module.dao.TwConfigDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwConfigService;

import javax.annotation.Resource;

/**
 * 网站配置表(TwConfig)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
@Service("twConfigService")
public class TwConfigServiceImpl extends ServiceImpl<TwConfigDao, TwConfig> implements TwConfigService {

}
