package committee.nova.elytraBombing.common.event;

import committee.nova.elytraBombing.common.config.CommonConfig;
import committee.nova.elytraBombing.common.data.tag.Tags;
import committee.nova.elytraBombing.common.tools.player.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.MessageFormat;

@Mod.EventBusSubscriber
public class BombingListener {
    @SubscribeEvent
    public static void onPlayerRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        final EntityPlayer player = event.getEntityPlayer();
        if (!player.isElytraFlying()) {
            return;
        }
        final ItemStack igniter = event.getItemStack();
        if (!Tags.IGNITERS.contains(igniter.getItem())) {
            return;
        }
        final ItemStack tnt = PlayerHandler.searchFor(player, new ItemStack(Blocks.TNT).getItem(), 0);
        if (tnt == null || tnt.isEmpty()) {
            return;
        }

        final Item tntItem = tnt.getItem();
        if (player.getCooldownTracker().hasCooldown(tntItem)) {
            final int cd = (int) (player.getCooldownTracker().getCooldown(tntItem, 0F) * CommonConfig.TNT_BOMBING_CD.get());
            final boolean isPlural = cd > 1;
            if (!player.world.isRemote) {
                player.sendMessage(
                        new TextComponentTranslation(MessageFormat.format(
                                new TextComponentTranslation("msg.ebb.bombing_cd").getString(),
                                cd,
                                isPlural ? new TextComponentTranslation("msg.ebb.unit.plural_suffix").getString() : ""
                        )));
            }
            return;
        }
        PlayerHandler.launchTnt(player, igniter, tnt);
    }
}
