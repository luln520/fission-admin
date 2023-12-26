package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwBill;
import net.lab1024.sa.admin.module.dao.TwBillDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwBillService;

import javax.annotation.Resource;

/**
 * 操作日志(TwBill)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@Service("twBillService")
public class TwBillServiceImpl extends ServiceImpl<TwBillDao, TwBill> implements TwBillService {

}
