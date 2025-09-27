package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.jei.generic.FluidHeatConversionRecipe;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class BoilerRecipe extends FluidHeatConversionRecipe {
    public BoilerRecipe(IJeiHelpers jeiHelpers, FluidStack input, FluidStack output) {
        super(jeiHelpers, input, output, 1);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth/2;
        int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT;
        RenderUtils.drawTextAlignedMiddle(">=100°C", xPos, yPos, Color.gray.getRGB());
    }

    @Override
    public String getPowerUnit() {
        return "";
    }
}
