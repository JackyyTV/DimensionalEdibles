package me.jacky1356400.dimensionaledibles.proxy;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.block.BlockEndCake;
import me.jacky1356400.dimensionaledibles.block.BlockNetherCake;
import me.jacky1356400.dimensionaledibles.block.BlockOverworldCake;
import me.jacky1356400.dimensionaledibles.compat.TOPCompat;
import me.jacky1356400.dimensionaledibles.compat.WailaCompat;
import me.jacky1356400.dimensionaledibles.item.ItemEnderApple;
import me.jacky1356400.dimensionaledibles.item.ItemNetherApple;
import me.jacky1356400.dimensionaledibles.item.ItemOverworldApple;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

public class CommonProxy {

    public static Configuration config;
    public static BlockEndCake endCake;
    public static BlockNetherCake netherCake;
    public static BlockOverworldCake overworldCake;
    public static ItemEnderApple enderApple;
    public static ItemNetherApple netherApple;
    public static ItemOverworldApple overworldApple;

    public void preInit(FMLPreInitializationEvent event) {
        File configDir = event.getModConfigurationDirectory();
        config = new Configuration(new File(configDir.getPath(), "DimensionalEdibles.cfg"));
        Config.readConfig();

        MinecraftForge.EVENT_BUS.register(this);

        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
        if (Loader.isModLoaded("waila")) {
            WailaCompat.register();
        }
    }

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                endCake = new BlockEndCake(),
                netherCake = new BlockNetherCake(),
                overworldCake = new BlockOverworldCake()
        );
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemBlock(endCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(endCake.getRegistryName()),
                new ItemBlock(netherCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(netherCake.getRegistryName()),
                new ItemBlock(overworldCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(overworldCake.getRegistryName()),
                enderApple = new ItemEnderApple(),
                netherApple = new ItemNetherApple(),
                overworldApple = new ItemOverworldApple()
        );
    }

    @SubscribeEvent
    public void onRecipeRegistry(RegistryEvent.Register<IRecipe> event) {
        if (Config.endCake)
            GameRegistry.addShapedRecipe(endCake.getRegistryName(), null, new ItemStack(Item.getItemFromBlock(endCake)),
                    "EEE", "ECE", "EEE", 'E', Items.ENDER_EYE, 'C', Items.CAKE);
        if (Config.netherCake)
            GameRegistry.addShapedRecipe(netherCake.getRegistryName(), null, new ItemStack(Item.getItemFromBlock(netherCake)),
                    "OOO", "OCO", "OOO", 'O', "obsidian", 'C', Items.CAKE);
        if (Config.overworldCake)
            GameRegistry.addShapedRecipe(overworldCake.getRegistryName(), null, new ItemStack(Item.getItemFromBlock(overworldCake)),
                    "SSS", "SCS", "SSS", 'S', "treeSapling", 'C', Items.CAKE);
        if (Config.enderApple)
            GameRegistry.addShapedRecipe(enderApple.getRegistryName(), null, new ItemStack(enderApple),
                    "EEE", "EAE", "EEE", 'E', Items.ENDER_EYE, 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
        if (Config.netherApple)
            GameRegistry.addShapedRecipe(netherApple.getRegistryName(), null, new ItemStack(netherApple),
                    "OOO", "OAO", "OOO", 'O', "obsidian", 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
        if (Config.overworldApple)
            GameRegistry.addShapedRecipe(overworldApple.getRegistryName(), null, new ItemStack(overworldApple),
                    "SSS", "SAS", "SSS", 'S', "treeSapling", 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

}
