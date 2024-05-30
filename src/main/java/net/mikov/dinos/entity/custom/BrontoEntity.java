package net.mikov.dinos.entity.custom;

import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.BrontoEggBlock;
import net.mikov.dinos.block.custom.TrexEggBlock;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.BrontoAttackGoal;
import net.mikov.dinos.entity.ai.LargerAnimalMateGoal;
import net.mikov.dinos.entity.ai.TrexAttackGoal;
import net.mikov.dinos.item.ModItems;
import net.mikov.dinos.sounds.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class BrontoEntity extends AbstractDonkeyEntity
        implements InventoryChangedListener, RideableInventory, Tameable, JumpingMount, Saddleable {
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIGGING_DIRT = DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    int dirtDiggingCounter;
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;
    @Nullable
    private UUID ownerUuid;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;

    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int field_30412 = 15;
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.HAY_BLOCK);

    public BrontoEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(2.0f);
        this.onChestedStatusChanged();
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
            this.idleAnimationState.start(this.age);
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
            DataTracker.registerData(BrontoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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
        this.goalSelector.add( 0, new SwimGoal(this));
        this.goalSelector.add( 0, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add( 1, new MateGoal(this, 1.0));
        this.goalSelector.add( 1, new LayEggGoal(this, 1.0));
        this.goalSelector.add( 2, new TemptGoal( this, 1.1D, Ingredient.ofItems(Items.HAY_BLOCK), false));
        this.goalSelector.add( 3, new FollowParentGoal(this, 1.15D));
        this.goalSelector.add( 4, new BrontoAttackGoal(this, 1.15, true));
        this.goalSelector.add( 6, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add( 7, new LookAtEntityGoal(this, PlayerEntity.class, 10f));
        this.goalSelector.add( 8, new LookAroundGoal(this));
        if (this.shouldAmbientStand()) {
            this.goalSelector.add(9, new AmbientStandGoal(this));
        }

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));

        if (!this.isTame()) {
            this.targetSelector.add(3, new RevengeGoal(this, new Class[0]).setGroupRevenge(BrontoEntity.class));
        }
    }

    public static DefaultAttributeContainer.Builder createBrontoAttributes() {
        return AbstractDonkeyEntity.createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 8)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 8)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.2)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40);

    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(BrontoEntity.getChildHealthBonus(random::nextInt));
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 100.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public AbstractDonkeyEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.BRONTO.create(world);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return this.canBreed();
        }
        if (other instanceof BrontoEntity) {
            return this.canBreed();
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BRONTO_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BRONTO_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BRONTO_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.TREX_STEP, 1.5f, 1.0f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(ModSounds.TREX_STEP, 1.4f, 1.0f);
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
        return ModSounds.BRONTO_HURT;
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
                    return BrontoEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (stack.isEmpty()) {
                        if (BrontoEntity.this.hasChest()) {
                            BrontoEntity.this.setHasChest(false);
                            BrontoEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    if (stack.isOf(Items.CHEST)) {
                        if (!BrontoEntity.this.hasChest()) {
                            BrontoEntity.this.setHasChest(true);
                            BrontoEntity.this.onChestedStatusChanged();
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
        if (item.isOf(Items.WHEAT)) {
            f = 2.0f;
            i = 20;
            j = 3;
        } else if (item.isOf(Items.CARROT)) {
            f = 1.0f;
            i = 30;
            j = 3;
        } else if (item.isOf(Items.BEETROOT)) {
            f = 3.0f;
            i = 60;
            j = 3;
        } else if (item.isOf(Items.POTATO)) {
            f = 4.0f;
            i = 60;
            j = 5;
            if (!this.getWorld().isClient && this.getBreedingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.lovePlayer(player);
            }
        } else if (item.isOf(Items.HAY_BLOCK)) {
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
            extends LargerAnimalMateGoal {
        private final BrontoEntity bronto;

        MateGoal(BrontoEntity bronto, double speed) {
            super(bronto, speed);
            this.bronto = bronto;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.bronto.hasEgg();
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
            this.bronto.setHasEgg(true);
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
        private final BrontoEntity bronto;

        LayEggGoal(BrontoEntity bronto, double speed) {
            super(bronto, speed, 16);
            this.bronto = bronto;
        }

        @Override
        public boolean canStart() {
            if (this.bronto.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.bronto.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.bronto.getBlockPos();
            if (!this.bronto.isTouchingWater() && this.hasReached()) {
                if (this.bronto.dirtDiggingCounter < 1) {
                    this.bronto.setDiggingDirt(true);
                } else if (this.bronto.dirtDiggingCounter > this.getTickCount(200)) {
                    World world = this.bronto.getWorld();
                    world.playSound(null, blockPos, SoundEvents.BLOCK_SNIFFER_EGG_PLOP, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.BRONTO_EGG_BLOCK.getDefaultState();
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.bronto, blockState));
                    this.bronto.setHasEgg(false);
                    this.bronto.setDiggingDirt(false);
                    this.bronto.setLoveTicks(600);
                }
                if (this.bronto.isDiggingDirt()) {
                    ++this.bronto.dirtDiggingCounter;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return BrontoEggBlock.isDirt(world, pos);
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
        if (target instanceof BrontoEntity) {
            BrontoEntity brontoEntity = (BrontoEntity)target;
            return !brontoEntity.isTame() || brontoEntity.getOwner() != owner;
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
        private final BrontoEntity bronto;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public TrackOwnerAttackerGoal(BrontoEntity bronto) {
            super(bronto, false);
            this.bronto = bronto;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.bronto.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.bronto.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacker = livingEntity.getAttacker();
            int i = livingEntity.getLastAttackedTime();
            return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.bronto.canAttackWithOwner(this.attacker, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingEntity = this.bronto.getOwner();
            if (livingEntity != null) {
                this.lastAttackedTime = livingEntity.getLastAttackedTime();
            }
            super.start();
        }
    }

    public static class AttackWithOwnerGoal
            extends TrackTargetGoal {
        private final BrontoEntity bronto;
        private LivingEntity attacking;
        private int lastAttackTime;

        public AttackWithOwnerGoal(BrontoEntity bronto) {
            super(bronto, false);
            this.bronto = bronto;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.bronto.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.bronto.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.bronto.canAttackWithOwner(this.attacking, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.bronto.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
            super.start();
        }
    }
}
