package net.mikov.dinos.entity.custom;

import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.TrexAttackGoal;
import net.mikov.dinos.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.mikov.dinos.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class TrexEntity extends AnimalEntity {

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;
    public static final AnimationState roaringAnimationState = new AnimationState();
    public int roaringAnimationTimeout = 0;

    public static final Predicate<LivingEntity> FOLLOW_PREDICATE = entity -> {
        EntityType<?> entityType = entity.getType();
        return entityType == EntityType.SHEEP ||
                entityType == EntityType.COW ||
                entityType == EntityType.HORSE ||
                entityType == EntityType.CAMEL ||
                entityType == EntityType.CREEPER ||
                entityType == EntityType.DONKEY ||
                entityType == EntityType.EVOKER ||
                entityType == EntityType.GOAT ||
                entityType == EntityType.HOGLIN ||
                entityType == EntityType.LLAMA ||
                entityType == EntityType.MULE ||
                entityType == EntityType.PANDA ||
                entityType == EntityType.PILLAGER ||
                entityType == EntityType.POLAR_BEAR ||
                entityType == EntityType.RAVAGER ||
                entityType == EntityType.SNIFFER ||
                entityType == EntityType.TRADER_LLAMA ||
                entityType == EntityType.VEX ||
                entityType == EntityType.VINDICATOR ||
                entityType == EntityType.WANDERING_TRADER ||
                entityType == EntityType.WITCH ||
                entityType == ModEntities.DODO ||
                entityType == ModEntities.ANKY ||
                entityType == EntityType.PIG;
    };

    public TrexEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(1120) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.isAttacking() && attackingAnimationTimeout <= 0) {
            attackingAnimationTimeout = 40;
            attackingAnimationState.start(this.age);
        } else {
            /*if (this.isAttacking() && !this.isInWalkTargetRange() && roaringAnimationTimeout <= 0) {
                roaringAnimationTimeout = 100;
                roaringAnimationState.start(this.age);
            } else {
                --this.roaringAnimationTimeout;
            }*/
            --this.attackingAnimationTimeout;
        }
        if(!this.isAttacking()) {
            attackingAnimationState.stop();
            //roaringAnimationState.stop();
        }

    }

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(TrexEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
        //this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 2.0f, this.getSoundPitch());
        super.setAttacking(attacking);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
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
        this.goalSelector.add( 0, new SwimGoal(this));
        this.goalSelector.add( 1, new AnimalMateGoal( this, 1.1D));
        this.goalSelector.add( 2, new TemptGoal( this, 1.1D, Ingredient.ofItems(ModItems.RAW_PRIMAL_MEAT), false));
        this.goalSelector.add( 3, new FollowParentGoal(this, 1.15D));
        this.goalSelector.add( 4, new TrexAttackGoal(this, 1.15, true));
        this.goalSelector.add( 6, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add( 7, new LookAtEntityGoal(this, PlayerEntity.class, 10f));
        this.goalSelector.add( 8, new LookAroundGoal(this));

        //this.targetSelector.add(5, new RevengeGoal(this, new Class[0]).setGroupRevenge(TrexEntity.class));
        this.targetSelector.add(1, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        //this.targetSelector.add(1, new ActiveTargetGoal<DodoEntity>((MobEntity)this, DodoEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<LivingEntity>(this, LivingEntity.class, true, FOLLOW_PREDICATE));
    }

    public static DefaultAttributeContainer.Builder createTrexAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 6)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 4)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40);

    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.RAW_PRIMAL_MEAT);
    }

    @Nullable
    @Override
    public AnimalEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.TREX.create(world);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.TREX_STEP, 1.5f, 1.0f);
    }

}
