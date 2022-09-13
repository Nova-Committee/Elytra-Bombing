package committee.nova.plr.ebb.compat

import committee.nova.plr.ebb.ElytraBombing
import ganymedes01.etfuturum.api.elytra.IElytraPlayer

object EFRCompat {
  def init(): Boolean = {
    try {
      ElytraBombing.elytraStatusCheck = player => player.asInstanceOf[IElytraPlayer].etfu$isElytraFlying()
      ElytraBombing.LOGGER.info("Et Futurum Requiem interaction established!")
      true
    } catch {
      case _: Exception =>
        ElytraBombing.LOGGER.warn("Et Futurum Requiem loaded but elytra content not found either.")
        false
    }
  }
}
