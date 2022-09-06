package committee.nova.plr.ebb.config

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Logger

import java.io.File

object CommonConfig {
  var inertia: Boolean = false
  var fuseTime: Int = 80
  var cd: Int = 60
  var config: Configuration = _
  private var logger: Logger = _

  def init(event: FMLPreInitializationEvent): Unit = {
    logger = event.getModLog
    config = new Configuration(new File(event.getModConfigurationDirectory, "ElytraBombing.cfg"))
    sync()
  }

  def sync(): Unit = {
    config.load()
    inertia = config.getBoolean("enableInertia", Configuration.CATEGORY_GENERAL, true, "Should TNTs have inertia when launched?", "config.ebb.inertia")
    fuseTime = config.getInt("fuseTime", Configuration.CATEGORY_GENERAL, 80, 20, Int.MaxValue, "How long should the launched TNT's fuse time be?", "config.ebb.fuseTime")
    cd = config.getInt("bombingCD", Configuration.CATEGORY_GENERAL, 60, 0, Int.MaxValue, "How long should the bombing cool-down time be?", "config.ebb.cd")
    config.save()
  }
}
