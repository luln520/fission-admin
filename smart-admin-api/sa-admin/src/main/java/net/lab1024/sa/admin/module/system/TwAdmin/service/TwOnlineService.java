package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TwOnline)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
public interface TwOnlineService extends IService<TwOnline> {
    IPage<TwOnline> listpage(PageParam pageParam, HttpServletRequest request);

    List<TwOnline> lists(int uid,int companyId);

    List<TwOnline> getId(int id);

    ResponseDTO del(int id);

    ResponseDTO backOnline(int id, String content);

    ResponseDTO sendMsg(int uid, String content);

}
