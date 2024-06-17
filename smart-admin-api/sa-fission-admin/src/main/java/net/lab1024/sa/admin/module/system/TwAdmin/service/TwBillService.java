package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志(TwBill)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
public interface TwBillService extends IService<TwBill> {

    IPage<TwBill> listpage(TwBillVo twBillVo, HttpServletRequest request);

    List<TwBill> lists(int uid);



}
