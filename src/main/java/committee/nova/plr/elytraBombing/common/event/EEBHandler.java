package committee.nova.plr.elytraBombing.common.event;

import committee.nova.plr.elytraBombing.ElytraBombing;
import committee.nova.plr.elytraBombing.common.tools.player.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= ElytraBombing.MOD_ID)
public class EEBHandler {
    @SubscribeEvent
    public static void register(EEBEvents.RegisterBomb registerBomb){
        registerBomb.registerTNT(
                (stack)->stack.getItem()== Item.getItemFromBlock(Blocks.TNT),
                PlayerHandler::preLaunchTnt
        );
        registerBomb.registerFlying(EntityLivingBase::isElytraFlying);
        registerBomb.registerIgniter(
                (stack)-> stack.getItem()== Items.FLINT_AND_STEEL || stack.getItem()==Items.FIRE_CHARGE,
                PlayerHandler::damageStack
        );
    }
}
