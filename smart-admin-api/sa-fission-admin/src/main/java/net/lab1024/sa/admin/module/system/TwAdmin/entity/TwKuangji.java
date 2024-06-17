package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    * 收益率
    */
    @ApiModelProperty("收益率  ")
    private BigDecimal dayoutnum;
    /**
    * 日产出币种 
    */
    @ApiModelProperty("日产出币种 ")
    private String outcoin;

    @ApiModelProperty("矿机单价最低额度")
    private BigDecimal pricemin;

    @ApiModelProperty("矿机单价最高额度")
    private BigDecimal pricemax;

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
    * 状态1正常2禁用3过期
    */
    @ApiModelProperty("状态1正常2禁用3过期")
    private Integer status;

    @ApiModelProperty("添加时间")
    private Date addtime=new Date();

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @TableField(exist = false)
    @ApiModelProperty("最低投资价格")
    private BigDecimal min;

    @TableField(exist = false)
    @ApiModelProperty("最高投资价格")
    private BigDecimal max;

    @TableField(exist = false)
    @ApiModelProperty("购买次数")
    private int num;

    @ApiModelProperty("公司id")
    private Integer companyId;
}
