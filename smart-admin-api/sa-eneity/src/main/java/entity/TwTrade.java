package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 交易下单表(TwTrade)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
@Data
@TableName("tw_trade")
public class TwTrade implements Serializable {
    private static final long serialVersionUID = -34506882725888635L;

    private String id;

    private String userid;

    private String market;

    private String price;

    private String num;

    private String deal;

    private String mum;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

}

