package io.bluebeaker.mteenoughitems.jei.buildcraft;

import buildcraft.silicon.BCSiliconItems;
import buildcraft.silicon.gate.GateVariant;
import buildcraft.silicon.item.ItemPluggableGate;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;

public class GateSubTypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        GateVariant variant = ItemPluggableGate.getVariant(itemStack);
        return variant.getVariantName();
    }

    public void register(ISubtypeRegistry subtypeRegistry){
        if(BCSiliconItems.plugGate!=null)
            subtypeRegistry.registerSubtypeInterpreter(BCSiliconItems.plugGate,this);
    }
}
