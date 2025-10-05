package io.bluebeaker.mteenoughitems.jei.buildcraft;

import buildcraft.api.BCItems;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.misc.ItemStackKey;
import buildcraft.lib.recipe.ChangingItemStack;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.silicon.plug.FacadeBlockStateInfo;
import buildcraft.silicon.plug.FacadeStateManager;
import buildcraft.silicon.recipe.FacadeAssemblyRecipes;
import forestry.energy.ModuleEnergy;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.jei.MTEEIPlugin;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.ItemUtils;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FacadeAssemblyCategory extends GenericRecipeCategory<FacadeAssemblyCategory.Wrapper> {
    public static final String UID = Categories.BuildCraft.FACADE_ASSEMBLY_UID;
    protected static final ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
    private final IDrawable background;
    private final IDrawable progressBar;
    // Structural pipe or cobble wall - cached for later use!
    private static ItemStack baseRequirementStack = null;

    public FacadeAssemblyCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.background = guiHelper.drawableBuilder(backgroundLocation, 5, 34, 166, 76).addPadding(10,0,0,0).build();
        progressBar = guiHelper.drawableBuilder(backgroundLocation, 176, 48, 4, 71).addPadding(10,0,0,0).buildAnimated(10, IDrawableAnimated.StartDirection.BOTTOM, false);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCSiliconBlocks.assemblyTable));
    }

    @Override
    public String getTranslationKey() {
        return Categories.BuildCraft.FACADE_ASSEMBLY;
    }

    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.BuildcraftSilicon.name;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.progressBar.draw(minecraft, 81, 2);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);

        for(int i = 0; i < inputs.size(); ++i) {
            guiItemStacks.init(i, true, 2 + i % 3 * 18, 11 + i / 3 * 18);
            guiItemStacks.set(i, inputs.get(i));
        }

        guiItemStacks.init(12, false, 110, 11);
        guiItemStacks.set(12, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    public static List<Wrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<Wrapper> recipes = new ArrayList<>();
        // Getting all items from FacadeBlockStateInfo for performance
        for (FacadeBlockStateInfo info : FacadeStateManager.validFacadeStates.values()) {
            if (info.isVisible) {
                recipes.add(new Wrapper(getInputsFor(info),(FacadeAssemblyRecipes.createFacadeStack(info, false)),64*MjAPI.MJ));
                recipes.add(new Wrapper(getInputsFor(info),(FacadeAssemblyRecipes.createFacadeStack(info, true)),64*MjAPI.MJ));
            }
        }

        return recipes;
    }

    public static List<ItemStack> getInputsFor(FacadeBlockStateInfo info){
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(info.requiredStack);
        // Cache for later use!
        if(baseRequirementStack==null){
            if (BCItems.Transport.PIPE_STRUCTURE == null) {
                baseRequirementStack = new ItemStack(Blocks.COBBLESTONE_WALL);
            }else {
                baseRequirementStack = new ItemStack(BCItems.Transport.PIPE_STRUCTURE, 3);
            }
        }
        stacks.add(baseRequirementStack);
        return stacks;
    }

    public static class Wrapper implements IRecipeWrapper {
        public final List<ItemStack> inputs;
        public final ItemStack output;
        public final long energy;
        public Wrapper(List<ItemStack> inputs, ItemStack output, long energy) {
            this.inputs = inputs;
            this.output = output;
            this.energy = energy;
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            List<List<ItemStack>> inputIngredients = new ArrayList<>();
//            for (IngredientStack input : inputs) {
//                List<ItemStack> stacks = ItemUtils.getStacksForIngredient(input.ingredient);
//                stacks.forEach((stack -> stack.setCount(input.count)));
//                inputIngredients.add(stacks);
//            }
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
            ingredients.setOutput(VanillaTypes.ITEM, output);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            RenderUtils.drawTextAlignedLeft(MjAPI.formatMj(energy) + " MJ", 4, 0, Color.gray.getRGB());
        }
    }
}
