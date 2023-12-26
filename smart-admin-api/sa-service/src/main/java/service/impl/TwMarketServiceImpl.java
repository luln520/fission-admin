package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwMarket;
import net.lab1024.sa.admin.module.dao.TwMarketDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwMarketService;

import javax.annotation.Resource;

/**
 * 行情配置表(TwMarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
@Service("twMarketService")
public class TwMarketServiceImpl extends ServiceImpl<TwMarketDao, TwMarket> implements TwMarketService {

}
