package committee.nova.plr.elytraBombing.tools;

import committee.nova.plr.elytraBombing.ElytraBombing;
import committee.nova.plr.elytraBombing.config.EBConfig;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.text.MessageFormat;

public class EBTools {
    public static final int[] removeOneFromStartSlot = new int[]{0, 1};

    //Config
    public static String getStringOrEmpty(FileConfiguration cfg, String path) {
        return cfg.getString(path) != null ? cfg.getString(path) : "";
    }

    //Player
    public static int getMaterialAmountInInv(Material item, PlayerInventory inv) {
        int m = 0;
        final int s = inv.getSize();
        for (int i = 0; i <= s; i++) {
            final ItemStack stack = inv.getItem(i);
            if (stack != null && stack.getType() == item) {
                m += stack.getAmount();
            }
        }
        return m;
    }

    public static int tryRemoveStackInInv(Player player, PlayerInventory inv, Material toRemove, int[] removeAttributes) {
        int amountToRemove = removeAttributes[1];
        int index = removeAttributes[0];
        final int size = inv.getSize();
        if (amountToRemove > 0 && index < size) {
            final ItemStack itemGot = inv.getItem(index);
            if (itemGot != null && itemGot.getType() == toRemove) {
                final int amountGot = itemGot.getAmount();
                if (itemGot.getAmount() == 1) {
                    itemGot.setType(Material.AIR);
                } else {
                    itemGot.setAmount(Math.max(0, amountGot - amountToRemove));
                }
                inv.setItem(index, itemGot);
                player.updateInventory();
                amountToRemove = Math.max(0, amountToRemove - amountGot);
            }
            index++;
            removeAttributes = new int[]{index, amountToRemove};
            tryRemoveStackInInv(player, inv, toRemove, removeAttributes);
        }
        return amountToRemove;
    }

    public static void launchTnt(FileConfiguration config, Player player, ItemStack igniter, boolean inertia) {
        final Vector playerSpeed = inertia ? player.getVelocity() : new Vector(0, 0, 0);
        final Location loc = player.getLocation().subtract(0, 1D, 0);
        final World world = player.getWorld();
        final TNTPrimed tnt = (TNTPrimed) world.spawnEntity(loc, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(config.getInt(EBConfig.INFUSION_TIME));
        tnt.setVelocity(playerSpeed);
        player.playSound(loc, Sound.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1F, 1F);
        final PlayerInventory inv = player.getInventory();
        if (igniter.getType() != Material.FLINT_AND_STEEL) {
            tryRemoveStackInInv(player, inv, igniter.getType(), removeOneFromStartSlot);
        }

        final int removed = tryRemoveStackInInv(player, inv, Material.TNT, removeOneFromStartSlot);
        if (removed != 1) {
            ElytraBombing.LOGGER.warning(MessageFormat.format("Unexpected amount of TNT removed, expected 1, got {0}", removed));
        }
        player.setCooldown(Material.TNT, config.getInt(EBConfig.CD_TIME));
    }
}
