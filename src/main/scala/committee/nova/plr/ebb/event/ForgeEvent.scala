package committee.nova.plr.ebb.event

import com.unascribed.backlytra.MethodImitations
import committee.nova.plr.ebb.ElytraBombing
import committee.nova.plr.ebb.config.CommonConfig
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.item.EntityTNTPrimed
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.Item
import net.minecraft.util.ChatComponentText
import net.minecraftforge.event.entity.player.PlayerInteractEvent

class ForgeEvent {
  @SubscribeEvent
  def onUseItem(e: PlayerInteractEvent): Unit = {
    if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR) return
    val player = e.entityPlayer
    val world = player.worldObj
    if (world.isRemote) return
    if (!MethodImitations.isElytraFlying(player)) return
    if (!Array(Items.flint_and_steel, Items.fire_charge).contains(player.getHeldItem.getItem)) return
    val tag = player.getEntityData
    tag.getInteger(ElytraBombing.MODID) match {
      case i if i > 0 => player.addChatMessage(new ChatComponentText(s"CD: $i tick(s)"))
      case _ =>
        if (!player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.tnt))) return
        val tnt = new EntityTNTPrimed(world, player.posX, player.posY, player.posZ, player)
        tnt.fuse = CommonConfig.fuseTime
        if (CommonConfig.inertia) tnt.setVelocity(player.motionX, player.motionY, player.motionZ)
        world.spawnEntityInWorld(tnt)
        world.playSoundAtEntity(tnt, "game.tnt.primed", 1F, 1F)
        player.getHeldItem.damageItem(1, player)
        tag.setInteger(ElytraBombing.MODID, CommonConfig.cd)
    }
  }
}
