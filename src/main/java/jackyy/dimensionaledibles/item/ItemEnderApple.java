package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.registry.ModConfig;
import jackyy.dimensionaledibles.util.Reference;
import jackyy.dimensionaledibles.util.TeleporterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemEnderApple extends ItemAppleBase {

    public ItemEnderApple() {
        setRegistryName(Reference.MODID + ":ender_apple");
        setUnlocalizedName(Reference.MODID + ".ender_apple");
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.provider.getDimension() != 1) {
            if (!world.isRemote) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                BlockPos coords;
                if (ModConfig.tweaks.enderApple.useCustomCoords) {
                    coords = new BlockPos(ModConfig.tweaks.enderApple.customCoords.x, ModConfig.tweaks.enderApple.customCoords.y, ModConfig.tweaks.enderApple.customCoords.z);
                } else {
                    coords = TeleporterHandler.getDimPos(playerMP, 1, player.getPosition());
                }
                TeleporterHandler.updateDimPos(playerMP, world.provider.getDimension(), player.getPosition());
                TeleporterHandler.teleport(playerMP, 1, coords.getX(), coords.getY(), coords.getZ(), playerMP.mcServer.getPlayerList());
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 200, false, false));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {
        if (ModConfig.general.enderApple)
            list.add(new ItemStack(this));
    }

}
