package io.bluebeaker.mteenoughitems.jei.storagedrawers;

import io.bluebeaker.mteenoughitems.MTEEnoughItemsConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class StorageDrawersPlugin implements IModPlugin {
    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if(MTEEnoughItemsConfig.storageDrawers.drawer_subtypes)
            new DrawerSubtypeInterpreter().register(subtypeRegistry);
    }
}
