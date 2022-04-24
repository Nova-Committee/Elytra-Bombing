package committee.nova.ebb;

import committee.nova.ebb.tools.PlayerHandler;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.text.MessageFormat;

public class ElytraBombing implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			final ItemStack stackInHand = player.getStackInHand(hand);
			return onBombing(player, world, hand, stackInHand) ? TypedActionResult.success(stackInHand) : TypedActionResult.pass(stackInHand);
		});
	}

	public boolean onBombing(PlayerEntity player, World world, Hand hand, ItemStack igniter) {
		if (!player.isFallFlying()) {
			return false;
		}
		if (igniter.getItem() != Items.FLINT_AND_STEEL && igniter.getItem() != Items.FIRE_CHARGE) {
			return false;
		}
		final ItemStack tnt = PlayerHandler.searchFor(player, Items.TNT, 0);
		if (tnt == null || tnt.isEmpty()) {
			return false;
		}

		final Item tntItem = tnt.getItem();
		if (player.getItemCooldownManager().isCoolingDown(tntItem)) {
			final int cd = (int) (player.getItemCooldownManager().getCooldownProgress(tntItem, 0F) *
					60);
			final boolean isPlural = cd > 1;
			if (!player.world.isClient) {
				player.sendMessage(
						new TranslatableText(MessageFormat.format(
								new TranslatableText("msg.ebb.bombing_cd").getString(),
								cd,
								isPlural ? new TranslatableText("msg.ebb.unit.plural_suffix").getString() : ""
						)), false);
			}
			return false;
		}
		PlayerHandler.launchTnt(player, igniter, tnt);
		return true;
	}
}
