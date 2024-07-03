package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwInviteSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_invite_set(邀请规则设置)】的数据库操作Service
* @createDate 2024-07-03 14:47:22
*/
public interface TwInviteSetService extends IService<TwInviteSet> {

    IPage<TwInviteSet> listpage(PageParam pageParam);

    boolean addOrUpdate(TwInviteSet twInviteSet);

    boolean delete(int id);

    TwInviteSet find(int id);

    ResponseDTO<List<TwInviteSet>> getTwLeverSet(int companyId);
}
