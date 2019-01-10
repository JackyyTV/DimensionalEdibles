package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.util.ITOPInfoProvider;
import jackyy.dimensionaledibles.util.IWailaInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * This is based on the vanilla cake class, but slightly modified and added
 * Waila / TOP support.
 */
public class BlockCakeBase extends Block implements ITOPInfoProvider, IWailaInfoProvider {

    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
    public static final AxisAlignedBB[] CAKE_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D)};

    public BlockCakeBase() {
        super(Material.CAKE);
        setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
        setTickRandomly(true);
        setHardness(0.5F);
        setSoundType(SoundType.CLOTH);
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CAKE_AABB[state.getValue(BITES)];
    }

    @Override @SideOnly(Side.CLIENT) @Deprecated
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return state.getCollisionBoundingBox(worldIn, pos);
    }

    @Override @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.canEat(false)) {
            playerIn.addStat(StatList.CAKE_SLICES_EATEN);
            playerIn.getFoodStats().addStats(2, 0.1F);
            int i = state.getValue(BITES);

            if (i < 6) {
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            } else {
                worldIn.setBlockToAir(pos);
            }
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

    @Override @Deprecated
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BITES, meta);
    }

    @Override @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BITES);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BITES);
    }

    @Override @Deprecated
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return (7 - blockState.getValue(BITES)) * 2;
    }

    @Override @Deprecated
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (world.getBlockState(data.getPos()).getBlock() instanceof BlockCakeBase) {
            probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .item(new ItemStack(Items.CAKE))
                    .text(TextFormatting.GREEN + "Bites: ")
                    .progress(6 - blockState.getValue(BITES), 6);
        }
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlockState().getBlock() instanceof BlockCakeBase) {
            currenttip.add(TextFormatting.GRAY + "Bites: " + (6 - accessor.getBlockState().getValue(BITES)) + " / 6");
        }
        return currenttip;
    }

}