package cn.eqxiu.mock.domain.entity;

/**
 * H5作品/活动 信息封装
 *
 * @author will
 */
public class Creation {
    private Long id;
    private String code;

    /**
     * 连抽次数,连抽场景时使用
     */
    private Integer drawNum;

    private Integer gameScore;


    /**
     * 抽奖结果    1 = 可参与抽奖；3 = 不中奖； 2 = 不中奖，可指定弹出提示及跳转链接；4 = 指定中奖
     */
    private Integer lottery = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(Integer drawNum) {
        this.drawNum = drawNum;
    }

    public Integer getLottery() {
        return lottery;
    }

    public void setLottery(Integer lottery) {
        this.lottery = lottery;
    }

    public Integer getGameScore() {
        return gameScore;
    }

    public void setGameScore(Integer gameScore) {
        this.gameScore = gameScore;
    }
}
