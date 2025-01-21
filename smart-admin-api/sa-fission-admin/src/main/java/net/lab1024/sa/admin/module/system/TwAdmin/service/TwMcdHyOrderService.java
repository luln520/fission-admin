package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdHyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface TwMcdHyOrderService {

    IPage<TwMcdHyOrder> listpage(TwHyorderVo twHyorderVo, HttpServletRequest request);

    ResponseDTO creatorder(int uid, String ctime, BigDecimal ctzed, String ccoinname, int ctzfx, BigDecimal cykbl,String language, String plantime);

    ResponseDTO<List<TwMcdHyOrder>> followHyorder(int uid);

    void settlement(String orderNo);

    void carrayout();

    void mockCarrayout();
}
