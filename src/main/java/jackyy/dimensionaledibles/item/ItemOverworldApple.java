package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.Reference;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOverworldApple extends ItemAppleBase {

    public ItemOverworldApple() {
        setRegistryName(Reference.MODID + ":overworld_apple");
        setTranslationKey(Reference.MODID + ".overworld_apple");
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != 0) {
            if (!world.isRemote) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                BlockPos coords;
                if (ModConfig.tweaks.overworldApple.useCustomCoords) {
                    coords = new BlockPos(ModConfig.tweaks.overworldApple.customCoords.x, ModConfig.tweaks.overworldApple.customCoords.y, ModConfig.tweaks.overworldApple.customCoords.z);
                } else {
                    WorldServer overworld = playerMP.server.getPlayerList().getServerInstance().getWorld(0);
                    if (ModConfig.tweaks.overworldApple.useWorldSpawn) {
                        coords = overworld.getTopSolidOrLiquidBlock(overworld.getSpawnPoint());
                    } else {
                        coords = TeleporterHandler.getDimPos(playerMP, 0, player.getPosition());
                    }
                }
                TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
                TeleporterHandler.teleport(playerMP, 0, coords.getX(), coords.getY(), coords.getZ(), playerMP.server.getPlayerList());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
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

}
