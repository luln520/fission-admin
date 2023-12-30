package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.admin.module.system.login.domain.LoginEmployeeDetail;
import net.lab1024.sa.admin.module.system.login.domain.LoginForm;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户信息表(TwUser)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
public interface TwUserService extends IService<TwUser> {

    Integer countAllUsers();

    Integer countLineUsers(String startTime , String endTime);


    IPage<TwUser> listpage(TwUserVo twUserVo);

    IPage<TwUser> listUserpage(TwUserVo twUserVo);

    ResponseDTO addOrUpdate(TwUser twUser, HttpServletRequest request) throws IOException;
    boolean setAgent(int isAgent, int id);

    boolean setBUy(int id, int buyOn);

    boolean setUser(int id,int type,int uid);

    boolean setMoney(int uid,int type,double money, String bizhong);

    boolean userNotice(int uid,int type,String title, String content,String imgs);

    ResponseDTO<TwUser> loginUser(UserReq userReq, String ip);

    ResponseDTO register(UserReq userReq, String ip);

    ResponseDTO editpassword(UserReq userReq);

    ResponseDTO<TwUser> userInfo(String token);
}
