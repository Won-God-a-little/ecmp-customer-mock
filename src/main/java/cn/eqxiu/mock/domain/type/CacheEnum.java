package cn.eqxiu.mock.domain.type;

public enum CacheEnum {

    TOKEN("token"),
    TEMPCODE("tempCode"),
    AUTHORIATION("authoration");

    private String prefix;

    CacheEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getCacheKey(String ...keys) {
        StringBuilder cacheKey = new StringBuilder(this.prefix);
        for (String s : keys) {
            cacheKey.append("_").append(s);
        }
        return cacheKey.toString();
    }

}
