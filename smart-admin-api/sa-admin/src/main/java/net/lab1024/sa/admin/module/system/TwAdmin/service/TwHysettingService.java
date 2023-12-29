package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约参数(TwHysetting)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
public interface TwHysettingService extends IService<TwHysetting> {

    TwHysetting hysettingId();

    boolean setWin(int id,int type,int uid);

    boolean edit(TwHysetting twHysetting);

}
