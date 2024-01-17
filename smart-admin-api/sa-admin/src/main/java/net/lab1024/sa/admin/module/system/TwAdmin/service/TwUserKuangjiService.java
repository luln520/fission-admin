package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserKuangji;

/**
* @author 1
* @description 针对表【tw_user_kuangji(用户矿机单控)】的数据库操作Service
* @createDate 2024-01-17 11:24:34
*/
public interface TwUserKuangjiService extends IService<TwUserKuangji> {

    boolean updatekj(TwUserKuangji twUserKuangji);
}
