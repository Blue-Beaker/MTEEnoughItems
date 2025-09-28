package io.bluebeaker.mteenoughitems.jei.forestry;

import forestry.api.fuels.EngineCopperFuel;
import forestry.api.fuels.FuelManager;
import forestry.core.ModuleCore;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PeatEngineCategory extends GenericRecipeCategory<PeatEngineCategory.PeatEngineRecipeWrapper> {
    public static final String UID = Categories.Forestry.PEAT_ENGINE_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("forestry","textures/gui/peatengine.png");
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public PeatEngineCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgHeatBar = guiHelper.createDrawable(GUI_PATH, 45, 27, 14, 14);
        this.heatBar = guiHelper.drawableBuilder(GUI_PATH, 176, 0, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModuleEnergy.getBlocks().peatEngine));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PeatEngineRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        guiItemStackGroup.set(0,wrapper.getInput());
        this.addItemSlot(guiItemStackGroup,1,GUI_WIDTH-24,GUI_HEIGHT/2-9);
        guiItemStackGroup.set(1,wrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-18);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Forestry.PEAT_ENGINE;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.Forestry.name;
    }

    public static List<PeatEngineRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<PeatEngineRecipeWrapper> recipes = new ArrayList<>();
        for (ItemStack input : FuelManager.copperEngineFuel.keySet()) {
            EngineCopperFuel fuel = FuelManager.copperEngineFuel.get(input);

            recipes.add(new PeatEngineRecipeWrapper(jeiHelpers,input,fuel.getPowerPerCycle(),fuel.getBurnDuration()));
        }

        return recipes;
    }

    public static class PeatEngineRecipeWrapper extends FuelRecipeWrapper {
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
}
