package cn.eqxiu.mock.domain.type;

public enum InterfaceTypeEnum {

    POST(1, "POST"),
    GET(2, "GET")
    ;
    private Integer type;
    private String description;

    InterfaceTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
