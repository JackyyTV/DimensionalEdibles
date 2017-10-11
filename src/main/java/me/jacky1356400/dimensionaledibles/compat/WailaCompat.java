package me.jacky1356400.dimensionaledibles.compat;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.jacky1356400.dimensionaledibles.block.BlockCakeBase;
import me.jacky1356400.dimensionaledibles.util.IWailaInfoProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WailaCompat implements IWailaDataProvider {

    public static final WailaCompat INSTANCE = new WailaCompat();

    private WailaCompat() {}

    private static boolean registered;
    private static boolean loaded;

    public static void load(IWailaRegistrar registrar) {
        if (!registered) {
            throw new RuntimeException("Please register this handler using the provided method.");
        }
        if (!loaded) {
            registrar.registerHeadProvider(INSTANCE, BlockCakeBase.class);
            registrar.registerBodyProvider(INSTANCE, BlockCakeBase.class);
            registrar.registerTailProvider(INSTANCE, BlockCakeBase.class);
            loaded = true;
        }
    }

    public static void register() {
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendMessage("Waila", "register", "me.jacky1356400.dimensionaledibles.compat.WailaCompat.load");
    }

    @Nullable
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Nonnull
    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() instanceof IWailaInfoProvider) {
            return ((IWailaInfoProvider)accessor.getBlock()).getWailaBody(itemStack, currenttip, accessor, config);
        }
        return currenttip;
    }

    @Nonnull
    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }

}
