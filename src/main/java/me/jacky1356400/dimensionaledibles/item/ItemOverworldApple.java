package me.jacky1356400.dimensionaledibles.item;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemOverworldApple extends ItemFood {

    public ItemOverworldApple() {
        super(4, 0.3F, false);
        setAlwaysEdible();
        setRegistryName(DimensionalEdibles.MODID + ":overworld_apple");
        setUnlocalizedName(DimensionalEdibles.MODID + ".overworld_apple");
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != 0) {
            if (!world.isRemote) {
                WorldServer worldServer = (WorldServer) world;
                TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200));
                BlockPos spawnCoords = ((WorldServer) world).getSpawnCoordinate();
                tp.teleportToDimension(player, 0, spawnCoords.getX(), spawnCoords.getY(), spawnCoords.getZ());
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (Config.overworldApple)
            list.add(new ItemStack(item));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
