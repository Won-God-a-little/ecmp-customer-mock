package cn.eqxiu.mock.domain.type;


public enum CachePrefixEnum {

    TOKEN_PREFIX("token_"),
    CODE_PREFIX("code_"),
    AUTHORIZATION("authorization_");

    private final String prefix;

    public String getPrefix() {
        return prefix;
    }

    CachePrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getCacheKey(String ...key){
        StringBuilder cacheKey = new StringBuilder(this.prefix);
        for (String s : key) {
            cacheKey.append("_").append(s);
        }
        return cacheKey.toString();
    }
}
