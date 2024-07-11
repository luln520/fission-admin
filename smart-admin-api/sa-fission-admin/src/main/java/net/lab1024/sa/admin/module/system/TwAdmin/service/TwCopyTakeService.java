package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_copy_take(接单员表)】的数据库操作Service
* @createDate 2024-07-09 15:15:00
*/
public interface TwCopyTakeService extends IService<TwCopyTake> {

    IPage<TwCopyTake> listpage(TwUserVo twUserVo, HttpServletRequest request);

    ResponseDTO win(String orderNo,int type);

    ResponseDTO loss(String orderNo,int type);
}
