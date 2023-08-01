package committee.nova.plr.elytraBombing;

import committee.nova.plr.elytraBombing.common.config.ConfigLoader;
import committee.nova.plr.elytraBombing.common.event.EEBEvents;
import committee.nova.plr.elytraBombing.common.event.EventLoader;
import committee.nova.plr.elytraBombing.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ElytraBombing.MOD_ID, name = ElytraBombing.MOD_NAME, version = ElytraBombing.VERSION, acceptedMinecraftVersions = "[1.12,)")
public class ElytraBombing {
    public static final String MOD_ID = "ebb";
    public static final String MOD_NAME = "Elytra Bombing";
    public static final String VERSION = "1.0.0";



    @EventHandler
    public void init(FMLInitializationEvent event) {

        new EventLoader();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        new ConfigLoader(event);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.post(new EEBEvents.RegisterBomb());
    }
}
