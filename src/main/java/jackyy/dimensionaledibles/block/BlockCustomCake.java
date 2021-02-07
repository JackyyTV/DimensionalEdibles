package jackyy.dimensionaledibles.block;

import jackyy.dimensionaledibles.block.tile.TileDimensionCake;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.Reference;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import jackyy.gunpowderlib.helper.NBTHelper;
import jackyy.gunpowderlib.helper.ObjectHelper;
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
import org.apache.logging.log4j.Level;

public class BlockCustomCake extends BlockCakeBase implements ITileEntityProvider {

    private int customX = 0;
    private int customY = 0;
    private int customZ = 0;

    public BlockCustomCake() {
        setRegistryName(Reference.MODID + ":custom_cake");
        setTranslationKey(Reference.MODID + ".custom_cake");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        int meta = getMetaFromState(world.getBlockState(pos)) - 1;
        ItemStack stack = player.getHeldItem(hand);
        int dimension = 0;
        TileEntity ent = world.getTileEntity(pos);
        if (ent != null && ent instanceof TileDimensionCake) {
            dimension = ((TileDimensionCake) ent).getDimensionID();
        }
        String fuel = "minecraft:air";
        for (String s : ModConfig.tweaks.customEdible.customCake.fuel) {
            try {
                String[] parts = s.split(",");
                if (parts.length < 2) {
                    Reference.LOGGER.log(Level.ERROR, s + " is not a valid input line! Format needs to be: <dimID>, <cakeFuel>");
                    continue;
                }
                if (Integer.parseInt(parts[0].trim()) == dimension) {
                    fuel = parts[1].trim();
                }
            } catch (NumberFormatException e) {
                Reference.LOGGER.log(Level.ERROR, s + " is not a valid line input! The dimension ID needs to be a number!");
            }
        }
        for (String s : ModConfig.tweaks.customEdible.customCoords) {
            try {
                String[] parts = s.split(",");
                if (parts.length < 4) {
                    Reference.LOGGER.log(Level.ERROR, s + " is not a valid input line! Format needs to be: <dimID>, <x>, <y>, <z>");
                    continue;
                }
                if (Integer.parseInt(parts[0].trim()) == dimension) {
                    customX = Integer.parseInt(parts[1].trim());
                    customY = Integer.parseInt(parts[2].trim());
                    customZ = Integer.parseInt(parts[3].trim());
                }
            } catch (NumberFormatException e) {
                Reference.LOGGER.log(Level.ERROR, s + " is not a valid line input! The dimension ID needs to be a number!");
            }
        }
        if (!stack.isEmpty() && stack.getItem() == ObjectHelper.getItemByName(fuel)) {
            if (meta >= 0) {
                world.setBlockState(pos, state.withProperty(BITES, meta), 2);
                if (!player.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }
                return true;
            }
        } else {
            if (world.provider.getDimension() != dimension) {
                if (!world.isRemote) {
                    if (player.capabilities.isCreativeMode || !ModConfig.tweaks.customEdible.customCake.consumeFuel) {
                        teleportPlayer(world, player, dimension);
                    } else {
                        consumeCake(world, pos, player, dimension);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void teleportPlayer(World world, EntityPlayer player, int dimension) {
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        BlockPos coords;
        if (customX != 0 && customY != 0 && customZ != 0) {
            coords = new BlockPos(customX, customY, customZ);
        } else {
            coords = TeleporterHandler.getDimPos(playerMP, dimension, player.getPosition());
        }
        TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
        TeleporterHandler.teleport(playerMP, dimension, coords.getX(), coords.getY(), coords.getZ(), playerMP.server.getPlayerList());
    }

    private void consumeCake(World world, BlockPos pos, EntityPlayer player, int dimension) {
        if (player.canEat(true)) {
            int l = world.getBlockState(pos).getValue(BITES);
            if (l < 6) {
                player.getFoodStats().addStats(2, 0.1F);
                world.setBlockState(pos, world.getBlockState(pos).withProperty(BITES, l + 1), 3);
                teleportPlayer(world, player, dimension);
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return ModConfig.tweaks.customEdible.customCake.preFueled ? getDefaultState().withProperty(BITES, 0) : getDefaultState().withProperty(BITES, 6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (ModConfig.general.customCake) {
            ItemStack stack;
            for (String s : ModConfig.tweaks.customEdible.dimensions) {
                try {
                    String[] parts = s.split(",");
                    if (parts.length < 2) {
                        Reference.LOGGER.log(Level.ERROR, s + " is not a valid input line! Format needs to be: <dimID>, <cakeName>");
                        continue;
                    }
                    int dimension = Integer.parseInt(parts[0].trim());
                    if (DimensionManager.isDimensionRegistered(dimension)) {
                        stack = new ItemStack(this);
                        NBTTagCompound nbt = NBTHelper.getTag(stack);
                        nbt.setInteger("dimID", dimension);
                        nbt.setString("cakeName", parts[1].trim());
                        list.add(stack);
                    } else {
                        Reference.LOGGER.log(Level.ERROR, parts[0] + " is not a valid dimension ID! (Needs to be a number)");
                    }
                } catch (NumberFormatException e) {
                    Reference.LOGGER.log(Level.ERROR, s + " is not a valid line input! The dimension ID needs to be a number!");
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileDimensionCake();
    }

}
