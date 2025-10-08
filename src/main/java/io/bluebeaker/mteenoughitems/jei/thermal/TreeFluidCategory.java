package io.bluebeaker.mteenoughitems.jei.thermal;

import cofh.core.util.BlockWrapper;
import cofh.thermalexpansion.block.device.BlockDevice;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import com.google.common.collect.ImmutableList;
import io.bluebeaker.mteenoughitems.Categories;
import io.bluebeaker.mteenoughitems.Constants;
import io.bluebeaker.mteenoughitems.jei.generic.GenericRecipeCategory;
import io.bluebeaker.mteenoughitems.jei.thermal.accessors.TapperAccessor;
import io.bluebeaker.mteenoughitems.jei.utils.BlockTooltipCallbacks;
import io.bluebeaker.mteenoughitems.utils.BlockDropChecker;
import io.bluebeaker.mteenoughitems.utils.ModChecker;
import io.bluebeaker.mteenoughitems.utils.thermal.ThermalUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class TreeFluidCategory extends GenericRecipeCategory<TreeFluidCategory.Wrapper> {
    public static final String UID = Categories.Thermal.TREE_EXTRACTOR_UID;
    protected final IDrawableStatic bgArrow;

    public TreeFluidCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,48);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 0, 24, 17);
        this.icon = guiHelper.createDrawableIngredient(BlockDevice.deviceTapper);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        int slotY = GUI_HEIGHT / 2;

        this.addItemSlot(itemStacks,0,16, slotY);
        itemStacks.set(0,wrapper.inputLog);

        this.addItemSlot(itemStacks,1,16, slotY-18);
        itemStacks.set(1,wrapper.inputLeaves);

        this.addFluidSlot(fluidStacks,2,GUI_WIDTH-34, slotY);
        fluidStacks.set(2,new FluidStack(wrapper.outputFluid,1000));

        BlockTooltipCallbacks callbacks = new BlockTooltipCallbacks().add(0,wrapper.logBlock);
        for (IBlockState leavesBlock : wrapper.leavesBlocks) {
            callbacks.add(1,leavesBlock);
        }
        itemStacks.addTooltipCallback(callbacks.getItemCallback());
        fluidStacks.addTooltipCallback(callbacks.getFluidCallback());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 46, GUI_HEIGHT/2);
    }

    @Override
    public String getTranslationKey() {
        return Categories.Thermal.TREE_EXTRACTOR;
    }
    @Override
    public String getTitle() {
        return BlockDevice.deviceTapper.getDisplayName();
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
        // Access the maps
        Map<BlockWrapper, FluidStack> blockWrapperFluidStackMap = TapperAccessor.blockMap.get(null);
        if(blockWrapperFluidStackMap!=null){
            for (Map.Entry<BlockWrapper, FluidStack> entry : blockWrapperFluidStackMap.entrySet()) {
                BlockWrapper key = entry.getKey();
                Set<BlockWrapper> leaves = TapperManager.getLeaf(key.block.getStateFromMeta(key.metadata));
                FluidStack output = entry.getValue();

                recipes.add(new Wrapper(key,leaves,output));
            }
        }

        return recipes;
    }

    public static class Wrapper implements IRecipeWrapper {
        public final IBlockState logBlock;
        public final List<IBlockState> leavesBlocks = new ArrayList<>();
        public final ItemStack inputLog;
        public final List<ItemStack> inputLeaves = new ArrayList<>();
        public final FluidStack outputFluid;
        public Wrapper(BlockWrapper log, Set<BlockWrapper> leaves, FluidStack output) {
            super();
            logBlock=ThermalUtils.getBlockstateFromWrapper(log);
            leaves.forEach((b)->{
                IBlockState state = ThermalUtils.getBlockstateFromWrapper(b);
                leavesBlocks.add(state);
                inputLeaves.add(BlockDropChecker.getDrop(state));
            });
            inputLog = BlockDropChecker.getDrop(logBlock);
            this.outputFluid = output;
        }

        @Override
        public void getIngredients(IIngredients iIngredients) {
            iIngredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(Collections.singletonList(inputLog),inputLeaves));
            iIngredients.setOutput(VanillaTypes.FLUID,outputFluid);
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth / 2;
            int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT;

        }
    }
}
