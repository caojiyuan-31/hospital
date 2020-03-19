package cn.ganwuwang.hospital.classification;

import java.io.Serializable;

public class CategoryRes implements Serializable {

    private double probility;//分类的概率
    private String category;//分类

    public double getProbility() {
        return probility;
    }

    public void setProbility(double probility) {
        this.probility = probility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
