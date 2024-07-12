package org.example.demo1;


import java.io.Serializable;

public abstract class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    private String type;
    private int cost;
    private int level;
    private String imagePath;


    public Card(String name, String type, int cost) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.level = 1;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                ", level=" + level +
                '}';
    }
}
