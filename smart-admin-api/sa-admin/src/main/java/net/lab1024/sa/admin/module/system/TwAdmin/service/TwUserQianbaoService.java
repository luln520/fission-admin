package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户钱包表(TwUserQianbao)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
public interface TwUserQianbaoService extends IService<TwUserQianbao> {
    IPage<TwUserQianbao> listpage(TwUserVo twUserVo);

    List<TwUserQianbao> lists(int uid);
    boolean addUpdate(TwUserQianbao userQianBao);

    boolean del(int id);

    boolean add(int uid,int oid, String address,String remark, String czline);

    ResponseDTO qbSum(int uid);



}
