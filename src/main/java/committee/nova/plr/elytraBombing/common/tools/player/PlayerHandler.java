package committee.nova.plr.elytraBombing.common.tools.player;

import committee.nova.plr.elytraBombing.common.config.ConfigLoader;
import committee.nova.plr.elytraBombing.common.event.EEBContext;
import committee.nova.plr.elytraBombing.common.event.EEBEvents;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;


public class PlayerHandler {
    public static Predicate<ItemStack> searchFor(EEBContext context) {
        final List<ItemStack> itemList = context.player.inventoryContainer.getInventory();
        for(ItemStack stack:itemList){
            for(Predicate<ItemStack> predicate: EEBEvents.ACCESS_TNT.keySet()){
                if (predicate.test(stack)){
                    context.tntStack=stack;
                    return predicate;
                }
            }
        }
        return null;
    }
    public static void damageStack(EEBContext context) {
        EntityPlayer entity=context.player;
        ItemStack stack=context.igniterStack;

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

    public static void preLaunchTnt(EEBContext context){
        EntityPlayer player=context.player;
        Item tntItem=context.tntStack.getItem();
        if (player.getCooldownTracker().hasCooldown(tntItem) && !player.isCreative()) {
            final int cd = (int) (player.getCooldownTracker().getCooldown(tntItem, 0) * 60);
            final boolean isPlural = cd > 1;
            if (!player.world.isRemote) {
                player.sendMessage(new TextComponentString(
                        MessageFormat.format(new TextComponentTranslation("msg.ebb.cd").getFormattedText(),
                                cd + "",
                                isPlural ? new TextComponentTranslation("msg.ebb.unit.plural_suffix").getFormattedText() : "")
                ));
            }
            return;
        }
        PlayerHandler.launchTnt(context);
    }

    public static void launchTnt(EEBContext context) {
        EntityPlayer player=context.player;
        ItemStack tntStack=context.tntStack;
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
        if (!player.isCreative()) {
            consumeStack(tntStack, player);
            player.getCooldownTracker().setCooldown(tntStack.getItem(), ConfigLoader.cd);
        }
    }
}
