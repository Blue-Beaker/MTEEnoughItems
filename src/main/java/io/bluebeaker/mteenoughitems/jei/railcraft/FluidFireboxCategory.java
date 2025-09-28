package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mods.railcraft.api.fuel.FluidFuelManager;
import mods.railcraft.common.blocks.RailcraftBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FluidFireboxCategory extends FluidPowerRecipeCategory<FluidFireboxCategory.FluidFireboxWrapper> {
    protected final IDrawableStatic bgFire;
    protected final IDrawableAnimated fire;
    public static final String UID = Categories.Railcraft.FLUID_FIREBOX_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("railcraft","textures/gui/gui_boiler_liquid.png");

    public FluidFireboxCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgFire = guiHelper.createDrawable(GUI_PATH, 62, 38, 14, 14);
        this.fire = guiHelper.drawableBuilder(GUI_PATH, 176, 47, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);

        ItemBlock item = RailcraftBlocks.BOILER_FIREBOX_FLUID.item();
        this.icon = item==null?null:guiHelper.createDrawableIngredient(new ItemStack(item));
    }

    @Override
    public String getModName() {
        return ModChecker.Railcraft.name;
    }

    @Override
    public String getTranslationKey() {
        return Categories.Railcraft.FLUID_FIREBOX;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgFire.draw(minecraft, 34, 8);
        this.fire.draw(minecraft, 34, 8);
    }

    public static List<FluidFireboxWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<FluidFireboxWrapper> recipes = new ArrayList<>();
        try {
            // Try to get fuels by reflection at first
            Field boilerFuel = FluidFuelManager.class.getDeclaredField("boilerFuel");
            boilerFuel.setAccessible(true);
            Object obj = boilerFuel.get(null);
            if(obj instanceof Map){
                Map<FluidStack,Integer> map = (Map<FluidStack, Integer>) obj;
                for (FluidStack fluid: map.keySet()) {
                    recipes.add(new FluidFireboxWrapper(jeiHelpers,fluid.getFluid(), 1, map.get(fluid)));
                }
            }
        } catch (Throwable e) {
            MTEEnoughItems.getLogger().error("Error loading recipes for FluidFireboxCategory: ",e);
            // Fallback to iterate through all fluids on failure
            for (Fluid fluid: FluidRegistry.getRegisteredFluids().values()) {
                int value = FluidFuelManager.getFuelValue(new FluidStack(fluid,1000));
                if(value<=0) continue;
                recipes.add(new FluidFireboxWrapper(jeiHelpers,fluid, 1, value));
            }
        }
        return recipes;
    }

    public static class FluidFireboxWrapper extends FluidPowerRecipeWrapper {
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
}
