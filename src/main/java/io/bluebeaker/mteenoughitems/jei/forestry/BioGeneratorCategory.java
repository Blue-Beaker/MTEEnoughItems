package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.api.fuels.FuelManager;
import forestry.api.fuels.GeneratorFuel;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class BioGeneratorCategory extends FluidPowerRecipeCategory<BioGeneratorRecipeWrapper> {
    public static final String UID = Categories.Forestry.BIO_GENERATOR_UID;

    public BioGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Forestry.BIO_GENERATOR;
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<BioGeneratorRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<BioGeneratorRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid fluid : FuelManager.generatorFuel.keySet()) {
            GeneratorFuel fuel = FuelManager.generatorFuel.get(fluid);

            recipes.add(new BioGeneratorRecipeWrapper(jeiHelpers,fluid, fuel.getEu(), (long) fuel.getRate() * fuel.getEu()));
        }

        return recipes;
    }
}
