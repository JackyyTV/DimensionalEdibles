package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemEnderApple enderApple = new ItemEnderApple();
    public static ItemNetherApple netherApple = new ItemNetherApple();
    public static ItemOverworldApple overworldApple = new ItemOverworldApple();
    public static ItemCustomApple customApple = new ItemCustomApple();
    public static ItemBlockCustomCake customCake = new ItemBlockCustomCake();

    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemBlock(ModBlocks.endCake) {
                    @Override
                    public IRarity getForgeRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(ModBlocks.endCake.getRegistryName()),
                new ItemBlock(ModBlocks.netherCake) {
                    @Override
                    public IRarity getForgeRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(ModBlocks.netherCake.getRegistryName()),
                new ItemBlock(ModBlocks.overworldCake) {
                    @Override
                    public IRarity getForgeRarity(ItemStack stack) {
                        return EnumRarity.EPIC;
                    }
                }.setRegistryName(ModBlocks.overworldCake.getRegistryName()),
                customCake, enderApple, netherApple, overworldApple, customApple);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ModelLoader.setCustomModelResourceLocation(enderApple, 0, new ModelResourceLocation(enderApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(netherApple, 0, new ModelResourceLocation(netherApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(overworldApple, 0, new ModelResourceLocation(overworldApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(customApple, 0, new ModelResourceLocation(customApple.getRegistryName(), "inventory"));
    }

}
