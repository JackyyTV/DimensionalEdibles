package me.jacky1356400.dimensionaledibles.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        for (int i = 0; i <= 6; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(endCake), i,
                    new ModelResourceLocation(endCake.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(netherCake), i,
                    new ModelResourceLocation(netherCake.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(overworldCake), i,
                    new ModelResourceLocation(overworldCake.getRegistryName(), "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(enderApple, 0,
                new ModelResourceLocation(enderApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(netherApple, 0,
                new ModelResourceLocation(netherApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(overworldApple, 0,
                new ModelResourceLocation(overworldApple.getRegistryName(), "inventory"));
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
