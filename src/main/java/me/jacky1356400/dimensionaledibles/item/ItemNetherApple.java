package me.jacky1356400.dimensionaledibles.item;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import me.jacky1356400.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemNetherApple extends ItemFood {

    public ItemNetherApple() {
        super(4, 0.3F, false);
        setAlwaysEdible();
        setRegistryName(DimensionalEdibles.MODID + ":nether_apple");
        setUnlocalizedName(DimensionalEdibles.MODID + ".nether_apple");
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != -1) {
            if (!world.isRemote) {
                WorldServer worldServer = (WorldServer) world;
                TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
                tp.teleportToDimension(player, -1, 0, world.getSeaLevel(), 0);
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab))
            if (Config.netherApple)
                list.add(new ItemStack(this));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
