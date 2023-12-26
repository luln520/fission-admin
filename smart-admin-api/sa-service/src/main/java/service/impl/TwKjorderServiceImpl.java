package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwKjorder;
import net.lab1024.sa.admin.module.dao.TwKjorderDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwKjorderService;

import javax.annotation.Resource;

/**
 * 矿机订单表(TwKjorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Service("twKjorderService")
public class TwKjorderServiceImpl extends ServiceImpl<TwKjorderDao, TwKjorder> implements TwKjorderService {

}
