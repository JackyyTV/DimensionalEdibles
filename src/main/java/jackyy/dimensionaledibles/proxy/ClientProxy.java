package jackyy.dimensionaledibles.proxy;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.client.TileCustomCakeRenderer;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModItems;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModItems.initModels();
        ModBlocks.initModels();
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileDimensionCake.class, TileCustomCakeRenderer.INSTANCE);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
