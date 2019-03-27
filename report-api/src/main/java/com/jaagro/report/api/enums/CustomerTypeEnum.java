package com.jaagro.report.api.enums;

/**
 * @author yj
 * @date 2019/3/27 16:20
 */
public enum CustomerTypeEnum {
    /**
     * 容量单位(1-ml,2-g)
     */
    PERSON(1, "PERSON", "自然人"),
    COMPANY(2, "COMPANY", "企业");
    private int code;
    private String type;
    private String desc;

    CustomerTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static CustomerTypeEnum toEnum(int code) {
        for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
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
