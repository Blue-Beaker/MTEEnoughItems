package io.bluebeaker.mteenoughitems.utils.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class BlockDropChecker {
    public static DummyWorld world = new DummyWorld();
    public static BlockPos pos = new BlockPos(0,63,0);
    public static ItemStack getDrop(IBlockState state){
        world.setBlockState(pos,state);
        return state.getBlock().getItem(world,pos,state);
    }
}
