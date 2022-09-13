package committee.nova.plr.ebb.compat

import com.unascribed.backlytra.MethodImitations
import committee.nova.plr.ebb.ElytraBombing

object BacklytraCompat {
  def init(): Boolean = {
    try {
      ElytraBombing.elytraStatusCheck = player => MethodImitations.isElytraFlying(player)
      ElytraBombing.LOGGER.info("Backlytra interaction established!")
      true
    } catch {
      case _: Exception =>
        ElytraBombing.LOGGER.warn("Backlytra loaded but compatibility failed, checking the EFR one")
        false
    }
  }
}
