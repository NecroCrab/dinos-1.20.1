package net.mikov.dinos.entity.custom;

import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.BondWithPlayerGoal;
import net.mikov.dinos.entity.ai.LargerAnimalMateGoal;
import net.mikov.dinos.entity.ai.MosaAttackGoal;
import net.mikov.dinos.entity.ai.AmbientStandGoal;
import net.mikov.dinos.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
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
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class MosaEntity extends AbstractMosaEntity implements InventoryChangedListener, RideableInventory, Tameable, Saddleable {
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(MosaEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;
    @Nullable
    private UUID ownerUuid;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MosaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);


    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.RAW_PRIMAL_MEAT);

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
                entityType == EntityType.COD ||
                entityType == EntityType.SALMON ||
                entityType == EntityType.SPIDER ||
                entityType == EntityType.SQUID ||
                entityType == EntityType.VILLAGER ||
                entityType == EntityType.RAVAGER ||
                entityType == EntityType.SNIFFER ||
                entityType == EntityType.TRADER_LLAMA ||
                entityType == EntityType.VEX ||
                entityType == EntityType.VINDICATOR ||
                entityType == EntityType.WANDERING_TRADER ||
                entityType == EntityType.WITCH ||
                entityType == ModEntities.DODO ||
                entityType == ModEntities.ANKY ||
                entityType == ModEntities.TRIKE ||
                entityType == ModEntities.TREX ||
                entityType == ModEntities.TRILOBITE ||
                entityType == ModEntities.COMPY ||
                entityType == ModEntities.COEL ||
                entityType == ModEntities.CERATO ||
                entityType == EntityType.BOAT ||
                entityType == EntityType.CHEST_BOAT ||
                entityType == EntityType.PIG;
    };

    public static final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;

    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(MosaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int field_30412 = 15;

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
            this.setAir(900);
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

    public MosaEntity(EntityType<? extends AbstractMosaEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new MosaEntity.FishMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.onChestedStatusChanged();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(1140) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.isAttacking() && this.attackingAnimationTimeout <= 0) {
            this.attackingAnimationTimeout = 40;
            this.attackingAnimationState.start(this.age);
        } else {
            --this.attackingAnimationTimeout;
        }
        if (!this.isAttacking()) {
            attackingAnimationState.stop();
        }
    }

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(MosaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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
        this.goalSelector.add(0, new LargerAnimalMateGoal(this, 1.5));
        this.goalSelector.add(0, new BondWithPlayerGoal(this, 1.2));
        this.goalSelector.add(2, new TemptGoal(this, 2.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(4, new MosaAttackGoal(this, 2.15, true));
        this.goalSelector.add(5, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add(6, new LookAroundGoal(this));
        if (this.shouldAmbientStand()) {
            this.goalSelector.add(9, new AmbientStandGoal(this));
        }

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));

        if (!this.isTame()) {
            this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge(MosaEntity.class));
            this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity) this, PlayerEntity.class, true));
            this.targetSelector.add(3, new ActiveTargetGoal<>(this, LivingEntity.class, true, FOLLOW_PREDICATE));
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(1.0f, movementInput);
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

    public static DefaultAttributeContainer.Builder createMosaAttributes() {
        return AbstractMosaEntity.createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 90)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.7)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 16)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 4)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 4)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 8)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 36);

    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(MosaEntity.getChildHealthBonus(random::nextInt));
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 90.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public AbstractMosaEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MOSA.create(world);
        //return null;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return this.canBreed();
        }
        if (other instanceof MosaEntity) {
            return this.canBreed();
        }
        return false;
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
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(CHEST, false);
        this.dataTracker.startTracking(HORSE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    protected boolean getHorseFlag(int bitmask) {
        return (this.dataTracker.get(HORSE_FLAGS) & bitmask) != 0;
    }

    protected void setHorseFlag(int bitmask, boolean flag) {
        byte b = this.dataTracker.get(HORSE_FLAGS);
        if (flag) {
            this.dataTracker.set(HORSE_FLAGS, (byte)(b | bitmask));
        } else {
            this.dataTracker.set(HORSE_FLAGS, (byte)(b & ~bitmask));
        }
    }

    public boolean isTame() {
        return this.getHorseFlag(TAMED_FLAG);
    }

    @Override
    @Nullable
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public void setTame(boolean tame) {
        this.setHorseFlag(TAMED_FLAG, tame);
    }

    protected boolean hasSelfControl() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    //riding

    @Override
    public EntityView method_48926() {
        return getWorld();
    }

    @Override
    protected void updateSaddle() {
        if (this.getWorld().isClient) {
            return;
        }
        super.updateSaddle();
        this.setArmorTypeFromStack(this.items.getStack(1));
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    private void setArmorTypeFromStack(ItemStack stack) {
        this.equipArmor(stack);
        if (!this.getWorld().isClient) {
            int i;
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HORSE_ARMOR_BONUS_ID);
            if (this.isHorseArmor(stack) && (i = ((HorseArmorItem)stack.getItem()).getBonus()) != 0) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier
                        (HORSE_ARMOR_BONUS_ID, "Horse armor bonus", (double)i, EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        ItemStack itemStack = this.getArmorType();
        super.onInventoryChanged(sender);
        ItemStack itemStack2 = this.getArmorType();
        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
        }
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl;
        boolean bl2 = bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();
        if (this.hasPassengers() || bl) {
            return super.interactMob(player, hand);
        }
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty()) {
            if (this.isBreedingItem(itemStack)) {
                return this.interactHorse(player, itemStack);
            }
            if (!this.isTame()) {
                this.playAngrySound();
                return ActionResult.success(this.getWorld().isClient);
            }
            if (!this.hasChest() && itemStack.isOf(Items.CHEST)) {
                this.addChest(player, itemStack);
                return ActionResult.success(this.getWorld().isClient);
            }
        }
        return super.interactMob(player, hand);
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    @Override
    protected int getInventorySize() {
        if (this.hasChest()) {
            return 17;
        }
        return super.getInventorySize();
    }

    private void addChest(PlayerEntity player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        if (!player.getAbilities().creativeMode) {
            chest.decrement(1);
        }
        this.onChestedStatusChanged();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
    }

    public int getInventoryColumns() {
        return 5;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.getWorld().isClient) {
                this.dropItem(Blocks.CHEST);
            }
            this.setHasChest(false);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        if (mappedIndex == 499) {
            return new StackReference(){

                @Override
                public ItemStack get() {
                    return MosaEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (stack.isEmpty()) {
                        if (MosaEntity.this.hasChest()) {
                            MosaEntity.this.setHasChest(false);
                            MosaEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    if (stack.isOf(Items.CHEST)) {
                        if (!MosaEntity.this.hasChest()) {
                            MosaEntity.this.setHasChest(true);
                            MosaEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    return false;
                }
            };
        }
        return super.getStackReference(mappedIndex);
    }

    @Override
    protected boolean receiveFood(PlayerEntity player, ItemStack item) {
        boolean bl = false;
        float f = 0.0f;
        int i = 0;
        int j = 0;
        if (item.isOf(Items.CHICKEN)) {
            f = 2.0f;
            i = 20;
            j = 3;
        } else if (item.isOf(Items.MUTTON)) {
            f = 1.0f;
            i = 30;
            j = 3;
        } else if (item.isOf(Items.PORKCHOP)) {
            f = 3.0f;
            i = 60;
            j = 3;
        } else if (item.isOf(Items.BEEF)) {
            f = 4.0f;
            i = 60;
            j = 5;
            if (!this.getWorld().isClient && this.getBreedingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.lovePlayer(player);
            }
        } else if (item.isOf(ModItems.RAW_PRIMAL_MEAT)) {
            f = 10.0f;
            i = 240;
            j = 10;
            if (!this.getWorld().isClient && this.getBreedingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.lovePlayer(player);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
            this.heal(f);
            bl = true;
        }
        if (this.isBaby() && i > 0) {
            this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER,
                    this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!this.getWorld().isClient) {
                this.growUp(i);
            }
            bl = true;
        }
        if (j > 0 && (bl || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            bl = true;
            if (!this.getWorld().isClient) {
                this.addTemper(j);
            }
        }
        if (bl) {
            this.emitGameEvent(GameEvent.EAT);
        }
        return bl;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ChestedHorse", this.hasChest());
        if (this.hasChest()) {
            NbtList nbtList = new NbtList();
            for (int i = 2; i < this.items.size(); ++i) {
                ItemStack itemStack = this.items.getStack(i);
                if (itemStack.isEmpty()) continue;
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
            nbt.put("Items", nbtList);
        }
        if (!this.items.getStack(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
        }
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    public ItemStack getArmorType() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }

    private void equipArmor(ItemStack stack) {
        this.equipStack(EquipmentSlot.CHEST, stack);
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        ItemStack itemStack;
        UUID uUID;
        super.readCustomDataFromNbt(nbt);
        this.setHasChest(nbt.getBoolean("ChestedHorse"));
        this.onChestedStatusChanged();
        if (this.hasChest()) {
            NbtList nbtList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 0xFF;
                if (j < 2 || j >= this.items.size()) continue;
                this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
        if (nbt.contains("ArmorItem", NbtElement.COMPOUND_TYPE) &&
                !(itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"))).isEmpty() && this.isHorseArmor(itemStack)) {
            this.items.setStack(1, itemStack);
        }
        this.updateSaddle();
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setOwnerUuid(uUID);
                this.setTame(true);
            } catch (Throwable throwable) {
                this.setTame(false);
            }
        }
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        return item.getItem() instanceof HorseArmorItem;
    }

    static class FishMoveControl
            extends MoveControl {
        private final MosaEntity mosa;

        FishMoveControl(MosaEntity owner) {
            super(owner);
            this.mosa = owner;
        }

        @Override
        public void tick() {
            if (this.mosa.isSubmergedIn(FluidTags.WATER)) {
                this.mosa.setVelocity(this.mosa.getVelocity().add(0.0, 0.005, 0.0));
            }
            if (this.state != State.MOVE_TO || this.mosa.getNavigation().isIdle()) {
                this.mosa.setMovementSpeed(0.0f);
                return;
            }
            float f = (float)(this.speed * this.mosa.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            this.mosa.setMovementSpeed(MathHelper.lerp(0.125f, this.mosa.getMovementSpeed(), f));
            double d = this.targetX - this.mosa.getX();
            double e = this.targetY - this.mosa.getY();
            double g = this.targetZ - this.mosa.getZ();
            if (e != 0.0) {
                double h = Math.sqrt(d * d + e * e + g * g);
                this.mosa.setVelocity(this.mosa.getVelocity().add(0.0, (double)this.mosa.getMovementSpeed() * (e / h) * 0.1, 0.0));
            }
            if (d != 0.0 || g != 0.0) {
                float i = (float)(MathHelper.atan2(g, d) * 57.2957763671875) - 90.0f;
                this.mosa.setYaw(this.wrapDegrees(this.mosa.getYaw(), i, 90.0f));
                this.mosa.bodyYaw = this.mosa.getYaw();
            }
        }
    }

    static class SwimToRandomPlaceGoal
            extends SwimAroundGoal {
        private final MosaEntity mosa;

        public SwimToRandomPlaceGoal(MosaEntity mosa) {
            super(mosa, 1.0, 40);
            this.mosa = mosa;
        }

        @Override
        public boolean canStart() {
            return this.mosa.hasSelfControl() && super.canStart();
        }
    }

    // attack goals

    public void setOwner(PlayerEntity player) {
        this.setTame(true);
        this.setOwnerUuid(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (this.isOwner(target)) {
            return false;
        }
        return super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        LivingEntity livingEntity;
        if (this.isTame() && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getScoreboardTeam();
        }
        return super.getScoreboardTeam();
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (this.isTame()) {
            LivingEntity livingEntity = this.getOwner();
            if (other == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isTeammate(other);
            }
        }
        return super.isTeammate(other);
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof GhastEntity) {
            return false;
        }
        if (target instanceof MosaEntity) {
            MosaEntity mosaEntity = (MosaEntity)target;
            return !mosaEntity.isTame() || mosaEntity.getOwner() != owner;
        }
        if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
            return false;
        }
        if (target instanceof AbstractMosaEntity && ((AbstractMosaEntity)target).isTame()) {
            return false;
        }
        return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
    }

    public static class TrackOwnerAttackerGoal
            extends TrackTargetGoal {
        private final MosaEntity mosa;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public TrackOwnerAttackerGoal(MosaEntity mosa) {
            super(mosa, false);
            this.mosa = mosa;
            this.setControls(EnumSet.of(Goal.Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.mosa.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.mosa.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacker = livingEntity.getAttacker();
            int i = livingEntity.getLastAttackedTime();
            return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.mosa.canAttackWithOwner(this.attacker, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingEntity = this.mosa.getOwner();
            if (livingEntity != null) {
                this.lastAttackedTime = livingEntity.getLastAttackedTime();
            }
            super.start();
        }
    }

    public static class AttackWithOwnerGoal
            extends TrackTargetGoal {
        private final MosaEntity mosa;
        private LivingEntity attacking;
        private int lastAttackTime;

        public AttackWithOwnerGoal(MosaEntity mosa) {
            super(mosa, false);
            this.mosa = mosa;
            this.setControls(EnumSet.of(Goal.Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.mosa.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.mosa.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.mosa.canAttackWithOwner(this.attacking, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.mosa.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
            super.start();
        }
    }

}
