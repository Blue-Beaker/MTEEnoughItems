package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mods.railcraft.api.crafting.ISimpleRecipe;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.util.crafting.BlastFurnaceCrafter;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlastFurnaceFuelCategory extends GenericRecipeCategory<BlastFurnaceFuelCategory.BlastFurnaceFuelRecipeWrapper> {
    public static final String UID = Categories.Railcraft.BLAST_FURNACE_FUEL_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("railcraft","textures/gui/gui_blast_furnace.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public BlastFurnaceFuelCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 56, 36, 14, 14);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 176, 0, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);

        ItemBlock item = RailcraftBlocks.BLAST_FURNACE.item();
        this.icon = item==null?null:guiHelper.createDrawableIngredient(new ItemStack(item));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BlastFurnaceFuelRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        guiItemStackGroup.set(0,wrapper.getInput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Railcraft.BLAST_FURNACE_FUEL;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.Forestry.name;
    }

    public static List<BlastFurnaceFuelRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<BlastFurnaceFuelRecipeWrapper> recipes = new ArrayList<>();

        for (ISimpleRecipe recipe : BlastFurnaceCrafter.INSTANCE.getFuels()) {
            for (ItemStack matchingStack : recipe.getInput().getMatchingStacks()) {
                recipes.add(new BlastFurnaceFuelRecipeWrapper(matchingStack,recipe.getTickTime(matchingStack)/5));
            }
        }

        return recipes;
    }

    public static class BlastFurnaceFuelRecipeWrapper extends FuelRecipeWrapper {

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
}
