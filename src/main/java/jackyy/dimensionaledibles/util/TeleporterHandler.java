package jackyy.dimensionaledibles.util;

import jackyy.dimensionaledibles.DimensionalEdibles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

public class TeleporterHandler {

    public static void teleport(EntityPlayerMP player, int dim, double x, double y, double z, PlayerList playerList) {
        if (!ForgeHooks.onTravelToDimension(player, dim))
            return;
        int oldDim = player.dimension;
        WorldServer worldServer = playerList.getServerInstance().getWorld(player.dimension);
        player.dimension = dim;
        WorldServer worldServer1 = playerList.getServerInstance().getWorld(player.dimension);
        player.connection.sendPacket(new SPacketRespawn(player.dimension, player.world.getDifficulty(), player.world.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
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
        player.connection.setPlayerLocation(x + 0.5D, y, z + 0.5D, player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(worldServer1);
        playerList.updateTimeAndWeatherForPlayer(player, worldServer1);
        playerList.syncPlayerInventory(player);
        for (PotionEffect potioneffect : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
        }
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDim, dim);
        worldServer1.playSound(null, x + 0.5D, y + 0.5D, z + 0.5D, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 0.25F, new Random().nextFloat() * 0.4F + 0.8F);
        BlockPos pos = new BlockPos(x, y - 1, z);
        if (!player.capabilities.isCreativeMode) {
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
        }
        if (worldServer1.provider.getDimension() != 0) {
            for (int xx = -1; xx <= 1; xx++) {
                for (int zz = -1; zz <= 1; zz++) {
                    if (!worldServer1.getBlockState(pos.add(xx, 0, zz)).isFullBlock()) {
                        worldServer1.setBlockState(pos.add(xx, 0, zz), Blocks.OBSIDIAN.getDefaultState());
                    }
                }
            }
        }
        for (int yy = 1; yy <= 3; yy++) {
            if (worldServer1.getBlockState(pos.add(0, yy, 0)).isFullBlock()) {
                worldServer1.setBlockToAir(pos.add(0, yy, 0));
            }
        }
    }

    public static void teleportEntity(Entity entity, WorldServer oldWorld, WorldServer newWorld) {
        WorldProvider oldProvider = oldWorld.provider;
        WorldProvider newProvider = newWorld.provider;
        double moveFactor = oldProvider.getMovementFactor() / newProvider.getMovementFactor();
        double x = entity.posX * moveFactor;
        double z = entity.posZ * moveFactor;
        oldWorld.profiler.startSection("teleporting_player");
        x = MathHelper.clamp(x, -29999872, 29999872);
        z = MathHelper.clamp(z, -29999872, 29999872);
        if (entity.isEntityAlive()) {
            entity.setLocationAndAngles(x, entity.posY, z, entity.rotationYaw, entity.rotationPitch);
            newWorld.spawnEntity(entity);
            newWorld.updateEntityWithOptionalForce(entity, false);
        }
        oldWorld.profiler.endSection();
        entity.setWorld(newWorld);
    }

    public static NBTTagCompound getModNBTData(EntityPlayer player) {
        NBTTagCompound dimensionCache = (NBTTagCompound) player.getEntityData().getTag(DimensionalEdibles.MODID);
        if (dimensionCache == null) {
            dimensionCache = new NBTTagCompound();
            player.getEntityData().setTag(DimensionalEdibles.MODID, dimensionCache);
        }
        return dimensionCache;
    }

    public static void updateDimPos(EntityPlayer player, int dimension, BlockPos pos) {
        NBTTagCompound dimensionCache = getModNBTData(player);
        NBTTagCompound dimPos = (NBTTagCompound) dimensionCache.getTag("" + dimension);
        if (dimPos == null) {
            dimPos = new NBTTagCompound();
            dimensionCache.setTag("" + dimension, dimPos);
        }
        dimPos.setInteger("x", pos.getX());
        dimPos.setInteger("y", pos.getY());
        dimPos.setInteger("z", pos.getZ());
    }

    public static BlockPos getDimPos(EntityPlayerMP player, int dimension, BlockPos currentPos) {
        NBTTagCompound dimensionCache = getModNBTData(player);
        NBTTagCompound dimPos = (NBTTagCompound) dimensionCache.getTag("" + dimension);
        BlockPos position;
        if (dimPos == null) {
            dimPos = new NBTTagCompound();
            dimensionCache.setTag("" + dimension, dimPos);

            WorldServer newDimworld = player.server.getPlayerList().getServerInstance().getWorld(dimension);

            if (dimension == 1) {
                position = newDimworld.getSpawnCoordinate();
            } else {
                position = getValidYSpawnPos(newDimworld, currentPos);
            }
        } else {
            position = new BlockPos(dimPos.getInteger("x"), dimPos.getInteger("y"), dimPos.getInteger("z"));
        }
        return position;
    }

    public static BlockPos getValidYSpawnPos(World world, BlockPos basePos) {
        MutableBlockPos pos = new MutableBlockPos(basePos.getX() / 8, basePos.getY(), basePos.getZ() / 8);
        MutableBlockPos spawnPosition = new MutableBlockPos(-1, -1, -1);
        int possibleYPosition = 0;

        do {
            //Scan the x,z coordinate point along the Y column to find a possible spawn location. Returns -1 if no location is found
            possibleYPosition = scanColumn(world, pos.getX(), pos.getZ(), pos.getY());
            if(possibleYPosition == -1) {
                incrementColumn(pos, basePos);
            }
            else {
                spawnPosition.setPos(pos.getX(), possibleYPosition, pos.getZ());
            }
        }
        while(spawnPosition.getY() == -1);

        return spawnPosition;
    }

    public static int scanColumn(World world, int x, int z, int targetY) {
        int possibleY = -1;
        //start searching Y locations at 4 to avoid vanilla bedrock generation
        for(int currentY = 4; currentY < world.getActualHeight(); currentY++) {
            BlockPos pos = new BlockPos(x, currentY, z);
            boolean isBlockBelowSolid = world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
            boolean isLegBlockSolid = world.getBlockState(pos).isNormalCube();
            boolean isChestBlockSolid = world.getBlockState(pos.up()).isNormalCube();

            //Check to see if the block below the player's feet is solid, and if the player has a two block spawning area
            if(isBlockBelowSolid && !isLegBlockSolid && !isChestBlockSolid) {
                //The first instance of a possible spawning location found
                if(possibleY == -1) {
                    possibleY = currentY;
                }
                else {
                    //If the currentY is closer to the targetY than the current possibleY,
                    //set that as the new possible spawning location
                    if(Math.abs(possibleY - targetY) > Math.abs(pos.getY() - targetY)) {
                        possibleY = currentY;
                    }
                }
            }

        }
        return possibleY;
    }

    public static void incrementColumn(MutableBlockPos currentPos, BlockPos originalPos) {

        double tempPosIncrementX = originalPos.getDistance(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        double tempPosIncrementZ = originalPos.getDistance(currentPos.getX(), currentPos.getY(), currentPos.getZ() + 1);

        if(tempPosIncrementX > tempPosIncrementZ) {
            currentPos.setPos(currentPos.getX(), currentPos.getY(), currentPos.getZ() + 1);
        }
        else {
            currentPos.setPos(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        }
    }

}
