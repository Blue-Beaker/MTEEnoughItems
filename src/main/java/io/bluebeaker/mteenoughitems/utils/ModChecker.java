package io.bluebeaker.mteenoughitems.utils;

import net.minecraftforge.fml.common.Loader;

public enum ModChecker {
    Forestry("forestry"),
    Railcraft("railcraft");
    ModChecker(String name){
        this.name = name;
    }
    public final String name;
    public boolean isLoaded(){
        return Loader.isModLoaded(this.name);
    }
}
