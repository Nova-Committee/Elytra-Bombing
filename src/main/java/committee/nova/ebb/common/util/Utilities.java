package committee.nova.ebb.common.util;

import committee.nova.ebb.common.config.CommonConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Utilities {
    public static ItemStack searchFor(Player entity, Item item, int startSlot) {
        final var itemList = entity.getInventory().items;
        final int size = itemList.size();
        if (startSlot >= size) return ItemStack.EMPTY;
        final var stackToTest = itemList.get(startSlot);
        return (stackToTest.getItem() == item) ? stackToTest : searchFor(entity, item, startSlot + 1);
    }

    public static void consumeStack(ItemStack stack, Player entity, @Nullable InteractionHand hand) {
        if (!(entity instanceof final ServerPlayer player) || player.getAbilities().instabuild) return;
        if (stack.isDamageableItem()) {
            stack.hurtAndBreak(1, player.serverLevel(), player, i -> {
                if (hand != null) player.onEquippedItemBroken(i, LivingEntity.getSlotForHand(hand));
            });
            return;
        }
        stack.shrink(1);
        if (stack.isEmpty()) player.getInventory().removeItem(stack);
    }

    public static void launchTnt(Player player, ItemStack igniter, ItemStack tntStack, InteractionHand hand) {
        final var level = player.level();
        final var initialSpeed = player.getDeltaMovement();
        final double x = player.getX();
        final double y = player.getY() - 0.5D;
        final double z = player.getZ();
        final var tnt = new PrimedTnt(level, x, y, z, player);
        tnt.setFuse(CommonConfig.TNT_INFUSION_TIME.get());
        tnt.setDeltaMovement((CommonConfig.INERTIA.get()) ? initialSpeed : new Vec3(0, 0, 0));
        level.addFreshEntity(tnt);
        level.playSound(player, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.PRIME_FUSE, player.blockPosition());
        consumeStack(igniter, player, hand);
        consumeStack(tntStack, player, null);
        player.getCooldowns().addCooldown(tntStack, CommonConfig.TNT_BOMBING_CD.get());
    }
}
