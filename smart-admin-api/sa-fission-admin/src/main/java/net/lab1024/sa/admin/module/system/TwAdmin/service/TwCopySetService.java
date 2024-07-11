package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopySet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_copy_set】的数据库操作Service
* @createDate 2024-07-09 15:14:25
*/
public interface TwCopySetService extends IService<TwCopySet> {


    TwCopySet hysettingId(int companyId);

    boolean edit(TwCopySet twCopySet);
}
