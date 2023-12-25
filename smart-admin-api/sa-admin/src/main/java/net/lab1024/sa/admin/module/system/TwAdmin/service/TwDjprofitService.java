package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDjprofit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 数字币冻结记录表(TwDjprofit)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:22:15
 */
public interface TwDjprofitService extends IService<TwDjprofit> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwDjprofit queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twDjprofit 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwDjprofit> queryByPage(TwDjprofit twDjprofit, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twDjprofit 实例对象
     * @return 实例对象
     */
    TwDjprofit insert(TwDjprofit twDjprofit);

    /**
     * 修改数据
     *
     * @param twDjprofit 实例对象
     * @return 实例对象
     */
    TwDjprofit update(TwDjprofit twDjprofit);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
