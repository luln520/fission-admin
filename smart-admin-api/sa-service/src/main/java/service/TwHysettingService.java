package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwHysetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约参数(TwHysetting)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
public interface TwHysettingService extends IService<TwHysetting> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwHysetting queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twHysetting 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwHysetting> queryByPage(TwHysetting twHysetting, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twHysetting 实例对象
     * @return 实例对象
     */
    TwHysetting insert(TwHysetting twHysetting);

    /**
     * 修改数据
     *
     * @param twHysetting 实例对象
     * @return 实例对象
     */
    TwHysetting update(TwHysetting twHysetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
