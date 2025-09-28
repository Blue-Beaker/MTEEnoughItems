package io.bluebeaker.mteenoughitems.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static ItemStack getItemstackFromBlockState(IBlockState input) {
        Item item = ItemBlock.getItemFromBlock(input.getBlock());
        return new ItemStack(item, 1, input.getBlock().getMetaFromState(input));
    }
}
