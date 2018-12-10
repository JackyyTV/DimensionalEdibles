package jackyy.dimensionaledibles.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

public class TeleporterHandler {

    public static void teleport(EntityPlayerMP player, int dim, double x, double y, double z, PlayerList playerList) {
        int oldDim = player.dimension;
        WorldServer worldServer = playerList.getServerInstance().worldServerForDimension(player.dimension);
        player.dimension = dim;
        WorldServer worldServer1 = playerList.getServerInstance().worldServerForDimension(player.dimension);
        player.connection.sendPacket(new SPacketRespawn(player.dimension, player.worldObj.getDifficulty(), player.worldObj.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        worldServer.removeEntityDangerously(player);
        if (player.isBeingRidden()) {
            player.removePassengers();
        }
        if (player.isRiding()) {
            player.dismountRidingEntity();
        }
        player.isDead = false;
        teleportEntity(player, worldServer, worldServer1);
        playerList.preparePlayer(player, worldServer);
        player.connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(worldServer1);
        playerList.updateTimeAndWeatherForPlayer(player, worldServer1);
        playerList.syncPlayerInventory(player);
        for (PotionEffect potioneffect : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
        }
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDim, dim);
        worldServer1.playSound(null, x + 0.5D, y + 0.5D, z + 0.5D, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 0.25F, new Random().nextFloat() * 0.4F + 0.8F);
    }

    public static void teleportEntity(Entity entity, WorldServer oldWorld, WorldServer newWorld) {
        WorldProvider oldProvider = oldWorld.provider;
        WorldProvider newProvider = newWorld.provider;
        double moveFactor = oldProvider.getMovementFactor() / newProvider.getMovementFactor();
        double x = entity.posX * moveFactor;
        double z = entity.posZ * moveFactor;
        oldWorld.theProfiler.startSection("teleporting_player");
        x = MathHelper.clamp_double(x, -29999872, 29999872);
        z = MathHelper.clamp_double(z, -29999872, 29999872);
        if (entity.isEntityAlive()) {
            entity.setLocationAndAngles(x, entity.posY, z, entity.rotationYaw, entity.rotationPitch);
            newWorld.spawnEntityInWorld(entity);
            newWorld.updateEntityWithOptionalForce(entity, false);
        }
        oldWorld.theProfiler.endSection();
        entity.setWorld(newWorld);
    }

}
