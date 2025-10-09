package io.bluebeaker.mteenoughitems.jei.immersiveengineering;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.common.Config;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalMultiblock;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeCategory;
import io.bluebeaker.mteenoughitems.jei.generic.FluidPowerRecipeWrapper;
import io.bluebeaker.mteenoughitems.utils.EnergyUnit;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DieselGeneratorCategory extends FluidPowerRecipeCategory<DieselGeneratorCategory.Wrapper> {
    public static final String UID = Categories.ImmersiveEngineering.DIESEL_GENERATOR_UID;

    public DieselGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(IEContent.blockMetalMultiblock, 1, BlockTypes_MetalMultiblock.DIESEL_GENERATOR.getMeta()));
    }

    @Override
    public String getModName() {
        return ModChecker.ImmersiveEngineering.name;
    }

    @Override
    public String getTranslationKey() {
        return "tile.immersiveengineering.metal_multiblock.diesel_generator.name";
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    public static List<Wrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<Wrapper> recipes = new ArrayList<>();
        Map<String, Integer> fuelValues = DieselHandler.getFuelValuesSorted(false);
        for (Map.Entry<String, Integer> entry : fuelValues.entrySet()) {
            String name = entry.getKey();
            int time = entry.getValue();
            Fluid fluid1 = FluidRegistry.getFluid(name);
            if(fluid1==null) continue;
            int power = Config.IEConfig.Machines.dieselGen_output;
            recipes.add(new Wrapper(jeiHelpers,new FluidStack(fluid1,1000/time),power, time));
        }

        return recipes;
    }

    public static class Wrapper extends FluidPowerRecipeWrapper {
        public Wrapper(IJeiHelpers jeiHelpers, FluidStack fluid, long power, long time) {
            super(jeiHelpers, fluid, power, time);
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth - 8;
            int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT;

            RenderUtils.drawTextAlignedRight(this.power+getPowerUnit()+"/t", xPos, yPos, Color.gray.getRGB());
            yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

            RenderUtils.drawTextAlignedRight(this.energy+"t/B", xPos, yPos, Color.gray.getRGB());
        }

        @Override
        public String getPowerUnit() {
            return EnergyUnit.RF.name;
        }
    }
}
