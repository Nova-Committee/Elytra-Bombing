package committee.nova.plr.ebb.config;

import committee.nova.plr.ebb.ElytraBombing;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = ElytraBombing.M)
public class CommonConfig implements ConfigData {
    @Comment("How long should the launched TNT's infusion time be?")
    public int fuseTime = 80;
    @Comment("How long should the bombing cool-down time be?")
    public int cdTime = 60;
    @Comment("Does TNT have inertia?")
    public boolean inertia = true;
}
