package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.DimensionalEdibles;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = DimensionalEdibles.MODID, name = "DimensionalEdibles", category = DimensionalEdibles.MODID)
public class ModConfig {

    public static General general = new General();
    public static Tweaks tweaks = new Tweaks();

    public static class General {
        @Config.Comment("Set to true to enable End Cake.")
        public boolean endCake = true;
        @Config.Comment("Set to true to enable Nether Cake.")
        public boolean netherCake = true;
        @Config.Comment("Set to true to enable Overworld Cake.")
        public boolean overworldCake = true;
        @Config.Comment("Set to true to enable Ender Cake.")
        public boolean enderApple = true;
        @Config.Comment("Set to true to enable Nether Cake.")
        public boolean netherApple = true;
        @Config.Comment("Set to true to enable Overworld Cake.")
        public boolean overworldApple = true;
    }

    public static class Tweaks {
        @Config.Comment("Set the fuel used by End Cake (Don't change this unless you know what you're doing).")
        public String endCakeFuel = "minecraft:ender_eye";
        @Config.Comment("Set the fuel used by Nether Cake (Don't change this unless you know what you're doing).")
        public String netherCakeFuel = "minecraft:obsidian";
        @Config.Comment("Set the fuel used by Overworld Cake (Don't change this unless you know what you're doing).")
        public String overworldCakeFuel = "minecraft:sapling";
    }

    @Mod.EventBusSubscriber
    public static class ConfigHolder {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(DimensionalEdibles.MODID)) {
                ConfigManager.sync(DimensionalEdibles.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
