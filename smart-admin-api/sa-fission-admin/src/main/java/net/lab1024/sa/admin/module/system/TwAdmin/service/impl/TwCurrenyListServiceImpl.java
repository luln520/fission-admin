package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCurrenyListMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrenyList;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCurrenyListService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
* @author 1
* @description 针对表【tw_curreny_list(货币汇率总表)】的数据库操作Service实现
* @createDate 2024-05-02 14:38:59
*/
@Service
public class TwCurrenyListServiceImpl extends ServiceImpl<TwCurrenyListMapper, TwCurrenyList>
implements TwCurrenyListService {

    @Override
    public ResponseDTO add() {
        String str = "https://open.er-api.com/v6/latest/USD";
        Map<String, Object> map = CommonUtil.doGet(str, "");
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        String rates = JSONObject.parseObject(res.get("rates").toString()).toString();
        String replace = rates.replace("{", "");
        String replace1 = replace.replace("}", "");
        String[] split = replace1.split(",");
        for(int  i = 0 ; i <split.length ; i++){
            TwCurrenyList twCurrenyList = new TwCurrenyList();
            String srate = split[i];
            String[] split1 = srate.split(":");
            String name = split1[0].replace("\"", "");
            String price = split1[1];
            QueryWrapper<TwCurrenyList> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name_en", name); // 添加查询条件
            TwCurrenyList one = this.getOne(queryWrapper1);
            if(one == null){
                twCurrenyList.setNameEn(name);
                twCurrenyList.setCurrenyRate(new BigDecimal(price));
                twCurrenyList.setCreateTime(new Date());
                this.save(twCurrenyList);
            }else{
                one.setCurrenyRate(new BigDecimal(price));
                one.setUpdateTime(new Date());
                this.updateById(one);
            }
        }
        return ResponseDTO.ok();
    }
}
