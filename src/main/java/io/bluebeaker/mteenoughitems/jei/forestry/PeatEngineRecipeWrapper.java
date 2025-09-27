package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.core.ModuleCore;
import io.bluebeaker.mteenoughitems.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class PeatEngineRecipeWrapper extends FuelRecipeWrapper {
    protected final ItemStack output;

    public PeatEngineRecipeWrapper(IJeiHelpers jeiHelpers, ItemStack input, int power, int duration) {
        super(input, power, duration);
        this.output = new ItemStack(ModuleCore.getItems().ash);
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, input);
        iIngredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = 30;
        int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT * 2;
        RenderUtils.drawTextAlignedLeft(this.power + "RF/t", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 1;
        RenderUtils.drawTextAlignedLeft(this.duration + "ticks", xPos, yPos, Color.gray.getRGB());

        RenderUtils.drawTextAlignedRight(this.duration + "/7500", recipeWidth - 6, recipeHeight / 2 + 9, Color.gray.getRGB());
    }

    public ItemStack getOutput(){
        return output;
    }

}
