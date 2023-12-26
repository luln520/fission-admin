package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwCtmarket;
import net.lab1024.sa.admin.module.dao.TwCtmarketDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwCtmarketService;

import javax.annotation.Resource;

/**
 * 合约交易对配置(TwCtmarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Service("twCtmarketService")
public class TwCtmarketServiceImpl extends ServiceImpl<TwCtmarketDao, TwCtmarket> implements TwCtmarketService {

}
