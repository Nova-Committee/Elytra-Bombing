package committee.nova.elytrabombing;


import committee.nova.elytrabombing.config.CommonConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class ElytraBombing {

    public ElytraBombing(IEventBus eventBus, ModContainer container) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        // Constants.LOG.info("Hello NeoForge world!");
        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.CONFIG);
        CommonClass.init();

    }
}