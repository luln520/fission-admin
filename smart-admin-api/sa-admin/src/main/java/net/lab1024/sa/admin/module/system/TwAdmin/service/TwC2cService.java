package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2cBank;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.C2CVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_c2c】的数据库操作Service
* @createDate 2024-02-25 20:52:34
*/
public interface TwC2cService extends IService<TwC2c> {

    IPage<TwC2c> listpage(C2CVo c2CVo, HttpServletRequest request);
    ResponseDTO info(int id);

    ResponseDTO rejectCoin(int id);

    ResponseDTO confirmCoin(int id);

    ResponseDTO reject(int id);

    ResponseDTO confirm(int id);

    ResponseDTO bankInfo(TwC2cBank c2cBank);

    ResponseDTO getBankInfo(String orderno);

    ResponseDTO cz(int  uid,  int currenyId,  BigDecimal num,  int bankType,String language);

    ResponseDTO czImg(String orderNo,String img,String language,String transferName);

    ResponseDTO c2ctx(TwC2cBank twC2cBank);

    ResponseDTO<List<TwC2c>> czList(int type, int uid);

    ResponseDTO<TwC2c> info(String orderNo);



}
