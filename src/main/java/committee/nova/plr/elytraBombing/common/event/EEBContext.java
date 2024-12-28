package committee.nova.plr.elytraBombing.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EEBContext {
    //not null
    public EntityPlayer player;
    //may be null @ACCESS_IGNITER
    public ItemStack tntStack;
    //not null
    public ItemStack igniterStack;
}
