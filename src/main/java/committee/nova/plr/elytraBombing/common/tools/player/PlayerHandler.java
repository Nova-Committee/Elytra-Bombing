package committee.nova.plr.elytraBombing.common.tools.player;

import committee.nova.plr.elytraBombing.common.config.ConfigLoader;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

public class PlayerHandler {
    public static ItemStack searchFor(EntityPlayer entity, Item item, int startSlot) {
        final List<ItemStack> itemList = entity.inventoryContainer.getInventory();
        final int size = itemList.size();
        if (startSlot < size) {
            ItemStack stackToTest = itemList.get(startSlot);
            return stackToTest.getItem() == item ? stackToTest : searchFor(entity, item, startSlot + 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void damageStack(ItemStack stack, EntityPlayer entity) {
        if (!entity.isCreative()) {
            if (stack.isItemStackDamageable()) {
                if (entity instanceof EntityPlayerMP) {
                    final EntityPlayerMP player = (EntityPlayerMP) entity;
                    if (stack.attemptDamageItem(1, player.world.rand, player)) {
                        stack.damageItem(1, player);
                        stack.setItemDamage(0);
                    }
                }
            } else {
                consumeStack(stack, entity);
            }
        }
    }

    public static void consumeStack(ItemStack stack, EntityPlayer player) {
        player.inventory.clearMatchingItems(stack.getItem(), -1, 1, null);
    }

    public static void launchTnt(EntityPlayer player, ItemStack igniter, ItemStack tntStack) {
        final World world = player.world;
        final Double[] vec = (ConfigLoader.inertia) ? new Double[]{player.motionX, player.motionY, player.motionZ} : new Double[]{0D, 0D, 0D};
        final Double[] pos = new Double[]{player.posX, player.posY, player.posZ};
        final EntityTNTPrimed tnt = new EntityTNTPrimed(world, pos[0], pos[1], pos[2], player);
        if (!world.isRemote) {
            tnt.setFuse(ConfigLoader.infusion);
            tnt.setVelocity(vec[0], vec[1], vec[2]);
            world.spawnEntity(tnt);
        }
        world.playSound(player, tnt.posX, tnt.posY, tnt.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        damageStack(igniter, player);
        consumeStack(tntStack, player);
        player.getCooldownTracker().setCooldown(tntStack.getItem(), ConfigLoader.cd);
    }
}
