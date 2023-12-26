package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwOnline)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
public interface TwOnlineService extends IService<TwOnline> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwOnline queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twOnline 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwOnline> queryByPage(TwOnline twOnline, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twOnline 实例对象
     * @return 实例对象
     */
    TwOnline insert(TwOnline twOnline);

    /**
     * 修改数据
     *
     * @param twOnline 实例对象
     * @return 实例对象
     */
    TwOnline update(TwOnline twOnline);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
