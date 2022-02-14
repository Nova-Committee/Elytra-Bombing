package committee.nova.plr.elytraBombing.tools;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class PlayerHandler {
    public static ItemStack searchFor(EntityPlayer entity, Item item, int startSlot) {
        final NonNullList<ItemStack> itemList = entity.inventory.mainInventory;
        final int size = itemList.size();
        if (startSlot < size) {
            final ItemStack stackToTest = itemList.get(startSlot);
            return (stackToTest.getItem() == item) ? stackToTest : searchFor(entity, item, startSlot + 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void consumeStack(ItemStack stack, EntityPlayer entity) {
        if (entity instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP) entity;
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

    public static void launchTnt(EntityPlayer player, ItemStack igniter, ItemStack tntStack) {
        final World level = player.world;
        final Double[] initialSpeed = new Double[]{player.motionX, player.motionY, player.motionZ};
        final double x = player.posX;
        final double y = player.posY - 0.5D;
        final double z = player.posZ;
        final EntityTNTPrimed tnt = new EntityTNTPrimed(level, x, y, z, player);
        tnt.setFuse(80);
        tnt.addVelocity(initialSpeed[0], initialSpeed[1], initialSpeed[2]);
        if (!level.isRemote) level.spawnEntity(tnt);
        level.playSound(player, tnt.posX, tnt.posY, tnt.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        consumeStack(igniter, player);
        consumeStack(tntStack, player);
        player.getCooldownTracker().setCooldown(tntStack.getItem(), 60);
    }
}
