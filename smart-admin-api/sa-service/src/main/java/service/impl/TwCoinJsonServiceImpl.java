package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwCoinJson;
import net.lab1024.sa.admin.module.dao.TwCoinJsonDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwCoinJsonService;

import javax.annotation.Resource;

/**
 * (TwCoinJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:02
 */
@Service("twCoinJsonService")
public class TwCoinJsonServiceImpl extends ServiceImpl<TwCoinJsonDao, TwCoinJson> implements TwCoinJsonService {

}
