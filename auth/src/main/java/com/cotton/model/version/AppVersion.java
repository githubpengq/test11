package com.cotton.model.version;

import com.cotton.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * app版本管理
 *
 * @author yongfei.zheng
 * @date 2017年3月17日 下午2:41:51 $
 */
@Entity
@Table(name = "t_app_version")
public class AppVersion extends BaseModel {

    /**
     * app名称
     */
    private String appName;

    /**
     * app编号,手动分配,与appName对应
     */
    private String appCode;

    /**
     * 类型 ios/android
     */
    private String appType;

    /**
     * 版本号
     */
    private String appVersion;

    /**
     * 是否强制更新 1强制更新,0不强制更新
     */
    private int forceUpdate;

    /**
     * 更新说明
     */
    private String updateList;

    /**
     * app下载地址
     */
    private String downloadUrl;

    private String createUser;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUpdateList() {
        return updateList;
    }

    public void setUpdateList(String updateList) {
        this.updateList = updateList;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "AppVersion [appName=" + appName + ", appCode=" + appCode + ", appType=" + appType + ", appVersion=" + appVersion + ", forceUpdate=" + forceUpdate + ", updateList="
                + updateList + ", downloadUrl=" + downloadUrl + "]";
    }

}
