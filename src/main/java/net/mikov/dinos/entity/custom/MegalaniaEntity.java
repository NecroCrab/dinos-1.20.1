package net.mikov.dinos.entity.custom;

import com.google.common.collect.Maps;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.CeratoEggBlock;
import net.mikov.dinos.block.custom.MegalaniaEggBlock;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.CeratoAttackGoal;
import net.mikov.dinos.entity.ai.MegalaniaAttackGoal;
import net.mikov.dinos.item.ModItems;
import net.mikov.dinos.sounds.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class MegalaniaEntity extends AbstractDonkeyEntity
        implements InventoryChangedListener, RideableInventory, Tameable, JumpingMount, Saddleable {
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIGGING_DIRT = DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    int dirtDiggingCounter;
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;
    @Nullable
    private UUID ownerUuid;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;
    public static final AnimationState sittingAnimationState = new AnimationState();
    public int sittingAnimationTimeout = 0;
    public static final AnimationState swimmingAnimationState = new AnimationState();
    public int swimmingAnimationTimeout = 0;

    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int field_30412 = 15;
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.RAW_PIRANHA);

    public static final Predicate<LivingEntity> FOLLOW_PREDICATE = entity -> {
        EntityType<?> entityType = entity.getType();
        return entityType == EntityType.SHEEP ||
                entityType == EntityType.COW ||
                entityType == EntityType.HORSE ||
                entityType == EntityType.CAMEL ||
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
                entityType == ModEntities.COMPY ||
                entityType == ModEntities.PIRANHA ||
                entityType == ModEntities.DIMORPH ||
                entityType == EntityType.PIG;
    };

    public MegalaniaEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0f);
        this.moveControl = new MegalaniaEntity.AxolotlMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.onChestedStatusChanged();
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
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
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(1120) + 80;
            idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.isAttacking() && attackingAnimationTimeout <= 0) {
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
            DataTracker.registerData(MegalaniaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
        super.setAttacking(attacking);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(CHEST, false);
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(DIGGING_DIRT, false);
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
        this.goalSelector.add( 0, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add( 1, new MateGoal(this, 1.0));
        this.goalSelector.add( 1, new LayEggGoal(this, 1.0));
        this.goalSelector.add( 2, new TemptGoal( this, 1.1D, Ingredient.ofItems(ModItems.RAW_PIRANHA), false));
        this.goalSelector.add( 3, new FollowParentGoal(this, 1.15D));
        this.goalSelector.add( 4, new MegalaniaAttackGoal(this, 1.15, true));
        //this.goalSelector.add( 5, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add( 5, new WanderInWaterGoal(this, 1.0));
        this.goalSelector.add( 6, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add( 7, new LookAtEntityGoal(this, PlayerEntity.class, 10f));
        this.goalSelector.add( 8, new LookAroundGoal(this));
        if (this.shouldAmbientStand()) {
            this.goalSelector.add(9, new AmbientStandGoal(this));
        }

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));

        if (!this.isTame()) {
            this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge(MegalaniaEntity.class));
            this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity) this, PlayerEntity.class, true));
            this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity) this, MerchantEntity.class, true));
            this.targetSelector.add(4, new ActiveTargetGoal<IronGolemEntity>((MobEntity) this, IronGolemEntity.class, true));
            this.targetSelector.add(5, new ActiveTargetGoal<>(this, LivingEntity.class, true, FOLLOW_PREDICATE));
        }
    }

    public static DefaultAttributeContainer.Builder createMegalaniaAttributes() {
        return AbstractDonkeyEntity.createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 2)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40);

    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(MegalaniaEntity.getChildHealthBonus(random::nextInt));
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 50.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public AbstractDonkeyEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MEGALANIA.create(world);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return this.canBreed();
        }
        if (other instanceof MegalaniaEntity) {
            return this.canBreed();
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGALANIA_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.MEGALANIA_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALANIA_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.5f, 1.0f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.4f, 1.0f);
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
        return ModSounds.CERATO_HURT;
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
                    return MegalaniaEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (stack.isEmpty()) {
                        if (MegalaniaEntity.this.hasChest()) {
                            MegalaniaEntity.this.setHasChest(false);
                            MegalaniaEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    if (stack.isOf(Items.CHEST)) {
                        if (!MegalaniaEntity.this.hasChest()) {
                            MegalaniaEntity.this.setHasChest(true);
                            MegalaniaEntity.this.onChestedStatusChanged();
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
        } else if (item.isOf(Items.COD)) {
            f = 2.0f;
            i = 20;
            j = 3;
        } else if (item.isOf(Items.MUTTON)) {
            f = 1.0f;
            i = 30;
            j = 3;
        } else if (item.isOf(Items.SALMON)) {
            f = 1.0f;
            i = 30;
            j = 3;
        } else if (item.isOf(Items.PORKCHOP)) {
            f = 3.0f;
            i = 60;
            j = 3;
        } else if (item.isOf(Items.TROPICAL_FISH)) {
            f = 3.0f;
            i = 60;
            j = 3;
        } else if (item.isOf(Items.BEEF)) {
            f = 4.0f;
            i = 60;
            j = 5;
        } else if (item.isOf(Items.PUFFERFISH)) {
            f = 4.0f;
            i = 60;
            j = 5;
        } else if (item.isOf(ModItems.RAW_PRIMAL_MEAT)) {
            f = 10.0f;
            i = 240;
            j = 10;
        } else if (item.isOf(ModItems.RAW_COEL)) {
            f = 10.0f;
            i = 240;
            j = 10;
        } else if (item.isOf(ModItems.RAW_PIRANHA)) {
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
        nbt.putBoolean("HasEgg", this.hasEgg());
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
        this.setHasEgg(nbt.getBoolean("HasEgg"));
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

    // hatching

    static class MateGoal
            extends AnimalMateGoal {
        private final MegalaniaEntity megalania;

        MateGoal(MegalaniaEntity megalania, double speed) {
            super(megalania, speed);
            this.megalania = megalania;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.megalania.hasEgg();
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
            this.megalania.setHasEgg(true);
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
        private final MegalaniaEntity megalania;

        LayEggGoal(MegalaniaEntity megalania, double speed) {
            super(megalania, speed, 16);
            this.megalania = megalania;
        }

        @Override
        public boolean canStart() {
            if (this.megalania.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.megalania.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.megalania.getBlockPos();
            if (!this.megalania.isTouchingWater() && this.hasReached()) {
                if (this.megalania.dirtDiggingCounter < 1) {
                    this.megalania.setDiggingDirt(true);
                } else if (this.megalania.dirtDiggingCounter > this.getTickCount(200)) {
                    World world = this.megalania.getWorld();
                    world.playSound(null, blockPos, SoundEvents.BLOCK_SNIFFER_EGG_PLOP, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.MEGALANIA_EGG_BLOCK.getDefaultState().with(MegalaniaEggBlock.EGGS, this.megalania.random.nextInt(4) + 1);
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.megalania, blockState));
                    this.megalania.setHasEgg(false);
                    this.megalania.setDiggingDirt(false);
                    this.megalania.setLoveTicks(600);
                }
                if (this.megalania.isDiggingDirt()) {
                    ++this.megalania.dirtDiggingCounter;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return MegalaniaEggBlock.isDirt(world, pos);
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
        if (target instanceof MegalaniaEntity) {
            MegalaniaEntity megalaniaEntity = (MegalaniaEntity)target;
            return !megalaniaEntity.isTame() || megalaniaEntity.getOwner() != owner;
        }
        if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
            return false;
        }
        if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
            return false;
        }
        return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
    }

    public static class TrackOwnerAttackerGoal
            extends TrackTargetGoal {
        private final MegalaniaEntity megalania;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public TrackOwnerAttackerGoal(MegalaniaEntity megalania) {
            super(megalania, false);
            this.megalania = megalania;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.megalania.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.megalania.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacker = livingEntity.getAttacker();
            int i = livingEntity.getLastAttackedTime();
            return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.megalania.canAttackWithOwner(this.attacker, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingEntity = this.megalania.getOwner();
            if (livingEntity != null) {
                this.lastAttackedTime = livingEntity.getLastAttackedTime();
            }
            super.start();
        }
    }

    public static class AttackWithOwnerGoal
            extends TrackTargetGoal {
        private final MegalaniaEntity megalania;
        private LivingEntity attacking;
        private int lastAttackTime;

        public AttackWithOwnerGoal(MegalaniaEntity megalania) {
            super(megalania, false);
            this.megalania = megalania;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.megalania.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.megalania.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.megalania.canAttackWithOwner(this.attacking, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.megalania.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
            super.start();
        }
    }

    //swimming

    static class AxolotlMoveControl
            extends AquaticMoveControl {
        private final MegalaniaEntity megalania;

        public AxolotlMoveControl(MegalaniaEntity megalania) {
            super(megalania, 85, 10, 1.6f, 1f, true);
            this.megalania = megalania;
        }

        @Override
        public void tick() {
            super.tick();
        }
    }

    static class SwimToRandomPlaceGoal
            extends SwimAroundGoal {
        private final MegalaniaEntity megalania;

        public SwimToRandomPlaceGoal(MegalaniaEntity megalania) {
            super(megalania, 1.0, 40);
            this.megalania = megalania;
        }

        @Override
        public boolean canStart() {
            return this.megalania.hasSelfControl() && super.canStart();
        }
    }

    protected boolean hasSelfControl() {
        return true;
    }

    static class WanderInWaterGoal
            extends MoveToTargetPosGoal {
        private static final int field_30385 = 1200;
        private final MegalaniaEntity megalania;

        WanderInWaterGoal(MegalaniaEntity megalania, double speed) {
            super(megalania, megalania.isBaby() ? 2.0 : speed, 24);
            this.megalania = megalania;
            this.lowestY = -1;
        }

        @Override
        public boolean shouldContinue() {
            return !this.megalania.isTouchingWater() && this.tryingTime <= 1200 && this.isTargetPos(this.megalania.getWorld(), this.targetPos);
        }

        @Override
        public boolean canStart() {
            if (this.megalania.isBaby() && !this.megalania.isTouchingWater()) {
                return super.canStart();
            }
            if (!(this.megalania.isOnGround() || this.megalania.isTouchingWater() || this.megalania.hasEgg())) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldResetPath() {
            return this.tryingTime % 160 == 0;
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isOf(Blocks.WATER);
        }
    }
}
