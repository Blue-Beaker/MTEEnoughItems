package io.bluebeaker.mteenoughitems.utils.thermal;

import cofh.core.util.BlockWrapper;
import net.minecraft.block.state.IBlockState;

public class ThermalUtils {
    public static IBlockState getBlockstateFromWrapper(BlockWrapper wrapper){
        return wrapper.block.getStateFromMeta(wrapper.metadata);
    }
}
