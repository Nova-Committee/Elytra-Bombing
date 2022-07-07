package committee.nova.plr.ebb;

import committee.nova.plr.ebb.config.CommonConfig;
import committee.nova.plr.ebb.tools.PlayerHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElytraBombing implements ModInitializer {
    public static final String M = "ElytraBombing";
    public static CommonConfig CONFIG;

    @Override
    public void onInitialize() {
        AutoConfig.register(CommonConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
        UseItemCallback.EVENT.register((player, world, hand) -> {
            final var stackInHand = player.getStackInHand(hand);
            return onBombing(player, world, hand, stackInHand) ? TypedActionResult.success(stackInHand) : TypedActionResult.pass(stackInHand);
        });
    }

    public boolean onBombing(PlayerEntity player, World world, Hand hand, ItemStack igniter) {
        if (!player.isFallFlying()) return false;
        if (igniter.getItem() != Items.FLINT_AND_STEEL && igniter.getItem() != Items.FIRE_CHARGE) return false;
        final var tnt = PlayerHandler.searchFor(player, Items.TNT, 0);
        if (tnt == null || tnt.isEmpty()) return false;
        final var tntItem = tnt.getItem();
        if (player.getItemCooldownManager().isCoolingDown(tntItem)) {
            final int cd = (int) (player.getItemCooldownManager().getCooldownProgress(tntItem, 0F) *
                    CONFIG.cdTime);
            if (!player.world.isClient) player.sendMessage(Text.literal("CD: " + cd + " tick(s)"), false);
            return false;
        }
        PlayerHandler.launchTnt(player, igniter, tnt);
        return true;
    }
}
