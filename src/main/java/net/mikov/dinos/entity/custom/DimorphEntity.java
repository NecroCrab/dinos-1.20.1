package net.mikov.dinos.entity.custom;

import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.CompyEggBlock;
import net.mikov.dinos.block.custom.DimorphEggBlock;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.DimorphAttackGoal;
import net.mikov.dinos.entity.animation.ModAnimations;
import net.mikov.dinos.entity.client.DimorphModel;
import net.mikov.dinos.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class DimorphEntity extends TameableEntity implements Tameable {
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(DimorphEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIGGING_DIRT = DataTracker.registerData(DimorphEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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

    public DimorphEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
        this.moveControl = new FlightMoveControl(this, 5, false);
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
            DataTracker.registerData(DimorphEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MateGoal(this, 1.0));
        this.goalSelector.add(2, new LayEggGoal(this, 1.0));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(6, new DimorphAttackGoal(this, 1.15, true));
        this.goalSelector.add(7, new FlyOntoTreeGoal(this, 1.0));
        //this.goalSelector.add(8, new FlyRandomlyGoal(this));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]).setGroupRevenge(DimorphEntity.class));
        this.targetSelector.add(4, new UntamedActiveTargetGoal<AnimalEntity>(this, AnimalEntity.class, false, FOLLOW_PREDICATE));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85f : dimensions.height * 0.92f;
    }

    public static DefaultAttributeContainer.Builder createDimorphAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.3)
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
        return ModEntities.DIMORPH.create(world);
        //return null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_SCREECH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FOX_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1.0f);
    }

    //taming

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    /*@Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.WHEAT;

        if (this.getWorld().isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.WHEAT) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }
        if (this.isTamed()) {
            ActionResult actionResult;
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                this.heal(item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            }
            if ((actionResult = super.interactMob(player, hand)).isAccepted() && !this.isBaby() || !this.isOwner(player))
                return actionResult;
            this.setSit(!this.isSitting());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return ActionResult.SUCCESS;
        }
        if (itemStack.isOf(Items.WHEAT) || !this.isTamed());
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            if (this.random.nextInt(3) == 0) {
                this.setOwner(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setSit(true);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                return ActionResult.SUCCESS;
            } else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }
            return ActionResult.SUCCESS;
    }*/

    /*@Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = ModItems.RAW_PRIMAL_MEAT;

        if (item == itemForTaming && !isTamed()) {
            if (this.getWorld().isClient() && this.random.nextInt(3) == 0) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemstack.decrement(1);
                }

                if (!this.getWorld().isClient()) {
                    super.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.getWorld().sendEntityStatus(this, (byte)7);
                    setSit(true);
                }
                return ActionResult.SUCCESS;
            }
        }

        if(isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND) {
            setSit(!isSitting());
            return ActionResult.SUCCESS;
        }

        if (itemstack.getItem() == itemForTaming) {
            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }*/

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0f);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(12.0);
        }
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0);
    }

    /*@Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(other instanceof CompyEntity)) {
            return false;
        }
        CompyEntity compyEntity = (CompyEntity) other;
        if (!compyEntity.isTamed()) {
            return false;
        }
        if (compyEntity.isInSittingPose()) {
            return false;
        }
        return this.isInLove() && compyEntity.isInLove();
    }*/

    private static final TrackedData<Boolean> SITTING =
            DataTracker.registerData(DimorphEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(DIGGING_DIRT, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isSitting", this.dataTracker.get(SITTING));
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SITTING, nbt.getBoolean("isSitting"));
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    //flying

    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
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
    protected boolean isFlappingWings() {
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

    static class FlyRandomlyGoal
            extends Goal {
        private final DimorphEntity dimorph;

        public FlyRandomlyGoal(DimorphEntity dimorph) {
            this.dimorph = dimorph;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            double f;
            double e;
            MoveControl moveControl = this.dimorph.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            }
            double d = moveControl.getTargetX() - this.dimorph.getX();
            double g = d * d + (e = moveControl.getTargetY() - this.dimorph.getY()) * e + (f = moveControl.getTargetZ() - this.dimorph.getZ()) * f;
            return g < 1.0 || g > 3600.0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.dimorph.getRandom();
            double d = this.dimorph.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double e = this.dimorph.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double f = this.dimorph.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.dimorph.getMoveControl().moveTo(d, e, f, 1.0);
        }
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
        private final DimorphEntity dimorph;

        MateGoal(DimorphEntity dimorph, double speed) {
            super(dimorph, speed);
            this.dimorph = dimorph;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.dimorph.hasEgg();
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
            this.dimorph.setHasEgg(true);
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
        private final DimorphEntity dimorph;

        LayEggGoal(DimorphEntity dimorph, double speed) {
            super(dimorph, speed, 16);
            this.dimorph = dimorph;
        }

        @Override
        public boolean canStart() {
            if (this.dimorph.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.dimorph.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.dimorph.getBlockPos();
            if (!this.dimorph.isTouchingWater() && this.hasReached()) {
                if (this.dimorph.dirtDiggingCounter < 1) {
                    this.dimorph.setDiggingDirt(true);
                } else if (this.dimorph.dirtDiggingCounter > this.getTickCount(200)) {
                    World world = this.dimorph.getWorld();
                    world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.DIMORPH_EGG_BLOCK.getDefaultState().with(DimorphEggBlock.EGGS, this.dimorph.random.nextInt(4) + 1);
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.dimorph, blockState));
                    this.dimorph.setHasEgg(false);
                    this.dimorph.setDiggingDirt(false);
                    this.dimorph.setLoveTicks(600);
                }
                if (this.dimorph.isDiggingDirt()) {
                    ++this.dimorph.dirtDiggingCounter;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return DimorphEggBlock.isDirt(world, pos);
        }
    }

}
