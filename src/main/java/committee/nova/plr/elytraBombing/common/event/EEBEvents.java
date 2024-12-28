package committee.nova.plr.elytraBombing.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class EEBEvents
{
    public static HashMap<Predicate<ItemStack>, Consumer<EEBContext>> ACCESS_IGNITER=new HashMap<>();
    public static List<Predicate<EntityPlayer>> ACCESS_FLYING=new LinkedList<>();
    public static Map<Predicate<ItemStack>,Consumer<EEBContext>> ACCESS_TNT=new HashMap<>();

    /** @Fire{@link net.minecraftforge.common.MinecraftForge.EVENT_BUS} **/

    public static class RegisterBomb extends Event {
        @Override
        public boolean isCancelable() {
            return false;
        }
        public void registerTNT(Predicate<ItemStack> canBeUsed,Consumer<EEBContext> useResult){
            ACCESS_TNT.put(canBeUsed,useResult);
        }
        public void registerIgniter(Predicate<ItemStack> canBeUsed ,Consumer<EEBContext> useResult){
            ACCESS_IGNITER.put(canBeUsed,useResult);
        }
        public void registerFlying(Predicate<EntityPlayer> returnIsFlying){
            ACCESS_FLYING.add(returnIsFlying);
        }
    }

}
