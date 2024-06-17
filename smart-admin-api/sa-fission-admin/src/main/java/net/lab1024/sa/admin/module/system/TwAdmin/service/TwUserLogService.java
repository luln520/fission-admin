package net.lab1024.sa.admin.module.system.TwAdmin.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户记录表(TwUserLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
public interface TwUserLogService extends IService<TwUserLog> {

    IPage<TwUserLog> listpage(TwUserVo twUserVo, HttpServletRequest request);

}
