package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.DimensionalEdibles;
import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
                BlockPos coords;
                if (ModConfig.tweaks.overworldApple.useCustomCoords) {
                    coords = new BlockPos(ModConfig.tweaks.overworldApple.customCoords.x, ModConfig.tweaks.overworldApple.customCoords.y, ModConfig.tweaks.overworldApple.customCoords.z);
                } else {
                    coords = world.getSpawnPoint();
                    while (world.getBlockState(world.getSpawnPoint()).isFullCube()) {
                        coords = coords.up(2);
                    }
                }
                TeleporterHandler.teleport(playerMP, 0, coords.getX(), coords.getY(), coords.getZ(), playerMP.mcServer.getPlayerList());
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (ModConfig.general.overworldApple)
            list.add(new ItemStack(item));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
