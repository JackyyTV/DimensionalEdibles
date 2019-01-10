package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.item.ItemCustomApple;
import jackyy.dimensionaledibles.item.ItemEnderApple;
import jackyy.dimensionaledibles.item.ItemNetherApple;
import jackyy.dimensionaledibles.item.ItemOverworldApple;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemEnderApple enderApple = new ItemEnderApple();
    public static ItemNetherApple netherApple = new ItemNetherApple();
    public static ItemOverworldApple overworldApple = new ItemOverworldApple();
    public static ItemCustomApple customApple = new ItemCustomApple();

    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new ItemBlock(ModBlocks.endCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }.setRegistryName(ModBlocks.endCake.getRegistryName()), new ItemBlock(ModBlocks.netherCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }.setRegistryName(ModBlocks.netherCake.getRegistryName()), new ItemBlock(ModBlocks.overworldCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }
        }.setRegistryName(ModBlocks.overworldCake.getRegistryName()), new ItemBlock(ModBlocks.customCake) {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return EnumRarity.EPIC;
            }

            @Override
            public String getItemStackDisplayName(ItemStack stack) {
                return getCakeName(stack) + " Cake";
            }

            public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, IBlockState blockState) {
                boolean placed = super.placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, blockState);

                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    if (te instanceof TileDimensionCake) {
                        ((TileDimensionCake) te).setDimensionID(getDimID(stack));
                        ((TileDimensionCake) te).setCakeName(getCakeName(stack));
                    }
                }

                return placed;
            }

            public String getCakeName(ItemStack stack) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null || !nbt.hasKey("cakeName")) {
                    return "ERROR Unconfigured!";
                }
                return nbt.getString("cakeName");
            }

            public int getDimID(ItemStack stack) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null || !nbt.hasKey("dimID")) {
                    return 0;
                }
                return nbt.getInteger("dimID");
            }

        }.setRegistryName(ModBlocks.customCake.getRegistryName()), enderApple, netherApple, overworldApple, customApple);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ModelLoader.setCustomModelResourceLocation(enderApple, 0, new ModelResourceLocation(enderApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(netherApple, 0, new ModelResourceLocation(netherApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(overworldApple, 0, new ModelResourceLocation(overworldApple.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(customApple, 0, new ModelResourceLocation(customApple.getRegistryName(), "inventory"));
    }

}
