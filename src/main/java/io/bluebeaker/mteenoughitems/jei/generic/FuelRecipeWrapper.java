package io.bluebeaker.mteenoughitems.jei.generic;

import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;

public abstract class FuelRecipeWrapper implements IRecipeWrapper {
    public final int power;
    public final int duration;
    protected final ItemStack input;

    public FuelRecipeWrapper(ItemStack input, int power, int duration) {
        this.input = input;
        this.power = power;
        this.duration = duration;
    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, input);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth / 2;
        int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT * 2;
        RenderUtils.drawTextAlignedMiddle(this.duration + "ticks", xPos, yPos, Color.gray.getRGB());
    }
}
