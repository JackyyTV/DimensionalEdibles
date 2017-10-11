package me.jacky1356400.dimensionaledibles.item;

import me.jacky1356400.dimensionaledibles.Config;
import me.jacky1356400.dimensionaledibles.DimensionalEdibles;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemEnderApple extends ItemFood {

    public ItemEnderApple() {
        super(4, 0.3F, false);
        setAlwaysEdible();
        setRegistryName(DimensionalEdibles.MODID + ":ender_apple");
        setUnlocalizedName(DimensionalEdibles.MODID + ".ender_apple");
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != 1) {
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200));
            player.changeDimension(1);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (Config.enderApple)
            list.add(new ItemStack(item));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
