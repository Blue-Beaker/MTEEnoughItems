package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class BlastFurnaceFuelRecipeWrapper extends FuelRecipeWrapper {

    public BlastFurnaceFuelRecipeWrapper(ItemStack input, int duration) {
        super(input, 0, duration);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth / 2;
        int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT;

        RenderUtils.drawTextAlignedMiddle(this.duration + "ticks", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;
        RenderUtils.drawTextAlignedMiddle(this.duration/20 + "secs", xPos, yPos, Color.gray.getRGB());
    }
}
