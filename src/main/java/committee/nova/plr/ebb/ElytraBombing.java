package committee.nova.plr.ebb;

import com.google.inject.Inject;
import committee.nova.plr.ebb.util.Utilities;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.fused.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.type.Include;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.world.World;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * @author Tapio
 */
@Plugin(ElytraBombing.PLUGIN_ID)
public class ElytraBombing {
    public static final String PLUGIN_ID = "ebb";
    private final Logger logger;

    @Inject
    ElytraBombing(final PluginContainer container, final Logger logger) {
        this.logger = logger;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        this.logger.info("Constructing ElytraBombing");
    }

    @Listener
    @Include(InteractItemEvent.Secondary.class)
    public void onInteractItem(final InteractItemEvent.Secondary event, @First Player player) {
        if (!player.elytraFlying().get()) return;
        final ItemStackSnapshot stack = event.itemStack();
        final ItemType type = stack.createStack().type();
        final Tuple<ItemType, ItemType> bombingItems = Tuple.of(ItemTypes.FIRE_CHARGE.get(), ItemTypes.FLINT_AND_STEEL.get());
        if (!type.isAnyOf(bombingItems.first(), bombingItems.second())) return;
        final Inventory inv = player.inventory();
        final ItemType tnt = ItemTypes.TNT.get();
        if (!inv.contains(tnt)) return;
        if (Utilities.consumeStackByType(inv, tnt, 1) != 1) return;
        final World<?, ?> world = player.world();
        final PrimedTNT tntEntity = world.createEntity(EntityTypes.TNT, player.position().add(0F, .5F, 0F));
        tntEntity.velocity().set(player.velocity().get());
        world.spawnEntity(tntEntity);
    }

}
