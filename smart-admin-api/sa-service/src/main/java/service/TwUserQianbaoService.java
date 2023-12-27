package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwUserQianbao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户钱包表(TwUserQianbao)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
public interface TwUserQianbaoService extends IService<TwUserQianbao> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserQianbao queryById(String id);

    /**
     * 分页查询
     *
     * @param twUserQianbao 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwUserQianbao> queryByPage(TwUserQianbao twUserQianbao, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twUserQianbao 实例对象
     * @return 实例对象
     */
    TwUserQianbao insert(TwUserQianbao twUserQianbao);

    /**
     * 修改数据
     *
     * @param twUserQianbao 实例对象
     * @return 实例对象
     */
    TwUserQianbao update(TwUserQianbao twUserQianbao);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
