package cn.eqxiu.mock.infra.service.mock;

import cn.eqxiu.mock.common.Result;

/**
 * 抽奖结果
 *
 * @author will
 */
public class LotteryResult extends Result {

    public LotteryResult(boolean success, String code, String msg) {
        super(success, code, msg);
    }


}
