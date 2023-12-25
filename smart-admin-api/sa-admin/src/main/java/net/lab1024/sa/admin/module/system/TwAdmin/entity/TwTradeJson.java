package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import java.io.Serializable;

/**
 * 交易图表表(TwTradeJson)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:28:00
 */
public class TwTradeJson implements Serializable {
    private static final long serialVersionUID = -35181137554144131L;

    private String id;

    private String market;

    private String data;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

