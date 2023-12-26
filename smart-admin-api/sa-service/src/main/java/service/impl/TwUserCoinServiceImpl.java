package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUserCoin;
import net.lab1024.sa.admin.module.dao.TwUserCoinDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserCoinService;

import javax.annotation.Resource;

/**
 * 用户币种表(TwUserCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Service("twUserCoinService")
public class TwUserCoinServiceImpl extends ServiceImpl<TwUserCoinDao, TwUserCoin> implements TwUserCoinService {

}
