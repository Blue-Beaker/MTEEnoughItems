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
        @LangKey(Categories.Forestry.PEAT_ENGINE)
        public boolean peat_engine = true;
        @Config.RequiresMcRestart
        @LangKey(Categories.Forestry.BIOGAS_ENGINE)
        public boolean biogas_engine = true;
        @Config.RequiresMcRestart
        @LangKey(Categories.Forestry.BIO_GENERATOR)
        public boolean bio_generator = true;
    }
    @LangKey("config.mteenoughitems.railcraft.name")
    public static Railcraft railcraft = new Railcraft();
    public static class Railcraft{
        @Config.RequiresMcRestart
        @LangKey(Categories.Railcraft.FLUID_FIREBOX)
        public boolean fluid_firebox = true;
        @Config.RequiresMcRestart
        @LangKey(Categories.Railcraft.BOILER)
        public boolean boiler = true;
    }
}