package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwPCKjprofitVo;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * 矿机列表(TwKuangji)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
public interface TwKuangjiService extends IService<TwKuangji> {

    IPage<TwKuangji> listpage(PageParam pageParam);

    IPage<TwKuangji> pcList(PageParam pageParam);

    TwKuangji detail(int id);

    boolean addkj(TwKuangji twKuangji);

    boolean open(int  id);
    boolean close(int  id);

    boolean delete(int  id);
    ResponseDTO buyKuangji(int uid, int kid, BigDecimal buynum);

    TwPCKjprofitVo kjprofitSum(int uid);
}
