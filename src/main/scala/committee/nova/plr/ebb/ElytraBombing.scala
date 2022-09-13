package committee.nova.plr.ebb

import committee.nova.plr.ebb.ElytraBombing.MODID
import committee.nova.plr.ebb.compat.{BacklytraCompat, EFRCompat}
import committee.nova.plr.ebb.config.CommonConfig
import committee.nova.plr.ebb.event.{FMLEvent, ForgeEvent}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{FMLCommonHandler, Loader, Mod}
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.MinecraftForge
import org.apache.logging.log4j.LogManager

@Mod(modid = MODID, modLanguage = "scala", useMetadata = true, guiFactory = "committee.nova.plr.ebb.client.gui.factory.GuiFactory")
object ElytraBombing {
  final val MODID = "ebb"
  final val LOGGER = LogManager.getLogger(MODID)
  var elytraStatusCheck: EntityPlayer => Boolean = _

  @EventHandler def preInit(e: FMLPreInitializationEvent): Unit = CommonConfig.init(e)

  @EventHandler def init(e: FMLInitializationEvent): Unit = {
    if (!checkCompat()) return
    FMLCommonHandler.instance().bus().register(new FMLEvent)
    MinecraftForge.EVENT_BUS.register(new ForgeEvent)
  }

  def checkCompat(): Boolean = {
    if (Loader.isModLoaded("backlytra") && BacklytraCompat.init()) return true
    if (Loader.isModLoaded("etfuturum") && EFRCompat.init()) return true
    Array("Failed interacting with Et Futurum Requiem or Backlytra...",
      "ElytraBombing events won't be registered.",
      "If you are using another 1.7.10 elytra mod, please send an issue for new compatibility on https://github.com/Nova-Committee/Elytra-Bombing/issues"
    ).foreach(msg => LOGGER.warn(msg))
    false
  }
}
