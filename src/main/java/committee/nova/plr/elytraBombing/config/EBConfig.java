package committee.nova.plr.elytraBombing.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class EBConfig {
    public static final String INFUSION_TIME = "The infusion time of TNT bombed out.(Unit: tick)";
    public static final String INERTIA = "Should TNT dropped has inertia?";

    public static void initialize(JavaPlugin plugin) {
        final FileConfiguration config = plugin.getConfig();
        config.addDefault(INFUSION_TIME, 80);
        config.addDefault(INERTIA, true);
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
