package net.teamhollow.theroofedcanyon.client.particle;

import net.teamhollow.theroofedcanyon.init.TRCParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class BlockLeakParticle extends SpriteBillboardParticle {
    private final Fluid fluid;

    private BlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0.06F;
        this.fluid = fluid;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public int getColorMultiplier(float tint) {
        return super.getColorMultiplier(tint);
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (!this.dead) {
            this.velocityY -= (double) this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateVelocity();
            if (!this.dead) {
                this.velocityX *= 0.9800000190734863D;
                this.velocityY *= 0.9800000190734863D;
                this.velocityZ *= 0.9800000190734863D;
                BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidState = this.world.getFluidState(blockPos);
                if (fluidState.getFluid() == this.fluid && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
                    this.markDead();
                }

            }
        }
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }

    }

    protected void updateVelocity() {}

    @Environment(EnvType.CLIENT)
    public static class LandingVileFactory implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public LandingVileFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BlockLeakParticle blockLeakParticle = new BlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
            blockLeakParticle.maxAge = (int) (128.0D / (Math.random() * 0.8D + 0.2D));
            blockLeakParticle.setColor(0.082F, 0.255F, 0.111F);
            blockLeakParticle.setSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class FallingVileFactory implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingVileFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BlockLeakParticle blockLeakParticle = new BlockLeakParticle.FallingVile(clientWorld, d, e, f, Fluids.EMPTY, TRCParticleTypes.LANDING_VILE);
            blockLeakParticle.gravityStrength = 0.01F;
            blockLeakParticle.setColor(0.082F, 0.255F, 0.125F);
            blockLeakParticle.setSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class DrippingVileFactory implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public DrippingVileFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BlockLeakParticle.Dripping dripping = new BlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, TRCParticleTypes.FALLING_VILE);
            dripping.gravityStrength *= 0.01F;
            dripping.maxAge = 100;
            dripping.setColor(0.082F, 0.255F, 0.105F);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    @Environment(EnvType.CLIENT)
    static class Landing extends BlockLeakParticle {
        private Landing(ClientWorld world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @Environment(EnvType.CLIENT)
    static class Falling extends BlockLeakParticle {
        private Falling(ClientWorld world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.maxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class FallingVile extends BlockLeakParticle.ContinuousFalling {
        private FallingVile(ClientWorld world, double x, double y, double z, Fluid fluid,
                ParticleEffect particleEffect) {
            super(world, x, y, z, fluid, particleEffect);
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
                this.world.playSound(this.x + 0.5D, this.y, this.z + 0.5D, SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 0.3F + this.world.random.nextFloat() * 2.0F / 3.0F, 1.0F, false);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class ContinuousFalling extends BlockLeakParticle.Falling {
        protected final ParticleEffect nextParticle;

        private ContinuousFalling(ClientWorld clientWorld, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(clientWorld, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class Dripping extends BlockLeakParticle {
        private final ParticleEffect nextParticle;

        private Dripping(ClientWorld clientWorld, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(clientWorld, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravityStrength *= 0.02F;
            this.maxAge = 40;
        }

        protected void updateAge() {
            if (this.maxAge-- <= 0) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }

        }

        protected void updateVelocity() {
            this.velocityX *= 0.02D;
            this.velocityY *= 0.02D;
            this.velocityZ *= 0.02D;
        }
    }
}
