package committee.nova.plr.ebb.client.gui.screen

import committee.nova.plr.ebb.ElytraBombing
import committee.nova.plr.ebb.config.CommonConfig
import cpw.mods.fml.client.config.GuiConfig
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.config.{ConfigElement, Configuration}

class GuiScreenConfig(parent: GuiScreen) extends GuiConfig(parent,
  new ConfigElement(CommonConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements,
  ElytraBombing.MODID,
  ElytraBombing.MODID,
  false,
  false,
  "ElytraBombing Config"
)
