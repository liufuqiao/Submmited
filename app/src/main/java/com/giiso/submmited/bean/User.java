package com.giiso.submmited.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class User implements Serializable{

    /**
     * isLogin : true
     * user : {"remarks":null,"createTime":null,"updateTime":null,"id":36,"userName":"liurz","companyInfo":null,"dept":null,"loginName":null,"pwd":null,"email":"liurc@giiso.com","mobile":null,"headImage":null,"userType":null,"status":"0","role":null,"roleList":[],"deptList":[],"admin":false}
     */

    private boolean isLogin;
    private UserBean user;

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable{
        /**
         * remarks : null
         * createTime : null
         * updateTime : null
         * id : 36
         * userName : liurz
         * companyInfo : null
         * dept : null
         * loginName : null
         * pwd : null
         * email : liurc@giiso.com
         * mobile : null
         * headImage : null
         * userType : null
         * status : 0
         * role : null
         * roleList : []
         * deptList : []
         * admin : false
         */

        private String remarks;
        private String createTime;
        private String updateTime;
        private int id;
        private String userName;
        private String companyInfo;
        private String dept;
        private String loginName;
        private String pwd;
        private String email;
        private String mobile;
        private String headImage;
        private String userType;
        private String status;
        private String role;
        private boolean admin;
        private List<?> roleList;
        private List<?> deptList;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public List<?> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<?> roleList) {
            this.roleList = roleList;
        }

        public List<?> getDeptList() {
            return deptList;
        }

        public void setDeptList(List<?> deptList) {
            this.deptList = deptList;
        }
    }
}
