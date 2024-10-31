package net.lab1024.sa.common.common.wallet;

public enum ChainEnum {
    BTC(0, "44H/0H/0H/0"),
    ETH(60, "44H/60H/0H/0"),
    TRON(195, "44H/195H/0H/0"),
    ;

    private int code;
    private String value;

    ChainEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ChainEnum getByCode(int code) {
        for (ChainEnum item : ChainEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return BTC;
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
