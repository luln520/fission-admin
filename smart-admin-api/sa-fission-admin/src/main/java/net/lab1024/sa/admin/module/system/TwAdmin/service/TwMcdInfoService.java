package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.FollowVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.McdInfoVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface TwMcdInfoService {


    List<McdInfoVo> listMcdUser(String companyId);

    List<FollowVo> listMyFollow(int uid);

    void addFollow(int followUid, int uid, @RequestParam BigDecimal investProp);

    void delFollow(int followUid, int uid);

    void applyMcd(int uid);

    IPage<TwMcdUser> listpage(PageParam pageParam);

    void approveMcd(int id);

    void rejectMcd(int id);
}


