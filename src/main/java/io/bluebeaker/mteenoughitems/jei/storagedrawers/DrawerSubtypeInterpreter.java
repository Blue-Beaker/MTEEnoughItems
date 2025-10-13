package io.bluebeaker.mteenoughitems.jei.storagedrawers;

import io.bluebeaker.mteenoughitems.utils.ModChecker;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public class DrawerSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTagCompound();
        if(tag!=null && tag.hasKey("material",8)){
            return tag.getString("material")+"_"+itemStack.getMetadata();
        }
        return String.valueOf(itemStack.getMetadata());
    }
    public void register(ISubtypeRegistry subtypeRegistry) {
        Optional.ofNullable(Item.REGISTRY.getObject(new ResourceLocation(ModChecker.StorageDrawers.name, "basicdrawers"))).ifPresent((i)->{subtypeRegistry.registerSubtypeInterpreter(i,this);});
        Optional.ofNullable(Item.REGISTRY.getObject(new ResourceLocation(ModChecker.StorageDrawersExtra.name, "extra_drawers"))).ifPresent((i)->{subtypeRegistry.registerSubtypeInterpreter(i,this);});
    }
}
