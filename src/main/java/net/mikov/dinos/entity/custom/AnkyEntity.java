package net.mikov.dinos.entity.custom;

import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.block.custom.AnkyEggBlock;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.ai.AnkyAttackGoal;
import net.mikov.dinos.entity.ai.CompyAttackGoal;
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

public class AnkyEntity extends AbstractDonkeyEntity
        implements InventoryChangedListener, RideableInventory, Tameable, JumpingMount, Saddleable {
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIGGING_DIRT = DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    int dirtDiggingCounter;
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;
    @Nullable
    private UUID ownerUuid;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);


    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems
            (Items.MELON, Items.PUMPKIN, Items.SWEET_BERRIES);

    public static final Predicate<LivingEntity> FOLLOW_PREDICATE = entity -> {
        EntityType<?> entityType = entity.getType();
        return entityType == ModEntities.TREX;
    };

    public static final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public static final AnimationState sittingAnimationState = new AnimationState();
    public int sittingAnimationTimeout = 0;
    public static final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationTimeout = 0;

    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int field_30412 = 15;

    public AnkyEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0f);
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
            this.idleAnimationTimeout = this.random.nextInt(1180) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        /*if (this.isSitting() && sittingAnimationTimeout <= 0) {
            sittingAnimationTimeout = 40;
            sittingAnimationState.start(this.age);
        } else {
            --this.sittingAnimationTimeout;
        }
        if(!this.isSitting()) {
            sittingAnimationState.stop();
        }*/
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
            DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add(1, new MateGoal(this, 1.0));
        this.goalSelector.add(1, new LayEggGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(4, new AnkyAttackGoal(this, 1.15, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        if (this.shouldAmbientStand()) {
            this.goalSelector.add(8, new AmbientStandGoal(this));
        }

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));

        if (!this.isTame()) {
            this.targetSelector.add(3, new RevengeGoal(this, new Class[0]).setGroupRevenge(AnkyEntity.class));
            this.targetSelector.add(4, new ActiveTargetGoal<AnimalEntity>(this, AnimalEntity.class, false, FOLLOW_PREDICATE));
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85f : dimensions.height * 0.92f;
    }

    public static DefaultAttributeContainer.Builder createAnkyAttributes() {
        return AbstractDonkeyEntity.createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 8)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 8)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 18);

    }

    @Override
    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(AnkyEntity.getChildHealthBonus(random::nextInt));
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 60.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.ANKY.create(world);
        //return null;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return this.canBreed();
        }
        if (other instanceof AnkyEntity) {
            return this.canBreed();
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ANKY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAMEL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ANKY_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 0.5f, 1.0f);
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

    /*@Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(200.0);
            this.setHealth(20.0f);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(120.0);
        }
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(8.0);
    }*/

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
            DataTracker.registerData(AnkyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        //super.setSitting(sitting);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
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
        return ModSounds.ANKY_DEATH;
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
                    return AnkyEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (stack.isEmpty()) {
                        if (AnkyEntity.this.hasChest()) {
                            AnkyEntity.this.setHasChest(false);
                            AnkyEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    if (stack.isOf(Items.CHEST)) {
                        if (!AnkyEntity.this.hasChest()) {
                            AnkyEntity.this.setHasChest(true);
                            AnkyEntity.this.onChestedStatusChanged();
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
            f = 1.0f;
            i = 30;
            j = 3;
        } else if (item.isOf(Items.PUMPKIN)) {
            f = 3.0f;
            i = 60;
            j = 3;
            if (!this.getWorld().isClient && this.getBreedingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.lovePlayer(player);
            }
        } else if (item.isOf(Items.MELON)) {
            f = 4.0f;
            i = 60;
            j = 5;
            if (!this.getWorld().isClient && this.getBreedingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.lovePlayer(player);
            }
        } else if (item.isOf(Items.SWEET_BERRIES)) {
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
        private final AnkyEntity anky;

        MateGoal(AnkyEntity anky, double speed) {
            super(anky, speed);
            this.anky = anky;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.anky.hasEgg();
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
            this.anky.setHasEgg(true);
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
        private final AnkyEntity anky;

        LayEggGoal(AnkyEntity anky, double speed) {
            super(anky, speed, 16);
            this.anky = anky;
        }

        @Override
        public boolean canStart() {
            if (this.anky.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.anky.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.anky.getBlockPos();
            if (!this.anky.isTouchingWater() && this.hasReached()) {
                if (this.anky.dirtDiggingCounter < 1) {
                    this.anky.setDiggingDirt(true);
                } else if (this.anky.dirtDiggingCounter > this.getTickCount(200)) {
                    World world = this.anky.getWorld();
                    world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                    BlockPos blockPos2 = this.targetPos.up();
                    BlockState blockState = ModBlocks.ANKY_EGG_BLOCK.getDefaultState().with(AnkyEggBlock.EGGS, this.anky.random.nextInt(4) + 1);
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.anky, blockState));
                    this.anky.setHasEgg(false);
                    this.anky.setDiggingDirt(false);
                    this.anky.setLoveTicks(600);
                }
                if (this.anky.isDiggingDirt()) {
                    ++this.anky.dirtDiggingCounter;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return AnkyEggBlock.isDirt(world, pos);
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
        if (target instanceof AnkyEntity) {
            AnkyEntity ankyEntity = (AnkyEntity)target;
            return !ankyEntity.isTame() || ankyEntity.getOwner() != owner;
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
        private final AnkyEntity anky;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public TrackOwnerAttackerGoal(AnkyEntity anky) {
            super(anky, false);
            this.anky = anky;
            this.setControls(EnumSet.of(Goal.Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.anky.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.anky.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacker = livingEntity.getAttacker();
            int i = livingEntity.getLastAttackedTime();
            return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.anky.canAttackWithOwner(this.attacker, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingEntity = this.anky.getOwner();
            if (livingEntity != null) {
                this.lastAttackedTime = livingEntity.getLastAttackedTime();
            }
            super.start();
        }
    }

    public static class AttackWithOwnerGoal
            extends TrackTargetGoal {
        private final AnkyEntity anky;
        private LivingEntity attacking;
        private int lastAttackTime;

        public AttackWithOwnerGoal(AnkyEntity anky) {
            super(anky, false);
            this.anky = anky;
            this.setControls(EnumSet.of(Goal.Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.anky.isTame()) {
                return false;
            }
            LivingEntity livingEntity = this.anky.getOwner();
            if (livingEntity == null) {
                return false;
            }
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.anky.canAttackWithOwner(this.attacking, livingEntity);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.anky.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
            super.start();
        }
    }

}
