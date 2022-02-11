package committee.nova.plr.elytraBombing.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class EBConfig {
    public static final String INFUSION_TIME = "The infusion time of TNT bombed out.(Unit: tick)";
    public static final String CD_TIME = "The cool-down time of bombing TNTs.(Unit: tick)";
    public static final String INERTIA = "Should TNT dropped has inertia?";
    public static final String CD_NOTIFICATION = "What should be displayed when player tries to bomb during the cool-down time?";
    public static final String PLURAL_SUFFIX = "The suffix string in plural form";

    public static void initialize(JavaPlugin plugin) {
        final FileConfiguration config = plugin.getConfig();
        config.addDefault(INFUSION_TIME, 80);
        config.addDefault(CD_TIME, 60);
        config.addDefault(INERTIA, true);
        config.addDefault(CD_NOTIFICATION, "Bombing Cool-down Time: {0} tick{1}");
        config.addDefault(PLURAL_SUFFIX, "s");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
