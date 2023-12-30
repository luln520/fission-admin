package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 矿机列表(TwKuangji)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Data
@TableName("tw_kuangji")
@ApiModel(value="广告图片表", description="")
public class TwKuangji implements Serializable {
    private static final long serialVersionUID = -97236916287711722L;
/**
     * ID
     */
    private Integer id;
/**
     * 矿机类型1独资2共享
     */
    private Integer type;
/**
     * 类型1购买2赠送
     */
    private Integer rtype;
/**
     * 共享矿机分享比例
     */
    private String sharebl;
/**
     * 共享识别码
     */
    private String sharecode;
/**
     * 矿机标题
     */
    private String title;
/**
     * 矿机内容
     */
    private String content;
/**
     * 矿机图片
     */
    private String imgs;
/**
     * 产出类型1按产值2按币量
     */
    private Integer outtype;
/**
     * 日产币量
     */
    private Double dayoutnum;
/**
     * 日产出币种
     */
    private String outcoin;
/**
     * 矿机单价额度
     */
    private Double pricenum;
/**
     * 矿机单价币种
     */
    private String pricecoin;
/**
     * 周期
     */
    private Integer cycle;
/**
     * 矿机算力
     */
    private String suanl;
/**
     * 库机库存总量
     */
    private Integer allnum;
/**
     * 预计出售量
     */
    private Integer ycnum;
/**
     * 已售数量
     */
    private Integer sellnum;
/**
     * 奖励币量
     */
    private Double jlnum;
/**
     * 奖励币种
     */
    private String jlcoin;
/**
     * 申购要求类型1按币量2按团队
     */
    private Integer buyask;
/**
     * 要求数量
     */
    private Integer asknum;
/**
     * 产币冻结状态：1否2是
     */
    private Integer djout;
/**
     * 产币冻结天数
     */
    private Integer djday;
/**
     * 状态1正常2禁用
     */
    private Integer status;
/**
     * 购买上限
     */
    private Integer buymax;
/**
     * 添加时间
     */
    private Date addtime;



}

