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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PlayerHandler {
    public static ItemStack searchFor(PlayerEntity entity, Item item, int startSlot) {
        final NonNullList<ItemStack> itemList = entity.inventory.mainInventory;
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
            if (stack.isDamageable()) {
                if (stack.attemptDamageItem(1, player.world.rand, player)) {
                    stack.shrink(1);
                    stack.setDamage(0);
                    if (stack.isEmpty()) player.inventory.deleteStack(stack);
                }
            } else {
                stack.shrink(1);
                if (stack.isEmpty()) player.inventory.deleteStack(stack);
            }
        }
    }

    public static void launchTnt(PlayerEntity player, ItemStack igniter, ItemStack tntStack) {
        final World level = player.world;
        final Vec3d initialSpeed = CommonConfig.INERTIA.get() ? player.getMotion() : new Vec3d(0D, 0D, 0D);
        final double x = player.posX;
        final double y = player.posY - 0.5D;
        final double z = player.posZ;
        final TNTEntity tnt = new TNTEntity(level, x, y, z, player);
        tnt.setFuse(CommonConfig.TNT_INFUSION_TIME.get());
        tnt.addVelocity(initialSpeed.x, initialSpeed.y, initialSpeed.z);
        if (!level.isRemote) level.addEntity(tnt);
        level.playSound(player, tnt.posX, tnt.posY, tnt.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        consumeStack(igniter, player);
        consumeStack(tntStack, player);
        player.getCooldownTracker().setCooldown(tntStack.getItem(), CommonConfig.TNT_BOMBING_CD.get());
    }
}
