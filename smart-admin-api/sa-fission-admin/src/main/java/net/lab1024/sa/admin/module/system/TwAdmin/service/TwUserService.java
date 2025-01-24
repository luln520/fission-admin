package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.*;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户信息表(TwUser)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
public interface TwUserService extends IService<TwUser> {

    Integer countAllUsers(int companyId);

    Integer countAuthAllUsers(int companyId);

    Integer countTodayUsers(long startTime, long endTime,int companyId);

    Integer countLineUsers(String startTime , String endTime,int companyId);

    Integer countYtUsers(int companyId);

    Integer countYtAuthUsers(int companyId);

    IPage<TwUser> listpage(TwUserVo twUserVo);

    IPage<TwUser> listUserpage(TwUserVo twUserVo,HttpServletRequest request);

    ResponseDTO addOrUpdate(TwUser twUser, HttpServletRequest request) throws IOException;
    boolean setAgent(int isAgent, int id);

    boolean setBUy(int id, int buyOn);

    boolean setUser(int id,int type,int uid);

    boolean setMoney(int uid, int type, BigDecimal money,BigDecimal codeAmount,HttpServletRequest request);

    boolean userNotice(int uid,int type,String title, String content,String imgs);

    ResponseDTO<TwUser> loginUser(UserReq userReq, String ip);

    ResponseDTO register(UserReq userReq, String ip);

    ResponseDTO editpassword(UserReq userReq);

    ResponseDTO payPasswd(String userCode,String passwd);

    ResponseDTO<TwUser> userInfo(String userCode,String companyId);
    ResponseDTO<TwUser> mockUserInfo(String userCode,String companyId);

    ResponseDTO auth(TwUser twUser);

    IPage<TwUser>  authList(TwUserVo twUserVo);

    boolean authProcess(int uid,int type, HttpServletRequest request);

    ResponseDTO code(String username,String area,int type,String language,int companyId) throws IOException;

    ResponseDTO codeResp() throws IOException;

    ResponseDTO usertj(int uid) ;

    ResponseDTO userdk(int uid) ;

    ResponseDTO userTeams(int uid) ;
    ResponseDTO mockUserAmount(int uid,String language) ;
    ResponseDTO mockUser(int uid,int type) ;
    ResponseDTO editPasword(int uid,String oldword,String newword,String language) ;

    StatisticUserVo statisticPerUserByDate(String startDate, String endDate, int companyId, boolean isAuth);

    List<PathVo>  statisticPathData();

    void changeEmployeeId(int destId, int userId,HttpServletRequest request);
}
