package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 广告图片表(TwAdver)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:17:46
 */
public interface TwAdverService extends IService<TwAdver> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAdver queryById(String id);

    /**
     * 分页查询
     *
     * @param twAdver 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAdver> queryByPage(TwAdver twAdver, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAdver 实例对象
     * @return 实例对象
     */
    TwAdver insert(TwAdver twAdver);

    /**
     * 修改数据
     *
     * @param twAdver 实例对象
     * @return 实例对象
     */
    TwAdver update(TwAdver twAdver);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
