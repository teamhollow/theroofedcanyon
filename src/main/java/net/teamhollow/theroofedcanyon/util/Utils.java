package net.teamhollow.theroofedcanyon.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class Utils {
    public static List<StatusEffect> harmfulStatusEffects = new ArrayList<StatusEffect>();

    public Utils() {
        Registry.STATUS_EFFECT.forEach((StatusEffect statusEffect) -> {
            if (statusEffect.getType() == StatusEffectType.HARMFUL) harmfulStatusEffects.add(statusEffect);
        });
    }
}
