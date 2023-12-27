package service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwAuthRule;
import net.lab1024.sa.admin.module.dao.TwAuthRuleDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAuthRuleService;

import javax.annotation.Resource;

/**
 * (TwAuthRule)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:36
 */
@Service("twAuthRuleService")
public class TwAuthRuleServiceImpl extends ServiceImpl<TwAuthRuleDao, TwAuthRule> implements TwAuthRuleService {
    @Resource
    private TwAuthRuleDao twAuthRuleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwAuthRule queryById(String id) {
        return this.twAuthRuleDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twAuthRule 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwAuthRule> queryByPage(TwAuthRule twAuthRule, PageRequest pageRequest) {
        long total = this.twAuthRuleDao.count(twAuthRule);
        return new PageImpl<>(this.twAuthRuleDao.queryAllByLimit(twAuthRule, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAuthRule 实例对象
     * @return 实例对象
     */
    @Override
    public TwAuthRule insert(TwAuthRule twAuthRule) {
        this.twAuthRuleDao.insert(twAuthRule);
        return twAuthRule;
    }

    /**
     * 修改数据
     *
     * @param twAuthRule 实例对象
     * @return 实例对象
     */
    @Override
    public TwAuthRule update(TwAuthRule twAuthRule) {
        this.twAuthRuleDao.update(twAuthRule);
        return this.queryById(twAuthRule.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twAuthRuleDao.deleteById(id) > 0;
    }
}
