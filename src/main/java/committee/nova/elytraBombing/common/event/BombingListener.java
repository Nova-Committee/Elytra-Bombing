package committee.nova.elytraBombing.common.event;

import committee.nova.elytraBombing.common.config.CommonConfig;
import committee.nova.elytraBombing.common.data.tag.Tags;
import committee.nova.elytraBombing.common.util.Utilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.MessageFormat;

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
                player.displayClientMessage(
                        Component.translatable(MessageFormat.format(
                                Component.translatable("msg.ebb.bombing_cd").getString(),
                                cd,
                                isPlural ? Component.translatable("msg.ebb.unit.plural_suffix").getString() : ""
                        )), true);
            }
            return;
        }
        Utilities.launchTnt(player, igniter, tnt);
    }
}
