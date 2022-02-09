package committee.nova.plr.elytraBombing.common.event;

import committee.nova.plr.elytraBombing.common.tools.player.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.MessageFormat;

public class EventLoader {
    public EventLoader() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        final EntityPlayer player = event.getEntityPlayer();
        if (!player.isElytraFlying()) {
            return;
        }
        final ItemStack stack = event.getItemStack();
        if (stack == null) {
            return;
        }
        final Item igniter = stack.getItem();
        if (!(igniter == Items.FLINT_AND_STEEL) && !(igniter == Items.FIRE_CHARGE)) {
            return;
        }
        final ItemStack tnt = PlayerHandler.searchFor(player, new ItemStack(Blocks.TNT).getItem(), 0);
        if (tnt == null) {
            return;
        }
        final Item tntItem = tnt.getItem();
        if (player.getCooldownTracker().hasCooldown(tntItem)) {
            final int cd = (int) (player.getCooldownTracker().getCooldown(tntItem, 0) * 60);
            final boolean isPlural = cd > 1;
            if (!player.worldObj.isRemote) {
                player.addChatMessage(new TextComponentString(
                        MessageFormat.format(new TextComponentTranslation("msg.ebb.cd").getFormattedText(),
                                cd + "",
                                isPlural ? new TextComponentTranslation("msg.ebb.unit.plural_suffix").getFormattedText() : "")
                ));
            }
            return;
        }
        PlayerHandler.launchTnt(player, stack, tnt);
    }
}
