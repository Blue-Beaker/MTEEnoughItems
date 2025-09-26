package io.bluebeaker.mteenoughitems.utils;

public enum EnergyUnit {
    EU("EU"),
    HU("HU"),
    KU("KU");
    EnergyUnit(String name){
        this.name = name;
    }
    public final String name;
}
