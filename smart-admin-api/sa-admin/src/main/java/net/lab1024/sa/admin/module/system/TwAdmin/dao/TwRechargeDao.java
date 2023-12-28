package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 充值记录(TwRecharge)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Mapper
public interface TwRechargeDao  extends BaseMapper<TwRecharge> {

    List<TwRecharge> listpage(@Param("objectPage") Page<TwRecharge> objectPage, @Param("obj") TwRechargeVo twRechargeVo);
}

