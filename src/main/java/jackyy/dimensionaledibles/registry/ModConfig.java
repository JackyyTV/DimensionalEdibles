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
            @Config.Comment("Set to true to make the End Cake consume fuel.")
            public boolean consumeFuel = true;
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment("Set to true to make the Nether Cake consume fuel.")
            public boolean consumeFuel = true;
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment("Set to true to make the Overworld Cake consume fuel.")
            public boolean consumeFuel = true;
            @Config.Comment({
                    "Set to true to make the Overworld Apple teleport players to world spawn.",
                    "Otherwise, it will use the cached position."
            })
            public boolean useWorldSpawn = true;
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment({
                    "Set to true to make the Overworld Apple teleport players to world spawn.",
                    "Otherwise, it will use the cached position)."
            })
            public boolean useWorldSpawn = true;
            @Config.Comment("Set to true to use custom coordinates for the teleportation.")
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
            @Config.Comment({
                    "Set a list of dimensions to add cakes / apples for.",
                    "Format: <Dimension ID>, <Cake / Apple Name>",
                    "Example: 0, Overworld",
                    "Note: \"Cake\" is automatically appended onto the end of the name for cakes."
            })
            public String[] dimensions = new String[0];
            @Config.Comment({
                    "Set a list of custom coordinates used by Custom Cakes / Apples, this is optional.",
                    "Format: <Dimension ID>, <X>, <Y>, <Z>",
                    "Example: 0, 420, 123, -420"
            })
            public String[] customCoords = new String[0];
            public CustomCake customCake = new CustomCake();
            public static class CustomCake {
                @Config.Comment("Set to true to make all Custom Cakes pre-fueled upon placed.")
                public boolean preFueled = false;
                @Config.Comment("Set to true to make all Custom Cakes consume fuel.")
                public boolean consumeFuel = true;
                @Config.Comment({
                        "Set the fuel used by Custom Cakes.",
                        "Format: <Dimension ID>, <Fuel Registry Name>",
                        "Example: 0, minecraft:apple"
                })
                public String[] fuel = new String[0];
            }
        }

        @Config.Comment("Set to true to disable the activation of vanilla End Portal.")
        public boolean disableVanillaEndPortal = false;

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
