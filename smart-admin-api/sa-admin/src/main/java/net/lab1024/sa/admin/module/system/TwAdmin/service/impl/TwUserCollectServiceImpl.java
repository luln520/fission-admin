package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCollectMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCollect;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCollectService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 1
* @description 针对表【tw_user_collect(用户收藏)】的数据库操作Service实现
* @createDate 2024-05-24 19:55:44
*/
@Service
public class TwUserCollectServiceImpl extends ServiceImpl<TwUserCollectMapper, TwUserCollect>
    implements TwUserCollectService {

    @Override
    public ResponseDTO lists(int uid) {
        QueryWrapper<TwUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return ResponseDTO.ok(this.list(queryWrapper));
    }

    @Override
    public ResponseDTO sel(int uid, String coinname, String language) {
        QueryWrapper<TwUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("coinname", coinname);
        return ResponseDTO.ok(this.getOne(queryWrapper));
    }

    @Override
    public ResponseDTO add(int uid, String coinname,String language) {
        TwUserCollect twUserCollect = new TwUserCollect();
        twUserCollect.setCoinname(coinname);
        twUserCollect.setUid(uid);
        twUserCollect.setCreateTime(new Date());
        this.saveOrUpdate(twUserCollect);
        return  ResponseDTO.ok();
    }

    @Override
    public ResponseDTO del(int uid, String coinname,String language) {
        QueryWrapper<TwUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("coinname", coinname);
        return ResponseDTO.ok(this.remove(queryWrapper));
    }
}




