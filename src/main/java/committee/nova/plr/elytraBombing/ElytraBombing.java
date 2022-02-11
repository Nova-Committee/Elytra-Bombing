package committee.nova.plr.elytraBombing;

import committee.nova.plr.elytraBombing.config.EBConfig;
import committee.nova.plr.elytraBombing.tools.EBTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ElytraBombing extends JavaPlugin implements Listener {
    public static final Logger LOGGER = Bukkit.getLogger();
    public final FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        LOGGER.info(ChatColor.GREEN + "Enabled " + this.getName());

        EBConfig.initialize(this);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerRightClickEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (!player.isGliding()) {
            return;
        }
        final PlayerInventory inv = player.getInventory();
        final ItemStack igniter = inv.getItemInMainHand();
        if (igniter.getType() != Material.FLINT_AND_STEEL && igniter.getType() != Material.FIREBALL) {
            return;
        }
        final int tntAmount = EBTools.getMaterialAmountInInv(Material.TNT, inv);
        if (tntAmount < 1) {
            return;
        }
        final boolean inertia = config.getBoolean(EBConfig.INERTIA);
        EBTools.launchTnt(config, player, igniter, inertia);
    }
}
