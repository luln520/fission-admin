package net.lab1024.sa.common.common.wallet;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class TokenUtils {

    // USDT的小数位数
    private static final int USDT_DECIMALS = 6;

    /**
     * 将显示金额转换为链上金额（考虑精度）
     */
    public static BigInteger toChainAmount(BigDecimal amount, int decimals) {
        return amount.multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
    }

    /**
     * 将链上金额转换为显示金额
     */
    public static BigDecimal toDisplayAmount(BigInteger chainAmount, int decimals) {
        return new BigDecimal(chainAmount).divide(BigDecimal.TEN.pow(decimals));
    }

    /**
     * ETH 单位转换
     */
    public static BigInteger toWei(String amount) {
        return Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
    }

    /**
     * Wei 转 ETH
     */
    public static BigDecimal fromWei(BigInteger wei) {
        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
    }

    /**
     * 将USDT原始余额转换为实际金额
     * @param rawBalance 原始余额
     * @return 实际USDT金额
     */
    public static BigDecimal convertUsdtBalance(BigInteger rawBalance) {
        return new BigDecimal(rawBalance)
                .divide(BigDecimal.TEN.pow(USDT_DECIMALS), USDT_DECIMALS, RoundingMode.HALF_DOWN);
    }
}
