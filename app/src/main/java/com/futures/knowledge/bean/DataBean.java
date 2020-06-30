package com.futures.knowledge.bean;

/**
 * @ClassName: DataBean
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/27 14:17
 */
public class DataBean {
    private int id;
    private String title;
    private String desc;
    private String time;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
