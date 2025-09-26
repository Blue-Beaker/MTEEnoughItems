package io.bluebeaker.mteenoughitems.jei.generator;

import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;

import java.util.Collections;
import java.util.List;

public class GeoGeneratorCategory extends FluidPowerRecipeCategory {
    public static final String UID = "mteenoughitems.gen_geo";
    public static final EnergyUnit ENERGY_UNIT = EnergyUnit.EU;
    public GeoGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Override
    public String getTranslationKey() {
        return "";
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidPowerRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        return Collections.emptyList();
    }

}
