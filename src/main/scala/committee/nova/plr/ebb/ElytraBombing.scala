package committee.nova.plr.ebb

import com.sun.org.slf4j.internal.LoggerFactory
import committee.nova.plr.ebb.ElytraBombing.MODID
import committee.nova.plr.ebb.config.CommonConfig
import committee.nova.plr.ebb.event.{FMLEvent, ForgeEvent}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import net.minecraftforge.common.MinecraftForge

@Mod(modid = MODID, modLanguage = "scala", useMetadata = true, dependencies = "required-after:backlytra@[0.0.3,)", guiFactory = "committee.nova.plr.ebb.client.gui.factory.GuiFactory")
object ElytraBombing {
  final val MODID = "ebb"
  final val LOGGER = LoggerFactory.getLogger(this.getClass)

  @EventHandler def preInit(e: FMLPreInitializationEvent): Unit = CommonConfig.init(e)

  @EventHandler def init(e: FMLInitializationEvent): Unit = {
    FMLCommonHandler.instance().bus().register(new FMLEvent)
    MinecraftForge.EVENT_BUS.register(new ForgeEvent)
  }
}
