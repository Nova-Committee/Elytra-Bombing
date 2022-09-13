package committee.nova.plr.ebb.compat

import committee.nova.plr.ebb.ElytraBombing
import ganymedes01.etfuturum.api.elytra.IElytraPlayer

object EFRCompat {
  def init(): Boolean = {
    try {
      ElytraBombing.elytraStatusCheck = player => {
        val elytraUser = player.asInstanceOf[IElytraPlayer]
        elytraUser.etfu$isElytraFlying()
      }
      ElytraBombing.LOGGER.info("Et Futurum Requiem interaction established!")
      true
    } catch {
      case e: Exception =>
        ElytraBombing.LOGGER.warn("Et Futurum Requiem loaded but elytra content not found either.")
        false
    }
  }
}
