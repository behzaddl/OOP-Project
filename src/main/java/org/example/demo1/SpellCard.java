package org.example.demo1;

import java.io.Serializable;

public class SpellCard extends Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private String specificAction;
    private int duration;

    public SpellCard(String name, String specificAction, int duration) {
        super(name, "spell", 50);
        this.specificAction = specificAction;
        this.duration = duration;
    }

    public String getSpecificAction() {
        return specificAction;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "SpellCard{" +
                "name='" + getName() + '\'' +
                ", specificAction='" + specificAction + '\'' +
                ", duration=" + duration +
                ", level=" + getLevel() +
                '}';
    }
}
