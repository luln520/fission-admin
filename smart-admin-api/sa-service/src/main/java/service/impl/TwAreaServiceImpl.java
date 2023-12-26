package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwArea;
import net.lab1024.sa.admin.module.dao.TwAreaDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAreaService;

import javax.annotation.Resource;

/**
 * (TwArea)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Service("twAreaService")
public class TwAreaServiceImpl extends ServiceImpl<TwAreaDao, TwArea> implements TwAreaService {

}
