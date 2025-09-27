package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class FluidFireboxWrapper extends FluidPowerRecipeWrapper {
    public FluidFireboxWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy) {
        super(jeiHelpers, fluid, power, energy);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = 52;
        int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT/2;

//        RenderUtils.drawTextAlignedRight(this.power+getPowerUnit()+"/t", xPos, yPos, Color.gray.getRGB());
//        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

        RenderUtils.drawTextAlignedLeft(this.energy+" "+getPowerUnit(), xPos, yPos, Color.gray.getRGB());
    }

    @Override
    public String getPowerUnit() {
        return "heat";
    }
}
