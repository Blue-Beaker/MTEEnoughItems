package io.bluebeaker.mteenoughitems.utils;

import net.minecraftforge.fml.common.Loader;

public enum ModChecker {
    Forestry("forestry"),
    BuildcraftSilicon("buildcraftsilicon"),
    ImmersiveEngineering("immersiveengineering"),
    Railcraft("railcraft"),
    ThermalFoundation("thermalfoundation"),
    ThermalExpansion("thermalexpansion"),
    IC2("ic2");

    ModChecker(String name){
        this.name = name;
    }
    public final String name;
    public boolean isLoaded(){
        return Loader.isModLoaded(this.name);
    }
}
