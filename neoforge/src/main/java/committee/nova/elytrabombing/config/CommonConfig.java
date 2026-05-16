package committee.nova.elytrabombing.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    public static final ModConfigSpec.IntValue TNT_FUSE_TIME;
    public static final ModConfigSpec.IntValue TNT_BOMBING_CD;
    public static final ModConfigSpec.BooleanValue INERTIA;
    public static final ModConfigSpec CONFIG;

    static {
        final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        TNT_FUSE_TIME = BUILDER.comment("Elytra Bombing Setting", "How long should the launched TNT's fuse time be?", "80 ticks (4 sec) by default, the same as Vanilla TNT.")
                .defineInRange("fuse_time", 80, 0, Integer.MAX_VALUE);
        TNT_BOMBING_CD = BUILDER.comment("How long should the bombing cool-down time be?", "60 ticks (3 sec) by default.")
                .defineInRange("bombing_cd", 60, 0, Integer.MAX_VALUE);
        INERTIA = BUILDER.comment("Does TNT have inertia? If true, the player's real-time speed will influence the TNT's initial speed.")
                .define("inertia", true);
        CONFIG = BUILDER.build();
    }
}
