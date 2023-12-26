package entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 矿机订单表(TwKjorder)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Data
@TableName("tw_kjorder")
public class TwKjorder implements Serializable {
    private static final long serialVersionUID = 707620219017503079L;
/**
     * 记录ID
     */
    private Integer id;
/**
     * 矿机ID
     */
    private Integer kid;
/**
     * 共享矿机识别号
     */
    private Integer sharbltxt;
/**
     * 矿机类型1独资2共享
     */
    private Integer type;
/**
     * 共享的占有比例
     */
    private String sharebl;
/**
     * 会员ID
     */
    private Integer uid;
/**
     * 会员账号
     */
    private String username;
/**
     * 矿机名称
     */
    private String kjtitle;
/**
     * 矿机图片
     */
    private String imgs;
/**
     * 矿机状态1正常2停止产币3过期
     */
    private Integer status;
/**
     * 矿机周期
     */
    private Integer cycle;
/**
     * 收益次数
     */
    private Integer synum;
/**
     * 产出类型1按产值2按币
     */
    private Integer outtype;
/**
     * 产出币种
     */
    private String outcoin;
/**
     * 产出的币量
     */
    private Double outnum;
/**
     * 按产币的量
     */
    private Double outusdt;
/**
     * 产币冻结1否2是
     */
    private Integer djout;
/**
     * 产币冻结天数
     */
    private Integer djnum;
/**
     * 购买日期
     */
    private Date addtime;
/**
     * 过期时间
     */
    private Date endtime;
/**
     * 购买时间戳
     */
    private Integer intaddtime;
/**
     * 到期时间戳
     */
    private Integer intendtime;

}

