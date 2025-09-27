package io.bluebeaker.mteenoughitems.jei.forestry;

import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;

public class BioGeneratorRecipeWrapper extends FluidPowerRecipeWrapper {
    public BioGeneratorRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy) {
        super(jeiHelpers, fluid, power, energy);
    }

    @Override
    public String getPowerUnit() {
        return EnergyUnit.EU.name;
    }
}
