package net.teamhollow.theroofedcanyon.init;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class TRCProperties {
    public static final IntProperty VILE_LEVEL = IntProperty.of("vile_level", 0, 5);
    public static final BooleanProperty INFESTED = BooleanProperty.of("infested");
}
