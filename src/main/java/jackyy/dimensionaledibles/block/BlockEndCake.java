package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockEndCake extends BlockCakeBase implements ITileEntityProvider {

    public BlockEndCake() {
        super();
        setRegistryName(DimensionalEdibles.MODID + ":end_cake");
        setUnlocalizedName(DimensionalEdibles.MODID + ".end_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCake.fuel))) {
            if (meta >= 0) {
                world.setBlockState(pos, state.withProperty(BITES, meta), 2);
                if (!player.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }
                return true;
            }
        } else {
            if (world.provider.getDimension() != 1) {
                if (!world.isRemote) {
                    if (player.capabilities.isCreativeMode) {
                        teleportPlayer(world, player);
                    } else {
                        consumeCake(world, pos, player);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void teleportPlayer(World world, EntityPlayer player) {
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        BlockPos coords;
        if (ModConfig.tweaks.endCake.useCustomCoords) {
            coords = new BlockPos(ModConfig.tweaks.endCake.customCoords.x, ModConfig.tweaks.endCake.customCoords.y, ModConfig.tweaks.endCake.customCoords.z);
        } else {
            coords = TeleporterHandler.getDimPos(playerMP, 1, player.getPosition());
        }
        TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
        TeleporterHandler.teleport(playerMP, 1, coords.getX(), coords.getY(), coords.getZ(), playerMP.mcServer.getPlayerList());
    }

    private void consumeCake(World world, BlockPos pos, EntityPlayer player) {
        if (player.canEat(true)) {
            int l = world.getBlockState(pos).getValue(BITES);
            if (l < 6) {
                player.getFoodStats().addStats(2, 0.1F);
                world.setBlockState(pos, world.getBlockState(pos).withProperty(BITES, l + 1), 3);
                teleportPlayer(world, player);
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return ModConfig.tweaks.endCake.preFueled ? getDefaultState().withProperty(BITES, 0) : getDefaultState().withProperty(BITES, 6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        if (ModConfig.general.endCake)
            list.add(new ItemStack(this));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDimensionCake(1, "End");
    }

}
