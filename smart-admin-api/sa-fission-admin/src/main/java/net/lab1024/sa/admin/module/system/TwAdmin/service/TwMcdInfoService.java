package net.lab1024.sa.admin.module.system.TwAdmin.service;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.FollowVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.McdInfoVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface TwMcdInfoService {


    List<McdInfoVo> listMcdUser(String companyId);

    List<FollowVo> listMyFollow(int uid);

    void addFollow(int followUid, int uid, @RequestParam BigDecimal investProp);

    void delFollow(int followUid, int uid);
}


