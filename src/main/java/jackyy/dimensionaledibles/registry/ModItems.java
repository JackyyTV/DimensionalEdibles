package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.item.ItemEnderApple;
import jackyy.dimensionaledibles.item.ItemNetherApple;
import jackyy.dimensionaledibles.item.ItemOverworldApple;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemEnderApple enderApple;
    public static ItemNetherApple netherApple;
    public static ItemOverworldApple overworldApple;

    public static void init() {
        enderApple = GameRegistry.register(new ItemEnderApple());
        netherApple = GameRegistry.register(new ItemNetherApple());
        overworldApple = GameRegistry.register(new ItemOverworldApple());
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
