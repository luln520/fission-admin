package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 公告内容(TwContent)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
public interface TwContentService extends IService<TwContent> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwContent queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twContent 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwContent> queryByPage(TwContent twContent, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twContent 实例对象
     * @return 实例对象
     */
    TwContent insert(TwContent twContent);

    /**
     * 修改数据
     *
     * @param twContent 实例对象
     * @return 实例对象
     */
    TwContent update(TwContent twContent);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
