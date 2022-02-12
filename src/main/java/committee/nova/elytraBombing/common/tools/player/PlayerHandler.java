package committee.nova.elytraBombing.common.tools.player;

import committee.nova.elytraBombing.common.config.CommonConfig;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PlayerHandler {
    public static ItemStack searchFor(PlayerEntity entity, Item item, int startSlot) {
        final NonNullList<ItemStack> itemList = entity.inventory.items;
        final int size = itemList.size();
        if (startSlot < size) {
            final ItemStack stackToTest = itemList.get(startSlot);
            return (stackToTest.getItem() == item) ? stackToTest : searchFor(entity, item, startSlot + 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void consumeStack(ItemStack stack, PlayerEntity entity) {
        if (entity instanceof ServerPlayerEntity) {
            final ServerPlayerEntity player = (ServerPlayerEntity) entity;
            if (stack.isDamageableItem()) {
                if (stack.hurt(1, player.getRandom(), player)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                    if (stack.isEmpty()) player.inventory.removeItem(stack);
                }
            } else {
                stack.shrink(1);
                if (stack.isEmpty()) player.inventory.removeItem(stack);
            }
        }
    }

    public static void launchTnt(PlayerEntity player, ItemStack igniter, ItemStack tntStack) {
        final World level = player.level;
        final Vector3d initialSpeed = player.getDeltaMovement();
        final double x = player.getX();
        final double y = player.getY() - 0.5D;
        final double z = player.getZ();
        final TNTEntity tnt = new TNTEntity(level, x, y, z, player);
        //todo:conf
        tnt.setFuse(CommonConfig.TNT_INFUSION_TIME.get());
        tnt.setDeltaMovement((CommonConfig.INERTIA.get()) ? initialSpeed : new Vector3d(0, 0, 0));
        level.addFreshEntity(tnt);
        level.playSound(player, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        consumeStack(igniter, player);
        consumeStack(tntStack, player);
        player.getCooldowns().addCooldown(tntStack.getItem(), CommonConfig.TNT_BOMBING_CD.get());
    }
}
