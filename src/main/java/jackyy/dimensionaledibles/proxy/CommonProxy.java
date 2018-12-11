package jackyy.dimensionaledibles.proxy;

import jackyy.dimensionaledibles.compat.TOPCompat;
import jackyy.dimensionaledibles.compat.WailaCompat;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModEvents;
import jackyy.dimensionaledibles.registry.ModItems;
import jackyy.dimensionaledibles.registry.ModRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        ModBlocks.init();
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
        if (Loader.isModLoaded("Waila")) {
            WailaCompat.register();
        }
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }

    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

}
