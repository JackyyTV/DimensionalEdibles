package jackyy.dimensionaledibles.registry;

import jackyy.dimensionaledibles.util.Reference;
import jackyy.gunpowderlib.helper.ChatHelper;
import jackyy.gunpowderlib.helper.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
                ChatHelper.msgPlayerRaw(player, TextFormatting.RED + StringHelper.localize(Reference.MODID, "error.end_portal_disabled"));
            }
        }
    }

}
