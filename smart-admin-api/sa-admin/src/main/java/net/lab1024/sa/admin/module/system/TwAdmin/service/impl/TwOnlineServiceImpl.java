package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNoticeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwOnlineDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.OnlineVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMessageRep;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * (TwOnline)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Service("twOnlineService")
public class TwOnlineServiceImpl extends ServiceImpl<TwOnlineDao, TwOnline> implements TwOnlineService {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TwOnlineDao twOnlineDao;
    @Autowired
    private TwNoticeService twNoticeService;
    @Autowired
    private TwNoticeDao twNoticeDao;
    @Override
    public IPage<TwOnline> listpage(OnlineVo onlineVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwOnline> objectPage = new Page<>(onlineVo.getPageNum(), onlineVo.getPageSize());
            List<TwOnline> listpage = baseMapper.listpage(objectPage, onlineVo);
            for (TwOnline twOnline:listpage){
                Integer uid = twOnline.getUid();
                QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("uid",uid);
                List<TwOnline> list = this.list(queryWrapper);
                for(TwOnline online:list){
                    Integer state = online.getState();
                    if(state == 0){
                        twOnline.setState(0);
                        break;
                    }else{
                        twOnline.setState(1);
                    }
                }
            }
            objectPage.setRecords(listpage);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwOnline> objectPage = new Page<>(onlineVo.getPageNum(), onlineVo.getPageSize());
                List<TwOnline> listpage = baseMapper.listpage(objectPage, onlineVo);
                for (TwOnline twOnline:listpage){
                    Integer uid = twOnline.getUid();
                    QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("uid",uid);
                    List<TwOnline> list = this.list(queryWrapper);
                    for(TwOnline online:list){
                        Integer state = online.getState();
                        if(state == 0){
                            twOnline.setState(0);
                            break;
                        }else{
                            twOnline.setState(1);
                        }
                    }
                }
                objectPage.setRecords(listpage);
                return objectPage;
            }else{
                Page<TwOnline> objectPage = new Page<>(onlineVo.getPageNum(), onlineVo.getPageSize());
                List<TwOnline> listpage = baseMapper.listpage(objectPage, onlineVo);
                for (TwOnline twOnline:listpage){
                    Integer uid = twOnline.getUid();
                    QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("uid",uid);
                    List<TwOnline> list = this.list(queryWrapper);
                    for(TwOnline online:list){
                        Integer state = online.getState();
                        if(state == 0){
                            twOnline.setState(0);
                            break;
                        }else{
                            twOnline.setState(1);
                        }
                    }
                }
                objectPage.setRecords(listpage);
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public List<TwOnline> lists(int uid,String uuid,int companyId) {
        if(StringUtils.isNotEmpty(uuid)){
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid",uuid);
            queryWrapper.eq("company_id",companyId);
            queryWrapper.orderByDesc("id");
            return this.list(queryWrapper);
        }else{
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid",uid);
            queryWrapper.eq("company_id",companyId);
            queryWrapper.orderByDesc("id");
            return this.list(queryWrapper);
        }

    }

    @Override
    public List<TwOnline> getId(int id) {
        return baseMapper.getId(id);
    }

    @Override
    public List<TwOnline> getUUId(String uuid) {
        return baseMapper.getUUId(uuid);
    }

    @Override
    public ResponseDTO del(int id) {
        return ResponseDTO.ok(this.removeById(id));
    }

    @Override
    public ResponseDTO backOnline(int id, String content) {
        try{
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid",id);
            queryWrapper.orderByDesc("addtime").last("LIMIT 1"); // Replace "your_date_column" with the actual column you want to use for ordering.
            TwOnline one = getOne(queryWrapper);
            Integer uid = one.getUid();

            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",uid);
            TwUser user = twUserService.getOne(queryWrapper1);

            TwOnline one1 =new TwOnline();
            one1.setUid(one.getUid());
            one1.setUsername(one.getUsername());
            one1.setContent(content);
            one1.setCompanyId(user.getCompanyId());
            one1.setType(1);
            one1.setAddtime(new Date());
            one1.setState(2);
            one1.setStatus(2);
            this.save(one1);

            this.baseMapper.updateState(uid);

            return ResponseDTO.ok("回复成功");

        }catch (Exception e){
            return ResponseDTO.userErrorParam("回复失败");
        }
    }
    @Override
    public ResponseDTO backOnlineUuid(String uuid, String content) {
        try{
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid",uuid);
            queryWrapper.orderByDesc("addtime").last("LIMIT 1"); // Replace "your_date_column" with the actual column you want to use for ordering.
            TwOnline one = getOne(queryWrapper);

            TwOnline one1 =new TwOnline();
            one1.setUuid(uuid);
            one1.setContent(content);
            one1.setCompanyId(one.getCompanyId());
            one1.setType(1);
            one1.setAddtime(new Date());
            one1.setState(2);
            one1.setStatus(2);
            this.save(one1);

            this.baseMapper.updateStateUuid(uuid);

            return ResponseDTO.ok("回复成功");

        }catch (Exception e){
            return ResponseDTO.userErrorParam("回复失败");
        }
    }

    @Override
    public ResponseDTO sendMsg(int uid, String content,String uuid,int type, int companyId) {
            if(type  == 1){
                QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id",uid);

                TwUser one = twUserService.getOne(queryWrapper);

                TwOnline one1 =new TwOnline();
                one1.setUid(one.getId());
                one1.setUsername(one.getUsername());
                one1.setContent(content);
                one1.setCompanyId(one.getCompanyId());
                one1.setType(2);
                one1.setAddtime(new Date());
                this.save(one1);

                return ResponseDTO.userErrorParam("发送成功");
            }

            if(type  == 2){
                TwOnline one1 =new TwOnline();
                one1.setUuid(uuid);
                one1.setContent(content);
                one1.setCompanyId(companyId);
                one1.setType(2);
                one1.setStatus(2);
                one1.setAddtime(new Date());
                this.save(one1);

                return ResponseDTO.userErrorParam("发送成功");
            }

            return null;
    }

    @Override
    public ResponseDTO upStatus(int uid, int companyId) {
        QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("status",2);
        queryWrapper.eq("company_id",companyId);
        List<TwOnline> list = this.list(queryWrapper);
        for (TwOnline twOnline:list){
            twOnline.setStatus(1);
            this.updateById(twOnline);
        }

        QueryWrapper<TwNotice> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("uid",uid);
        queryWrapper1.eq("status",1);
        queryWrapper1.eq("company_id",companyId);
        List<TwNotice> list1 = twNoticeService.list(queryWrapper1);
        for (TwNotice twNotice:list1){
            twNotice.setStatus(2);
            twNoticeService.updateById(twNotice);
        }

        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO upUuidStatus(String uuid, int companyId) {
        QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid",uuid);
        queryWrapper.eq("status",2);
        queryWrapper.eq("company_id",companyId);
        List<TwOnline> list = this.list(queryWrapper);
        for (TwOnline twOnline:list){
            twOnline.setStatus(1);
            this.updateById(twOnline);
        }
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO userMsg(int uid, int companyId) {
        TwMessageRep messageRep = new TwMessageRep();
        QueryWrapper<TwOnline> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("status",2);
        queryWrapper.eq("company_id",companyId);
        messageRep.setUserCount(twOnlineDao.selectCount(queryWrapper).intValue());

        QueryWrapper<TwNotice> queryWrapper1= new QueryWrapper<>();
        queryWrapper1.eq("uid",uid);
        queryWrapper1.eq("status",1);
        queryWrapper1.eq("company_id",companyId);
        messageRep.setNoticeCount(twNoticeDao.selectCount(queryWrapper1).intValue());
        return ResponseDTO.ok(messageRep);
    }

    @Override
    public ResponseDTO userUuidMsg(String uuid, int companyId) {
        TwMessageRep messageRep = new TwMessageRep();
        QueryWrapper<TwOnline> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("uuid",uuid);
        queryWrapper.eq("status",1);
        queryWrapper.eq("company_id",companyId);
        messageRep.setUserCount(twOnlineDao.selectCount(queryWrapper).intValue());

        return ResponseDTO.ok(messageRep);
    }
}
