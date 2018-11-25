package jackyy.dimensionaledibles.registry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {

    public static void init() {
        if (ModConfig.general.endCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.endCake)),
                    "EEE", "ECE", "EEE", 'E', Items.ENDER_EYE, 'C', Items.CAKE));
        if (ModConfig.general.netherCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.netherCake)),
                    "OOO", "OCO", "OOO", 'O', "obsidian", 'C', Items.CAKE));
        if (ModConfig.general.overworldCake)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.overworldCake)),
                    "SSS", "SCS", "SSS", 'S', "treeSapling", 'C', Items.CAKE));
        if (ModConfig.general.enderApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.enderApple),
                    "EEE", "EAE", "EEE", 'E', Items.ENDER_EYE, 'A', Items.GOLDEN_APPLE));
        if (ModConfig.general.netherApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.netherApple),
                    "OOO", "OAO", "OOO", 'O', "obsidian", 'A', Items.GOLDEN_APPLE));
        if (ModConfig.general.overworldApple)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.overworldApple),
                    "SSS", "SAS", "SSS", 'S', "treeSapling", 'A', Items.GOLDEN_APPLE));
    }

}