package me.jacky1356400.dimensionaledibles;

import me.jacky1356400.dimensionaledibles.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_TWEAKS = "tweaks";

    public static boolean endCake;
    public static boolean netherCake;
    public static boolean overworldCake;
    public static boolean enderApple;
    public static boolean netherApple;
    public static boolean overworldApple;

    public static String endCakeFuel;
    public static String netherCakeFuel;
    public static String overworldCakeFuel;

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initConfig(cfg);
        } catch (Exception e) {
            DimensionalEdibles.logger.error("Error caught while loading config!", e);
        } finally {
            if(cfg.hasChanged()){
                cfg.save();
            }
        }
    }

    private static void initConfig(Configuration cfg) {
        endCake = cfg.getBoolean("endCake", CATEGORY_GENERAL, true, "Set to true to enable End Cake.");
        netherCake = cfg.getBoolean("netherCake", CATEGORY_GENERAL, true, "Set to true to enable Nether Cake.");
        overworldCake = cfg.getBoolean("overworldCake", CATEGORY_GENERAL, true, "Set to true to enable Overworld Cake.");
        enderApple = cfg.getBoolean("enderApple", CATEGORY_GENERAL, true, "Set to true to enable Ender Apple.");
        netherApple = cfg.getBoolean("netherApple", CATEGORY_GENERAL, true, "Set to true to enable Nether Apple.");
        overworldApple = cfg.getBoolean("overworldApple", CATEGORY_GENERAL, true, "Set to true to enable Overworld Apple.");

        endCakeFuel = cfg.getString("endCakeFuel", CATEGORY_TWEAKS, "minecraft:ender_eye", "Set the fuel used by End Cake (Don't change this unless you know what you're doing).");
        netherCakeFuel = cfg.getString("netherCakeFuel", CATEGORY_TWEAKS, "minecraft:obsidian", "Set the fuel used by Nether Cake (Don't change this unless you know what you're doing).");
        overworldCakeFuel = cfg.getString("overworldCakeFuel", CATEGORY_TWEAKS, "minecraft:sapling", "Set the fuel used by Overworld Cake (Don't change this unless you know what you're doing).");
    }

}
