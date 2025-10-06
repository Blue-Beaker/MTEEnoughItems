package io.bluebeaker.mteenoughitems.jei.thermal.accessors;

import cofh.core.fluid.BlockFluidInteractive;
import io.bluebeaker.mteenoughitems.utils.FieldAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.HashMap;

public class FluidInteractiveAccessor {
    public static FieldAccessor<BlockFluidInteractive, HashMap<IBlockState, IBlockState>> collisionMap = new FieldAccessor<>(BlockFluidInteractive.class, "collisionMap");
    public static FieldAccessor<BlockFluidInteractive, HashMap<Block, IBlockState>> anyState = new FieldAccessor<>(BlockFluidInteractive.class, "anyState");
}
