package committee.nova.plr.ebb.event

import committee.nova.plr.ebb.ElytraBombing
import committee.nova.plr.ebb.config.CommonConfig
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent

class FMLEvent {
  @SubscribeEvent
  def onTick(e: PlayerTickEvent): Unit = {
    val player = e.player
    if (player.worldObj.isRemote) return
    val tag = player.getEntityData
    tag.getInteger(ElytraBombing.MODID) match {
      case i if i > 0 => tag.setInteger(ElytraBombing.MODID, i - 1)
      case _ =>
    }
  }

  @SubscribeEvent
  def onConfigChange(event: OnConfigChangedEvent): Unit = {
    if (event.modID.equals(ElytraBombing.MODID)) {
      CommonConfig.config.save()
      CommonConfig.sync()
    }
  }
}
