package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwCoinComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwCoinComment)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:50
 */
public interface TwCoinCommentService extends IService<TwCoinComment> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwCoinComment queryById(String id);

    /**
     * 分页查询
     *
     * @param twCoinComment 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwCoinComment> queryByPage(TwCoinComment twCoinComment, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twCoinComment 实例对象
     * @return 实例对象
     */
    TwCoinComment insert(TwCoinComment twCoinComment);

    /**
     * 修改数据
     *
     * @param twCoinComment 实例对象
     * @return 实例对象
     */
    TwCoinComment update(TwCoinComment twCoinComment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
