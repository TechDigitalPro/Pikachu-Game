/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onet.onet;

public class ILanguage
{
    public String desc;
    public String lbtn;
    public String rbtn;
    public String title;
    
    public ILanguage(final String title, final String desc, final String lbtn, final String rbtn) {
        super();
        this.title = "";
        this.desc = "";
        this.lbtn = "";
        this.rbtn = "";
        this.title = title;
        this.desc = desc;
        this.lbtn = lbtn;
        this.rbtn = rbtn;
    }
}