package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwTradeJson;
import net.lab1024.sa.admin.module.dao.TwTradeJsonDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwTradeJsonService;

import javax.annotation.Resource;

/**
 * 交易图表表(TwTradeJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:01
 */
@Service("twTradeJsonService")
public class TwTradeJsonServiceImpl extends ServiceImpl<TwTradeJsonDao, TwTradeJson> implements TwTradeJsonService {

}
