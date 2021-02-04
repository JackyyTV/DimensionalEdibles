package jackyy.dimensionaledibles.item;

import jackyy.dimensionaledibles.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAppleBase extends ItemFood {

    public ItemAppleBase() {
        super(4, 0.3F, false);
        setAlwaysEdible();
        setCreativeTab(Reference.TAB);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

}
