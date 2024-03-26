package cn.eqxiu.mock.domain.entity;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 访客用户信息类
 *
 * @author will
 */
public class Visitor {
    private String userId;

    private String openId;

    private String nickName;

    private String headImg;

    /**
     * 性别,1男,2女
     */
    private Integer sex;

    private String mobile;

    /**
     *  渠道编码
     */
    private String channelKey = "mo";

    private String[] headUrl = {"https://asset.eqxiu.cn/common/icon-wechat.png",
            "https://asset.eqxiu.cn/common/icon-third-system.png",
            "https://asset.eqxiu.cn/common/icon-data-push.png",
            "https://asset.eqxiu.cn/common/icon-form-custom-submit.png",
            "https://asset.eqxiu.cn/common/icon-page-integral.png",
            "https://asset.eqxiu.cn/common/icon-organization-user.png",
            "https://asset.eqxiu.cn/common/icon-custom-set.png",
            "https://asset.eqxiu.cn/common/benefit.png",
            "https://www.eqxiu.cn/img/user-avatar.5a9eab5b.png",
            "https://asset.eqxiu.cn/tencent/image/593108331f5948e9b775d96192935f36/lj8x3sjm8e.jpeg",
            "https://asset.eqxiu.cn/material/template/31697.png"
    };

    public Visitor createVisitor(String code){
        this.userId = RandomUtil.randomString(32);
        this.nickName = code;
        this.headImg = headUrl[(int) (Math.random() * 10)];
        this.sex =1;
        this.mobile = "136" + RandomUtil.randomNumbers(8);
        return this;
    }

    public Visitor createVisitor(String nickName,String password,String channelKey){
        this.userId = RandomUtil.randomString(32);
        this.nickName = nickName;
        this.headImg = headUrl[(int) (Math.random() * 10)];
        this.sex =1;
        this.mobile = "136" + RandomUtil.randomNumbers(8);
        if (StrUtil.isNotEmpty(password)) {
            this.channelKey = "ori_" + password;
        }
        if (StrUtil.isNotEmpty(channelKey)){
            this.channelKey = channelKey;
        }
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Integer getSex() {
        return sex;
    }

    public String getMobile() {
        return mobile;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public boolean isErrorCase() {
        return this.nickName.startsWith("error");
    }
}
