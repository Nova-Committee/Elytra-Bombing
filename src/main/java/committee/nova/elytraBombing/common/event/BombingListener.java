package committee.nova.elytraBombing.common.event;

import committee.nova.elytraBombing.common.config.CommonConfig;
import committee.nova.elytraBombing.common.data.tag.Tags;
import committee.nova.elytraBombing.common.tools.player.PlayerHandler;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.MessageFormat;

@Mod.EventBusSubscriber
public class BombingListener {
    @SubscribeEvent
    public static void onPlayerRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        final Player player = event.getPlayer();
        if (!player.isFallFlying()) {
            return;
        }
        final ItemStack igniter = event.getItemStack();
        if (!igniter.is(Tags.IGNITERS)) {
            return;
        }
        final ItemStack tnt = PlayerHandler.searchFor(player, Items.TNT, 0);
        if (tnt == null || tnt.isEmpty()) {
            return;
        }

        final Item tntItem = tnt.getItem();
        if (player.getCooldowns().isOnCooldown(tntItem)) {
            final int cd = (int) (player.getCooldowns().getCooldownPercent(tntItem, 0F) * CommonConfig.TNT_BOMBING_CD.get());
            final boolean isPlural = cd > 1;
            if (!player.level.isClientSide) {
                player.displayClientMessage(
                        new TranslatableComponent(MessageFormat.format(
                                new TranslatableComponent("msg.ebb.bombing_cd").getString(),
                                cd,
                                isPlural ? new TranslatableComponent("msg.ebb.unit.plural_suffix").getString() : ""
                        )), true);
            }
            return;
        }
        PlayerHandler.launchTnt(player, igniter, tnt);
    }
}
