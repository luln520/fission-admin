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
* 矿机订单表
* @TableName tw_kjorder
*/
@Data
@TableName("tw_kjorder")
@ApiModel(value="矿机订单表", description="")
public class TwKjorder implements Serializable {

    /**
    * 记录ID
    */
    @ApiModelProperty("记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 矿机ID
    */
    @ApiModelProperty("矿机ID")
    private Integer kid;
    /**
    * 共享矿机识别号   删除
    */
    @ApiModelProperty("共享矿机识别号   删除")
    private Integer sharbltxt;
    /**
    * 矿机类型1独资2共享  只有独资
    */
    @ApiModelProperty("矿机类型1独资2共享  只有独资")
    private Integer type;
    /**
    * 共享的占有比例   删除
    */
    @ApiModelProperty("共享的占有比例   删除")
    private Double sharebl;
    /**
    * 会员ID
    */
    @ApiModelProperty("会员ID")
    private Integer uid;
    /**
    * 会员账号
    */
    @ApiModelProperty("会员账号")
    private String username;
    /**
    * 矿机名称
    */
    @ApiModelProperty("矿机名称")
    private String kjtitle;
    /**
    * 矿机图片
    */
    @ApiModelProperty("矿机图片")
    private String imgs;
    /**
    * 矿机状态1正常2停止产币3过期
    */
    @ApiModelProperty("矿机状态1正常2停止产币3过期")
    private Integer status;
    /**
    * 矿机周期
    */
    @ApiModelProperty("矿机周期")
    private Integer cycle;
    /**
    * 收益次数
    */
    @ApiModelProperty("收益次数")
    private Integer synum;
    /**
    * 产出类型1按产值2按币  只按币量
    */
    @ApiModelProperty("产出类型1按产值2按币  只按币量")
    private Integer outtype;
    /**
    * 产出币种  删除
    */
    @ApiModelProperty("产出币种  删除")
    private String outcoin;
    /**
    * 产出的币量  删除
    */
    @ApiModelProperty("产出的币量  删除")
    private BigDecimal outnum;
    /**
    * 按产币的量  删除
    */
    @ApiModelProperty("按产币的量  删除")
    private BigDecimal outusdt;
    /**
    * 产币冻结1否2是  删除
    */
    @ApiModelProperty("产币冻结1否2是  删除")
    private Integer djout;
    /**
    * 产币冻结天数   删除
    */
    @ApiModelProperty("产币冻结天数   删除")
    private Integer djnum;
    /**
    * 购买日期
    */
    @ApiModelProperty("购买日期")
    private Date addtime=new Date();
    /**
    * 过期时间
    */
    @ApiModelProperty("过期时间")
    private Date endtime;
    /**
    * 购买时间戳
    */
    @ApiModelProperty("购买时间戳")
    private Integer intaddtime;
    /**
    * 到期时间戳
    */
    @ApiModelProperty("到期时间戳")
    private Integer intendtime;

    @ApiModelProperty("购买数量")
    private BigDecimal buynum;

}
