package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 认购发行表(TwIssue)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:47
 */
public interface TwIssueService extends IService<TwIssue> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwIssue queryById(String id);

    /**
     * 分页查询
     *
     * @param twIssue 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwIssue> queryByPage(TwIssue twIssue, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twIssue 实例对象
     * @return 实例对象
     */
    TwIssue insert(TwIssue twIssue);

    /**
     * 修改数据
     *
     * @param twIssue 实例对象
     * @return 实例对象
     */
    TwIssue update(TwIssue twIssue);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
