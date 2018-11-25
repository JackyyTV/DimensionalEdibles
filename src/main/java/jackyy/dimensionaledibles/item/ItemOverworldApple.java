package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOverworldApple extends ItemFood {

    public ItemOverworldApple() {
        super(4, 0.3F, false);
        setAlwaysEdible();
        setRegistryName(DimensionalEdibles.MODID + ":overworld_apple");
        setTranslationKey(DimensionalEdibles.MODID + ".overworld_apple");
        setCreativeTab(DimensionalEdibles.TAB);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != 0) {
            if (!world.isRemote) {
                WorldServer worldServer = (WorldServer) world;
                TeleporterHandler tp = new TeleporterHandler(worldServer, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
                BlockPos coords;
                if (player.getBedLocation(0) != null) {
                    coords = player.getBedLocation(0);
                } else {
                    coords = world.getSpawnPoint();
                }
                tp.teleportToDimension(player, 0, coords.getX(), coords.getY(), coords.getZ());
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab))
            if (ModConfig.general.overworldApple)
                list.add(new ItemStack(this));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
