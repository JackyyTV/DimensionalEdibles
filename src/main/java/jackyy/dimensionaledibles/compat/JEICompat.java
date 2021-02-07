package jackyy.dimensionaledibles.compat;

import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.registry.ModItems;
import jackyy.dimensionaledibles.util.Reference;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

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
                I18n.format(
                        Reference.MODID + ".end_cake.jeidesc",
                        TextFormatting.DARK_PURPLE + new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCake.fuel))).getDisplayName() + TextFormatting.RESET
                ));
        //Nether Cake
        registry.addDescription(new ItemStack(ModBlocks.netherCake),
                I18n.format(
                        Reference.MODID + ".nether_cake.jeidesc",
                        TextFormatting.DARK_PURPLE + new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCake.fuel))).getDisplayName() + TextFormatting.RESET
                ));
        //Overworld Cake
        registry.addDescription(new ItemStack(ModBlocks.overworldCake),
                I18n.format(
                        Reference.MODID + ".overworld_cake.jeidesc",
                        TextFormatting.DARK_PURPLE + new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.overworldCake.fuel))).getDisplayName() + TextFormatting.RESET
                ));
        //Ender Apple
        registry.addDescription(new ItemStack(ModItems.enderApple),
                Reference.MODID + ".ender_apple.jeidesc");
        //Nether Apple
        registry.addDescription(new ItemStack(ModItems.netherApple),
                Reference.MODID + ".nether_apple.jeidesc");
        //Overworld Apple
        registry.addDescription(new ItemStack(ModItems.overworldApple),
                Reference.MODID + ".overworld_apple.jeidesc");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

}
