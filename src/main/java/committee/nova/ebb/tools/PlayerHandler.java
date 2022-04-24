package committee.nova.ebb.tools;

import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class PlayerHandler {
    public static ItemStack searchFor(PlayerEntity entity, Item item, int startSlot) {
        final DefaultedList<ItemStack> itemList = entity.getInventory().main;
        final int size = itemList.size();
        if (startSlot < size) {
            final ItemStack stackToTest = itemList.get(startSlot);
            return (stackToTest.getItem() == item) ? stackToTest : searchFor(entity, item, startSlot + 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void launchTnt(PlayerEntity player, ItemStack igniter, ItemStack tntStack) {
        final World level = player.world;
        final double x = player.getX();
        final double y = player.getY() - 0.5D;
        final double z = player.getZ();
        final TntEntity tnt = new TntEntity(level, x, y, z, player);
        tnt.setFuse(80);
        tnt.setVelocity(player.getVelocity());
        level.spawnEntity(tnt);
        level.playSound(player, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        consumeStack(igniter, player);
        consumeStack(tntStack, player);
        player.getItemCooldownManager().set(tntStack.getItem(), 60);
    }

    public static void consumeStack(ItemStack stack, PlayerEntity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            if (stack.isDamageable()) {
                if (stack.damage(1, player.getRandom(), player)) {
                    stack.decrement(1);
                    stack.setDamage(0);
                    if (stack.isEmpty()) player.getInventory().removeOne(stack);
                }
            } else {
                stack.decrement(1);
                if (stack.isEmpty()) player.getInventory().removeOne(stack);
            }
        }
    }
}
