package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 矿机列表(TwKuangji)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
public interface TwKuangjiService extends IService<TwKuangji> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwKuangji queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twKuangji 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwKuangji> queryByPage(TwKuangji twKuangji, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twKuangji 实例对象
     * @return 实例对象
     */
    TwKuangji insert(TwKuangji twKuangji);

    /**
     * 修改数据
     *
     * @param twKuangji 实例对象
     * @return 实例对象
     */
    TwKuangji update(TwKuangji twKuangji);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
