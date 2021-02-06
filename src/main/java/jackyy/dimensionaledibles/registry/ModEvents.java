package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public void onEnderEyeUse(PlayerInteractEvent.RightClickBlock event) {
        if (ModConfig.tweaks.disableVanillaEndPortal) {
            ItemStack stack = event.getItemStack();
            EntityPlayer player = event.getEntityPlayer();
            if (!stack.isEmpty() && stack.getItem() == Items.ENDER_EYE && event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME) {
                event.setCanceled(true);
                player.sendStatusMessage(new TextComponentTranslation(Reference.MODID + ".error.end_portal_disabled").setStyle(new Style().setColor(TextFormatting.RED)), true);
            }
        }
    }

}
