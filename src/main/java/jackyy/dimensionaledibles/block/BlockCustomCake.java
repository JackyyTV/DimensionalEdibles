package jackyy.dimensionaledibles.block;

import org.apache.logging.log4j.Level;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.block.tileentity.TileEntityCustomCake;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomCake extends BlockCakeBase implements ITileEntityProvider {
    public BlockCustomCake() {
	super();
	setRegistryName(DimensionalEdibles.MODID + ":custom_cake");
	setTranslationKey(DimensionalEdibles.MODID + ".custom_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	int meta = getMetaFromState(world.getBlockState(pos)) - 1;

	if (player.capabilities.isCreativeMode || meta < 0) {
	    meta = 0;
	}

	int dimension = 0;
	TileEntity ent = world.getTileEntity(pos);
	if (ent != null && ent instanceof TileEntityCustomCake) {
	    dimension = ((TileEntityCustomCake) ent).getDimensionID();
	}
	if (world.provider.getDimension() != dimension) {
	    if (!world.isRemote) {
		teleportPlayer(world, player, dimension);
	    }
	}
	return true;
    }

    private void teleportPlayer(World world, EntityPlayer player, int dimension) {

	EntityPlayerMP playerMP = (EntityPlayerMP) player;
	BlockPos coords = TeleporterHandler.getDimensionPosition(playerMP, dimension, player.getPosition());
	TeleporterHandler.updateDimensionPosition(playerMP, world.provider.getDimension(), player.getPosition());
	TeleporterHandler.teleport(playerMP, dimension, coords.getX(), coords.getY(), coords.getZ(), playerMP.server.getPlayerList());
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	return getStateFromMeta(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
	if (ModConfig.general.customCake) {
	    ItemStack stack;
	    for (String s : ModConfig.tweaks.customEdible.dimensions) {
		try {

		    int dimension = Integer.parseInt(s);
		    if (DimensionManager.isDimensionRegistered(dimension)) {
			stack = new ItemStack(this);
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null) {
			    nbt = new NBTTagCompound();
			    stack.setTagCompound(nbt);
			}
			nbt.setInteger("dimID", dimension);
			list.add(stack);
		    } else {
			DimensionalEdibles.logger.log(Level.ERROR, s + " is not a valid dimension id! (Needs to be a number)");
		    }
		} catch (NumberFormatException e) {
		    DimensionalEdibles.logger.log(Level.ERROR, s + " is not a valid dimension id! (Needs to be a number)");
		}
	    }
	}
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
	return new TileEntityCustomCake();
    }
}
