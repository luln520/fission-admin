package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_copy_order(跟单表)】的数据库操作Service
* @createDate 2024-07-09 15:14:42
*/
public interface TwCopyOrderService extends IService<TwCopyOrder> {
    IPage<TwCopyOrder> listpage(TwUserVo twUserVo, HttpServletRequest request);
}
