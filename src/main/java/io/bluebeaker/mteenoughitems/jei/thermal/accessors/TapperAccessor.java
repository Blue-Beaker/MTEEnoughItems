package io.bluebeaker.mteenoughitems.jei.thermal.accessors;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.core.util.ItemWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import com.google.common.collect.SetMultimap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.bluebeaker.mteenoughitems.utils.FieldAccessor;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class TapperAccessor {
    public static FieldAccessor<TapperManager, Map<BlockWrapper, FluidStack>> blockMap = new FieldAccessor<>(TapperManager.class, "blockMap");
    public static FieldAccessor<TapperManager, Map<ItemWrapper, FluidStack>> itemMap = new FieldAccessor<>(TapperManager.class, "itemMap");
    public static FieldAccessor<TapperManager, SetMultimap<BlockWrapper, BlockWrapper>> leafMap = new FieldAccessor<>(TapperManager.class, "leafMap");
    public static FieldAccessor<TapperManager, TObjectIntHashMap<ComparableItemStack>> fertilizerMap = new FieldAccessor<>(TapperManager.class, "fertilizerMap");
}
