package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FluidFireboxCategory extends FluidPowerRecipeCategory<FluidFireboxWrapper> {
    protected final IDrawableStatic bgFire;
    protected final IDrawableAnimated fire;
    public static final String UID = Categories.Railcraft.BOILER_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("railcraft","textures/gui/gui_boiler_liquid.png");

    public FluidFireboxCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgFire = guiHelper.createDrawable(GUI_PATH, 62, 38, 14, 14);
        this.fire = guiHelper.drawableBuilder(GUI_PATH, 176, 47, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Railcraft.BOILER;
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
}
