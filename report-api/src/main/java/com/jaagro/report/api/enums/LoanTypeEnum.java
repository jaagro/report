package com.jaagro.report.api.enums;

/**
 * @description: 贷款类型
 * @author: @Gao.
 * @create: 2019-04-02 09:44
 **/
public enum LoanTypeEnum {
    /**
     *
     */
    BATCH(1, "BATCH", "批次贷款"),
    PURCHASE_ORDER(2, "PURCHASE_ORDER", "订单贷款");
    private int code;
    private String type;
    private String desc;

    LoanTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (LoanTypeEnum type : LoanTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (LoanTypeEnum type : LoanTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (LoanTypeEnum type : LoanTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static Integer getCodeByType(String type) {
        for (LoanTypeEnum loanTypeEnum : LoanTypeEnum.values()) {
            if (loanTypeEnum.getType().equalsIgnoreCase(type)) {
                return loanTypeEnum.getCode();
            }
        }
        return null;
    }


    public static LoanTypeEnum toEnum(int code) {
        for (LoanTypeEnum type : LoanTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
