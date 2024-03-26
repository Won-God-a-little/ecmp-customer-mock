package cn.eqxiu.mock.infra.service.mock;


import cn.eqxiu.mock.common.Result;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrizeLibraryMock {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public Result getPrizesInfo(JSONObject paraObj) {

        String prizeName = paraObj.getString("prizeName");
        String prizeCode = paraObj.getString("prizeCode");
//        奖品类型[1、礼品 2、优惠券 3、积分]
        int prizeType = paraObj.getInteger("prizeType");
        int pageSize = paraObj.getInteger("pageSize");
        int pageNo = paraObj.getInteger("pageNo");

        // 返回结果
        Result result = new Result(true,"200","查询成功");
        Map<String, Object> mapPage = new HashMap<>();
        mapPage.put("pageNo",pageNo);
        mapPage.put("pageSize",pageSize);
        mapPage.put("count",43);
        mapPage.put("end",false);
        if (pageNo == 5) {
            mapPage.put("end",true);
        }
        result.put("map",mapPage);
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> mapPrize = new HashMap<>();
            mapPrize.put("prizeName","测试"+ prizeName + pageNo + i);
            mapPrize.put("prizeCode","prize" + prizeCode + pageNo + i);
            mapPrize.put("surplus",i+2);
            mapPrize.put("prizeImgUrl","https://asset.eqxiu.cn/common/icon-form-custom-submit.png");
            list.add(mapPrize);
        }
        result.put("list",list);
        logger.info("qryMemRightPoint backInfo: {}", result);
        return result;
    }

    public Result getAddressList(String userId) {
        List<Map<String, Object>> addressList = new ArrayList<>();
        Map<String, Object> address = new HashMap<>();
        address.put("name", "小龙女"+ userId);
        address.put("phone", "18388888888");
        address.put("addr", "陕西省钟南山觉清山麓古墓派");
        addressList.add(address);
        Map<String, Object> address2 = new HashMap<>();
        address2.put("name", "杨过"+ userId);
        address2.put("phone", "18388888889");
        address2.put("addr", "陕西省钟南山觉清山麓古墓派");
        addressList.add(address2);
        Result result = new Result(true,"200","查询完成");
        result.put("obj",addressList);
        logger.info("getAddressList backInfo: {}", result);
        return result;
    }
}
