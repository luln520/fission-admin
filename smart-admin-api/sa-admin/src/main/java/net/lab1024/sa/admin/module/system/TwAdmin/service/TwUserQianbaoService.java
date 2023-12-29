package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户钱包表(TwUserQianbao)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
public interface TwUserQianbaoService extends IService<TwUserQianbao> {
    IPage<TwUserQianbao> listpage(TwUserVo twUserVo);
    boolean addUpdate(TwUserQianbao userQianBao);

    boolean delete(int id);

}
