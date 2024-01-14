package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMessageRep;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.module.support.jwe.JweUserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:09
 */
@Service("twAdminLogService")
@Slf4j
public class TwAdminLogServiceImpl extends ServiceImpl<TwAdminLogDao, TwAdminLog> implements TwAdminLogService {

    @Autowired
    private TwUserDao twUserDao;
    @Autowired
    private TwMyzcDao twMyzcDao;
    @Autowired
    private TwRechargeDao twRechargeDao;

    private Function<HttpServletRequest, JweUserKey> userFunction;
    @Override
    public IPage<TwAdminLog> listpage(TwBillVo twBillVo) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        JweUserKey user = this.userFunction.apply(request);
        log.info("登陆人信息{}"+user);
        Page<TwAdminLog> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
        return objectPage;
    }

    @Override
    public boolean add(TwAdminLog twAdminLog) {
        return this.save(twAdminLog);
    }

    @Override
    public TwMessageRep message() {
        TwMessageRep messageRep = new TwMessageRep();
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rzstatus", 1);
        messageRep.setAuthCount(twUserDao.selectCount(queryWrapper).intValue());

        QueryWrapper<TwMyzc> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("status", 1);
        messageRep.setMyzcCount(twMyzcDao.selectCount(queryWrapper1).intValue());

        QueryWrapper<TwRecharge> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status", 1);
        messageRep.setRechargeCount(twRechargeDao.selectCount(queryWrapper2).intValue());

        return messageRep;

    }
}
