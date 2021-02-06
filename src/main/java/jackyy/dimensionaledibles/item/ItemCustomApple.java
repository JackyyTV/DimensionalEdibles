package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.Reference;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;

public class ItemCustomApple extends ItemAppleBase {

    public ItemCustomApple() {
        setRegistryName(Reference.MODID + ":custom_apple");
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        int dimension;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null || !nbt.hasKey("dimID")) {
            return;
        }
        dimension = nbt.getInteger("dimID");
        int customX = nbt.getInteger("x");
        int customY = nbt.getInteger("y");
        int customZ = nbt.getInteger("z");
        if (world.provider.getDimension() != dimension) {
            if (!world.isRemote) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                BlockPos coords;
                if (customX != 0 && customY != 0 && customZ != 0) {
                    coords = new BlockPos(customX, customY, customZ);
                } else {
                    coords = TeleporterHandler.getDimPos(playerMP, dimension, player.getPosition());
                }
                TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
                TeleporterHandler.teleport(playerMP, dimension, coords.getX(), coords.getY(), coords.getZ(), playerMP.mcServer.getPlayerList());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        if (ModConfig.general.customApple) {
            ItemStack stack;
            for (String s : ModConfig.tweaks.customEdible.dimensions) {
                try {
                    String[] parts = s.split(",");
                    if (parts.length < 2) {
                        Reference.LOGGER.log(Level.ERROR, s + " is not a valid input line! Format needs to be: <dimID>, <cakeName>");
                        continue;
                    }
                    int dimension = Integer.parseInt(parts[0]);
                    if (DimensionManager.isDimensionRegistered(dimension)) {
                        stack = new ItemStack(this);
                        NBTTagCompound nbt = stack.getTagCompound();
                        if (nbt == null) {
                            nbt = new NBTTagCompound();
                            stack.setTagCompound(nbt);
                        }
                        nbt.setInteger("dimID", dimension);
                        nbt.setString("appleName", parts[1].trim());
                        nbt.setInteger("x", 0);
                        nbt.setInteger("y", 0);
                        nbt.setInteger("z", 0);
                        for (String c : ModConfig.tweaks.customEdible.customCoords) {
                            try {
                                String[] coords = c.split(",");
                                if (coords.length < 4) {
                                    Reference.LOGGER.log(Level.ERROR, c + " is not a valid input line! Format needs to be: <dimID>, <x>, <y>, <z>");
                                    continue;
                                }
                                if (Integer.parseInt(coords[0].trim()) == dimension) {
                                    nbt.setInteger("x", Integer.parseInt(coords[1].trim()));
                                    nbt.setInteger("y", Integer.parseInt(coords[2].trim()));
                                    nbt.setInteger("z", Integer.parseInt(coords[3].trim()));
                                }
                            } catch (NumberFormatException e) {
                                Reference.LOGGER.log(Level.ERROR, c + " is not a valid line input! The dimension ID needs to be a number!");
                            }
                        }
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

    @Override @SuppressWarnings("deprecation")
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocalFormatted("item." + Reference.MODID + ".custom_apple.name", getAppleName(stack));
    }

    @SuppressWarnings("deprecation")
    public String getAppleName(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null || !nbt.hasKey("appleName")) {
            return I18n.translateToLocal(Reference.MODID + ".custom");
        }
        return nbt.getString("appleName");
    }

}
