package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNoticeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwOnlineDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * (TwOnline)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Service("twOnlineService")
public class TwOnlineServiceImpl extends ServiceImpl<TwOnlineDao, TwOnline> implements TwOnlineService {

    @Override
    public IPage<TwOnline> listpage(PageParam pageParam) {
        Page<TwOnline> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public List<TwOnline> getId(int id) {
        return baseMapper.getId(id);
    }

    @Override
    public ResponseDTO backOnline(int id, String content) {
        try{
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            queryWrapper.orderByDesc("addtime").last("LIMIT 1"); // Replace "your_date_column" with the actual column you want to use for ordering.

            TwOnline one = getOne(queryWrapper, false);
            Integer uid = one.getUid();

            TwOnline one1 =new TwOnline();
            one1.setUid(one.getUid());
            one1.setUsername(one.getUsername());
            one1.setContent(content);
            one1.setType(1);
            one1.setAddtime(new Date());
            one1.setState(1);
            this.save(one1);

            this.baseMapper.updateState(uid);

            return ResponseDTO.userErrorParam("回复成功");

        }catch (Exception e){
            return ResponseDTO.userErrorParam("回复失败");
        }
    }
}
