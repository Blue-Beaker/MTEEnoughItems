package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.core.config.Constants;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BiogasEngineCategory extends FluidPowerRecipeCategory<BiogasEngineRecipeWrapper> {
    public static final String UID = Categories.Forestry.BIOGAS_ENGINE_UID;
    public static final EnergyUnit ENERGY_UNIT = EnergyUnit.RF;
    public final int SLOT_Y = GUI_HEIGHT/2-9;

    public static final ResourceLocation GUI_PATH = new ResourceLocation("forestry","textures/gui/bioengine.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public BiogasEngineCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 53, 46, 4, 18);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 176, 60, 4, 16).buildAnimated(200, IDrawableAnimated.StartDirection.BOTTOM, false);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModuleEnergy.getBlocks().biogasEngine));
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 8, SLOT_Y);
        this.heatBar.draw(minecraft, 8, SLOT_Y+1);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BiogasEngineRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,GUI_WIDTH-44, SLOT_Y);
        guiFluidStackGroup.set(0,wrapper.getFluidStack());
        this.addFluidSlot(guiFluidStackGroup,1, GUI_WIDTH-26, SLOT_Y);
        guiFluidStackGroup.set(1,BiogasEngineRecipeWrapper.LAVA);
        guiFluidStackGroup.addTooltipCallback(new TooltipCallback(wrapper));
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

    public static class TooltipCallback implements ITooltipCallback<FluidStack> {
        public final float minLoss;
        public final float maxLoss;

        public TooltipCallback(BiogasEngineRecipeWrapper wrapper) {
            int minHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY - wrapper.dissipationMultiplier;
            int maxHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY * 3 - wrapper.dissipationMultiplier*2;

            minLoss = maxHeatGain<0 ? (float) -Constants.ENGINE_HEAT_VALUE_LAVA /maxHeatGain : 0;
            maxLoss = maxHeatGain<0 ? (float) -Constants.ENGINE_HEAT_VALUE_LAVA /minHeatGain : 0;
        }

        @Override
        public void onTooltip(int i, boolean input, FluidStack fluidStack, List<String> list) {
            if(i!=1 || !input) return;
            if(minLoss>0 || maxLoss>0){
                list.add(I18n.format("category.mteenoughitems.forestry.biogas_engine.lava_constant"));
            }else {
                list.add(I18n.format("category.mteenoughitems.forestry.biogas_engine.lava_startup"));
            }
        }
    }
}
