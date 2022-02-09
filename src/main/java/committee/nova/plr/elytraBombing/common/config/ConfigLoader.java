package committee.nova.plr.elytraBombing.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader {
    private static Configuration config;

    public static int infusion;
    public static int cd;
    public static boolean inertia;

    public ConfigLoader(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        init();
    }

    public void init() {
        config.load();
        infusion = config.get(Configuration.CATEGORY_GENERAL,
                "infusion", 80,
                "How long should the launched TNT's infusion time be?").getInt();
        cd = config.get(Configuration.CATEGORY_GENERAL,
                "bombing_cd", 60,
                "How long should the bombing cool-down time be?").getInt();
        inertia = config.get(Configuration.CATEGORY_GENERAL, "inertia", true, "Does TNT have inertia? If true, the player's real-time speed will influence the TNT's initial speed.").getBoolean();
        config.save();
    }
}
