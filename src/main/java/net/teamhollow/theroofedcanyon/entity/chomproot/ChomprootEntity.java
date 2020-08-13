package net.teamhollow.theroofedcanyon.entity.chomproot;

import java.util.Random;

import net.teamhollow.theroofedcanyon.init.TRCBlocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ChomprootEntity extends HostileEntity {
    public static final String id = "chomproot";
    public static final EntityType.Builder<ChomprootEntity> builder = EntityType.Builder
        .create(ChomprootEntity::new, SpawnGroup.MONSTER)
        .setDimensions(1.8F, 1.8F)
        .maxTrackingRange(8);
    public static final int[] spawnEggColors = { 4662316, 41278 };

    private static final TrackedData<Integer> CHOMPROOT_SIZE = DataTracker.registerData(ChomprootEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected ChomprootEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,  EntityData entityData, CompoundTag entityTag) {
        int size = this.random.nextInt(4);
        if (size < 2 && this.random.nextFloat() < 0.5F * difficulty.getClampedLocalDifficulty()) size++;

        this.setSize(1 << size, true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ChomprootEntity.AttackGoal(this, 1.0D, false));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, (float)this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> {
            return Math.abs(livingEntity.getY() - this.getY()) <= 4.0D;
        }));
        this.targetSelector.add(3, new FollowTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHOMPROOT_SIZE, 1);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (CHOMPROOT_SIZE.equals(data)) {
            this.calculateDimensions();
            this.yaw = this.headYaw;
            this.bodyYaw = this.headYaw;
            if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
                this.onSwimmingStart();
            }
        }

        super.onTrackedDataSet(data);
    }

    protected void setSize(int size, boolean heal) {
        this.dataTracker.set(CHOMPROOT_SIZE, size);
    
        this.refreshPosition();
        this.calculateDimensions();

        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(5.0F + (5.0F * size));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MathHelper.clamp(0.2F + (0.1F * size), 0, 0.3D));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(size);

        if (heal) this.setHealth(this.getMaxHealth());

        this.experiencePoints = size;
    }
    public int getSize() {
        return this.dataTracker.get(CHOMPROOT_SIZE);
    }
    public boolean isSmall() {
        return getSize() <= 2;
    }

    @Override
    public void calculateDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.calculateDimensions();
        this.updatePosition(x, y, z);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F * dimensions.height;
    }
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.67F + 0.15F * this.getSize());
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Size", this.getSize() - 1);
    }
    public void readCustomDataFromTag(CompoundTag tag) {
        int i = tag.getInt("Size");
        if (i < 0) i = 0;

        this.setSize(i + 1, false);
        super.readCustomDataFromTag(tag);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_STRIDER_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_STRIDER_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRIDER_DEATH;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    public static DefaultAttributeContainer.Builder createChomprootAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    public static boolean canSpawn(EntityType<ChomprootEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !world.getBlockState(pos.down()).isOf(TRCBlocks.TURFWOOD.LEAVES);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent();
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isOf(TRCBlocks.TURFWOOD.LEAVES) ? 10.0F : 0.0F;
    }

    @Override
    protected boolean canDropLootAndXp() {
        return true;
    }

    @Override
    protected int getCurrentExperience(PlayerEntity player) {
        return this.experiencePoints;
    }

    class AttackGoal extends MeleeAttackGoal {
        private final PathAwareEntity entity;
        private int ticks;

        public AttackGoal(PathAwareEntity entity, double speed, boolean pauseWhenMobIdle) {
            super(entity, speed, pauseWhenMobIdle);
            this.entity = entity;
        }

        public void start() {
            super.start();
            this.ticks = 0;
        }

        public void stop() {
            super.stop();
            this.entity.setAttacking(false);
        }

        public void tick() {
            super.tick();
            this.ticks++;
            if (this.ticks >= 5 && this.method_28348() < this.method_28349() / 2) {
                this.entity.setAttacking(true);
            } else {
                this.entity.setAttacking(false);
            }
        }
    }
}
