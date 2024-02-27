package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.C2CVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_c2c】的数据库操作Service
* @createDate 2024-02-25 20:52:34
*/
public interface TwC2cService extends IService<TwC2c> {

    IPage<TwC2c> listpage(C2CVo c2CVo, HttpServletRequest request);
    ResponseDTO rejectCoin(int id);

    ResponseDTO confirmCoin(int id);

    ResponseDTO reject(int id);

    ResponseDTO confirm(int id);

}
