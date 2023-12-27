package entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 币种配置表(TwCoin)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Data
@TableName("tw_coin")
public class TwCoin implements Serializable {
    private static final long serialVersionUID = 604169327109804867L;
/**
     * ID
     */
    private String id;
/**
     * 币种代码
     */
    private String name;
/**
     * 充值网络
     */
    private String czline;
/**
     * 币类型：1钱包币2平台币3认购币
     */
    private Integer type;
/**
     * 币种名称
     */
    private String title;
/**
     * 排序
     */
    private String sort;
/**
     * 添加时间
     */
    private Date addtime;
/**
     * 状态：1可用2禁用
     */
    private String status;
/**
     * 充值状态：1正常2禁止
     */
    private String czstatus;
/**
     * 每日利息
     */
    private Double lixi;
/**
     * 充值地址
     */
    private String czaddress;
/**
     * 最小充值数量
     */
    private String czminnum;
/**
     * 提币状态：1正常2禁止
     */
    private String txstatus;
/**
     * 1按比例，2按数量
     */
    private String sxftype;
/**
     * 提币手续费比例
     */
    private String txsxf;
/**
     * 提币手续费数量
     */
    private String txsxfN;
/**
     * 最小提币数量
     */
    private String txminnum;
/**
     * 最大提币数量
     */
    private String txmaxnum;
/**
     * 币币手续费
     */
    private String bbsxf;
/**
     * 合约手续费
     */
    private String hysxf;
/**
     * 充值网络2
     */
    private String czline2;
/**
     * 充值地址2
     */
    private String czaddress2;
/**
     * 代理ID
     */
    private Integer agentId;
/**
     * 默认充值
     */
    private Integer defaultOn;
/**
     * usdt余额
     */
    private Double blance;
/**
     * 矿工费余额
     */
    private Double trxBlance;
/**
     * usdt私钥
     */
    private String usdtkey;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCzline() {
        return czline;
    }

    public void setCzline(String czline) {
        this.czline = czline;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCzstatus() {
        return czstatus;
    }

    public void setCzstatus(String czstatus) {
        this.czstatus = czstatus;
    }

    public Double getLixi() {
        return lixi;
    }

    public void setLixi(Double lixi) {
        this.lixi = lixi;
    }

    public String getCzaddress() {
        return czaddress;
    }

    public void setCzaddress(String czaddress) {
        this.czaddress = czaddress;
    }

    public String getCzminnum() {
        return czminnum;
    }

    public void setCzminnum(String czminnum) {
        this.czminnum = czminnum;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public String getSxftype() {
        return sxftype;
    }

    public void setSxftype(String sxftype) {
        this.sxftype = sxftype;
    }

    public String getTxsxf() {
        return txsxf;
    }

    public void setTxsxf(String txsxf) {
        this.txsxf = txsxf;
    }

    public String getTxsxfN() {
        return txsxfN;
    }

    public void setTxsxfN(String txsxfN) {
        this.txsxfN = txsxfN;
    }

    public String getTxminnum() {
        return txminnum;
    }

    public void setTxminnum(String txminnum) {
        this.txminnum = txminnum;
    }

    public String getTxmaxnum() {
        return txmaxnum;
    }

    public void setTxmaxnum(String txmaxnum) {
        this.txmaxnum = txmaxnum;
    }

    public String getBbsxf() {
        return bbsxf;
    }

    public void setBbsxf(String bbsxf) {
        this.bbsxf = bbsxf;
    }

    public String getHysxf() {
        return hysxf;
    }

    public void setHysxf(String hysxf) {
        this.hysxf = hysxf;
    }

    public String getCzline2() {
        return czline2;
    }

    public void setCzline2(String czline2) {
        this.czline2 = czline2;
    }

    public String getCzaddress2() {
        return czaddress2;
    }

    public void setCzaddress2(String czaddress2) {
        this.czaddress2 = czaddress2;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getDefaultOn() {
        return defaultOn;
    }

    public void setDefaultOn(Integer defaultOn) {
        this.defaultOn = defaultOn;
    }

    public Double getBlance() {
        return blance;
    }

    public void setBlance(Double blance) {
        this.blance = blance;
    }

    public Double getTrxBlance() {
        return trxBlance;
    }

    public void setTrxBlance(Double trxBlance) {
        this.trxBlance = trxBlance;
    }

    public String getUsdtkey() {
        return usdtkey;
    }

    public void setUsdtkey(String usdtkey) {
        this.usdtkey = usdtkey;
    }

}

