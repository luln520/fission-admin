package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCollect;
import net.lab1024.sa.common.common.domain.ResponseDTO;

/**
* @author 1
* @description 针对表【tw_user_collect(用户收藏)】的数据库操作Service
* @createDate 2024-05-24 19:55:44
*/
public interface TwUserCollectService extends IService<TwUserCollect> {
    ResponseDTO lists(int uid);
    ResponseDTO sel(int uid,String coinname,String language);
    ResponseDTO add(int uid,String coinname,String language);
    ResponseDTO del(int uid,String coinname,String language);
}
