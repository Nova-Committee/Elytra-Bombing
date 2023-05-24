package committee.nova.plr.ebb.compat

import committee.nova.plr.ebb.ElytraBombing

import scala.util.Try

object EFRCompat {
  def init(): Boolean = {
    Try(Class.forName("ganymedes01.etfuturum.api.elytra.IElytraPlayer"))
      .orElse(Try(Class.forName("ganymedes01.etfuturum.elytra.IElytraPlayer"))).toOption match {
      case Some(e) =>
        Try(e.getMethod("etfu$isElytraFlying")).toOption match {
          case Some(m) =>
            m.setAccessible(true)
            ElytraBombing.elytraStatusCheck = p => Try(m.invoke(p).asInstanceOf[Boolean]).getOrElse(false)
            ElytraBombing.LOGGER.info("Et Futurum Requiem interaction established!")
            true
          case None =>
            ElytraBombing.LOGGER.warn("Et Futurum Requiem loaded but elytra content not found either.")
            false
        }
      case None =>
        ElytraBombing.LOGGER.warn("Et Futurum Requiem loaded but elytra content not found either.")
        false
    }
  }
}
