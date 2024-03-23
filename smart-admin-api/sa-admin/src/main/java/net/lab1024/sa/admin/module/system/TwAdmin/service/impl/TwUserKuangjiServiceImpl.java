package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserKuangjiMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserKuangjiService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_user_kuangji(用户矿机单控)】的数据库操作Service实现
* @createDate 2024-01-17 11:24:34
*/
@Service
public class TwUserKuangjiServiceImpl extends ServiceImpl<TwUserKuangjiMapper, TwUserKuangji>
    implements TwUserKuangjiService {

    @Override
    public boolean updatekj(TwUserKuangji twUserKuangji) {
//        QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("kj_id", twUserKuangji.getKjId());
//        queryWrapper.eq("user_id", twUserKuangji.getUserId());
//        TwUserKuangji one = this.getOne(queryWrapper);
//        one.setMin(twUserKuangji.getMin());
//        one.setMax(twUserKuangji.getMax());
//        one.setNum(twUserKuangji.getNum());
//        one.setUpdateTime(new Date());
//        return this.updateById(one);
        if(twUserKuangji == null){
            this.save(twUserKuangji);
        }else{
            QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("kj_id", twUserKuangji.getKjId());
            queryWrapper.eq("user_id", twUserKuangji.getUserId());
            TwUserKuangji one = this.getOne(queryWrapper);
            one.setNum(twUserKuangji.getNum());
            one.setMin(twUserKuangji.getMin());
            one.setMax(twUserKuangji.getMax());
            one.setFloatMin(twUserKuangji.getFloatMin());
            this.updateById(one);
        }


//        QueryWrapper<TwUserKuangji> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("user_id", twUserKuangji.getUserId());
//        List<TwUserKuangji> list1 = this.list(queryWrapper1);
//        for(TwUserKuangji twUserKuang:list1){
//            twUserKuang.setMin(twUserKuangji.getMin());
//            twUserKuang.setMax(twUserKuangji.getMax());
//            twUserKuang.setUpdateTime(new Date());
//            this.updateById(twUserKuang);
//        }

        return true;
    }
}




