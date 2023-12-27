package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwAuthRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwAuthRule)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:19:36
 */
public interface TwAuthRuleService extends IService<TwAuthRule> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAuthRule queryById(String id);

    /**
     * 分页查询
     *
     * @param twAuthRule 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAuthRule> queryByPage(TwAuthRule twAuthRule, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAuthRule 实例对象
     * @return 实例对象
     */
    TwAuthRule insert(TwAuthRule twAuthRule);

    /**
     * 修改数据
     *
     * @param twAuthRule 实例对象
     * @return 实例对象
     */
    TwAuthRule update(TwAuthRule twAuthRule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
