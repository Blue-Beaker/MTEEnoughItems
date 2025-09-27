package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.core.config.Constants;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BiogasEngineRecipeWrapper extends FluidPowerRecipeWrapper {
    public static final FluidStack LAVA = new FluidStack(FluidRegistry.LAVA, 1000);
    public final int dissipationMultiplier;
    public BiogasEngineRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy,int dissipationMultiplier) {
        super(jeiHelpers, fluid, power, energy);
        this.dissipationMultiplier=dissipationMultiplier;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        List<FluidStack> inputs = new ArrayList<>();
        inputs.add(fluidStack);
        inputs.add(LAVA);
        iIngredients.setInputs(VanillaTypes.FLUID,inputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = 16;
        int yPos = recipeHeight/2-minecraft.fontRenderer.FONT_HEIGHT;

        RenderUtils.drawTextAlignedLeft(this.power+getPowerUnit()+"/t", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

        RenderUtils.drawTextAlignedLeft(this.energy+getPowerUnit()+"/mB", xPos, yPos, Color.gray.getRGB());

        int minHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY - dissipationMultiplier;
        int maxHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY * 3 - dissipationMultiplier*2;

        float minLoss = maxHeatGain<0 ? (float) -Constants.ENGINE_HEAT_VALUE_LAVA /maxHeatGain : 0;
        float maxLoss = maxHeatGain<0 ? (float) -Constants.ENGINE_HEAT_VALUE_LAVA /minHeatGain : 0;
        if(maxLoss>0 || minLoss>0){
            RenderUtils.drawTextAlignedLeft("!", recipeWidth-7, recipeHeight/2-8, Color.red.getRGB());
        }
    }
}
