package io.bluebeaker.mteenoughitems.jei.thermal;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.thermalfoundation.init.TFFluids;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.Constants;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.utils.ItemUtils;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.List;

public class FluidConversionCategory extends GenericRecipeCategory<FluidConversionCategory.Wrapper> {
    public static final String UID = Categories.Thermal.FLUID_CONVERSION_UID;
    protected final IDrawableStatic bgArrow;

    public FluidConversionCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 0, 24, 17);
        this.icon = guiHelper.createDrawableIngredient(FluidUtil.getFilledBucket(new FluidStack(TFFluids.fluidMana,1000)));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        int slotY = GUI_HEIGHT / 2 - 9;
        this.addFluidSlot(fluidStacks,0,8, slotY);
        fluidStacks.set(0,new FluidStack(wrapper.fluid,1000));

        if(wrapper.inputFluid!=null){
            this.addFluidSlot(fluidStacks,1,32, slotY);
            fluidStacks.set(1,new FluidStack(wrapper.inputFluid,1000));
        }else {
            this.addItemSlot(itemStacks,1,32, slotY);
            itemStacks.set(1,wrapper.inputItem);
        }

        if(wrapper.outputFluid!=null){
            this.addFluidSlot(fluidStacks,2,GUI_WIDTH-26, slotY);
            fluidStacks.set(2,new FluidStack(wrapper.outputFluid,1000));
        }else {
            this.addItemSlot(itemStacks,2,GUI_WIDTH-26, slotY);
            itemStacks.set(2,wrapper.outputItem);
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 54, 8);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Thermal.FLUID_CONVERSION;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return ModChecker.ThermalFoundation.name;
    }

    public static List<Wrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<Wrapper> recipes = new ArrayList<>();
        recipes.addAll(getRecipesForBlock((BlockFluidInteractive) TFFluids.blockFluidCryotheum));
        recipes.addAll(getRecipesForBlock((BlockFluidInteractive) TFFluids.blockFluidPetrotheum));
        recipes.addAll(getRecipesForBlock((BlockFluidInteractive) TFFluids.blockFluidPyrotheum));
        recipes.addAll(getRecipesForBlock((BlockFluidInteractive) TFFluids.blockFluidMana));
        return recipes;
    }

    protected static List<Wrapper> getRecipesForBlock(BlockFluidInteractive fluidBlock){
        List<Wrapper> recipes = new ArrayList<>();
        // Access the maps
        HashMap<IBlockState, IBlockState> collisionMap = ThermalAccessors.collisionMap.get(fluidBlock);
        HashMap<Block, IBlockState> anyStateMap = ThermalAccessors.anyState.get(fluidBlock);
        if(collisionMap==null || anyStateMap==null) return recipes;

        Set<Block> blocks = new HashSet<>(anyStateMap.keySet());
        collisionMap.keySet().stream().map(IBlockState::getBlock).forEach(blocks::add);

        for (Block block1 : blocks) {
            if(FluidRegistry.lookupFluidForBlock(block1)!=null){
                addWrapperForFluid(recipes,fluidBlock,block1.getStateFromMeta(0));
                addWrapperForFluid(recipes,fluidBlock,block1.getStateFromMeta(1));
            }else {
                Item item = ItemBlock.getItemFromBlock(block1);
                if(item ==Items.AIR) continue;
                NonNullList<ItemStack> items = NonNullList.create();
                block1.getSubBlocks(CreativeTabs.BUILDING_BLOCKS,items);
                if(items.isEmpty()){
                    items.add(new ItemStack(block1));
                }
                for (ItemStack inputItem : items) {
                    IBlockState input = block1.getStateFromMeta(inputItem.getItem().getMetadata(inputItem.getMetadata()));
                    addWrapperForFluid(recipes,fluidBlock,input,inputItem,null);
                }
            }
        }

        return recipes;
    }
    protected static void addWrapperForFluid(List<Wrapper> wrappers, BlockFluidInteractive fluid, IBlockState input){
        addWrapperForFluid(wrappers,fluid,input,null,null);
    }
    protected static void addWrapperForFluid(List<Wrapper> wrappers, BlockFluidInteractive fluid, IBlockState input, @Nullable ItemStack inItem, @Nullable ItemStack outItem){
        IBlockState output = fluid.getInteraction(input);
        if(output==null) return;
        wrappers.add(new Wrapper(fluid.getFluid(),input, output,inItem,outItem));
    }

    public static class Wrapper implements IRecipeWrapper {
        public final Fluid fluid;
        public final ItemStack inputItem;
        public final ItemStack outputItem;
        public final Fluid inputFluid;
        public final Fluid outputFluid;
        public Wrapper(Fluid fluid, IBlockState input, IBlockState output, @Nullable ItemStack inItem, @Nullable ItemStack outItem) {
            super();
            this.fluid = fluid;
            Block inputBlock = input.getBlock();
            Block outputBlock = output.getBlock();

            inputFluid = FluidRegistry.lookupFluidForBlock(inputBlock);
            outputFluid = FluidRegistry.lookupFluidForBlock(outputBlock);
            
            inputItem = inItem!=null?inItem: ItemUtils.getItemstackFromBlockState(input);
            outputItem = outItem!=null?outItem: ItemUtils.getItemstackFromBlockState(output);
        }

        @Override
        public void getIngredients(IIngredients iIngredients) {
            List<FluidStack> inputFluids = new ArrayList<>();
            inputFluids.add(new FluidStack(fluid,1000));
            if(inputFluid!=null){
                inputFluids.add(new FluidStack(inputFluid,1000));
            }else {
                iIngredients.setInput(VanillaTypes.ITEM,inputItem);
            }
            iIngredients.setInputs(VanillaTypes.FLUID,inputFluids);
            if(outputFluid!=null){
                iIngredients.setOutput(VanillaTypes.FLUID,new FluidStack(outputFluid,1000));
            }else {
                iIngredients.setOutput(VanillaTypes.ITEM,outputItem);
            }
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth / 2;
            int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT;

        }
    }
}
