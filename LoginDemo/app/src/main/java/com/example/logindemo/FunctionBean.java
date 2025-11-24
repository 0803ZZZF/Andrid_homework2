package com.example.logindemo;

public class FunctionBean {
    private int iconRes; // 图标资源ID
    private String name; // 功能名称

    public FunctionBean(int iconRes, String name) {
        this.iconRes = iconRes;
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getName() {
        return name;
    }
}