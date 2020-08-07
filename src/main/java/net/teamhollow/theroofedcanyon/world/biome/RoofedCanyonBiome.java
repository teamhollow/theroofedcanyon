package net.teamhollow.theroofedcanyon.world.biome;

import com.google.common.collect.ImmutableList;

import net.teamhollow.theroofedcanyon.init.TRCDecorators;
import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class RoofedCanyonBiome extends Biome {
    public static final String id = "roofed_canyon";

	public RoofedCanyonBiome() {
        super(
            new Biome.Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.FOREST)
                .depth(0.025F)
                .scale(0.8F)
                .temperature(0.9F)
                .downfall(0.4F)
                .effects(
                    new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build()
                )
                .parent(null)
        );
        addFeatures(this);
        this.addSpawn(SpawnGroup.CREATURE, new Biome.SpawnEntry(TRCEntities.GRUBWORM, 8, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 95, 4, 4));
    }

	public static void addFeatures(Biome biome) {
        DefaultBiomeFeatures.addDefaultUndergroundStructures(biome);
        DefaultBiomeFeatures.addLandCarvers(biome);
        DefaultBiomeFeatures.addDefaultLakes(biome);
        DefaultBiomeFeatures.addDungeons(biome);
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_SELECTOR
                        .configure(new RandomFeatureConfig(
                                ImmutableList.of(
                                        Feature.TREE.configure(TRCDecorators.MEGA_TURFWOOD_TREE_CONFIG).withChance(1.0F)),
                                Feature.TREE.configure(TRCDecorators.MEGA_TURFWOOD_TREE_CONFIG)))
                        .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP
                                .configure(new CountExtraChanceDecoratorConfig(50, 1.0F, 3))));
        DefaultBiomeFeatures.addForestFlowers(biome);
        DefaultBiomeFeatures.addMineables(biome);
        DefaultBiomeFeatures.addDefaultOres(biome);
        DefaultBiomeFeatures.addDefaultDisks(biome);
        DefaultBiomeFeatures.addDefaultFlowers(biome);
        DefaultBiomeFeatures.addJungleGrass(biome);
        DefaultBiomeFeatures.addDefaultMushrooms(biome);
        DefaultBiomeFeatures.addDefaultVegetation(biome);
        DefaultBiomeFeatures.addFrozenTopLayer(biome);
	}
}
