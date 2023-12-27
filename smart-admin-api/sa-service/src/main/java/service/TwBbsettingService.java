package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwBbsetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 币币交易设置(TwBbsetting)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
public interface TwBbsettingService extends IService<TwBbsetting> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwBbsetting queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twBbsetting 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwBbsetting> queryByPage(TwBbsetting twBbsetting, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twBbsetting 实例对象
     * @return 实例对象
     */
    TwBbsetting insert(TwBbsetting twBbsetting);

    /**
     * 修改数据
     *
     * @param twBbsetting 实例对象
     * @return 实例对象
     */
    TwBbsetting update(TwBbsetting twBbsetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
