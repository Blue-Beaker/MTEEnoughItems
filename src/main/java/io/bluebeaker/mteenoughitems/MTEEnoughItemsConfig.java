package io.bluebeaker.mteenoughitems;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = MTEEnoughItems.MODID,type = Type.INSTANCE,category = "general")
public class MTEEnoughItemsConfig {
    @LangKey("config.mteenoughitems.forestry.name")
    public static Forestry forestry = new Forestry();
    public static class Forestry{
        @Config.RequiresMcRestart
        @LangKey(Categories.Forestry.BIOGAS_ENGINE)
        public boolean biogas_engine = true;
        @Config.RequiresMcRestart
        @LangKey(Categories.Forestry.BIO_GENERATOR)
        public boolean bio_generator = true;
    }
}