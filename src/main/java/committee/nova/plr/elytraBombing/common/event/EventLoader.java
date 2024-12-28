package committee.nova.plr.elytraBombing.common.event;

import committee.nova.plr.elytraBombing.common.tools.player.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventLoader {
    public EventLoader() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        final EntityPlayer player = event.getEntityPlayer();
        EEBContext context=new EEBContext();
        context.player=player;
        //player is not null

        if (player==null)return;
        if (player.isSpectator())return;

        boolean flag=false;
        for (Predicate<EntityPlayer> predicate:EEBEvents.ACCESS_FLYING){
            if(predicate.test(player)) {
                flag=true;
                break;
            }
        }
        if (!flag)return;
        final ItemStack stackIgiter = event.getItemStack();
        if (stackIgiter.isEmpty()) {
            return;
        }
        for (Predicate<ItemStack> accessIngiter: EEBEvents.ACCESS_IGNITER.keySet()){
            if (accessIngiter.test(stackIgiter)){
                Consumer<EEBContext> consumer= EEBEvents.ACCESS_IGNITER.get(accessIngiter);
                //igniter is not null
                context.igniterStack=stackIgiter;
                consumer.accept(context);
                break;
            }
        }

        if (context.igniterStack==null) return;

        final Predicate<ItemStack> tnt = PlayerHandler.searchFor(context);
        if (tnt==null) return;

        Consumer<EEBContext> consumer=EEBEvents.ACCESS_TNT.get(tnt);
        //tnt is not null
        consumer.accept(context);

    }
}
