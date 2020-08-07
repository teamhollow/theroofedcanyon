package net.teamhollow.theroofedcanyon.world.biome;

import net.teamhollow.theroofedcanyon.init.TRCDecorators;
import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;

public final class ModifiedRoofedCanyonBiome extends RoofedCanyonBiome {
    public static final String id = "modified_roofed_canyon";

    public ModifiedRoofedCanyonBiome() {
        super();
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.MELON_PATCH_CONFIG).createDecoratedFeature(
                        Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(1))));
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(TRCDecorators.VILEPOT_PATCH_CONFIG).createDecoratedFeature(
                        Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(1))));

        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(TRCEntities.CHOMPROOT, 2, 1, 1));
    }
}
