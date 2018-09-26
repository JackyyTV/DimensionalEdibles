package jackyy.dimensionaledibles.compat;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.registry.ModItems;
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
        registry.addDescription(new ItemStack(ModBlocks.endCake),
                I18n.format(DimensionalEdibles.MODID + ".end_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.endCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.endCakeFuel))))));
        //Nether Cake
        registry.addDescription(new ItemStack(ModBlocks.netherCake),
                I18n.format(DimensionalEdibles.MODID + ".nether_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.netherCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.netherCakeFuel))))));
        //Overworld Cake
        registry.addDescription(new ItemStack(ModBlocks.overworldCake),
                I18n.format(DimensionalEdibles.MODID + ".overworld_cake.jeidesc",
                        Item.REGISTRY.getObject(new ResourceLocation(ModConfig.overworldCakeFuel)).getItemStackDisplayName(
                                new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.overworldCakeFuel))))));
        //Ender Apple
        registry.addDescription(new ItemStack(ModItems.enderApple),
                DimensionalEdibles.MODID + ".ender_apple.jeidesc");
        //Nether Apple
        registry.addDescription(new ItemStack(ModItems.netherApple),
                DimensionalEdibles.MODID + ".nether_apple.jeidesc");
        //Overworld Apple
        registry.addDescription(new ItemStack(ModItems.overworldApple),
                DimensionalEdibles.MODID + ".overworld_apple.jeidesc");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

}
