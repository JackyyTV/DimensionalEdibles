package jackyy.dimensionaledibles.util;

import jackyy.dimensionaledibles.registry.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Reference {

    public static final String MODID = "dimensionaledibles";
    public static final String MODNAME = "Dimensional Edibles";
    public static final String VERSION = "1.3.2";
    public static final String MCVERSION = "[1.10,1.11)";

    public static final String DEPENDS = "after:Waila;after:theoneprobe;after:JEI;";

    public static final String GUIFACTORY = "jackyy.dimensionaledibles.registry.ConfigGuiFactory";

    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.endCake);
        }
    };

    public static final Logger LOGGER = LogManager.getLogger(MODNAME);

    public static final String COMMON_PROXY = "jackyy.dimensionaledibles.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "jackyy.dimensionaledibles.proxy.ClientProxy";

}
