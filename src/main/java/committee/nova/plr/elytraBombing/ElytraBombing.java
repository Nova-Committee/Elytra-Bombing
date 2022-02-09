package committee.nova.plr.elytraBombing;

import committee.nova.plr.elytraBombing.common.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ElytraBombing.MOD_ID, name = ElytraBombing.MOD_NAME, version = ElytraBombing.VERSION, acceptedMinecraftVersions = "[1.11,)")
public class ElytraBombing {
    public static final String MOD_ID = "ebb";
    public static final String MOD_NAME = "Elytra Bombing";
    public static final String VERSION = "1.0.0";

    @SidedProxy(serverSide = "committee.nova.plr.elytraBombing.common.proxy.CommonProxy", clientSide = "committee.nova.plr.elytraBombing.client.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}
