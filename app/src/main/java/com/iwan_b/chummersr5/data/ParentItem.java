package com.iwan_b.chummersr5.data;

import java.util.ArrayList;
import java.util.List;

public class ParentItem {
    public String name;

    private List<Weapon> childItemList;

    public ParentItem(String name) {
        this.name = name;
        childItemList = new ArrayList<>();
    }

    public List<Weapon> getChildItemList() {
        return childItemList;
    }
}