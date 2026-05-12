package committee.nova.elytrabombing;

import committee.nova.elytrabombing.config.CommonConfig;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.neoforged.fml.config.ModConfig;

public class ElytraBombing implements ModInitializer {
    @Override
    public void onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        // Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, CommonConfig.CONFIG);
        UseItemCallback.EVENT.register((p, l, h) -> CommonClass.bombard(
                p, l, h,
                CommonConfig.TNT_BOMBING_CD.get(),
                CommonConfig.TNT_FUSE_TIME.get(),
                CommonConfig.INERTIA.get()
        ));
    }
}
