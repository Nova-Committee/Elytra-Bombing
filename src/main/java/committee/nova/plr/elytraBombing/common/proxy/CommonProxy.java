package committee.nova.plr.elytraBombing.common.proxy;

import committee.nova.plr.elytraBombing.common.config.ConfigLoader;
import committee.nova.plr.elytraBombing.common.event.EventLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        new ConfigLoader(event);
    }

    public void init(FMLInitializationEvent event) {
        new EventLoader();
    }
}
