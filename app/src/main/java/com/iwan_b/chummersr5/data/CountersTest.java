package com.iwan_b.chummersr5.data;

public class CountersTest {
    private static CountersTest instance;
    private PriorityTable meta;
    private PriorityTable attr;
    private PriorityTable magic;
    private PriorityTable skill;

    private PriorityTable res;

    private CountersTest() {
    }

    public static CountersTest getCounters() {
        if (instance == null) {
            instance = new CountersTest();
        }
        return instance;
    }

    public PriorityTable getAttr() {
        return attr;
    }

    public void setAttr(PriorityTable attr) {
        this.attr = attr;
    }

    public PriorityTable getMagic() {
        return magic;
    }

    public void setMagic(PriorityTable magic) {
        this.magic = magic;
    }

    public PriorityTable getMeta() {
        return meta;
    }

    public void setMeta(PriorityTable meta) {
        this.meta = meta;
    }

    public PriorityTable getRes() {
        return res;
    }

    public void setRes(PriorityTable res) {
        this.res = res;
    }

    public PriorityTable getSkill() {
        return skill;
    }

    public void setSkill(PriorityTable skill) {
        this.skill = skill;
    }
}