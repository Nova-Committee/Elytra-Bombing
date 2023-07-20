package committee.nova.elytraBombing.common;

import committee.nova.elytraBombing.common.config.CommonConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ElytraBombing.MODID)
public class ElytraBombing {
    public static final String MODID = "ebb";

    public ElytraBombing() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.CONFIG);
    }
}
