package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户币种表(TwUserCoin)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Data
@TableName("tw_user_coin")
public class TwUserCoin implements Serializable {
    private static final long serialVersionUID = -97030412396849313L;

    private String id;

    private String userid;

    private String usdt;

    private String usdtd;

    private String usdtb;

    private String btc;

    private String btcd;

    private String btcb;

    private String eth;

    private String ethd;

    private String ethb;
}

