package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.DimensionalEdibles;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Config(modid = DimensionalEdibles.MODID, name = "DimensionalEdibles")
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
        private static final MethodHandle CONFIGS_GETTER = findFieldGetter(ConfigManager.class, "CONFIGS");
        private static Configuration config;
        private static MethodHandle findFieldGetter(Class<?> clazz, String... fieldNames) {
            final Field field = ReflectionHelper.findField(clazz, fieldNames);
            try {
                return MethodHandles.lookup().unreflectGetter(field);
            } catch (IllegalAccessException e) {
                throw new ReflectionHelper.UnableToAccessFieldException(fieldNames, e);
            }
        }
        @SuppressWarnings("unchecked")
        public static Configuration getConfig() {
            if (config == null) {
                try {
                    final String fileName = "Exchangers.cfg";
                    final Map<String, Configuration> configsMap = (Map<String, Configuration>) CONFIGS_GETTER.invokeExact();
                    final Optional<Map.Entry<String, Configuration>> entryOptional = configsMap.entrySet().stream()
                            .filter(entry -> fileName.equals(new File(entry.getKey()).getName())).findFirst();
                    entryOptional.ifPresent(stringConfigurationEntry -> config = stringConfigurationEntry.getValue());
                } catch (Throwable throwable) {
                    DimensionalEdibles.logger.error("Failed to get Configuration instance!", throwable);
                }
            }
            return config;
        }
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(DimensionalEdibles.MODID)) {
                ConfigManager.load(DimensionalEdibles.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
