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
        @Config.Comment("Set to true to enable custom Cakes.")
        public boolean customCake = true;
        @Config.Comment("Set to true to enable Ender Cake.")
        public boolean enderApple = true;
        @Config.Comment("Set to true to enable Nether Cake.")
        public boolean netherApple = true;
        @Config.Comment("Set to true to enable Overworld Cake.")
        public boolean overworldApple = true;
        @Config.Comment("Set to true to enable Custom Cake.")
        public boolean customApple = true;
    }

    public static class Tweaks {

        public EndCake endCake = new EndCake();
        public static class EndCake {
            @Config.Comment("Set the fuel used by End Cake (Don't change this unless you know what you're doing).")
            public String fuel = "minecraft:ender_eye";
            @Config.Comment("Set to true to make the End Cake pre-fueled upon placed.")
            public boolean preFueled = false;
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, spawn platform will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public EnderApple enderApple = new EnderApple();
        public static class EnderApple {
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, spawn platform will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public NetherCake netherCake = new NetherCake();
        public static class NetherCake {
            @Config.Comment("Set the fuel used by Nether Cake (Don't change this unless you know what you're doing).")
            public String fuel = "minecraft:obsidian";
            @Config.Comment("Set to true to make the Nether Cake pre-fueled upon placed.")
            public boolean preFueled = false;
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, x0 z0 will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public NetherApple netherApple = new NetherApple();
        public static class NetherApple {
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, x0 z0 will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public OverworldCake overworldCake = new OverworldCake();
        public static class OverworldCake {
            @Config.Comment("Set the fuel used by Overworld Cake (Don't change this unless you know what you're doing).")
            public String fuel = "minecraft:sapling";
            @Config.Comment("Set to true to make the Overworld Cake pre-fueled upon placed.")
            public boolean preFueled = false;
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, world spawn point will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public OverworldApple overworldApple = new OverworldApple();
        public static class OverworldApple {
            @Config.Comment({"Set to true to use custom coordinates for the teleportation.", "Otherwise, world spawn point will be used by default."})
            public boolean useCustomCoords = false;
            public CustomCoords customCoords = new CustomCoords();

            public static class CustomCoords {
                public double x = 0.0D;
                public double y = 64.0D;
                public double z = 0.0D;
            }
        }

        public CustomEdible customEdible = new CustomEdible();
        public static class CustomEdible {
            @Config.Comment({"Set a list of dimensions to add cakes / apples for.", "Format : <Dimension ID>, <Cake Name>", "NOTE: \"Cake\" is auto appended onto the end of the item and block name"})
            public String[] dimensions = new String[0];
        }

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
