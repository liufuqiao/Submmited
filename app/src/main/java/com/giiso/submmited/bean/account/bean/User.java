package com.giiso.submmited.bean.account.bean;

import java.io.Serializable;

/**
 * 用户信息类
 *
 * @author lyb 2017-08-22
 */
public class User implements Serializable {
    private String token;
    private int id;
    private String imei;
    private String vid;//用户的标识
    private String uid;
    private String userName;//用户名
    private String nikeName;//用户的昵称，优先使用这个，如果这个没有，就用userName，如果userName还没有，就使用手机号码作为用户名
    private String headimgUrl;//头像
    private String qqBind;//qq的绑定状态，0代表绑定了，1代表未绑定
    private String weixinBind;//微信的绑定状态，0代表绑定了，1代表未绑定
    private String weiboBind;//微博的简介，0代表绑定了，1代表未绑定
    private String qqOpenId;//qq的openId
    private String wxOpenId;//微信的openId
    private String wbOpenId;//微博的openId
    private String mobile;//	手机号码
    private int platCode;
    private int userType;//用户表示(-1:游客,0会员，1 大咖)
    private int status;//账号状态，0代表正常，1代表屏蔽
    private String description;//用户的简介
    private String cardId;//用户的身份证
    private String area;//区域
    private String sex;//性别，0代表男，1代表女
    private String addr;//用户的详细地址
    private String birthday;//生日
    private String otherCode;//	平台类型，QQ：qq，微博：wb，微信：wx
    private String fingerCode;

    public String getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPlatCode() {
        return platCode;
    }

    public void setPlatCode(int platCode) {
        this.platCode = platCode;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQqBind() {
        return qqBind;
    }

    public void setQqBind(String qqBind) {
        this.qqBind = qqBind;
    }

    public String getWeixinBind() {
        return weixinBind;
    }

    public void setWeixinBind(String weixinBind) {
        this.weixinBind = weixinBind;
    }

    public String getWeiboBind() {
        return weiboBind;
    }

    public void setWeiboBind(String weiboBind) {
        this.weiboBind = weiboBind;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getWbOpenId() {
        return wbOpenId;
    }

    public void setWbOpenId(String wbOpenId) {
        this.wbOpenId = wbOpenId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getFingerCode() {
        return fingerCode;
    }

    public void setFingerCode(String fingerCode) {
        this.fingerCode = fingerCode;
    }
}
