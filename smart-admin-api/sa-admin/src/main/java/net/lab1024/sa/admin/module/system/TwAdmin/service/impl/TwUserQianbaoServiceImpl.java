package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserQianbaoDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户钱包表(TwUserQianbao)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Service("twUserQianbaoService")
public class TwUserQianbaoServiceImpl extends ServiceImpl<TwUserQianbaoDao, TwUserQianbao> implements TwUserQianbaoService {

    @Override
    public IPage<TwUserQianbao> listpage(TwUserVo twUserVo) {
            Page<TwUserQianbao> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
        }

    @Override
    public boolean addUpdate(TwUserQianbao userQianBao) {
        return this.saveOrUpdate(userQianBao);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }
}
