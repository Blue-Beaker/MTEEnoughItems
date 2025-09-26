package io.bluebeaker.mteenoughitems.utils;

public enum EnergyUnit {
    RF("RF"),
    EU("EU"),
    HU("HU"),
    KU("KU");
    EnergyUnit(String name){
        this.name = name;
    }
    public final String name;
}
