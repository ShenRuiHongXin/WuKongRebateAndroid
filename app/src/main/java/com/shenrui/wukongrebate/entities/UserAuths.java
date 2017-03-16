package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/14.
 */

public class UserAuths {
    private int id;//用户认证信息在数据库中的唯一标识
    private int user_id;//用户在数据库中的唯一标识
    private String identity_type;//帐号类型（phone,qq,wechat,sina,taobao）
    private String identifier;//帐号
    private String credential;//密码

    public UserAuths() {
    }

    @Override
    public String toString() {
        return "UserAuths{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", identity_type='" + identity_type + '\'' +
                ", identifier='" + identifier + '\'' +
                ", credential='" + credential + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
