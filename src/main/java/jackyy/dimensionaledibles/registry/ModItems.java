package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.item.ItemEnderApple;
import jackyy.dimensionaledibles.item.ItemNetherApple;
import jackyy.dimensionaledibles.item.ItemOverworldApple;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static jackyy.dimensionaledibles.registry.ModBlocks.*;

public class ModItems {

    public static ItemEnderApple enderApple = new ItemEnderApple();
    public static ItemNetherApple netherApple = new ItemNetherApple();
    public static ItemOverworldApple overworldApple = new ItemOverworldApple();

    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemBlock(endCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(endCake.getRegistryName()),
                new ItemBlock(netherCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(netherCake.getRegistryName()),
                new ItemBlock(overworldCake) {
                    @Override
                    public EnumRarity getRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(overworldCake.getRegistryName()),
                enderApple,
                netherApple,
                overworldApple
        );
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ModelLoader.setCustomModelResourceLocation(enderApple, 0,
                new ModelResourceLocation(enderApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(netherApple, 0,
                new ModelResourceLocation(netherApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(overworldApple, 0,
                new ModelResourceLocation(overworldApple.getRegistryName(), "inventory"));
    }

}
