package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.block.BlockEndCake;
import jackyy.dimensionaledibles.block.BlockNetherCake;
import jackyy.dimensionaledibles.block.BlockOverworldCake;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockEndCake endCake;
    public static BlockNetherCake netherCake;
    public static BlockOverworldCake overworldCake;

    public static void init() {
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
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for (int i = 0; i <= 6; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(endCake), i,
                    new ModelResourceLocation(endCake.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(netherCake), i,
                    new ModelResourceLocation(netherCake.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(overworldCake), i,
                    new ModelResourceLocation(overworldCake.getRegistryName(), "inventory"));
        }
    }

}
