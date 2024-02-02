package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAppcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAreaDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAppc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAppcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TwArea)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Service("twAreaService")
public class TwAreaServiceImpl extends ServiceImpl<TwAreaDao, TwArea> implements TwAreaService {


    @Override
    public List<TwArea> lists() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("international_area_code");
        return this.list(queryWrapper);
    }
}
