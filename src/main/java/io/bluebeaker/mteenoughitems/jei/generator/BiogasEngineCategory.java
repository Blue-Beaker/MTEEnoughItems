package io.bluebeaker.mteenoughitems.jei.generator;

import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.utils.Area2i;
import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class BiogasEngineCategory extends FluidPowerRecipeCategory<BiogasEngineRecipeWrapper> {
    public static final String UID = "mteenoughitems.forestry.biogas_engine";
    public static final EnergyUnit ENERGY_UNIT = EnergyUnit.RF;
    public static final int SLOT_Y = 24;

    public static final ResourceLocation GUI_PATH = new ResourceLocation("forestry","textures/gui/bioengine.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public BiogasEngineCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,50);
        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 53, 46, 4, 18);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 176, 60, 4, 16).buildAnimated(200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 30, SLOT_Y);
        this.heatBar.draw(minecraft, 30, SLOT_Y+1);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BiogasEngineRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8, SLOT_Y);
        guiFluidStackGroup.set(0,wrapper.getFluidStack());
        this.addFluidSlot(guiFluidStackGroup,1, GUI_WIDTH-26, SLOT_Y-10);
        guiFluidStackGroup.set(1,BiogasEngineRecipeWrapper.LAVA);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Forestry.BIOGAS_ENGINE;
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<BiogasEngineRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<BiogasEngineRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid fluid : FuelManager.bronzeEngineFuel.keySet()) {
            EngineBronzeFuel fuel = FuelManager.bronzeEngineFuel.get(fluid);

            recipes.add(new BiogasEngineRecipeWrapper(jeiHelpers,fluid,fuel.getPowerPerCycle(), (long) fuel.getBurnDuration()*fuel.getPowerPerCycle()/1000,fuel.getDissipationMultiplier()));
        }

        return recipes;
    }

}
