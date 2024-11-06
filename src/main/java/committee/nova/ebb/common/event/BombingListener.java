package committee.nova.ebb.common.event;

import committee.nova.ebb.common.ElytraBombing;
import committee.nova.ebb.common.config.CommonConfig;
import committee.nova.ebb.common.util.Utilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.text.MessageFormat;

@EventBusSubscriber
public class BombingListener {
    @SubscribeEvent
    public static void onPlayerRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        final var player = event.getEntity();
        if (!player.isFallFlying()) return;
        final var igniter = event.getItemStack();
        if (!igniter.is(ElytraBombing.IGNITERS)) return;
        final InteractionHand hand = event.getHand();
        final var tnt = Utilities.searchFor(player, Items.TNT, 0);
        if (tnt == null || tnt.isEmpty()) return;
        if (player.getCooldowns().isOnCooldown(tnt)) {
            final int cd = (int) (player.getCooldowns().getCooldownPercent(tnt, 0F) * CommonConfig.TNT_BOMBING_CD.get());
            final var isPlural = cd > 1;
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                        Component.translatable(MessageFormat.format(
                                Component.translatable(isPlural ? "msg.ebb.bombing_cd_plural" : "msg.ebb.bombing_cd").getString(),
                                cd
                        )), true);
            }
            return;
        }
        Utilities.launchTnt(player, igniter, tnt, hand);
    }
}
