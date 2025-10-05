package io.bluebeaker.mteenoughitems.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static ItemStack getItemstackFromBlockState(IBlockState input) {
        Item item = ItemBlock.getItemFromBlock(input.getBlock());
        return new ItemStack(item, 1, input.getBlock().getMetaFromState(input));
    }

    public static List<ItemStack> getStacksForIngredient(Ingredient ingredient){
        return Arrays.asList(ingredient.matchingStacks);
    }
    public static String getStackRepresentation(ItemStack stack){
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItem().getRegistryName());
        builder.append("@").append(stack.getMetadata());
        if(stack.getCount()!=1)
            builder.append("*").append(stack.getCount());
        return builder.toString();
    }
}
