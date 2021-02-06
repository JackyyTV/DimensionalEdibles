package jackyy.dimensionaledibles;

import jackyy.dimensionaledibles.proxy.CommonProxy;
import jackyy.dimensionaledibles.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSION, dependencies = Reference.DEPENDS, certificateFingerprint = "@FINGERPRINT@", useMetadata = true)
public class DimensionalEdibles {

    @SidedProxy(serverSide = Reference.COMMON_PROXY, clientSide = Reference.CLIENT_PROXY)
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
        Reference.LOGGER.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been modified. This will NOT be supported by the mod author!");
    }

}
