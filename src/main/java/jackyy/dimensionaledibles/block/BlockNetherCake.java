package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.Reference;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockNetherCake extends BlockCakeBase implements ITileEntityProvider {

    public BlockNetherCake() {
        super();
        setRegistryName(Reference.MODID + ":nether_cake");
        setUnlocalizedName(Reference.MODID + ".nether_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;
        if (stack != null && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCake.fuel))) {
            if (meta >= 0) {
                world.setBlockState(pos, state.withProperty(BITES, meta), 2);
                if (!player.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
                return true;
            }
        } else {
            if (world.provider.getDimension() != -1) {
                if (!world.isRemote) {
                    if (player.capabilities.isCreativeMode || !ModConfig.tweaks.netherCake.consumeFuel) {
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
        if (ModConfig.tweaks.netherCake.useCustomCoords) {
            coords = new BlockPos(ModConfig.tweaks.netherCake.customCoords.x, ModConfig.tweaks.netherCake.customCoords.y, ModConfig.tweaks.netherCake.customCoords.z);
        } else {
            coords = TeleporterHandler.getDimPos(playerMP, -1, player.getPosition());
        }
        TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
        TeleporterHandler.teleport(playerMP, -1, coords.getX(), coords.getY(), coords.getZ(), playerMP.mcServer.getPlayerList());
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack) {
        return ModConfig.tweaks.netherCake.preFueled ? getDefaultState().withProperty(BITES, 0) : getDefaultState().withProperty(BITES, 6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (ModConfig.general.netherCake)
            list.add(new ItemStack(this));
    }

    @Override @SuppressWarnings("deprecation")
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDimensionCake(-1, I18n.translateToLocal(Reference.MODID + ".nether"));
    }

}
