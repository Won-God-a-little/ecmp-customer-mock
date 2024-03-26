package cn.eqxiu.mock.infra.service.mock;

import cn.eqxiu.mock.domain.entity.Visitor;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

/**
 * 访客服务
 *
 * @author will
 */
@Service
public class VisitorMock {

    private final Cache<String,Object> visitorCache = CacheUtil.newTimedCache(1000 * 60 * 60 * 72);

    public Visitor login(String userName,String password,String channelKey){
        Visitor visitor =  (Visitor)visitorCache.get(userName);
        if (visitor != null){
            if (StrUtil.isNotEmpty(password)) {
                visitor.setChannelKey("ori_" + password);
            }
            if (StrUtil.isNotEmpty(channelKey)) {
                visitor.setChannelKey(channelKey);
            }
            return visitor;
        }
        visitor = new Visitor().createVisitor(userName,password,channelKey);
        visitorCache.put(userName,visitor);
        return visitor;
    }

    public String genVisitorAuthCode(String  userName){
        String code = RandomUtil.randomString(6);
        visitorCache.put(code,userName,1000 * 60 * 5);
        return code;
    }

    public Visitor getVisitorByCode(String code){
        String userName = (String)visitorCache.get(code);
        if (userName != null){
            Visitor visitor = (Visitor) visitorCache.get(userName);
            if (visitor != null && visitor.isErrorCase()){
                return null;
            }
            return visitor;
        }
        return null;
    }

    public Visitor getVisitorByRandomCode(String code) {
        return  new Visitor().createVisitor(code);
    }
}
