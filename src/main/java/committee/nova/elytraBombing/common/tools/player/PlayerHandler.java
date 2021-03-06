package committee.nova.elytraBombing.common.tools.player;

import committee.nova.elytraBombing.common.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class PlayerHandler {
    public static ItemStack searchFor(Player entity, Item item, int startSlot) {
        final NonNullList<ItemStack> itemList = entity.getInventory().items;
        final int size = itemList.size();
        if (startSlot < size) {
            final ItemStack stackToTest = itemList.get(startSlot);
            return (stackToTest.getItem() == item) ? stackToTest : searchFor(entity, item, startSlot + 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void consumeStack(ItemStack stack, Player entity) {
        if (entity instanceof ServerPlayer player) {
            if (stack.isDamageableItem()) {
                if (stack.hurt(1, player.getRandom(), player)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                    if (stack.isEmpty()) player.getInventory().removeItem(stack);
                }
            } else {
                stack.shrink(1);
                if (stack.isEmpty()) player.getInventory().removeItem(stack);
            }
        }
    }

    public static void launchTnt(Player player, ItemStack igniter, ItemStack tntStack) {
        final Level level = player.level;
        final Vec3 initialSpeed = player.getDeltaMovement();
        final double x = player.getX();
        final double y = player.getY() - 0.5D;
        final double z = player.getZ();
        final PrimedTnt tnt = new PrimedTnt(level, x, y, z, player);
        //todo:conf
        tnt.setFuse(CommonConfig.TNT_INFUSION_TIME.get());
        tnt.setDeltaMovement((CommonConfig.INERTIA.get()) ? initialSpeed : new Vec3(0, 0, 0));
        level.addFreshEntity(tnt);
        level.playSound(player, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.PRIME_FUSE, new BlockPos(x, y, z));
        consumeStack(igniter, player);
        consumeStack(tntStack, player);
        player.getCooldowns().addCooldown(tntStack.getItem(), CommonConfig.TNT_BOMBING_CD.get());
    }
}
