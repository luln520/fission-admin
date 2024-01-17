package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.HyorderOneRes;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合约订单表(TwHyorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
public interface TwHyorderService extends IService<TwHyorder> {

    int countUnClosedOrders();

    TwHyorder hyorderId(int id);

    ResponseDTO<HyorderOneRes> getHyorderOne(int id);

    ResponseDTO<List<TwHyorder>> gethyorder(int uid);

    ResponseDTO<List<TwHyorder>> cbillList(int uid);

    ResponseDTO<List<TwHyorder>> contractTy(int uid,int type);

    ResponseDTO<TwHyorder> cbillinfo(int uid,int id);

    IPage<TwHyorder> listpage(TwHyorderVo twHyorderVo, HttpServletRequest request);

    boolean editKongyK(Integer kongyk, int id);

    ResponseDTO creatorder(int uid,int ctime,BigDecimal ctzed, String ccoinname,int ctzfx,BigDecimal cykbl,String language);

}
