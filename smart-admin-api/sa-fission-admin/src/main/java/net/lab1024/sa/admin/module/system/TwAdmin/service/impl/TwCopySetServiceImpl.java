package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCopySetMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopySet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCopySetService;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【tw_copy_set】的数据库操作Service实现
* @createDate 2024-07-09 15:14:25
*/
@Service
public class TwCopySetServiceImpl extends ServiceImpl<TwCopySetMapper, TwCopySet>
    implements TwCopySetService {

    @Override
    public TwCopySet hysettingId(int companyId) {
        QueryWrapper<TwCopySet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", companyId); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean edit(TwCopySet twCopySet) {
            return this.saveOrUpdate(twCopySet);
    }
}




