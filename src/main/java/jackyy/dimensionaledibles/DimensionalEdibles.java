package jackyy.dimensionaledibles;

import jackyy.dimensionaledibles.proxy.CommonProxy;
import jackyy.dimensionaledibles.registry.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DimensionalEdibles.MODID, name = DimensionalEdibles.MODNAME, version = DimensionalEdibles.VERSION, acceptedMinecraftVersions = DimensionalEdibles.MCVERSION, dependencies = DimensionalEdibles.DEPENDS, certificateFingerprint = "@FINGERPRINT@", useMetadata = true)
public class DimensionalEdibles {

    public static final String VERSION = "1.1";
    public static final String MCVERSION = "[1.12,1.13)";
    public static final String MODID = "dimensionaledibles";
    public static final String MODNAME = "Dimensional Edibles";
    public static final String DEPENDS = "after:waila;after:theoneprobe;";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.endCake);
        }
    };
    public static Logger logger = LogManager.getLogger(MODNAME);

    @SidedProxy(serverSide = "jackyy.dimensionaledibles.proxy.CommonProxy", clientSide = "jackyy.dimensionaledibles.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        logger.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been modified. This will NOT be supported by the mod author!");
    }

}
