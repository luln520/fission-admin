package net.lab1024.sa.common.common.wallet;

public enum CurrencyEnum {
    USDT(0, "USDT"),
    ETH(1, "ETH"),
    ;

    private int code;
    private String value;

    CurrencyEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CurrencyEnum getByCode(int code) {
        for (CurrencyEnum item : CurrencyEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return USDT;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
