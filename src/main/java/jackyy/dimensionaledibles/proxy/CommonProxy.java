package jackyy.dimensionaledibles.proxy;

import jackyy.dimensionaledibles.compat.TOPCompat;
import jackyy.dimensionaledibles.compat.WailaCompat;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.registry.ModItems;
import jackyy.dimensionaledibles.registry.ModRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        File configDir = event.getModConfigurationDirectory();
        config = new Configuration(new File(configDir.getPath(), "DimensionalEdibles.cfg"));
        ModConfig.readConfig();
        ModItems.init();
        ModBlocks.init();
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
        if (Loader.isModLoaded("Waila")) {
            WailaCompat.register();
        }
    }

    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

}
