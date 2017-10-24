package me.jacky1356400.dimensionaledibles.compat;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.proxy.CommonProxy;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
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
    public void registerCategories(IRecipeCategoryRegistration registry) {
    }

    @Override
    public void register(IModRegistry registry) {
        //End Cake
        registry.addIngredientInfo(new ItemStack(CommonProxy.endCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".end_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.endCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.endCakeFuel))))));
        //Nether Cake
        registry.addIngredientInfo(new ItemStack(CommonProxy.netherCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".nether_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel))))));
        //Overworld Cake
        registry.addIngredientInfo(new ItemStack(CommonProxy.overworldCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".overworld_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(Config.overworldCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(Config.overworldCakeFuel))))));
        //Ender Apple
        registry.addIngredientInfo(new ItemStack(CommonProxy.enderApple), ItemStack.class,
                DimensionalEdibles.MODID + ".ender_apple.jeidesc");
        //Nether Apple
        registry.addIngredientInfo(new ItemStack(CommonProxy.netherApple), ItemStack.class,
                DimensionalEdibles.MODID + ".nether_apple.jeidesc");
        //Overworld Apple
        registry.addIngredientInfo(new ItemStack(CommonProxy.overworldApple), ItemStack.class,
                DimensionalEdibles.MODID + ".overworld_apple.jeidesc");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

}
