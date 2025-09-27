package io.bluebeaker.mteenoughitems.jei.generic;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import io.bluebeaker.mteenoughitems.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class FluidHeatConversionCategory<T extends FluidHeatConversionRecipe> extends GenericRecipeCategory<T> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableAnimated arrow;
    public FluidHeatConversionCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 17, 48, 10);
        this.arrow = guiHelper.drawableBuilder(Constants.GUI_0, 48, 17, 48, 10).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 34, GUI_HEIGHT/2);
        this.arrow.draw(minecraft, 34, GUI_HEIGHT/2);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8,GUI_HEIGHT/2-9);
        guiFluidStackGroup.set(0,wrapper.inputStack);
        this.addFluidSlot(guiFluidStackGroup,1,this.GUI_WIDTH-24,GUI_HEIGHT/2-9);
        guiFluidStackGroup.set(1,wrapper.outputStack);
    }

    public static List<FluidHeatConversionRecipe> getRecipes(IJeiHelpers jeiHelpers, ILiquidHeatExchangerManager manager, @Nullable ILiquidAcceptManager acceptManager){

        List<FluidHeatConversionRecipe> recipes = new ArrayList<>();
        for (Fluid inputFluid : manager.getAcceptedFluids()) {
            if(acceptManager!=null && !acceptManager.acceptsFluid(inputFluid))
                continue;
            ILiquidHeatExchangerManager.HeatExchangeProperty property = manager.getHeatExchangeProperty(inputFluid);
            recipes.add(new FluidHeatConversionRecipe(jeiHelpers,inputFluid,property.outputFluid,property.huPerMB));
        }

        return recipes;
    }

}
