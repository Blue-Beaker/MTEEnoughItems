package io.bluebeaker.mteenoughitems.jei.utils;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockTooltipCallbacks {
    Map<Integer, IBlockState> blockStates = new HashMap<>();
    public BlockTooltipCallbacks(){
    }
    public BlockTooltipCallbacks add(int i, IBlockState state){
        blockStates.put(i,state);
        return this;
    }
    public void onTooltip(int i, List<String> list){
        IBlockState state = blockStates.get(i);
        if(state!=null){
            list.add(state.toString());
        }
    }

    public ITooltipCallback<ItemStack> getItemCallback(){
        return new ITooltipCallback<ItemStack>() {
            @Override
            public void onTooltip(int i, boolean b, ItemStack stack, List<String> list) {
                BlockTooltipCallbacks.this.onTooltip(i,list);
            }
        };
    }
    public ITooltipCallback<FluidStack> getFluidCallback(){
        return new ITooltipCallback<FluidStack>() {
            @Override
            public void onTooltip(int i, boolean b, FluidStack fluidStack, List<String> list) {
                BlockTooltipCallbacks.this.onTooltip(i,list);
            }
        };
    }
}
