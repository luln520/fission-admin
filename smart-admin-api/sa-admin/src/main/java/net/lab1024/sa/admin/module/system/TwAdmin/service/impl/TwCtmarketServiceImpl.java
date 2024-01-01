package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCtmarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCtmarketService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.AllsymbolRes;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.CoinpriceRes;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 合约交易对配置(TwCtmarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Service("twCtmarketService")
public class TwCtmarketServiceImpl extends ServiceImpl<TwCtmarketDao, TwCtmarket> implements TwCtmarketService {

    @Override
    public IPage<TwCtmarket> listpage(PageParam pageParam) {
        Page<TwCtmarket> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }
    @Override
    public IPage<TwCtmarket> listPCpage(PageParam pageParam) {
        Page<TwCtmarket> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listPCpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwCtmarket twCtmarket) {
       return  this.saveOrUpdate(twCtmarket);
    }

    @Override
    public boolean updateStatus(int id, int status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCtmarket one = this.getOne(queryWrapper);
        one.setStatus(status);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
         return this.removeById(id);
    }

    @Override
    public TwCtmarket find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public CoinpriceRes getcoinprice(String symbol) {
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        Map<String, Object> map = CommonUtil.doGet(str, null);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal open = new BigDecimal(jsonObject.get("open").toString()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal lowhig = new BigDecimal(0);
        lowhig = close.subtract(open);
        BigDecimal charge = new BigDecimal(0);
        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
        charge = lowhig.divide(open,mathContext).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP);

        CoinpriceRes coinpriceRes = new CoinpriceRes();
        coinpriceRes.setAmount(new BigDecimal(jsonObject.get("amount").toString()).setScale(2, RoundingMode.HALF_UP));
        coinpriceRes.setHigh(new BigDecimal(jsonObject.get("high").toString()).setScale(2, RoundingMode.HALF_UP));
        coinpriceRes.setLow(new BigDecimal(jsonObject.get("low").toString()).setScale(2, RoundingMode.HALF_UP));
        coinpriceRes.setPrice(new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP));
        coinpriceRes.setOpen(new BigDecimal(jsonObject.get("open").toString()).setScale(2, RoundingMode.HALF_UP));
        coinpriceRes.setCode(res.get("status").toString());
        coinpriceRes.setChange(charge);

        return coinpriceRes;
    }

    @Override
    public List<AllsymbolRes> getallsymbol() {
        List<AllsymbolRes> allsymbolRes = new ArrayList<>();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status",1);
        List<TwCtmarket> list = this.list(queryWrapper);
        for (TwCtmarket twCtmarket:list){
            String symbol = twCtmarket.getSymbol();

            String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
            Map<String, Object> map = CommonUtil.doGet(str, null);
            JSONObject res = JSONObject.parseObject(map.get("res").toString());
            JSONArray data = JSONArray.parseArray(res.get("data").toString());
            JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

            BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal open = new BigDecimal(jsonObject.get("open").toString()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal lowhig = new BigDecimal(0);
            lowhig = close.subtract(open);
            BigDecimal charge = new BigDecimal(0);
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
            charge = lowhig.divide(open,mathContext).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP);

            AllsymbolRes allsymbolRes1 = new AllsymbolRes();
            allsymbolRes1.setId(twCtmarket.getId());
            allsymbolRes1.setCoinname(twCtmarket.getCoinname());
            allsymbolRes1.setClose(close);
            allsymbolRes1.setChange(charge);
            allsymbolRes.add(allsymbolRes1);
        }
        return allsymbolRes;
    }
}
