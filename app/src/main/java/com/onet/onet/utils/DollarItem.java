package com.onet.onet.utils;

public class DollarItem
{
    int dollar;
    String name;
    int theme;
    
    public DollarItem(final String name, final int dollar, final int theme) {
        super();
        this.name = name;
        this.dollar = dollar;
        this.theme = theme;
    }
    
    public int getDollar() {
        return this.dollar;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getTheme() {
        return this.theme;
    }
}