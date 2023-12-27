package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwBbsetting;
import entity.TwBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 操作日志(TwBill)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
public interface TwBillService extends IService<TwBill> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwBill queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twBill 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwBill> queryByPage(TwBill twBill, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twBill 实例对象
     * @return 实例对象
     */
    TwBill insert(TwBill twBill);

    /**
     * 修改数据
     *
     * @param twBill 实例对象
     * @return 实例对象
     */
    TwBill update(TwBill twBill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
