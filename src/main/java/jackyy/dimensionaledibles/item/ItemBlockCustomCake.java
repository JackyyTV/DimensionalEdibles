package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("deprecation")
public class ItemBlockCustomCake extends ItemBlock {

    public ItemBlockCustomCake() {
        super(ModBlocks.customCake);
        setRegistryName(ModBlocks.customCake.getRegistryName());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocalFormatted("item." + Reference.MODID + ".custom_cake.name", getCakeName(stack));
    }

    @Override
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

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        int dim = getDimID(stack);
        String dimName = DimensionType.getById(dim).getName();
        tooltip.add(TextFormatting.AQUA + I18n.translateToLocal(Reference.MODID + ".dimension") + TextFormatting.WHITE + " " + dimName + " (" + dim + ")");
    }

    public String getCakeName(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null || !nbt.hasKey("cakeName")) {
            return I18n.translateToLocal(Reference.MODID + ".custom");
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

}
