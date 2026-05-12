package committee.nova.elytrabombing;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as NeoForge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        // Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        // Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        //if (Services.PLATFORM.isModLoaded("examplemod")) {
        //    Constants.LOG.info("Hello to examplemod");
        //}
    }

    public static InteractionResult bombard(Player p, Level l, InteractionHand h, int bombingCD, int fuseTime, boolean inertia) {
        if (l.isClientSide()) return InteractionResult.PASS;
        if (!p.isFallFlying()) return InteractionResult.PASS;
        final ItemStack stack = p.getItemInHand(h);
        if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE)) return InteractionResult.PASS;
        final ItemStack tnt4cd = Items.TNT.getDefaultInstance();
        if (p.getCooldowns().isOnCooldown(tnt4cd)) {
            return InteractionResult.PASS;
        }
        final int tnt = p.getInventory().clearOrCountMatchingItems(i -> i.is(Items.TNT), 1, p.inventoryMenu.getCraftSlots());
        if (tnt == 0) {
            return InteractionResult.PASS;
        }
        // TODO: For non-damageable items?
        if (stack.isDamageableItem()) stack.hurtAndBreak(1, p, h);
        else p.getInventory().clearOrCountMatchingItems(i -> i.is(stack.getItem()), 1, p.inventoryMenu.getCraftSlots());
        p.getCooldowns().addCooldown(tnt4cd, bombingCD);
        final PrimedTnt tntEntity = new PrimedTnt(l, p.getX(), p.getY() - 0.5, p.getZ(), p);
        tntEntity.setFuse(fuseTime);
        if (inertia) tntEntity.setDeltaMovement(p.getDeltaMovement());
        l.playSound(null, p.getX(), p.getY() - 0.5, p.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS);
        l.addFreshEntity(tntEntity);
        return InteractionResult.SUCCESS;
    }
}