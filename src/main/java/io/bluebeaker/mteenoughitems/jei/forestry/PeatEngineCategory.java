package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.api.fuels.EngineCopperFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.fuels.GeneratorFuel;
import forestry.core.config.Constants;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PeatEngineCategory extends GenericRecipeCategory<PeatEngineRecipeWrapper> {
    public static final String UID = Categories.Forestry.PEAT_ENGINE_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("forestry","textures/gui/peatengine.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public PeatEngineCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 45, 27, 14, 14);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 176, 0, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.BOTTOM, false);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModuleEnergy.getBlocks().peatEngine));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PeatEngineRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        guiItemStackGroup.set(0,wrapper.getInput());
        this.addItemSlot(guiItemStackGroup,1,GUI_WIDTH-24,GUI_HEIGHT/2-9);
        guiItemStackGroup.set(1,wrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Forestry.PEAT_ENGINE;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.Forestry.name;
    }

    public static List<PeatEngineRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<PeatEngineRecipeWrapper> recipes = new ArrayList<>();
        for (ItemStack input : FuelManager.copperEngineFuel.keySet()) {
            EngineCopperFuel fuel = FuelManager.copperEngineFuel.get(input);

            recipes.add(new PeatEngineRecipeWrapper(jeiHelpers,input,fuel.getPowerPerCycle(),fuel.getBurnDuration()));
        }

        return recipes;
    }
}
