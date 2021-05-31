package com.onet.onet.ads;

public class AdsItemModel {
    private String app_name;
    private String app_description;
    private String app_icon;
    private String package_name;

    public AdsItemModel(String app_icon, String app_name, String app_description, String package_name){
        this.app_icon = app_icon;
        this.app_name = app_name;
        this.app_description = app_description;
        this.package_name = package_name;
    }
    public String getPackageName(){
        return package_name;
    }
    public void setPackageName(String package_name){
        this.package_name = package_name;
    }

    public String getAppIcon() {
        return app_icon;
    }

    public void setAppIcon(String app_icon) {
        this.app_icon = app_icon;
    }

    public String getAppName() {
        return app_name;
    }

    public void setAppName(String app_name) {
        this.app_name = app_name;
    }

    public String getAppDescription() {
        return app_description;
    }

    public void setAppDescription(String app_description) {
        this.app_description = app_description;
    }
}
