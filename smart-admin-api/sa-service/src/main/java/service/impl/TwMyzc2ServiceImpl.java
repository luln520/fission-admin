package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwMyzc2;
import net.lab1024.sa.admin.module.dao.TwMyzc2Dao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwMyzc2Service;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc2)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:52
 */
@Service("twMyzc2Service")
public class TwMyzc2ServiceImpl extends ServiceImpl<TwMyzc2Dao, TwMyzc2> implements TwMyzc2Service {

}
