package me.jacky1356400.dimensionaledibles.compat;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.proxy.CommonProxy;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class JEICompat implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void register(IModRegistry registry) {
        //End Cake
        registry.addDescription(new ItemStack(CommonProxy.endCake),
                I18n.format(DimensionalEdibles.MODID + ".end_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.endCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.endCakeFuel))))));
        //Nether Cake
        registry.addDescription(new ItemStack(CommonProxy.netherCake),
                I18n.format(DimensionalEdibles.MODID + ".nether_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel))))));
        //Overworld Cake
        registry.addDescription(new ItemStack(CommonProxy.overworldCake),
                I18n.format(DimensionalEdibles.MODID + ".overworld_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.overworldCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.overworldCakeFuel))))));
        //Ender Apple
        registry.addDescription(new ItemStack(CommonProxy.enderApple),
                DimensionalEdibles.MODID + ".ender_apple.jeidesc");
        //Nether Apple
        registry.addDescription(new ItemStack(CommonProxy.netherApple),
                DimensionalEdibles.MODID + ".nether_apple.jeidesc");
        //Overworld Apple
        registry.addDescription(new ItemStack(CommonProxy.overworldApple),
                DimensionalEdibles.MODID + ".overworld_apple.jeidesc");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

}
