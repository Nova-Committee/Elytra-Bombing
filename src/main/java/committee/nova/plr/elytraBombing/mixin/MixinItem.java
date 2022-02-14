package committee.nova.plr.elytraBombing.mixin;

import committee.nova.plr.elytraBombing.tools.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.text.MessageFormat;

@Mixin({Item.class})
public abstract class MixinItem {
    @Inject(method = {"onItemRightClick"}, at = {@At("HEAD")}, cancellable = true)
    public void click(World worldIn, EntityPlayer playerIn, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        if (!playerIn.isElytraFlying()) {
            return;
        }
        final ItemStack stackIn = playerIn.getHeldItem(handIn);
        final Item igniterItem = stackIn.getItem();
        if (igniterItem != Items.FLINT_AND_STEEL && igniterItem != Items.FIRE_CHARGE) {
            return;
        }
        final ItemStack tntStack = PlayerHandler.searchFor(playerIn, Blocks.TNT.asItem(), 0);
        if (tntStack.isEmpty()) {
            return;
        }
        final Item tntItem = tntStack.getItem();
        if (playerIn.getCooldownTracker().hasCooldown(tntItem)) {
            final int cd = (int) (playerIn.getCooldownTracker().getCooldown(tntItem, 0F) * 60);
            final boolean isPlural = cd > 1;
            if (!playerIn.world.isRemote) {
                playerIn.sendMessage(
                        new TextComponentTranslation(MessageFormat.format(
                                new TextComponentTranslation("msg.ebb.bombing_cd").getString(),
                                cd,
                                isPlural ? new TextComponentTranslation("msg.ebb.unit.plural_suffix").getString() : ""
                        )));
            }
            return;
        }
        PlayerHandler.launchTnt(playerIn, stackIn, tntStack);
        cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn)));
        cir.cancel();
    }
}
