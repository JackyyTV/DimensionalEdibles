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
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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

        endCake = GameRegistry.register(new BlockEndCake());
        GameRegistry.register(new ItemBlock(endCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }, endCake.getRegistryName());
        netherCake = GameRegistry.register(new BlockNetherCake());
        GameRegistry.register(new ItemBlock(netherCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }, netherCake.getRegistryName());
        overworldCake = GameRegistry.register(new BlockOverworldCake());
        GameRegistry.register(new ItemBlock(overworldCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }, overworldCake.getRegistryName());
        enderApple = GameRegistry.register(new ItemEnderApple());
        netherApple = GameRegistry.register(new ItemNetherApple());
        overworldApple = GameRegistry.register(new ItemOverworldApple());

        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
        if (Loader.isModLoaded("Waila")) {
            WailaCompat.register();
        }
    }

    public void init(FMLInitializationEvent event) {
        if (Config.endCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(endCake)),
                    "EEE", "ECE", "EEE", 'E', Items.ENDER_EYE, 'C', Items.CAKE));
        if (Config.netherCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(netherCake)),
                    "OOO", "OCO", "OOO", 'O', "obsidian", 'C', Items.CAKE));
        if (Config.overworldCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(overworldCake)),
                    "SSS", "SCS", "SSS", 'S', "treeSapling", 'C', Items.CAKE));
        if (Config.enderApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(enderApple),
                    "EEE", "EAE", "EEE", 'E', Items.ENDER_EYE, 'A', Items.GOLDEN_APPLE));
        if (Config.netherApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(netherApple),
                    "OOO", "OAO", "OOO", 'O', "obsidian", 'A', Items.GOLDEN_APPLE));
        if (Config.overworldApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(overworldApple),
                    "SSS", "SAS", "SSS", 'S', "treeSapling", 'A', Items.GOLDEN_APPLE));
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

}
