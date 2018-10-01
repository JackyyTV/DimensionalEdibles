package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModConfig;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockEndCake extends BlockCakeBase {

    public BlockEndCake() {
        super();
        setRegistryName(DimensionalEdibles.MODID + ":end_cake");
        setUnlocalizedName(DimensionalEdibles.MODID + ".end_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;

        if (player.capabilities.isCreativeMode) {
            if (stack != null && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCakeFuel))) {
                world.setBlockState(pos, getStateFromMeta(0), 2);
                return true;
            }
            else {
                if (world.provider.getDimension() != 1) {
                    if (!world.isRemote) {
                        player.changeDimension(1);
                        return true;
                    }
                }
            }
        }
        else {
            if (stack != null && stack.getItem() == Item.REGISTRY.getObject(new ResourceLocation(ModConfig.tweaks.endCakeFuel))) {
                if (meta >= 0) {
                    world.setBlockState(pos, getStateFromMeta(meta), 2);
                    --stack.stackSize;
                    return true;
                }
            }
            else {
                if (world.provider.getDimension() != 1) {
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
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
                player.changeDimension(1);
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
        if (ModConfig.general.endCake)
            list.add(new ItemStack(item));
    }

}
