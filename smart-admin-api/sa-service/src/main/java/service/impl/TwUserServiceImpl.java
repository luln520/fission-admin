package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUser;
import net.lab1024.sa.admin.module.dao.TwUserDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserService;

import javax.annotation.Resource;

/**
 * 用户信息表(TwUser)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */




@Service("twUserService")
public class TwUserServiceImpl extends ServiceImpl<TwUserDao, TwUser> implements TwUserService {

}
