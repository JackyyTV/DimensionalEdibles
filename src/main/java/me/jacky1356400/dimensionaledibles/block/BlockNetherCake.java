package me.jacky1356400.dimensionaledibles.block;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockNetherCake extends BlockCakeBase {

    public BlockNetherCake() {
        super();
        setRegistryName(DimensionalEdibles.MODID + ":nether_cake");
        setUnlocalizedName(DimensionalEdibles.MODID + ".nether_cake");
        setHardness(0.5F);
        setSoundType(SoundType.CLOTH);
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack item, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;

        if (player.capabilities.isCreativeMode) {
            if (item != null && item.getItem() == Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel))) {
                world.setBlockState(pos, getStateFromMeta(0), 2);
                return true;
            }
            else {
                if (!world.isRemote) {
                    WorldServer worldServer = (WorldServer) world;
                    TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                    tp.teleportToDimension(player, -1, 0, world.getSeaLevel(), 0);
                }
                return true;
            }
        }
        else {
            if (item != null && item.getItem() == Item.REGISTRY.getObject(new ResourceLocation(Config.netherCakeFuel))) {
                if (meta >= 0) {
                    world.setBlockState(pos, getStateFromMeta(meta), 2);
                    --item.stackSize;
                    return true;
                }
            }
            else {
                consumeCake(world, pos, player);
                return true;
            }
        }

        return false;
    }

    private void consumeCake(World world, BlockPos pos, EntityPlayer player) {
        if (player.canEat(true)) {
            int l = getMetaFromState(world.getBlockState(pos)) + 1;

            if (l >= 6) {
                return;
            }
            else {
                player.getFoodStats().addStats(2, 0.1F);
                world.setBlockState(pos, getStateFromMeta(l), 2);
                if (world.provider.getDimension() != -1) {
                    if (!world.isRemote) {
                        WorldServer worldServer = (WorldServer) world;
                        TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200));
                        tp.teleportToDimension(player, -1, 0, world.getSeaLevel(), 0);
                    }
                }
            }
        }
    }

    @Override @SuppressWarnings("deprecation")
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (Config.netherCake)
            list.add(new ItemStack(item));
    }

}
