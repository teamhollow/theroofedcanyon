package net.teamhollow.theroofedcanyon.init;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.client.particle.BlockLeakParticle.*;
import net.teamhollow.theroofedcanyon.particle.PublicDefaultParticleType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TRCParticleTypes {
    public static final DefaultParticleType DRIPPING_VILE = register("dripping_vile", false);
	public static final DefaultParticleType FALLING_VILE = register("falling_vile", false);
	public static final DefaultParticleType LANDING_VILE = register("landing_vile", false);

    public TRCParticleTypes() {}
    
    public static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(DRIPPING_VILE, DrippingVileFactory::new);
        ParticleFactoryRegistry.getInstance().register(FALLING_VILE, FallingVileFactory::new);
        ParticleFactoryRegistry.getInstance().register(LANDING_VILE, LandingVileFactory::new);
    }

    private static DefaultParticleType register(String id, boolean alwaysShow) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(TheRoofedCanyon.MOD_ID, id), new PublicDefaultParticleType(alwaysShow));
    }
}
