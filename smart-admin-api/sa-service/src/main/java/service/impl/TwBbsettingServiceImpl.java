package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwBbsetting;
import net.lab1024.sa.admin.module.dao.TwBbsettingDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwBbsettingService;

import javax.annotation.Resource;

/**
 * 币币交易设置(TwBbsetting)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
@Service("twBbsettingService")
public class TwBbsettingServiceImpl extends ServiceImpl<TwBbsettingDao, TwBbsetting> implements TwBbsettingService {

}
