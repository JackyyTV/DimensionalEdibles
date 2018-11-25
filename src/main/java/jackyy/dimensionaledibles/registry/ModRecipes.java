package jackyy.dimensionaledibles.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(RegistryEvent.Register<IRecipe> event) {
        if (ModConfig.general.endCake)
            GameRegistry.addShapedRecipe(ModBlocks.endCake.getRegistryName(), null, new ItemStack(ModBlocks.endCake),
                    "EEE", "ECE", "EEE", 'E', Items.ENDER_EYE, 'C', Items.CAKE);
        if (ModConfig.general.netherCake)
            GameRegistry.addShapedRecipe(ModBlocks.netherCake.getRegistryName(), null, new ItemStack(ModBlocks.netherCake),
                    "OOO", "OCO", "OOO", 'O', "obsidian", 'C', Items.CAKE);
        if (ModConfig.general.overworldCake)
            GameRegistry.addShapedRecipe(ModBlocks.overworldCake.getRegistryName(), null, new ItemStack(ModBlocks.overworldCake),
                    "SSS", "SCS", "SSS", 'S', "treeSapling", 'C', Items.CAKE);
        if (ModConfig.general.enderApple)
            GameRegistry.addShapedRecipe(ModItems.enderApple.getRegistryName(), null, new ItemStack(ModItems.enderApple),
                    "EEE", "EAE", "EEE", 'E', Items.ENDER_EYE, 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
        if (ModConfig.general.netherApple)
            GameRegistry.addShapedRecipe(ModItems.netherApple.getRegistryName(), null, new ItemStack(ModItems.netherApple),
                    "OOO", "OAO", "OOO", 'O', "obsidian", 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
        if (ModConfig.general.overworldApple)
            GameRegistry.addShapedRecipe(ModItems.overworldApple.getRegistryName(), null, new ItemStack(ModItems.overworldApple),
                    "SSS", "SAS", "SSS", 'S', "treeSapling", 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 0));
    }

}
