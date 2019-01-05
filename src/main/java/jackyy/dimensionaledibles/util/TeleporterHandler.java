package jackyy.dimensionaledibles.util;

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
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

import jackyy.dimensionaledibles.DimensionalEdibles;

public class TeleporterHandler {

    public static void teleport(EntityPlayerMP player, int dim, double x, double y, double z, PlayerList playerList) {
	if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(player, dim))
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

	if (worldServer1.provider.getDimension() == -1) {
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

    public static void updateDimensionPosition(EntityPlayer player, int dimension, BlockPos pos) {
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

    public static BlockPos getDimensionPosition(EntityPlayerMP player, int dimension, BlockPos currentPos) {
	NBTTagCompound dimensionCache = getModNBTData(player);
	NBTTagCompound dimPos = (NBTTagCompound) dimensionCache.getTag("" + dimension);
	BlockPos position;
	if (dimPos == null) {
	    dimPos = new NBTTagCompound();
	    dimensionCache.setTag("" + dimension, dimPos);

	    WorldServer newDimworld = player.server.getPlayerList().getServerInstance().getWorld(dimension);

	    position = getValidYSpawnPosition(newDimworld, currentPos);
	} else {
	    position = new BlockPos(dimPos.getInteger("x"), dimPos.getInteger("y"), dimPos.getInteger("z"));
	}

	return position;
    }

    public static BlockPos getValidYSpawnPosition(World world, BlockPos basePos) {
	MutableBlockPos pos = new MutableBlockPos(basePos.getX() / 8, basePos.getY(), basePos.getZ() / 8);
	boolean foundSpawnPos = false;
	while (!foundSpawnPos && pos.getY() < world.getActualHeight()) {
	    if (world.getBlockState(pos.add(0, -1, 0)).isSideSolid(world, pos.add(0, -1, 0), EnumFacing.UP) && !world.getBlockState(pos).isNormalCube() && !world.getBlockState(pos.add(0, 1, 0)).isNormalCube()) {
		foundSpawnPos = true;
	    }
	    if (!foundSpawnPos) {
		pos.move(EnumFacing.UP);
	    }
	}
	while (!foundSpawnPos && pos.getY() > 0) {
	    if (pos.getY() < world.getActualHeight()) {
		if (world.getBlockState(pos.add(0, -1, 0)).isSideSolid(world, pos.add(0, -1, 0), EnumFacing.UP) && !world.getBlockState(pos).isNormalCube() && !world.getBlockState(pos.add(0, 1, 0)).isNormalCube()) {
		    foundSpawnPos = true;
		}
	    }
	    if (!foundSpawnPos) {
		pos.move(EnumFacing.DOWN);
	    }
	}

	if (!foundSpawnPos) {
	    pos.setPos(basePos);
	    for (int x = -1; x < 2; x++) {
		for (int z = -1; z < 2; z++) {
		    world.setBlockState(pos.add(x, -1, z), Blocks.OBSIDIAN.getDefaultState());
		    world.setBlockToAir(pos.add(x, 0, z));
		    world.setBlockToAir(pos.add(x, 1, z));
		}
	    }
	}
	return pos;
    }

}
