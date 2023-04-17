package com.zerolouis.practice10.bean;

import com.zerolouis.practice10.R;

public class News {

    private String name;
    private String desc;

    private int type;

    private final int[] icons = {
            R.drawable.news1,R.drawable.news1,R.drawable.news2,R.drawable.news3,R.drawable.news4,R.drawable.news5
    };

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public int getIcon(){
        return icons[this.type];
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public News() {
    }

    public News(String name, String desc, Integer type) {
        this.name = name;
        this.desc = desc;
        this.type = type;
    }
}
