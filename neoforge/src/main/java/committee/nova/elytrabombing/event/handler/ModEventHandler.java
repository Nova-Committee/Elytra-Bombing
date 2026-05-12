package committee.nova.elytrabombing.event.handler;

import committee.nova.elytrabombing.CommonClass;
import committee.nova.elytrabombing.config.CommonConfig;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class ModEventHandler {
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        event.setCancellationResult(CommonClass.bombard(
                event.getEntity(),
                event.getLevel(),
                event.getHand(),
                CommonConfig.TNT_BOMBING_CD.get(),
                CommonConfig.TNT_FUSE_TIME.get(),
                CommonConfig.INERTIA.get()
        ));
        if (event.getCancellationResult() == InteractionResult.SUCCESS) event.setCanceled(true);
    }
}
