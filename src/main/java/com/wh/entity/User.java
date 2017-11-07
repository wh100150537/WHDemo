package com.wh.entity;

import java.util.Date;

/**
 * Created by wangyc on 2017/10/27.
 */
public class User {

    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer status;

    private String name;

    private String loginName;

    private Integer roleId;

    private String phone;

    private Integer agentId;

    private Date loginTime;

    private String loginIp;

    private String password;

    private String remark;

    private Integer displayStatistics;

    private Integer isHidePhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", roleId=" + roleId +
                ", phone='" + phone + '\'' +
                ", agentId=" + agentId +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                ", displayStatistics=" + displayStatistics +
                ", isHidePhone=" + isHidePhone +
                '}';
    }

    public Integer getDisplayStatistics() {
        return displayStatistics;
    }

    public void setDisplayStatistics(Integer displayStatistics) {
        this.displayStatistics = displayStatistics;
    }

    public Integer getIsHidePhone() {
        return isHidePhone;
    }

    public void setIsHidePhone(Integer isHidePhone) {
        this.isHidePhone = isHidePhone;
    }
}
