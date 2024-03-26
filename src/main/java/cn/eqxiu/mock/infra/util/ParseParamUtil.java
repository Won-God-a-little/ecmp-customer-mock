package cn.eqxiu.mock.infra.util;

import cn.eqxiu.mock.domain.entity.Creation;
import cn.eqxiu.mock.domain.entity.Visitor;
import com.alibaba.fastjson.JSONObject;

public class ParseParamUtil {

    public static Visitor parseVisitor(JSONObject paraObj) {
        Visitor visitor = new Visitor();
        String mobile = paraObj.getString("mobile");
        if (mobile != null) {
            visitor.setMobile(mobile);
        }

        String openId = paraObj.getString("openId");
        if (openId != null) {
            visitor.setOpenId(openId);
        }

        String nickName = paraObj.getString("nickName");
        if (nickName != null){
            visitor.setNickName(nickName);
        }
        return visitor;
    }

    public static Creation parseCreation(JSONObject paraObj) {
        Creation creation = new Creation();
        Long id = paraObj.getLong("id");
        if (id != null) {
            creation.setId(id);
        }
        String code = paraObj.getString("code");
        if (code != null){
            creation.setCode(code);
        }
        Integer drawNum = paraObj.getInteger("drawNum");
        if (drawNum != null) {
            creation.setDrawNum(drawNum);
        }
        Integer lottery = paraObj.getInteger("lottery");
        if (lottery != null) {
            creation.setLottery(lottery);
        }
        Integer gameScore = paraObj.getInteger("gameScore");
        if (gameScore != null) {
            creation.setGameScore(gameScore);
        }
        return  creation;
    }
}
