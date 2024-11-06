package committee.nova.ebb.common;

import committee.nova.ebb.common.config.CommonConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ElytraBombing.MODID)
public class ElytraBombing {
    public static final String MODID = "ebb";

    public static final TagKey<Item> IGNITERS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("c", "igniters")
    );

    public ElytraBombing(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.CONFIG);
    }
}
