package committee.nova.plr.ebb.util;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class Utilities {
    public static int consumeStackByType(Inventory inv, ItemType type, int amount) {
        final int size = inv.capacity();
        int consumed = 0;
        for (int i = 0; i < size; i++) {
            if (amount == consumed) break;
            final Optional<ItemStack> s = inv.peekAt(i);
            if (!s.isPresent()) continue;
            final ItemStack stack = s.get();
            if (stack.type() != type) continue;
            final int shouldConsume = Math.min(stack.quantity(), amount - consumed);
            final ItemStack stackToSet = stack.copy();
            stackToSet.setQuantity(stack.quantity() - shouldConsume);
            inv.set(i, stackToSet);
            consumed += shouldConsume;
        }
        return consumed;
    }
}
