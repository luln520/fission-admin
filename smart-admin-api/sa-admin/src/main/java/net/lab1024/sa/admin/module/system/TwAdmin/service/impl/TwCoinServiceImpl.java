package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 币种配置表(TwCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Service("twCoinService")
public class TwCoinServiceImpl extends ServiceImpl<TwCoinDao, TwCoin> implements TwCoinService {

}
