package committee.nova.elytraBombing.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.IntValue TNT_INFUSION_TIME;
    public static final ForgeConfigSpec.IntValue TNT_BOMBING_CD;
    public static final ForgeConfigSpec.BooleanValue INERTIA;
    public static final ForgeConfigSpec CONFIG;

    static {
        final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        TNT_INFUSION_TIME = BUILDER.comment("Elytra Bombing Setting", "How long should the launched TNT's infusion time be?", "Default is 80 ticks (4 sec), the same as Vanilla TNT.")
                .defineInRange("infusion_time", 80, 0, Integer.MAX_VALUE);
        TNT_BOMBING_CD = BUILDER.comment("How long should the bombing cool-down time be?", "Default is 60 ticks (3 sec).")
                .defineInRange("bombing_cd", 60, 0, Integer.MAX_VALUE);
        INERTIA = BUILDER.comment("Does TNT have inertia? If true, the player's real-time speed will influence the TNT's initial speed.")
                .define("inertia", true);
        CONFIG = BUILDER.build();
    }
}
