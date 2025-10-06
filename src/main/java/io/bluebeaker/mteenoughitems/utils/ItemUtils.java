package io.bluebeaker.mteenoughitems.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
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

    @Nullable
    public static Item getItemById(ResourceLocation id){
        return Item.REGISTRY.getObject(id);
    }
    @Nullable
    public static Item getItemById(String id){
        return getItemById(new ResourceLocation(id));
    }

    public static ItemStack getItemstack(String modid, String id){
        Item item = getItemById(new ResourceLocation(modid,id));
        if(item==null) return ItemStack.EMPTY;
        return new ItemStack(item);
    }
    public static ItemStack getItemstack(String modid, String id, int meta){
        ItemStack stack = getItemstack(modid,id);
        if(stack.isEmpty()) return stack;
        stack.setItemDamage(meta);
        return stack;
    }
}
