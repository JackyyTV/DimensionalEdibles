package me.jacky1356400.dimensionaledibles;

import me.jacky1356400.dimensionaledibles.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DimensionalEdibles.MODID, name = DimensionalEdibles.MODNAME, version = DimensionalEdibles.VERSION, useMetadata = true)
public class DimensionalEdibles {

    public static final String VERSION = "1.0";
    public static final String MODID = "dimensionaledibles";
    public static final String MODNAME = "DimensionalEdibles";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(CommonProxy.endCake);
        }
    };
    public static Logger logger = LogManager.getLogger("DimensionalEdibles");

    @SidedProxy(serverSide = "me.jacky1356400.dimensionaledibles.proxy.CommonProxy", clientSide = "me.jacky1356400.dimensionaledibles.proxy.ClientProxy")
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

}
