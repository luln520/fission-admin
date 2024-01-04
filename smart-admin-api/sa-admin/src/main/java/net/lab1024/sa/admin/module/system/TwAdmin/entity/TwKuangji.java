package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 矿机列表
* @TableName tw_kuangji
*/
@Data
@TableName("tw_kuangji")
@ApiModel(value="矿机列表", description="")
public class TwKuangji implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 矿机类型1独资2共享  只有独资
    */
    @ApiModelProperty("矿机类型1独资2共享  只有独资")
    private Integer type;
    /**
    * 类型1购买2赠送   只有购买
    */
    @ApiModelProperty("类型1购买2赠送   只有购买")
    private Integer rtype;
    /**
    * 共享矿机分享比例   删除
    */
    @ApiModelProperty("共享矿机分享比例   删除")
    private String sharebl;
    /**
    * 共享识别码   删除
    */
    @ApiModelProperty("共享识别码   删除")
    private String sharecode;
    /**
    * 矿机标题
    */
    @ApiModelProperty("矿机标题")
    private String title;
    /**
    * 矿机内容
    */
    @ApiModelProperty("矿机内容")
    private String content;
    /**
    * 矿机图片
    */
    @ApiModelProperty("矿机图片")
    private String imgs;
    /**
    * 产出类型1按产值2按币量  //暂时不要
    */
    @ApiModelProperty("产出类型1按产值2按币量  //暂时不要")
    private Integer outtype;
    /**
    * 日产币量  //此字段要更改
    */
    @ApiModelProperty("日产币量  //此字段要更改")
    private BigDecimal dayoutnum;
    /**
    * 日产出币种 
    */
    @ApiModelProperty("日产出币种 ")
    private String outcoin;
    /**
    * 矿机单价额度
    */
    @ApiModelProperty("矿机单价额度")
    private BigDecimal pricenum;
    /**
    * 矿机单价币种
    */
    @ApiModelProperty("矿机单价币种")
    private String pricecoin;
    /**
    * 周期
    */
    @ApiModelProperty("周期")
    private Integer cycle;
    /**
    * 矿机算力  删除
    */
    @ApiModelProperty("矿机算力  删除")
    private Double suanl;
    /**
    * 库机库存总量
    */
    @ApiModelProperty("库机库存总量")
    private Integer allnum;
    /**
    * 预计出售量
    */
    @ApiModelProperty("预计出售量")
    private Integer ycnum;
    /**
    * 已售数量
    */
    @ApiModelProperty("已售数量")
    private Integer sellnum;
    /**
    * 奖励币量  删除
    */
    @ApiModelProperty("奖励币量  删除")
    private BigDecimal jlnum;
    /**
    * 奖励币种   删除
    */
    @ApiModelProperty("奖励币种   删除")
    private String jlcoin;
    /**
    * 申购要求类型1按币量2按团队   字段不要
    */
    @ApiModelProperty("申购要求类型1按币量2按团队   字段不要")
    private Integer buyask;
    /**
    * 要求数量  删除
    */
    @ApiModelProperty("要求数量  删除")
    private Integer asknum;
    /**
    * 产币冻结状态：1否2是  删除
    */
    @ApiModelProperty("产币冻结状态：1否2是  删除")
    private Integer djout;
    /**
    * 产币冻结天数   删除
    */
    @ApiModelProperty("产币冻结天数   删除")
    private Integer djday;
    /**
    * 状态1正常2禁用3过期
    */
    @ApiModelProperty("状态1正常2禁用3过期")
    private Integer status;
    /**
    * 购买上限
    */
    @ApiModelProperty("购买上限")
    private Integer buymax;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime=new Date();

}
