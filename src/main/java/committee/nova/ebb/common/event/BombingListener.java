package committee.nova.ebb.common.event;

import committee.nova.ebb.common.config.CommonConfig;
import committee.nova.ebb.common.data.tag.Tags;
import committee.nova.ebb.common.util.Utilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BombingListener {
    @SubscribeEvent
    public static void onPlayerRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        final var player = event.getEntity();
        if (!player.isFallFlying()) return;
        final var igniter = event.getItemStack();
        if (!igniter.is(Tags.IGNITERS)) return;
        final var tnt = Utilities.searchFor(player, Items.TNT, 0);
        if (tnt == null || tnt.isEmpty()) return;
        final var tntItem = tnt.getItem();
        if (player.getCooldowns().isOnCooldown(tntItem)) {
            final int cd = (int) (player.getCooldowns().getCooldownPercent(tntItem, 0F) * CommonConfig.TNT_BOMBING_CD.get());
            final var isPlural = cd > 1;
            if (!player.level().isClientSide) {
                player.displayClientMessage(Component.translatable(
                        isPlural ? "msg.ebb.bombing_cd.plural" : "msg.ebb.bombing_cd",
                        cd
                ), true);
            }
            return;
        }
        Utilities.launchTnt(player, igniter, tnt);
    }
}
