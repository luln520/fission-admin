package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 充值记录(TwRecharge)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
public interface TwRechargeService extends IService<TwRecharge> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwRecharge queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twRecharge 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwRecharge> queryByPage(TwRecharge twRecharge, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twRecharge 实例对象
     * @return 实例对象
     */
    TwRecharge insert(TwRecharge twRecharge);

    /**
     * 修改数据
     *
     * @param twRecharge 实例对象
     * @return 实例对象
     */
    TwRecharge update(TwRecharge twRecharge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
