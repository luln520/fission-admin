package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMessageRep;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:17:08
 */
public interface TwAdminLogService extends IService<TwAdminLog> {

    IPage<TwAdminLog> listpage(TwBillVo twBillVo);

    /**
     * 日志新增记录
     * @return
     */
    boolean add(TwAdminLog twAdminLog);


    TwMessageRep message();

}
