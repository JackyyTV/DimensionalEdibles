package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.registry.ModBlocks;
import jackyy.dimensionaledibles.util.Reference;
import jackyy.gunpowderlib.helper.NBTHelper;
import jackyy.gunpowderlib.helper.StringHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings("deprecation")
public class ItemBlockCustomCake extends ItemBlock {

    public ItemBlockCustomCake() {
        super(ModBlocks.customCake);
        setRegistryName(ModBlocks.customCake.getRegistryName());
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        int dim = getDimID(stack);
        String dimName = DimensionType.getById(dim).getName();
        tooltip.add(TextFormatting.AQUA + StringHelper.localize(Reference.MODID, "dimension") + TextFormatting.WHITE + " " + dimName + " (" + dim + ")");
    }

    public String getCakeName(ItemStack stack) {
        NBTTagCompound nbt = NBTHelper.getTag(stack);
        if (!nbt.hasKey("cakeName")) {
            return StringHelper.localize(Reference.MODID, "custom");
        }
        return nbt.getString("cakeName");
    }

    public int getDimID(ItemStack stack) {
        NBTTagCompound nbt = NBTHelper.getTag(stack);
        if (!nbt.hasKey("dimID")) {
            return 0;
        }
        return nbt.getInteger("dimID");
    }

}
