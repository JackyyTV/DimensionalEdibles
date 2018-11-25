package jackyy.dimensionaledibles.compat;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.registry.ModItems;
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
        registry.addIngredientInfo(new ItemStack(ModBlocks.endCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".end_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCakeFuel))))));
        //Nether Cake
        registry.addIngredientInfo(new ItemStack(ModBlocks.netherCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".nether_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCakeFuel))))));
        //Overworld Cake
        registry.addIngredientInfo(new ItemStack(ModBlocks.overworldCake), ItemStack.class,
                I18n.format(DimensionalEdibles.MODID + ".overworld_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.overworldCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.overworldCakeFuel))))));
        //Ender Apple
        registry.addIngredientInfo(new ItemStack(ModItems.enderApple), ItemStack.class,
                DimensionalEdibles.MODID + ".ender_apple.jeidesc");
        //Nether Apple
        registry.addIngredientInfo(new ItemStack(ModItems.netherApple), ItemStack.class,
                DimensionalEdibles.MODID + ".nether_apple.jeidesc");
        //Overworld Apple
        registry.addIngredientInfo(new ItemStack(ModItems.overworldApple), ItemStack.class,
                DimensionalEdibles.MODID + ".overworld_apple.jeidesc");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

}
