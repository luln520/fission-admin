package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 通知表(TwNotice)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
public interface TwNoticeService extends IService<TwNotice> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwNotice queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twNotice 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwNotice> queryByPage(TwNotice twNotice, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twNotice 实例对象
     * @return 实例对象
     */
    TwNotice insert(TwNotice twNotice);

    /**
     * 修改数据
     *
     * @param twNotice 实例对象
     * @return 实例对象
     */
    TwNotice update(TwNotice twNotice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
