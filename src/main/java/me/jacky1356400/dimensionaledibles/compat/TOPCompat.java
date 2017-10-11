package me.jacky1356400.dimensionaledibles.compat;

import com.google.common.base.Function;
import mcjty.theoneprobe.api.*;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.util.ITOPInfoProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;

public class TOPCompat {

    private static boolean registered;

    public static void register() {
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "me.jacky1356400.dimensionaledibles.compat.TOPCompat$GetTheOneProbe");
    }


    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {

        public static ITheOneProbe probe;

        @Override @Nullable
        public Void apply(ITheOneProbe theOneProbe) {
            probe = theOneProbe;
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return DimensionalEdibles.MODID + "cake";
                }
                @Override
                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                    if (blockState.getBlock() instanceof ITOPInfoProvider) {
                        ITOPInfoProvider provider = (ITOPInfoProvider)blockState.getBlock();
                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                    }

                }
            });
            return null;
        }
    }

}
