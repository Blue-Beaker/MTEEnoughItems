package io.bluebeaker.mteenoughitems.jei.generic;

import io.bluebeaker.mteenoughitems.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public abstract class FluidPowerRecipeCategory<T extends FluidPowerRecipeWrapper> extends GenericRecipeCategory<T> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableAnimated arrow;

    public FluidPowerRecipeCategory(IGuiHelper guiHelper) {
        this(guiHelper,116,32);
    }
    public FluidPowerRecipeCategory(IGuiHelper guiHelper, int width, int height) {
        super(guiHelper,width,height);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 0, 24, 17);
        this.arrow = guiHelper.drawableBuilder(Constants.GUI_0, 24, 0, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 34, 8);
        this.arrow.draw(minecraft, 34, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8,GUI_HEIGHT/2-9);
        guiFluidStackGroup.set(0,wrapper.getFluidStack());
    }
}
