package io.bluebeaker.mteenoughitems.jei.railcraft;

import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.jei.generic.FluidHeatConversionCategory;
import io.bluebeaker.mteenoughitems.jei.generic.FluidHeatConversionRecipe;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mods.railcraft.api.fuel.FluidFuelManager;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.fluids.Fluids;
import mods.railcraft.common.util.steam.SteamConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoilerCategory extends FluidHeatConversionCategory<BoilerRecipe> {
    protected final IDrawableStatic bgFire;
    protected final IDrawableAnimated fire;

    public static final String UID = Categories.Railcraft.BOILER_UID;
    public static final ResourceLocation GUI_PATH = new ResourceLocation("railcraft","textures/gui/gui_boiler_liquid.png");

    public BoilerCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.bgFire = guiHelper.createDrawable(GUI_PATH, 62, 38, 14, 14);
        this.fire = guiHelper.drawableBuilder(GUI_PATH, 176, 47, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);

        ItemBlock item = RailcraftBlocks.BOILER_TANK_PRESSURE_LOW.item();
        this.icon = item==null?null:guiHelper.createDrawableIngredient(new ItemStack(item));
    }

    @Override
    public String getTranslationKey() {
        return Categories.Railcraft.BOILER;
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<BoilerRecipe> getRecipes(IJeiHelpers jeiHelpers){
        List<BoilerRecipe> recipes = new ArrayList<>();
        recipes.add(new BoilerRecipe(jeiHelpers,new FluidStack(FluidRegistry.WATER,1),new FluidStack(Fluids.STEAM.get(), SteamConstants.STEAM_PER_UNIT_WATER)));
        return recipes;
    }
}
