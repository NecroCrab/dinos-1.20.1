package net.mikov.dinos.entity.custom;

import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.AbstractFishEggBlock;
import net.mikov.dinos.block.custom.AnkyEggBlock;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.item.ModItems;
import net.mikov.dinos.sounds.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CoelEntity extends AnimalEntity implements Bucketable {
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(CoelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(CoelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems
            (Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD);

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(this.getDamageSources().drown(), 2.0f);
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    public CoelEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new CoelEntity.FishMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(1140) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(1, new MateGoal(this, 1.0));
        this.goalSelector.add(1, new LayEggGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 2.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(6, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void tickMovement() {
        if (!this.isTouchingWater() && this.isOnGround() && this.verticalCollision) {
            this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0f - 1.0f) * 0.05f, 0.4f, (this.random.nextFloat() * 2.0f - 1.0f) * 0.05f));
            this.setOnGround(false);
            this.velocityDirty = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        super.tickMovement();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85f : dimensions.height * 0.92f;
    }

    public static DefaultAttributeContainer.Builder createCoelAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12);

    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.COEL.create(world);
        //return null;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.COEL_BUCKET);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(HAS_EGG, false);
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    protected boolean hasSelfControl() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    static class FishMoveControl
            extends MoveControl {
        private final CoelEntity fish;

        FishMoveControl(CoelEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {
            if (this.fish.isSubmergedIn(FluidTags.WATER)) {
                this.fish.setVelocity(this.fish.getVelocity().add(0.0, 0.005, 0.0));
            }
            if (this.state != MoveControl.State.MOVE_TO || this.fish.getNavigation().isIdle()) {
                this.fish.setMovementSpeed(0.0f);
                return;
            }
            float f = (float)(this.speed * this.fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            this.fish.setMovementSpeed(MathHelper.lerp(0.125f, this.fish.getMovementSpeed(), f));
            double d = this.targetX - this.fish.getX();
            double e = this.targetY - this.fish.getY();
            double g = this.targetZ - this.fish.getZ();
            if (e != 0.0) {
                double h = Math.sqrt(d * d + e * e + g * g);
                this.fish.setVelocity(this.fish.getVelocity().add(0.0, (double)this.fish.getMovementSpeed() * (e / h) * 0.1, 0.0));
            }
            if (d != 0.0 || g != 0.0) {
                float i = (float)(MathHelper.atan2(g, d) * 57.2957763671875) - 90.0f;
                this.fish.setYaw(this.wrapDegrees(this.fish.getYaw(), i, 90.0f));
                this.fish.bodyYaw = this.fish.getYaw();
            }
        }
    }

    static class SwimToRandomPlaceGoal
            extends SwimAroundGoal {
        private final CoelEntity fish;

        public SwimToRandomPlaceGoal(CoelEntity fish) {
            super(fish, 1.0, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }

    //hatching

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    static class MateGoal
            extends AnimalMateGoal {
        private final CoelEntity coel;

        MateGoal(CoelEntity coel, double speed) {
            super(coel, speed);
            this.coel = coel;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.coel.hasEgg();
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
            this.coel.setHasEgg(true);
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
        private final CoelEntity coel;

        LayEggGoal(CoelEntity coel, double speed) {
            super(coel, speed, 16);
            this.coel = coel;
        }

        @Override
        public boolean canStart() {
            if (this.coel.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.coel.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.coel.getBlockPos();
            if (this.coel.isTouchingWater() && this.hasReached()) {
                    World world = this.coel.getWorld();
                    world.playSound(null, blockPos, SoundEvents.ENTITY_FROG_LAY_SPAWN, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.COEL_EGG_BLOCK.getDefaultState();
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.coel, blockState));
                    this.coel.setHasEgg(false);
                    this.coel.setLoveTicks(600);
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isWater(pos.up())) {
                return false;
            }
            return AbstractFishEggBlock.canLayAt(world, pos);
        }
    }

}
