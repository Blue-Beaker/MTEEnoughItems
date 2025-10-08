package io.bluebeaker.mteenoughitems.utils;

import io.bluebeaker.mteenoughitems.MTEEnoughItems;
import io.bluebeaker.mteenoughitems.utils.world.DummyWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class BlockDropChecker {
    public static DummyWorld world = new DummyWorld();
    public static BlockPos pos = new BlockPos(0,63,0);
    public static ItemStack getDrop(IBlockState state){
        try {
            world.setBlockState(pos,state, 20);
            return state.getBlock().getItem(world,pos,state);
        } catch (Throwable e) {
            MTEEnoughItems.getLogger().error("Error getting item for blockstate {}:",state,e);
        }
        return ItemStack.EMPTY;
    }
}
