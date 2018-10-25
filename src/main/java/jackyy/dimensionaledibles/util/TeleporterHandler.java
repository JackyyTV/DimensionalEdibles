package jackyy.dimensionaledibles.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;

public class TeleporterHandler extends Teleporter {

    private final WorldServer worldServer;
    private double x;
    private double y;
    private double z;

    public TeleporterHandler(WorldServer world, double x, double y, double z) {
        super(world);
        this.worldServer = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
        int oldDimension = player.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.worldServerForDimension(dimension);

        if (worldServer == null || worldServer.getMinecraftServer() == null) {
            throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist!");
        }

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(
                entityPlayerMP, dimension, new TeleporterHandler(worldServer, x, y, z));

        BlockPos pos = new BlockPos(x, y - 1, z);
        for (int xx = -2; xx <= 2; xx++) {
            for (int zz = -2; zz <= 2; zz++) {
                if (!worldServer.getBlockState(pos.add(xx, 0, zz)).isFullBlock()
                        && !worldServer.getBlockState(pos.add(xx, -1, zz)).isFullBlock()
                        && !worldServer.getBlockState(pos.add(xx, -2, zz)).isFullBlock()) {
                    worldServer.setBlockState(pos.add(xx, 0, zz), Blocks.OBSIDIAN.getDefaultState());
                }
            }
        }

        for (int yy = 0; yy <= 2; yy++) {
            if (worldServer.getBlockState(pos.add(0, yy, 0)).isFullBlock()) {
                worldServer.setBlockToAir(pos.add(0, yy, 0));
            }
        }

        player.setPositionAndUpdate(x + .5, y + .5, z + .5);

        if (oldDimension == 1) {
            player.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntityInWorld(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }

    @Override
    public void placeInPortal(@Nonnull Entity entity, float rotationYaw) {
        this.worldServer.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));
        entity.setPosition(this.x, this.y, this.z);
        entity.motionX = 0.0f;
        entity.motionY = 0.0f;
        entity.motionZ = 0.0f;
    }

}
