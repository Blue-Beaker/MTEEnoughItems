package io.bluebeaker.mteenoughitems.jei.generator;

import forestry.core.config.Constants;
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

import static io.bluebeaker.mteenoughitems.jei.generator.BiogasEngineCategory.SLOT_Y;

public class BiogasEngineRecipeWrapper extends FluidPowerRecipeWrapper{
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
        int xPos = 8;
        int yPos = 4 ;

        RenderUtils.drawTextAlignedLeft(this.power+getPowerUnit()+"/t", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

        RenderUtils.drawTextAlignedLeft(this.energy+getPowerUnit()+"/mB", xPos, yPos, Color.gray.getRGB());

        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

        int minHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY - this.dissipationMultiplier;
        int maxHeatGain = Constants.ENGINE_BRONZE_HEAT_GENERATION_ENERGY * 3 - this.dissipationMultiplier*2;

//        RenderUtils.drawTextAlignedRight(minHeatGain +"-"+ maxHeatGain +"heat/tick", xPos, yPos, Color.gray.getRGB());

        int minLoss = maxHeatGain<0 ? -Constants.ENGINE_HEAT_VALUE_LAVA/maxHeatGain : 0;
        int maxLoss = maxHeatGain<0 ? -Constants.ENGINE_HEAT_VALUE_LAVA/minHeatGain : 0;
        if(minLoss>0 || maxLoss>0){
            RenderUtils.drawTextAlignedRight(minLoss+"-"+maxLoss+"t/mb",recipeWidth-8,SLOT_Y+10,Color.gray.getRGB());
        }
    }
}
