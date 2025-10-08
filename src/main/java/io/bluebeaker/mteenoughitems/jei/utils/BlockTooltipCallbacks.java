package io.bluebeaker.mteenoughitems.jei.utils;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class BlockTooltipCallbacks {
    Map<Integer, Set<IBlockState>> blockStates = new HashMap<>();
    public BlockTooltipCallbacks(){
    }
    public BlockTooltipCallbacks add(int i, IBlockState state){
        if(!blockStates.containsKey(i)){
            blockStates.put(i,new HashSet<>());
        }
        blockStates.get(i).add(state);
        return this;
    }

    public void onTooltip(int i, List<String> list){
        Set<IBlockState> states = blockStates.get(i);
        if(states!=null){
            states.forEach((b)->{
                addBlockStrToList(list, b);
            });
        }
    }

    public static void addBlockStrToList(List<String> list, IBlockState b) {
        list.add(b.toString());
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
