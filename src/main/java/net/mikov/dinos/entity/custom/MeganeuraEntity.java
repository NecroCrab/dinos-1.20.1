package net.mikov.dinos.entity.custom;

import com.google.common.collect.Lists;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.MeganeuraEggBlock;
import net.mikov.dinos.block.entity.MeganeuraHiveBlockEntity;
import net.mikov.dinos.block.entity.ModBlockEntities;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.MeganeuraAttackGoal;
import net.mikov.dinos.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeganeuraEntity extends AnimalEntity implements Angerable {
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(MeganeuraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIGGING_DIRT = DataTracker.registerData(MeganeuraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    int dirtDiggingCounter;
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems
            (Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT, ModItems.RAW_PRIMAL_MEAT);
    public static final Predicate<LivingEntity> FOLLOW_PREDICATE = entity -> {
        EntityType<?> entityType = entity.getType();
        return entityType == EntityType.CHICKEN ||
                entityType == ModEntities.DODO ||
                entityType == EntityType.RABBIT ||
                entityType == EntityType.FROG;
    };

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public static final AnimationState sittingAnimationState = new AnimationState();
    public int sittingAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;
    public static final AnimationState flyingAnimationState = new AnimationState();
    public int flyingAnimationTimeout = 0;
    public static final AnimationState walkingAnimationState = new AnimationState();
    public int walkingAnimationTimeout = 0;

    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0f;
    private float field_28640 = 1.0f;

    PollinateGoal pollinateGoal;
    MoveToHiveGoal moveToHiveGoal;
    private MoveToFlowerGoal moveToFlowerGoal;
    private int cropsGrownSincePollination;
    private float lastPitch;
    private float currentPitch;
    private static final TrackedData<Byte> BEE_FLAGS = DataTracker.registerData(MeganeuraEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Integer> ANGER = DataTracker.registerData(MeganeuraEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int NEAR_TARGET_FLAG = 2;
    private static final int HAS_STUNG_FLAG = 4;
    private static final int HAS_NECTAR_FLAG = 8;
    int ticksSincePollination;
    private int cannotEnterHiveTicks;
    @Nullable
    BlockPos flowerPos;
    @Nullable
    BlockPos hivePos;
    private static final int field_30274 = 200;
    int ticksLeftToFindHive;
    private static final int field_30275 = 200;
    int ticksUntilCanPollinate;
    public static final String CROPS_GROWN_SINCE_POLLINATION_KEY = "CropsGrownSincePollination";
    public static final String CANNOT_ENTER_HIVE_TICKS_KEY = "CannotEnterHiveTicks";
    public static final String TICKS_SINCE_POLLINATION_KEY = "TicksSincePollination";
    public static final String HAS_STUNG_KEY = "HasStung";
    public static final String HAS_NECTAR_KEY = "HasNectar";
    public static final String FLOWER_POS_KEY = "FlowerPos";
    public static final String HIVE_POS_KEY = "HivePos";
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    @Nullable
    private UUID angryAt;

    public MeganeuraEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.ticksUntilCanPollinate = MathHelper.nextInt(this.random, 20, 60);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new BeeLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    public boolean isDiggingDirt() {
        return this.dataTracker.get(DIGGING_DIRT);
    }

    void setDiggingDirt(boolean diggingDirt) {
        this.dirtDiggingCounter = diggingDirt ? 1 : 0;
        this.dataTracker.set(DIGGING_DIRT, diggingDirt);
    }

    private void setupAnimationStates() {
        if (this.isOnGround() && this.getVelocity().length() == 0.00 && this.idleAnimationTimeout <= 0) {
            idleAnimationTimeout = this.random.nextInt(1180) + 80;
            idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if (!this.isOnGround() && this.flyingAnimationTimeout <= 0) {
            flyingAnimationTimeout = this.random.nextInt(1180) + 80;
            flyingAnimationState.start(this.age);
        } else {
            --this.flyingAnimationTimeout;
        }
        if (this.isOnGround() && this.getVelocity().length() >= 0.01 && this.walkingAnimationTimeout <= 0) {
            walkingAnimationTimeout = this.random.nextInt(1180) + 40;
            walkingAnimationState.start(this.age);
        } else {
            --this.walkingAnimationTimeout;
        }
        /*if (this.isSitting() && sittingAnimationTimeout <= 0) {
            sittingAnimationTimeout = 400;
            sittingAnimationState.start(this.age);
        } else {
            --this.sittingAnimationTimeout;
        }
        if(!this.isSitting()) {
            sittingAnimationState.stop();
        }*/
        if (this.isAttacking() && attackingAnimationTimeout <= 0) {
            idleAnimationState.stop();
            attackingAnimationTimeout = 40;
            attackingAnimationState.start(this.age);
        } else {
            --this.attackingAnimationTimeout;
        }
        if(!this.isAttacking()) {
            attackingAnimationState.stop();
        }
    }

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(MeganeuraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
        super.setAttacking(attacking);
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.8f);

        float u = this.getPose() == EntityPose.FALL_FLYING ? Math.min(posDelta * 6.0f, 8.0f) : 0.0f;
        this.limbAnimator.updateLimbs(u, 0.8f);

        float s = this.getPose() == EntityPose.SITTING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(s, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }
        if (this.hasNectar() && this.getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05f) {
            for (int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.addParticle(this.getWorld(),
                        this.getX() - (double)0.3f, this.getX() + (double)0.3f,
                        this.getZ() - (double)0.3f, this.getZ() + (double)0.3f,
                        this.getBodyY(0.5), ParticleTypes.ITEM_SLIME);
            }
        }
        this.updateBodyPitch();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new MeganeuraAttackGoal(this, 1.15, true));
        this.goalSelector.add(1, new MateGoal(this, 1.0));
        this.goalSelector.add(1, new LayEggGoal(this, 1.0));
        this.goalSelector.add(1, new EnterHiveGoal());
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.ofItems
                (Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT, ModItems.RAW_PRIMAL_MEAT), false));
        this.pollinateGoal = new PollinateGoal();
        this.goalSelector.add(3, this.pollinateGoal);
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new FindHiveGoal());
        this.moveToHiveGoal = new MoveToHiveGoal();
        this.goalSelector.add(5, this.moveToHiveGoal);
        this.moveToFlowerGoal = new MoveToFlowerGoal();
        this.goalSelector.add(6, this.moveToFlowerGoal);
        //this.goalSelector.add(7, new GrowCropsGoal());
        this.goalSelector.add(7, new BeeWanderAroundGoal());
        this.goalSelector.add(8, new FlyOntoTreeGoal(this, 1.0));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(DimorphEntity.class));
        //this.goalSelector.add(5, new FlyRandomlyGoal(this));
        //this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal<AnimalEntity>(this, AnimalEntity.class, false, FOLLOW_PREDICATE));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85f : dimensions.height * 0.92f;
    }

    public static DefaultAttributeContainer.Builder createMeganeuraAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.7)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 2)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.2)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16);

    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public MeganeuraEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MEGANEURA.create(world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BEE_LOOP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(DIGGING_DIRT, false);
        this.dataTracker.startTracking(BEE_FLAGS, (byte)0);
        this.dataTracker.startTracking(ANGER, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.hasHive()) {
            nbt.put(HIVE_POS_KEY, NbtHelper.fromBlockPos(this.getHivePos()));
        }
        if (this.hasFlower()) {
            nbt.put(FLOWER_POS_KEY, NbtHelper.fromBlockPos(this.getFlowerPos()));
        }
        nbt.putBoolean(HAS_NECTAR_KEY, this.hasNectar());
        nbt.putBoolean(HAS_STUNG_KEY, this.hasStung());
        nbt.putInt(TICKS_SINCE_POLLINATION_KEY, this.ticksSincePollination);
        nbt.putInt(CANNOT_ENTER_HIVE_TICKS_KEY, this.cannotEnterHiveTicks);
        nbt.putInt(CROPS_GROWN_SINCE_POLLINATION_KEY, this.cropsGrownSincePollination);
        this.writeAngerToNbt(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.hivePos = null;
        if (nbt.contains(HIVE_POS_KEY)) {
            this.hivePos = NbtHelper.toBlockPos(nbt.getCompound(HIVE_POS_KEY));
        }
        this.flowerPos = null;
        if (nbt.contains(FLOWER_POS_KEY)) {
            this.flowerPos = NbtHelper.toBlockPos(nbt.getCompound(FLOWER_POS_KEY));
        }
        super.readCustomDataFromNbt(nbt);
        this.setHasNectar(nbt.getBoolean(HAS_NECTAR_KEY));
        this.setHasStung(nbt.getBoolean(HAS_STUNG_KEY));
        this.ticksSincePollination = nbt.getInt(TICKS_SINCE_POLLINATION_KEY);
        this.cannotEnterHiveTicks = nbt.getInt(CANNOT_ENTER_HIVE_TICKS_KEY);
        this.cropsGrownSincePollination = nbt.getInt(CROPS_GROWN_SINCE_POLLINATION_KEY);
        this.readAngerFromNbt(this.getWorld(), nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    //flying

    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            @Override
            public void tick() {
                if (MeganeuraEntity.this.pollinateGoal.isRunning()) {
                    return;
                }
                super.tick();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            if (this.cannotEnterHiveTicks > 0) {
                --this.cannotEnterHiveTicks;
            }
            if (this.ticksLeftToFindHive > 0) {
                --this.ticksLeftToFindHive;
            }
            if (this.ticksUntilCanPollinate > 0) {
                --this.ticksUntilCanPollinate;
            }
            boolean bl = this.hasAngerTime() && !this.hasStung() && this.getTarget() != null && this.getTarget().squaredDistanceTo(this) < 4.0;
            this.setNearTarget(bl);
            if (this.age % 20 == 0 && !this.isHiveValid()) {
                this.hivePos = null;
            }
        }
        this.flapWings();
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float)(this.isOnGround() || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.isOnGround() && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    @Override
    public boolean isFlappingWings() {
        return this.speed > this.field_28640;
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
        this.field_28640 = this.speed + this.maxWingDeviation / 2.0f;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    static class FlyOntoTreeGoal
            extends FlyGoal {
        public FlyOntoTreeGoal(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Override
        @Nullable
        protected Vec3d getWanderTarget() {
            Vec3d vec3d = null;
            if (this.mob.isTouchingWater()) {
                vec3d = FuzzyTargeting.find(this.mob, 15, 15);
            }
            if (this.mob.getRandom().nextFloat() >= this.probability) {
                vec3d = this.locateTree();
            }
            return vec3d == null ? super.getWanderTarget() : vec3d;
        }

        @Nullable
        private Vec3d locateTree() {
            BlockPos blockPos = this.mob.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            BlockPos.Mutable mutable2 = new BlockPos.Mutable();
            Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 3.0), MathHelper.floor(this.mob.getY() - 6.0), MathHelper.floor(this.mob.getZ() - 3.0), MathHelper.floor(this.mob.getX() + 3.0), MathHelper.floor(this.mob.getY() + 6.0), MathHelper.floor(this.mob.getZ() + 3.0));
            for (BlockPos blockPos2 : iterable) {
                BlockState blockState;
                boolean bl;
                if (blockPos.equals(blockPos2) || !(bl = (blockState = this.mob.getWorld().getBlockState(mutable2.set((Vec3i)blockPos2, Direction.DOWN))).getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS)) || !this.mob.getWorld().isAir(blockPos2) || !this.mob.getWorld().isAir(mutable.set((Vec3i)blockPos2, Direction.UP))) continue;
                return Vec3d.ofBottomCenter(blockPos2);
            }
            return null;
        }
    }

    // hatching

    static class MateGoal
            extends AnimalMateGoal {
        private final MeganeuraEntity dragonfly;

        MateGoal(MeganeuraEntity dragonfly, double speed) {
            super(dragonfly, speed);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.dragonfly.hasEgg();
        }

        @Override
        protected void breed() {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
            if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
                serverPlayerEntity = this.mate.getLovingPlayer();
            }
            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, null);
            }
            this.dragonfly.setHasEgg(true);
            this.animal.setBreedingAge(6000);
            this.mate.setBreedingAge(6000);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            Random random = this.animal.getRandom();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class LayEggGoal
            extends MoveToTargetPosGoal {
        private final MeganeuraEntity dragonfly;

        LayEggGoal(MeganeuraEntity dragonfly, double speed) {
            super(dragonfly, speed, 16);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canStart() {
            if (this.dragonfly.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.dragonfly.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.dragonfly.getBlockPos();
            if (!this.dragonfly.isTouchingWater() && this.hasReached()) {
                if (this.dragonfly.dirtDiggingCounter < 1) {
                    this.dragonfly.setDiggingDirt(true);
                } else if (this.dragonfly.dirtDiggingCounter > this.getTickCount(200)) {
                    World world = this.dragonfly.getWorld();
                    world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.MEGANEURA_EGG_BLOCK.getDefaultState().with(MeganeuraEggBlock.EGGS, this.dragonfly.random.nextInt(4) + 1);
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.dragonfly, blockState));
                    this.dragonfly.setHasEgg(false);
                    this.dragonfly.setDiggingDirt(false);
                    this.dragonfly.setLoveTicks(600);
                }
                if (this.dragonfly.isDiggingDirt()) {
                    ++this.dragonfly.dirtDiggingCounter;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return MeganeuraEggBlock.isDirt(world, pos);
        }
    }

    // Bee goals

    int getCropsGrownSincePollination() {
        return this.cropsGrownSincePollination;
    }

    private void addParticle(World world, double lastX, double x, double lastZ, double z, double y, ParticleEffect effect) {
        world.addParticle(effect, MathHelper.lerp(world.random.nextDouble(), lastX, x), y, MathHelper.lerp(world.random.nextDouble(), lastZ, z),
                0.0, 0.0, 0.0);
    }

    private void updateBodyPitch() {
        this.lastPitch = this.currentPitch;
        this.currentPitch = this.isNearTarget() ? Math.min(1.0f, this.currentPitch + 0.2f) : Math.max(0.0f, this.currentPitch - 0.24f);
    }

    private boolean isNearTarget() {
        return this.getBeeFlag(NEAR_TARGET_FLAG);
    }

    public boolean hasNectar() {
        return this.getBeeFlag(HAS_NECTAR_FLAG);
    }

    void setHasNectar(boolean hasNectar) {
        if (hasNectar) {
            this.resetPollinationTicks();
        }
        this.setBeeFlag(HAS_NECTAR_FLAG, hasNectar);
    }

    public void resetPollinationTicks() {
        this.ticksSincePollination = 0;
    }

    private boolean getBeeFlag(int location) {
        return (this.dataTracker.get(BEE_FLAGS) & location) != 0;
    }

    private void setBeeFlag(int bit, boolean value) {
        if (value) {
            this.dataTracker.set(BEE_FLAGS, (byte)(this.dataTracker.get(BEE_FLAGS) | bit));
        } else {
            this.dataTracker.set(BEE_FLAGS, (byte)(this.dataTracker.get(BEE_FLAGS) & ~bit));
        }
    }

    public boolean hasStung() {
        return this.getBeeFlag(HAS_STUNG_FLAG);
    }

    private void setHasStung(boolean hasStung) {
        this.setBeeFlag(HAS_STUNG_FLAG, hasStung);
    }

    private void setNearTarget(boolean nearTarget) {
        this.setBeeFlag(NEAR_TARGET_FLAG, nearTarget);
    }

    boolean isTooFar(BlockPos pos) {
        return !this.isWithinDistance(pos, 32);
    }

    boolean isWithinDistance(BlockPos pos, int distance) {
        return pos.isWithinDistance(this.getBlockPos(), (double)distance);
    }

    @Debug
    public int getMoveGoalTicks() {
        return Math.max(this.moveToHiveGoal.ticks, this.moveToFlowerGoal.ticks);
    }

    @Debug
    public List<BlockPos> getPossibleHives() {
        return this.moveToHiveGoal.possibleHives;
    }

    boolean isHiveValid() {
        if (!this.hasHive()) {
            return false;
        }
        if (this.isTooFar(this.hivePos)) {
            return false;
        }
        BlockEntity blockEntity = this.getWorld().getBlockEntity(this.hivePos);
        return blockEntity != null && blockEntity.getType() == ModBlockEntities.BUGHIVE;
    }

    boolean canEnterHive() {
        if (this.cannotEnterHiveTicks > 0 || this.pollinateGoal.isRunning() || this.hasStung() || this.getTarget() != null) {
            return false;
        }
        boolean bl = this.failedPollinatingTooLong() || this.getWorld().isRaining() || this.getWorld().isNight() || this.hasNectar();
        return bl && !this.isHiveNearFire();
    }

    public void setCannotEnterHiveTicks(int cannotEnterHiveTicks) {
        this.cannotEnterHiveTicks = cannotEnterHiveTicks;
    }

    private boolean isHiveNearFire() {
        if (this.hivePos == null) {
            return false;
        }
        BlockEntity blockEntity = this.getWorld().getBlockEntity(this.hivePos);
        return blockEntity instanceof MeganeuraHiveBlockEntity && ((MeganeuraHiveBlockEntity)blockEntity).isNearFire();
    }

    private boolean failedPollinatingTooLong() {
        return this.ticksSincePollination > 3600;
    }

    private boolean doesHiveHaveSpace(BlockPos pos) {
        BlockEntity blockEntity = this.getWorld().getBlockEntity(pos);
        if (blockEntity instanceof MeganeuraHiveBlockEntity) {
            return !((MeganeuraHiveBlockEntity)blockEntity).isFullOfBees();
        }
        return false;
    }

    @Debug
    public boolean hasHive() {
        return this.hivePos != null;
    }

    @Nullable
    @Debug
    public BlockPos getHivePos() {
        return this.hivePos;
    }

    void startMovingTo(BlockPos pos) {
        Vec3d vec3d2;
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);
        int i = 0;
        BlockPos blockPos = this.getBlockPos();
        int j = (int)vec3d.y - blockPos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }
        int k = 6;
        int l = 8;
        int m = blockPos.getManhattanDistance(pos);
        if (m < 15) {
            k = m / 2;
            l = m / 2;
        }
        if ((vec3d2 = NoWaterTargeting.find(this, k, l, i, vec3d, 0.3141592741012573)) == null) {
            return;
        }
        this.navigation.setRangeMultiplier(0.5f);
        this.navigation.startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, 1.0);
    }

    @Nullable
    public BlockPos getFlowerPos() {
        return this.flowerPos;
    }

    public boolean hasFlower() {
        return this.flowerPos != null;
    }

    public void setFlowerPos(BlockPos flowerPos) {
        this.flowerPos = flowerPos;
    }

    boolean isFlowers(BlockPos pos) {
        return this.getWorld().canSetBlock(pos) && this.getWorld().getBlockState(pos).isIn(BlockTags.FLOWERS);
    }

    void addCropCounter() {
        ++this.cropsGrownSincePollination;
    }

    public void onHoneyDelivered() {
        this.setHasNectar(false);
        //this.resetCropCounter();
    }

    public void readAngerFromNbt(World world, NbtCompound nbt) {
        this.setAngerTime(nbt.getInt(ANGER_TIME_KEY));
        if (!(world instanceof ServerWorld)) {
            return;
        }
        if (!nbt.containsUuid(ANGRY_AT_KEY)) {
            this.setAngryAt(null);
            return;
        }
        UUID uUID = nbt.getUuid(ANGRY_AT_KEY);
        this.setAngryAt(uUID);
        Entity entity = ((ServerWorld)world).getEntity(uUID);
        if (entity == null) {
            return;
        }
        if (entity instanceof MobEntity) {
            this.setAttacker((MobEntity)entity);
        }
        if (entity.getType() == EntityType.PLAYER) {
            this.setAttacking((PlayerEntity)entity);
        }
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER, angerTime);
    }

    @Override
    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    class BeeLookControl
            extends LookControl {
        BeeLookControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {
            if (MeganeuraEntity.this.hasAngerTime()) {
                return;
            }
            super.tick();
        }

        @Override
        protected boolean shouldStayHorizontal() {
            return !MeganeuraEntity.this.pollinateGoal.isRunning();
        }
    }

    class EnterHiveGoal
            extends MeganeuraEntity.NotAngryGoal {
        EnterHiveGoal() {
        }

        @Override
        public boolean canBeeStart() {
            BlockEntity blockEntity;
            if (MeganeuraEntity.this.hasHive() && MeganeuraEntity.this.canEnterHive()
                    && MeganeuraEntity.this.hivePos.isWithinDistance(MeganeuraEntity.this.getPos(), 2.0)
                    && (blockEntity = MeganeuraEntity.this.getWorld().getBlockEntity(MeganeuraEntity.this.hivePos))
                    instanceof MeganeuraHiveBlockEntity) {
                MeganeuraHiveBlockEntity dragonflyHiveBlockEntity = (MeganeuraHiveBlockEntity)blockEntity;
                if (dragonflyHiveBlockEntity.isFullOfBees()) {
                    MeganeuraEntity.this.hivePos = null;
                } else {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canBeeContinue() {
            return false;
        }

        @Override
        public void start() {
            BlockEntity blockEntity = MeganeuraEntity.this.getWorld().getBlockEntity(MeganeuraEntity.this.hivePos);
            if (blockEntity instanceof MeganeuraHiveBlockEntity) {
                MeganeuraHiveBlockEntity dragonflyHiveBlockEntity = (MeganeuraHiveBlockEntity)blockEntity;
                dragonflyHiveBlockEntity.tryEnterHive(MeganeuraEntity.this, MeganeuraEntity.this.hasNectar());
            }
        }
    }

    class FindHiveGoal
            extends MeganeuraEntity.NotAngryGoal {
        FindHiveGoal() {
        }

        @Override
        public boolean canBeeStart() {
            return MeganeuraEntity.this.ticksLeftToFindHive == 0 && !MeganeuraEntity.this.hasHive() && MeganeuraEntity.this.canEnterHive();
        }

        @Override
        public boolean canBeeContinue() {
            return false;
        }

        @Override
        public void start() {
            MeganeuraEntity.this.ticksLeftToFindHive = 200;
            List<BlockPos> list = this.getNearbyFreeHives();
            if (list.isEmpty()) {
                return;
            }
            for (BlockPos blockPos : list) {
                if (MeganeuraEntity.this.moveToHiveGoal.isPossibleHive(blockPos)) continue;
                MeganeuraEntity.this.hivePos = blockPos;
                return;
            }
            MeganeuraEntity.this.moveToHiveGoal.clearPossibleHives();
            MeganeuraEntity.this.hivePos = list.get(0);
        }

        private List<BlockPos> getNearbyFreeHives() {
            BlockPos blockPos = MeganeuraEntity.this.getBlockPos();
            PointOfInterestStorage pointOfInterestStorage = ((ServerWorld)MeganeuraEntity.this.getWorld()).getPointOfInterestStorage();
            Stream<PointOfInterest> stream = pointOfInterestStorage.getInCircle(poiType -> poiType.isIn(PointOfInterestTypeTags.BEE_HOME), blockPos, 20, PointOfInterestStorage.OccupationStatus.ANY);
            return stream.map(PointOfInterest::getPos).filter(MeganeuraEntity.this::doesHiveHaveSpace).sorted(Comparator.comparingDouble(blockPos2 -> blockPos2.getSquaredDistance(blockPos))).collect(Collectors.toList());
        }
    }

    @Debug
    public class MoveToHiveGoal
            extends MeganeuraEntity.NotAngryGoal {
        public static final int field_30295 = 600;
        int ticks;
        private static final int field_30296 = 3;
        final List<BlockPos> possibleHives;
        @Nullable
        private Path path;
        private static final int field_30297 = 60;
        private int ticksUntilLost;

        MoveToHiveGoal() {
            this.ticks = MeganeuraEntity.this.getWorld().random.nextInt(10);
            this.possibleHives = Lists.newArrayList();
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            return MeganeuraEntity.this.hivePos != null && !MeganeuraEntity.this.hasPositionTarget()
                    && MeganeuraEntity.this.canEnterHive() && !this.isCloseEnough(MeganeuraEntity.this.hivePos)
                    && MeganeuraEntity.this.getWorld().getBlockState(MeganeuraEntity.this.hivePos).isIn(BlockTags.BEEHIVES);
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void start() {
            this.ticks = 0;
            this.ticksUntilLost = 0;
            super.start();
        }

        @Override
        public void stop() {
            this.ticks = 0;
            this.ticksUntilLost = 0;
            MeganeuraEntity.this.navigation.stop();
            MeganeuraEntity.this.navigation.resetRangeMultiplier();
        }

        @Override
        public void tick() {
            if (MeganeuraEntity.this.hivePos == null) {
                return;
            }
            ++this.ticks;
            if (this.ticks > this.getTickCount(600)) {
                this.makeChosenHivePossibleHive();
                return;
            }
            if (MeganeuraEntity.this.navigation.isFollowingPath()) {
                return;
            }
            if (MeganeuraEntity.this.isWithinDistance(MeganeuraEntity.this.hivePos, 16)) {
                boolean bl = this.startMovingToFar(MeganeuraEntity.this.hivePos);
                if (!bl) {
                    this.makeChosenHivePossibleHive();
                } else if (this.path != null && MeganeuraEntity.this.navigation.getCurrentPath().equalsPath(this.path)) {
                    ++this.ticksUntilLost;
                    if (this.ticksUntilLost > 60) {
                        this.setLost();
                        this.ticksUntilLost = 0;
                    }
                } else {
                    this.path = MeganeuraEntity.this.navigation.getCurrentPath();
                }
                return;
            }
            if (MeganeuraEntity.this.isTooFar(MeganeuraEntity.this.hivePos)) {
                this.setLost();
                return;
            }
            MeganeuraEntity.this.startMovingTo(MeganeuraEntity.this.hivePos);
        }

        private boolean startMovingToFar(BlockPos pos) {
            MeganeuraEntity.this.navigation.setRangeMultiplier(10.0f);
            MeganeuraEntity.this.navigation.startMovingTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
            return MeganeuraEntity.this.navigation.getCurrentPath() != null && MeganeuraEntity.this.navigation.getCurrentPath().reachesTarget();
        }

        boolean isPossibleHive(BlockPos pos) {
            return this.possibleHives.contains(pos);
        }

        private void addPossibleHive(BlockPos pos) {
            this.possibleHives.add(pos);
            while (this.possibleHives.size() > 3) {
                this.possibleHives.remove(0);
            }
        }

        void clearPossibleHives() {
            this.possibleHives.clear();
        }

        private void makeChosenHivePossibleHive() {
            if (MeganeuraEntity.this.hivePos != null) {
                this.addPossibleHive(MeganeuraEntity.this.hivePos);
            }
            this.setLost();
        }

        private void setLost() {
            MeganeuraEntity.this.hivePos = null;
            MeganeuraEntity.this.ticksLeftToFindHive = 200;
        }

        private boolean isCloseEnough(BlockPos pos) {
            if (MeganeuraEntity.this.isWithinDistance(pos, 2)) {
                return true;
            }
            Path path = MeganeuraEntity.this.navigation.getCurrentPath();
            return path != null && path.getTarget().equals(pos) && path.reachesTarget() && path.isFinished();
        }
    }

    public class MoveToFlowerGoal
            extends MeganeuraEntity.NotAngryGoal {
        private static final int MAX_FLOWER_NAVIGATION_TICKS = 600;
        int ticks;

        MoveToFlowerGoal() {
            this.ticks = MeganeuraEntity.this.getWorld().random.nextInt(10);
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            return MeganeuraEntity.this.flowerPos != null && !MeganeuraEntity.this.hasPositionTarget() && this.shouldMoveToFlower()
                    && MeganeuraEntity.this.isFlowers(MeganeuraEntity.this.flowerPos)
                    && !MeganeuraEntity.this.isWithinDistance(MeganeuraEntity.this.flowerPos, 2);
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void start() {
            this.ticks = 0;
            super.start();
        }

        @Override
        public void stop() {
            this.ticks = 0;
            MeganeuraEntity.this.navigation.stop();
            MeganeuraEntity.this.navigation.resetRangeMultiplier();
        }

        @Override
        public void tick() {
            if (MeganeuraEntity.this.flowerPos == null) {
                return;
            }
            ++this.ticks;
            if (this.ticks > this.getTickCount(600)) {
                MeganeuraEntity.this.flowerPos = null;
                return;
            }
            if (MeganeuraEntity.this.navigation.isFollowingPath()) {
                return;
            }
            if (MeganeuraEntity.this.isTooFar(MeganeuraEntity.this.flowerPos)) {
                MeganeuraEntity.this.flowerPos = null;
                return;
            }
            MeganeuraEntity.this.startMovingTo(MeganeuraEntity.this.flowerPos);
        }

        private boolean shouldMoveToFlower() {
            return MeganeuraEntity.this.ticksSincePollination > 2400;
        }
    }

    /*class GrowCropsGoal
            extends MeganeuraEntity.NotAngryGoal {
        static final int field_30299 = 30;

        GrowCropsGoal() {
        }

        @Override
        public boolean canBeeStart() {
            if (MeganeuraEntity.this.getCropsGrownSincePollination() >= 10) {
                return false;
            }
            if (MeganeuraEntity.this.random.nextFloat() < 0.3f) {
                return false;
            }
            return MeganeuraEntity.this.hasNectar() && MeganeuraEntity.this.isHiveValid();
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void tick() {
            if (MeganeuraEntity.this.random.nextInt(this.getTickCount(30)) != 0) {
                return;
            }
            for (int i = 1; i <= 2; ++i) {
                BlockPos blockPos = MeganeuraEntity.this.getBlockPos().down(i);
                BlockState blockState = MeganeuraEntity.this.getWorld().getBlockState(blockPos);
                Block block = blockState.getBlock();
                BlockState blockState2 = null;
                if (!blockState.isIn(BlockTags.BEE_GROWABLES)) continue;
                if (block instanceof CropBlock) {
                    CropBlock cropBlock = (CropBlock)block;
                    if (!cropBlock.isMature(blockState)) {
                        blockState2 = cropBlock.withAge(cropBlock.getAge(blockState) + 1);
                    }
                } else if (block instanceof StemBlock) {
                    j = blockState.get(StemBlock.AGE);
                    if (j < 7) {
                        blockState2 = blockState.with(StemBlock.AGE, j + 1);
                    }
                } else if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) {
                    j = blockState.get(SweetBerryBushBlock.AGE);
                    if (j < 3) {
                        blockState2 = blockState.with(SweetBerryBushBlock.AGE, j + 1);
                    }
                } else if (blockState.isOf(Blocks.CAVE_VINES) || blockState.isOf(Blocks.CAVE_VINES_PLANT)) {
                    ((Fertilizable) blockState.getBlock()).grow((ServerWorld)MeganeuraEntity.this.getWorld(),
                            MeganeuraEntity.this.random, blockPos, blockState);
                }
                if (blockState2 == null) continue;
                MeganeuraEntity.this.getWorld().syncWorldEvent(WorldEvents.PLANT_FERTILIZED, blockPos, 0);
                MeganeuraEntity.this.getWorld().setBlockState(blockPos, blockState2);
                MeganeuraEntity.this.addCropCounter();
            }
        }
    }*/

    class BeeWanderAroundGoal
            extends Goal {
        private static final int MAX_DISTANCE = 22;

        BeeWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return MeganeuraEntity.this.navigation.isIdle() && MeganeuraEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return MeganeuraEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                MeganeuraEntity.this.navigation.startMovingAlong(MeganeuraEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d),
                        1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            if (MeganeuraEntity.this.isHiveValid() && !MeganeuraEntity.this.isWithinDistance(MeganeuraEntity.this.hivePos, 22)) {
                Vec3d vec3d = Vec3d.ofCenter(MeganeuraEntity.this.hivePos);
                vec3d2 = vec3d.subtract(MeganeuraEntity.this.getPos()).normalize();
            } else {
                vec3d2 = MeganeuraEntity.this.getRotationVec(0.0f);
            }
            int i = 8;
            Vec3d vec3d3 = AboveGroundTargeting.find(MeganeuraEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null) {
                return vec3d3;
            }
            return NoPenaltySolidTargeting.find(MeganeuraEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    abstract class NotAngryGoal
            extends Goal {
        NotAngryGoal() {
        }

        public abstract boolean canBeeStart();

        public abstract boolean canBeeContinue();

        @Override
        public boolean canStart() {
            return this.canBeeStart() && !MeganeuraEntity.this.hasAngerTime();
        }

        @Override
        public boolean shouldContinue() {
            return this.canBeeContinue() && !MeganeuraEntity.this.hasAngerTime();
        }
    }

    class PollinateGoal
            extends MeganeuraEntity.NotAngryGoal {
        private static final int field_30300 = 400;
        private static final int field_30301 = 20;
        private static final int field_30302 = 60;
        private final Predicate<BlockState> flowerPredicate;
        private static final double field_30303 = 0.1;
        private static final int field_30304 = 25;
        private static final float field_30305 = 0.35f;
        private static final float field_30306 = 0.6f;
        private static final float field_30307 = 0.33333334f;
        private int pollinationTicks;
        private int lastPollinationTick;
        private boolean running;
        @Nullable
        private Vec3d nextTarget;
        private int ticks;
        private static final int field_30308 = 600;

        PollinateGoal() {
            this.flowerPredicate = state -> {
                if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED).booleanValue()) {
                    return false;
                }
                if (state.isIn(BlockTags.FLOWERS)) {
                    if (state.isOf(Blocks.SUNFLOWER)) {
                        return state.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER;
                    }
                    return true;
                }
                return false;
            };
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            if (MeganeuraEntity.this.ticksUntilCanPollinate > 0) {
                return false;
            }
            if (MeganeuraEntity.this.hasNectar()) {
                return false;
            }
            if (MeganeuraEntity.this.getWorld().isRaining()) {
                return false;
            }
            Optional<BlockPos> optional = this.getFlower();
            if (optional.isPresent()) {
                MeganeuraEntity.this.flowerPos = optional.get();
                MeganeuraEntity.this.navigation.startMovingTo(
                        (double)MeganeuraEntity.this.flowerPos.getX() + 0.5,
                        (double)MeganeuraEntity.this.flowerPos.getY() + 0.5,
                        (double)MeganeuraEntity.this.flowerPos.getZ() + 0.5, 1.2f);
                return true;
            }
            MeganeuraEntity.this.ticksUntilCanPollinate = MathHelper.nextInt(MeganeuraEntity.this.random, 20, 60);
            return false;
        }

        @Override
        public boolean canBeeContinue() {
            if (!this.running) {
                return false;
            }
            if (!MeganeuraEntity.this.hasFlower()) {
                return false;
            }
            if (MeganeuraEntity.this.getWorld().isRaining()) {
                return false;
            }
            if (this.completedPollination()) {
                return MeganeuraEntity.this.random.nextFloat() < 0.2f;
            }
            if (MeganeuraEntity.this.age % 20 == 0 && !MeganeuraEntity.this.isFlowers(MeganeuraEntity.this.flowerPos)) {
                MeganeuraEntity.this.flowerPos = null;
                return false;
            }
            return true;
        }

        private boolean completedPollination() {
            return this.pollinationTicks > 400;
        }

        boolean isRunning() {
            return this.running;
        }

        void cancel() {
            this.running = false;
        }

        @Override
        public void start() {
            this.pollinationTicks = 0;
            this.ticks = 0;
            this.lastPollinationTick = 0;
            this.running = true;
            MeganeuraEntity.this.resetPollinationTicks();
        }

        @Override
        public void stop() {
            if (this.completedPollination()) {
                MeganeuraEntity.this.setHasNectar(true);
            }
            this.running = false;
            MeganeuraEntity.this.navigation.stop();
            MeganeuraEntity.this.ticksUntilCanPollinate = 200;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            ++this.ticks;
            if (this.ticks > 600) {
                MeganeuraEntity.this.flowerPos = null;
                return;
            }
            Vec3d vec3d = Vec3d.ofBottomCenter(MeganeuraEntity.this.flowerPos).add(0.0, 0.6f, 0.0);
            if (vec3d.distanceTo(MeganeuraEntity.this.getPos()) > 1.0) {
                this.nextTarget = vec3d;
                this.moveToNextTarget();
                return;
            }
            if (this.nextTarget == null) {
                this.nextTarget = vec3d;
            }
            boolean bl = MeganeuraEntity.this.getPos().distanceTo(this.nextTarget) <= 0.1;
            boolean bl2 = true;
            if (!bl && this.ticks > 600) {
                MeganeuraEntity.this.flowerPos = null;
                return;
            }
            if (bl) {
                boolean bl3;
                boolean bl4 = bl3 = MeganeuraEntity.this.random.nextInt(25) == 0;
                if (bl3) {
                    this.nextTarget = new Vec3d(vec3d.getX() + (double)this.getRandomOffset(), vec3d.getY(), vec3d.getZ()
                            + (double)this.getRandomOffset());
                    MeganeuraEntity.this.navigation.stop();
                } else {
                    bl2 = false;
                }
                MeganeuraEntity.this.getLookControl().lookAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
            }
            if (bl2) {
                this.moveToNextTarget();
            }
            ++this.pollinationTicks;
            if (MeganeuraEntity.this.random.nextFloat() < 0.05f && this.pollinationTicks > this.lastPollinationTick + 60) {
                this.lastPollinationTick = this.pollinationTicks;
                MeganeuraEntity.this.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0f, 1.0f);
            }
        }

        private void moveToNextTarget() {
            MeganeuraEntity.this.getMoveControl().moveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), 0.35f);
        }

        private float getRandomOffset() {
            return (MeganeuraEntity.this.random.nextFloat() * 2.0f - 1.0f) * 0.33333334f;
        }

        private Optional<BlockPos> getFlower() {
            return this.findFlower(this.flowerPredicate, 5.0);
        }

        private Optional<BlockPos> findFlower(Predicate<BlockState> predicate, double searchDistance) {
            BlockPos blockPos = MeganeuraEntity.this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int i = 0;
            while ((double)i <= searchDistance) {
                int j = 0;
                while ((double)j < searchDistance) {
                    int k = 0;
                    while (k <= j) {
                        int l;
                        int n = l = k < j && k > -j ? j : 0;
                        while (l <= j) {
                            mutable.set(blockPos, k, i - 1, l);
                            if (blockPos.isWithinDistance(mutable, searchDistance)
                                    && predicate.test(MeganeuraEntity.this.getWorld().getBlockState(mutable))) {
                                return Optional.of(mutable);
                            }
                            l = l > 0 ? -l : 1 - l;
                        }
                        k = k > 0 ? -k : 1 - k;
                    }
                    ++j;
                }
                i = i > 0 ? -i : 1 - i;
            }
            return Optional.empty();
        }
    }

}
