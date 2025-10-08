package io.bluebeaker.mteenoughitems.jei.thermal;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalfoundation.init.TFItems;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.jei.thermal.accessors.TapperAccessor;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreeFluidFuelCategory extends GenericRecipeCategory<TreeFluidFuelCategory.Wrapper> {
    public static final String UID = Categories.Thermal.TREE_EXTRACTOR_FUEL_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("cofh","textures/gui/elements/scale_flame_green.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;


    public TreeFluidFuelCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);

        Item item = TFItems.itemFertilizer;
        this.icon = item==null?null:guiHelper.createDrawableIngredient(new ItemStack(item,1,2));

        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 0, 0, 16, 16);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 16, 0, 16, 16).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2-9);
        guiItemStackGroup.set(iIngredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Thermal.TREE_EXTRACTOR_FUEL;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.ThermalExpansion.name;
    }

    public static List<Wrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<Wrapper> recipes = new ArrayList<>();
        TObjectIntHashMap<ComparableItemStack> map = TapperAccessor.fertilizerMap.get(null);
        if(map!=null){
            for (ComparableItemStack stack : map.keySet()) {
                recipes.add(new Wrapper(new ItemStack(stack.item,1,stack.metadata),map.get(stack)));
            }
        }
        return recipes;
    }

    public static class Wrapper extends FuelRecipeWrapper {

        public Wrapper(ItemStack stack, int multiplier) {
            super(stack, multiplier, 0);
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth / 2;
            int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT;

            RenderUtils.drawTextAlignedMiddle(String.format("%dx",this.power), xPos, yPos, Color.gray.getRGB());
        }
    }
}
