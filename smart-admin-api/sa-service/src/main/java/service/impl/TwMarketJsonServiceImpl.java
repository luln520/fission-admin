package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwMarketJson;
import net.lab1024.sa.admin.module.dao.TwMarketJsonDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwMarketJsonService;

import javax.annotation.Resource;

/**
 * (TwMarketJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@Service("twMarketJsonService")
public class TwMarketJsonServiceImpl extends ServiceImpl<TwMarketJsonDao, TwMarketJson> implements TwMarketJsonService {

}
