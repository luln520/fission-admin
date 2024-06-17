package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwPCKjprofitVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 矿机收益表(TwKjprofit)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
public interface TwKjprofitService extends IService<TwKjprofit> {

    IPage<TwKjprofit> listpage(TwKjprofitVo twKjprofitVo, HttpServletRequest request);

    List<TwKjprofit> kjprofit(int uid);

}
