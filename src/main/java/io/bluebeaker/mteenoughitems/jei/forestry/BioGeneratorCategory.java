package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.api.fuels.FuelManager;
import forestry.api.fuels.GeneratorFuel;
import forestry.plugins.PluginIC2;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BioGeneratorCategory extends FluidPowerRecipeCategory<BioGeneratorCategory.BioGeneratorRecipeWrapper> {
    public static final String UID = Categories.Forestry.BIO_GENERATOR_UID;

    public BioGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(PluginIC2.getBlocks().generator));
    }

    @Override
    public String getModName() {
        return ModChecker.Forestry.name;
    }

    @Override
    public String getTranslationKey() {
        return Categories.Forestry.BIO_GENERATOR;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    public static List<BioGeneratorRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<BioGeneratorRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid fluid : FuelManager.generatorFuel.keySet()) {
            GeneratorFuel fuel = FuelManager.generatorFuel.get(fluid);

            recipes.add(new BioGeneratorRecipeWrapper(jeiHelpers,fluid, fuel.getEu(), (long) fuel.getRate() * fuel.getEu()));
        }

        return recipes;
    }

    public static class BioGeneratorRecipeWrapper extends FluidPowerRecipeWrapper {
        public BioGeneratorRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy) {
            super(jeiHelpers, fluid, power, energy);
        }

        @Override
        public String getPowerUnit() {
            return EnergyUnit.EU.name;
        }
    }
}
