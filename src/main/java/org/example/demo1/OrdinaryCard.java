package org.example.demo1;

import java.io.Serializable;

public class OrdinaryCard extends Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private int damage;
    private int duration;
    private int power;

    public OrdinaryCard(String name, int damage, int duration, int power) {
        super(name, "ordinary", 50);
        this.damage = damage;
        this.duration = duration;
        this.power = power;
    }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void setName(String name) {
        super.name = name;
    }
    @Override
    public String toString() {
        return "OrdinaryCard{" +
                "name='" + getName() + '\'' +
                ", damage=" + damage +
                ", duration=" + duration +
                ", power=" + power +
                ", level=" + getLevel() +
                '}';
    }
}
