package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTyhyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户币种表(TwUserCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Service("twUserCoinService")
public class TwUserCoinServiceImpl extends ServiceImpl<TwUserCoinDao, TwUserCoin> implements TwUserCoinService {

    @Override
    public int incre(Integer uid, double num) {
        return this.baseMapper.incre(uid,num);
    }
}
