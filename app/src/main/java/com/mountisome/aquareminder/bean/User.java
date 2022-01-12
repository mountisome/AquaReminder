package com.mountisome.aquareminder.bean;

import androidx.annotation.NonNull;

public class User {

    private int id;
    private String name, pwd, mailbox;
    private int water;
    private int energy;
    private String planted;
    private int day;
    private int average_water;
    private int average_time;
    private int total_water;
    private int total_time;


    public User() {
    }

    public User(String name, String pwd, String mailbox, int water, int energy, String planted,
                int day, int average_water, int average_time, int total_water, int total_time) {
        this.name = name;
        this.pwd = pwd;
        this.mailbox = mailbox;
        this.water = water;
        this.energy = energy;
        this.planted = planted;
        this.day = day;
        this.average_water = average_water;
        this.average_time = average_time;
        this.total_water = total_water;
        this.total_time = total_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getPlanted() {
        return planted;
    }

    public void setPlanted(String planted) {
        this.planted = planted;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getAverage_water() {
        return average_water;
    }

    public void setAverage_water(int average_water) {
        this.average_water = average_water;
    }

    public int getAverage_time() {
        return average_time;
    }

    public void setAverage_time(int average_time) {
        this.average_time = average_time;
    }

    public int getTotal_water() {
        return total_water;
    }

    public void setTotal_water(int total_water) {
        this.total_water = total_water;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", water=" + water +
                ", energy=" + energy +
                ", planted='" + planted + '\'' +
                ", day=" + day +
                ", average_water=" + average_water +
                ", average_time=" + average_time +
                ", total_water=" + total_water +
                ", total_time=" + total_time +
                '}';
    }

}
