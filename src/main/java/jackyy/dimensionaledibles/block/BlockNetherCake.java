package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.TeleporterHandler;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherCake extends BlockCakeBase {

    public BlockNetherCake() {
        super();
        setRegistryName(DimensionalEdibles.MODID + ":nether_cake");
        setTranslationKey(DimensionalEdibles.MODID + ".nether_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;
        ItemStack stack = player.getHeldItem(hand);

        if (player.capabilities.isCreativeMode) {
            if (!stack.isEmpty() && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCakeFuel))) {
                world.setBlockState(pos, getStateFromMeta(0), 2);
                return true;
            }
            else {
                if (world.provider.getDimension() != -1) {
                    if (!world.isRemote) {
                        WorldServer worldServer = (WorldServer) world;
                        TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                        tp.teleportToDimension(player, -1, 0, player.getPosition().getY() + 1, 0);
                    }
                }
                return true;
            }
        }
        else {
            if (!stack.isEmpty() && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.netherCakeFuel))) {
                if (meta >= 0) {
                    world.setBlockState(pos, getStateFromMeta(meta), 2);
                    stack.shrink(1);
                    return true;
                }
            }
            else {
                if (world.provider.getDimension() != -1) {
                    if (!world.isRemote) {
                        consumeCake(world, pos, player);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void consumeCake(World world, BlockPos pos, EntityPlayer player) {
        if (player.canEat(true)) {
            int l = world.getBlockState(pos).getValue(BITES);

            if (l < 6) {
                player.getFoodStats().addStats(2, 0.1F);
                world.setBlockState(pos, world.getBlockState(pos).withProperty(BITES, l + 1), 3);
                WorldServer worldServer = (WorldServer) world;
                TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
                tp.teleportToDimension(player, -1, 0, player.getPosition().getY() + 1, 0);
            }
        }
    }

    @Override @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (ModConfig.general.netherCake)
            list.add(new ItemStack(this));
    }

}
