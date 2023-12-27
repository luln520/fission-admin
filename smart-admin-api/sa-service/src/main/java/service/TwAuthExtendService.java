package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwAuthExtend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwAuthExtend)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
public interface TwAuthExtendService extends IService<TwAuthExtend> {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    TwAuthExtend queryById( );

    /**
     * 分页查询
     *
     * @param twAuthExtend 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAuthExtend> queryByPage(TwAuthExtend twAuthExtend, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAuthExtend 实例对象
     * @return 实例对象
     */
    TwAuthExtend insert(TwAuthExtend twAuthExtend);

    /**
     * 修改数据
     *
     * @param twAuthExtend 实例对象
     * @return 实例对象
     */
    TwAuthExtend update(TwAuthExtend twAuthExtend);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    boolean deleteById( );

}
