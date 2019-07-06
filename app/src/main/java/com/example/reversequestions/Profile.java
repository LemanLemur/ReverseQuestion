package com.example.reversequestions;

public class Profile {

    public String name;
    public String id;
    public int lvl;
    public int avatar;
    public int exp;

    public Profile(String name, String id, int lvl, int avatar, int exp) {
        this.name = name;
        this.id = id;
        this.lvl = lvl;
        this.avatar = avatar;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return name;
    }
}
