package org.pussinboots.morning.online.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("wx_user_info")
public class WxUserInfo extends Model<WxUserInfo> {
    private static final long serialVersionUID = 1L;
    @TableId(value = "wx_user_id", type = IdType.AUTO)
    private Long wxUserId;
    /**
     * 用户ID
     */
    @TableField("os_user_id")
    private Long osUserId;
    /**
     */
    @TableField("avatar_url")
    private String avatarUrl;
    /**
     */
    private String country;
    private String city;

    private String language;

    @TableField("nick_name")
    private String nickName;
    private String province;
    private String mobile;
    private String unionid;
    @TableField("open_id")
    private String openId;
    @TableField("session_key")
    private String sessionKey;
    /**
     * 状态 0=冻结/1=正常
     */
    private Integer status;
    private String remark;
    @TableField("create_time")
    private Date createTime;
    /**
     * 性别 0=保密/1=男/2=女
     */
    private Integer gender;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public Long getOsUserId() {
        return osUserId;
    }

    public void setOsUserId(Long osUserId) {
        this.osUserId = osUserId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.wxUserId;
    }


}
