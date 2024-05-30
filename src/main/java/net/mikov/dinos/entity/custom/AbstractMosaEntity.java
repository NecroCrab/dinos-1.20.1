package net.mikov.dinos.entity.custom;

import net.mikov.dinos.entity.ai.BondWithPlayerGoal;
import net.mikov.dinos.entity.ai.AmbientStandGoal;
import net.mikov.dinos.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.mikov.dinos.entity.custom.AbstractMosaEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class AbstractMosaEntity extends AnimalEntity implements InventoryChangedListener, RideableInventory, Tameable, Saddleable {

    protected AbstractMosaEntity(EntityType<? extends AbstractMosaEntity> entityType, World world) {
        super(entityType, world);
        this.playExtraHorseSounds = false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HORSE_FLAGS, (byte)0);
        this.dataTracker.startTracking(CHEST, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("EatingHaystack", this.isEatingGrass());
        nbt.putBoolean("Bred", this.isBred());
        nbt.putInt("Temper", this.getTemper());
        nbt.putBoolean("Tame", this.isTame());
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        if (!this.items.getStack(0).isEmpty()) {
            nbt.put("SaddleItem", this.items.getStack(0).writeNbt(new NbtCompound()));
        }
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
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
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
        ItemStack itemStack;
        UUID uUID;
        this.setEatingGrass(nbt.getBoolean("EatingHaystack"));
        this.setBred(nbt.getBoolean("Bred"));
        this.setTemper(nbt.getInt("Temper"));
        this.setTame(nbt.getBoolean("Tame"));
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID != null) {
            this.setOwnerUuid(uUID);
        }
        if (nbt.contains("SaddleItem", NbtElement.COMPOUND_TYPE) && (itemStack = ItemStack.fromNbt(nbt.getCompound("SaddleItem"))).isOf(Items.SADDLE)) {
            this.items.setStack(0, itemStack);
        }
        this.updateSaddle();
    }

    //donkey data


    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(AbstractMosaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int field_30412 = 15;

    protected void initAttributes(Random random) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(AbstractMosaEntity.getChildHealthBonus(random::nextInt));
    }

    public static DefaultAttributeContainer.Builder createAbstractMosaAttributes() {
        return AbstractMosaEntity.createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175f)
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.5);
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    protected int getInventorySize() {
        if (this.hasChest()) {
            return 17;
        } else {
            return 2;
        }
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.25;
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
                    return AbstractMosaEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (stack.isEmpty()) {
                        if (AbstractMosaEntity.this.hasChest()) {
                            AbstractMosaEntity.this.setHasChest(false);
                            AbstractMosaEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    if (stack.isOf(Items.CHEST)) {
                        if (!AbstractMosaEntity.this.hasChest()) {
                            AbstractMosaEntity.this.setHasChest(true);
                            AbstractMosaEntity.this.onChestedStatusChanged();
                        }
                        return true;
                    }
                    return false;
                }
            };
        }
        int j;
        int i = mappedIndex - 400;
        if (i >= 0 && i < 2 && i < this.items.size()) {
            if (i == 0) {
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || stack.isOf(Items.SADDLE));
            }
            if (i == 1) {
                if (!this.hasArmorSlot()) {
                    return StackReference.EMPTY;
                }
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || this.isHorseArmor((ItemStack)stack));
            }
        }
        if ((j = mappedIndex - 500 + 2) >= 2 && j < this.items.size()) {
            return StackReference.of(this.items, j);
        }
        return super.getStackReference(mappedIndex);
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
        if (this.hasPassengers() || this.isBaby()) {
            return super.interactMob(player, hand);
        }
        if (this.isTame() && player.shouldCancelInteraction()) {
            this.openInventory(player);
            return ActionResult.success(this.getWorld().isClient);
        }
        if (!itemStack.isEmpty()) {
            ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
            if (actionResult.isAccepted()) {
                return actionResult;
            }
            if (this.hasArmorSlot() && this.isHorseArmor(itemStack) && !this.hasArmorInSlot()) {
                this.equipHorseArmor(player, itemStack);
                //return ActionResult.success(this.getWorld().isClient);
                return super.interactMob(player, hand);
            }
        }
        this.putPlayerOnBack(player);
        return ActionResult.success(this.getWorld().isClient);
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

    //horse data

    public static final int field_30413 = 400;
    public static final int field_30414 = 499;
    public static final int field_30415 = 500;
    public static final double field_42647 = 0.15;
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float)AbstractMosaEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)AbstractMosaEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = AbstractMosaEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = AbstractMosaEntity.getChildHealthBonus(max -> max - 1);
    private static final float field_42979 = 0.25f;
    private static final float field_42980 = 0.5f;
    private static final Predicate<LivingEntity> IS_BRED_HORSE = entity -> entity instanceof AbstractMosaEntity && ((AbstractMosaEntity)entity).isBred();
    private static final TargetPredicate PARENT_HORSE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().setPredicate(IS_BRED_HORSE);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.RAW_PRIMAL_MEAT);
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(AbstractMosaEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;
    private static final int SADDLED_FLAG = 4;
    private static final int BRED_FLAG = 8;
    private static final int EATING_GRASS_FLAG = 16;
    private static final int ANGRY_FLAG = 32;
    private static final int EATING_FLAG = 64;
    public static final int field_30416 = 0;
    public static final int field_30417 = 1;
    public static final int field_30418 = 2;
    private int eatingGrassTicks;
    private int eatingTicks;
    private int angryTicks;
    public int tailWagTicks;
    public int field_6958;
    protected boolean inAir;
    protected SimpleInventory items;
    protected int temper;
    private float eatingGrassAnimationProgress;
    private float lastEatingGrassAnimationProgress;
    private float angryAnimationProgress;
    private float lastAngryAnimationProgress;
    private float eatingAnimationProgress;
    private float lastEatingAnimationProgress;
    protected boolean playExtraHorseSounds = true;
    protected int soundTicks;
    @Nullable
    private UUID ownerUuid;

        @Override
        protected void initGoals() {
            this.goalSelector.add(1, new BondWithPlayerGoal(this, 1.2));
            this.goalSelector.add(2, new AnimalMateGoal(this, 1.0, net.mikov.dinos.entity.custom.AbstractMosaEntity.class));
            this.goalSelector.add(4, new FollowParentGoal(this, 1.0));
            this.goalSelector.add(5, new SwimToRandomPlaceGoal(this));
            this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
            this.goalSelector.add(8, new LookAroundGoal(this));
            if (this.shouldAmbientStand()) {
                this.goalSelector.add(9, new AmbientStandGoal(this));
            }
            this.initCustomGoals();
        }

        protected void initCustomGoals() {
            this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems
                    (Items.PORKCHOP, Items.BEEF, ModItems.RAW_PRIMAL_MEAT), false));
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

        public boolean isInAir() {
            return this.inAir;
        }

        public void setTame(boolean tame) {
            this.setHorseFlag(TAMED_FLAG, tame);
        }

        public void setInAir(boolean inAir) {
            this.inAir = inAir;
        }

        @Override
        protected void updateForLeashLength(float leashLength) {
            if (leashLength > 6.0f && this.isEatingGrass()) {
                this.setEatingGrass(false);
            }
        }

        public boolean isEatingGrass() {
            return this.getHorseFlag(EATING_GRASS_FLAG);
        }

        public boolean isAngry() {
            return this.getHorseFlag(ANGRY_FLAG);
        }

        public boolean isBred() {
            return this.getHorseFlag(BRED_FLAG);
        }

        public void setBred(boolean bred) {
            this.setHorseFlag(BRED_FLAG, bred);
        }

        @Override
        public boolean canBeSaddled() {
            return this.isAlive() && !this.isBaby() && this.isTame();
        }

        @Override
        public void saddle(@Nullable SoundCategory sound) {
            this.items.setStack(0, new ItemStack(Items.SADDLE));
        }

        public void equipHorseArmor(PlayerEntity player, ItemStack stack) {
            if (this.isHorseArmor(stack)) {
                this.items.setStack(1, stack.copyWithCount(1));
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
            }
        }

        @Override
        public boolean isSaddled() {
            return this.getHorseFlag(SADDLED_FLAG);
        }

        public int getTemper() {
            return this.temper;
        }

        public void setTemper(int temper) {
            this.temper = temper;
        }

        public int addTemper(int difference) {
            int i = MathHelper.clamp(this.getTemper() + difference, 0, this.getMaxTemper());
            this.setTemper(i);
            return i;
        }

        @Override
        public boolean isPushable() {
            return !this.hasPassengers();
        }

        private void playEatingAnimation() {
            SoundEvent soundEvent;
            this.setEating();
            if (!this.isSilent() && (soundEvent = this.getEatSound()) != null) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
            }
        }

        @Override
        public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
            int i;
            if (fallDistance > 1.0f) {
                this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4f, 1.0f);
            }
            if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) {
                return false;
            }
            this.damage(damageSource, i);
            if (this.hasPassengers()) {
                for (Entity entity : this.getPassengersDeep()) {
                    entity.damage(damageSource, i);
                }
            }
            this.playBlockFallSound();
            return true;
        }

        @Override
        protected int computeFallDamage(float fallDistance, float damageMultiplier) {
            return MathHelper.ceil((fallDistance * 0.5f - 3.0f) * damageMultiplier);
        }

        protected void onChestedStatusChanged() {
            SimpleInventory simpleInventory = this.items;
            this.items = new SimpleInventory(this.getInventorySize());
            if (simpleInventory != null) {
                simpleInventory.removeListener(this);
                int i = Math.min(simpleInventory.size(), this.items.size());
                for (int j = 0; j < i; ++j) {
                    ItemStack itemStack = simpleInventory.getStack(j);
                    if (itemStack.isEmpty()) continue;
                    this.items.setStack(j, itemStack.copy());
                }
            }
            this.items.addListener(this);
            this.updateSaddle();
        }

        protected void updateSaddle() {
            if (this.getWorld().isClient) {
                return;
            }
            this.setHorseFlag(SADDLED_FLAG, !this.items.getStack(0).isEmpty());
        }

        @Override
        public void onInventoryChanged(Inventory sender) {
            boolean bl = this.isSaddled();
            this.updateSaddle();
            if (this.age > 20 && !bl && this.isSaddled()) {
                this.playSound(this.getSaddleSound(), 0.5f, 1.0f);
            }
        }

        @Override
        public boolean damage(DamageSource source, float amount) {
            boolean bl = super.damage(source, amount);
            if (bl && this.random.nextInt(3) == 0) {
                this.updateAnger();
            }
            return bl;
        }

        protected boolean shouldAmbientStand() {
            return true;
        }

        @Nullable
        protected SoundEvent getEatSound() {
            return null;
        }

        @Nullable
        protected SoundEvent getAngrySound() {
            return null;
        }

        @Override
        protected void playStepSound(BlockPos pos, BlockState state) {
            if (state.isLiquid()) {
                return;
            }
            BlockState blockState = this.getWorld().getBlockState(pos.up());
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();
            if (blockState.isOf(Blocks.SNOW)) {
                blockSoundGroup = blockState.getSoundGroup();
            }
            if (this.hasPassengers() && this.playExtraHorseSounds) {
                ++this.soundTicks;
                if (this.soundTicks > 5 && this.soundTicks % 3 == 0) {
                    this.playWalkSound(blockSoundGroup);
                } else if (this.soundTicks <= 5) {
                    this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
                }
            } else if (this.isWooden(blockSoundGroup)) {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
            } else {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
            }
        }

        private boolean isWooden(BlockSoundGroup soundGroup) {
            return soundGroup == BlockSoundGroup.WOOD || soundGroup == BlockSoundGroup.NETHER_WOOD || soundGroup == BlockSoundGroup.NETHER_STEM || soundGroup == BlockSoundGroup.CHERRY_WOOD || soundGroup == BlockSoundGroup.BAMBOO_WOOD;
        }

        protected void playWalkSound(BlockSoundGroup group) {
            this.playSound(SoundEvents.ENTITY_HORSE_GALLOP, group.getVolume() * 0.15f, group.getPitch());
        }

        public static DefaultAttributeContainer.Builder createBaseHorseAttributes() {
            return MobEntity.createMobAttributes()
                    .add(EntityAttributes.HORSE_JUMP_STRENGTH)
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 53.0)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f);
        }

        @Override
        public int getLimitPerChunk() {
            return 6;
        }

        public int getMaxTemper() {
            return 100;
        }

        @Override
        protected float getSoundVolume() {
            return 0.8f;
        }

        @Override
        public int getMinAmbientSoundDelay() {
            return 400;
        }

        @Override
        public void openInventory(PlayerEntity player) {
            if (!this.getWorld().isClient && (!this.hasPassengers() || this.hasPassenger(player)) && this.isTame()) {
                this.openHorseInventory(this, this.items);
            }
        }

    public void openHorseInventory(AbstractMosaEntity mosa, Inventory inventory) {
    }

        public ActionResult interactHorse(PlayerEntity player, ItemStack stack) {
            boolean bl = this.receiveFood(player, stack);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (this.getWorld().isClient) {
                return ActionResult.CONSUME;
            }
            return bl ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        protected boolean receiveFood(PlayerEntity player, ItemStack item) {
            boolean bl = false;
            float f = 0.0f;
            int i = 0;
            int j = 0;
            if (item.isOf(Items.RABBIT)) {
                f = 2.0f;
                i = 20;
                j = 3;
            } else if (item.isOf(Items.CHICKEN)) {
                f = 1.0f;
                i = 30;
                j = 3;
            } else if (item.isOf(Items.MUTTON)) {
                f = 20.0f;
                i = 180;
            } else if (item.isOf(Items.PORKCHOP)) {
                f = 3.0f;
                i = 60;
                j = 3;
            } else if (item.isOf(Items.BEEF)) {
                f = 4.0f;
                i = 60;
                j = 5;
                if (!this.getWorld().isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
                    bl = true;
                    this.lovePlayer(player);
                }
            } else if (item.isOf(ModItems.RAW_PRIMAL_MEAT) || item.isOf(ModItems.COOKED_PRIMAL_MEAT)) {
                f = 10.0f;
                i = 240;
                j = 10;
                if (!this.getWorld().isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
                    bl = true;
                    this.lovePlayer(player);
                }
            }
            if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
                this.heal(f);
                bl = true;
            }
            if (this.isBaby() && i > 0) {
                this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
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
                this.playEatingAnimation();
                this.emitGameEvent(GameEvent.EAT);
            }
            return bl;
        }

        protected void putPlayerOnBack(PlayerEntity player) {
            this.setEatingGrass(false);
            this.setAngry(false);
            if (!this.getWorld().isClient) {
                player.setYaw(this.getYaw());
                player.setPitch(this.getPitch());
                player.startRiding(this);
            }
        }

        @Override
        public boolean isImmobile() {
            return super.isImmobile() && this.hasPassengers() && this.isSaddled() || this.isEatingGrass() || this.isAngry();
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return BREEDING_INGREDIENT.test(stack);
        }

        private void wagTail() {
            this.tailWagTicks = 1;
        }

        @Override
        public void tickMovement() {
            if (this.random.nextInt(200) == 0) {
                this.wagTail();
            }
            super.tickMovement();
            if (this.getWorld().isClient || !this.isAlive()) {
                return;
            }
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (this.eatsGrass()) {
                if (!this.isEatingGrass() && !this.hasPassengers() && this.random.nextInt(300) == 0
                        && this.getWorld().getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)) {
                    this.setEatingGrass(true);
                }
                if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
                    this.eatingGrassTicks = 0;
                    this.setEatingGrass(false);
                }
            }
            this.walkToParent();
        }

        protected void walkToParent() {
            net.mikov.dinos.entity.custom.AbstractMosaEntity livingEntity;
            if (this.isBred() && this.isBaby() && !this.isEatingGrass()
                    && (livingEntity = this.getWorld().getClosestEntity(net.mikov.dinos.entity.custom.AbstractMosaEntity.class,
                    PARENT_HORSE_PREDICATE, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(16.0)))
                    != null && this.squaredDistanceTo(livingEntity) > 4.0) {
                this.navigation.findPathTo(livingEntity, 0);
            }
        }

        public boolean eatsGrass() {
            return true;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.eatingTicks > 0 && ++this.eatingTicks > 30) {
                this.eatingTicks = 0;
                this.setHorseFlag(EATING_FLAG, false);
            }
            if (this.canMoveVoluntarily() && this.angryTicks > 0 && ++this.angryTicks > 20) {
                this.angryTicks = 0;
                this.setAngry(false);
            }
            if (this.tailWagTicks > 0 && ++this.tailWagTicks > 8) {
                this.tailWagTicks = 0;
            }
            if (this.field_6958 > 0) {
                ++this.field_6958;
                if (this.field_6958 > 300) {
                    this.field_6958 = 0;
                }
            }
            this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
            if (this.isEatingGrass()) {
                this.eatingGrassAnimationProgress += (1.0f - this.eatingGrassAnimationProgress) * 0.4f + 0.05f;
                if (this.eatingGrassAnimationProgress > 1.0f) {
                    this.eatingGrassAnimationProgress = 1.0f;
                }
            } else {
                this.eatingGrassAnimationProgress += (0.0f - this.eatingGrassAnimationProgress) * 0.4f - 0.05f;
                if (this.eatingGrassAnimationProgress < 0.0f) {
                    this.eatingGrassAnimationProgress = 0.0f;
                }
            }
            this.lastAngryAnimationProgress = this.angryAnimationProgress;
            if (this.isAngry()) {
                this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress = 0.0f;
                this.angryAnimationProgress += (1.0f - this.angryAnimationProgress) * 0.4f + 0.05f;
                if (this.angryAnimationProgress > 1.0f) {
                    this.angryAnimationProgress = 1.0f;
                }
            } else {
                this.jumping = false;
                this.angryAnimationProgress += (0.8f * this.angryAnimationProgress * this.angryAnimationProgress * this.angryAnimationProgress - this.angryAnimationProgress) * 0.6f - 0.05f;
                if (this.angryAnimationProgress < 0.0f) {
                    this.angryAnimationProgress = 0.0f;
                }
            }
            this.lastEatingAnimationProgress = this.eatingAnimationProgress;
            if (this.getHorseFlag(EATING_FLAG)) {
                this.eatingAnimationProgress += (1.0f - this.eatingAnimationProgress) * 0.7f + 0.05f;
                if (this.eatingAnimationProgress > 1.0f) {
                    this.eatingAnimationProgress = 1.0f;
                }
            } else {
                this.eatingAnimationProgress += (0.0f - this.eatingAnimationProgress) * 0.7f - 0.05f;
                if (this.eatingAnimationProgress < 0.0f) {
                    this.eatingAnimationProgress = 0.0f;
                }
            }
        }

        private void setEating() {
            if (!this.getWorld().isClient) {
                this.eatingTicks = 1;
                this.setHorseFlag(EATING_FLAG, true);
            }
        }

        public void setEatingGrass(boolean eatingGrass) {
            this.setHorseFlag(EATING_GRASS_FLAG, eatingGrass);
        }

        public void setAngry(boolean angry) {
            if (angry) {
                this.setEatingGrass(false);
            }
            this.setHorseFlag(ANGRY_FLAG, angry);
        }

        @Nullable
        public SoundEvent getAmbientStandSound() {
            return this.getAmbientSound();
        }

        public void updateAnger() {
            if (this.shouldAmbientStand() && this.canMoveVoluntarily()) {
                this.angryTicks = 1;
                this.setAngry(true);
            }
        }

        public void playAngrySound() {
            if (!this.isAngry()) {
                this.updateAnger();
                SoundEvent soundEvent = this.getAngrySound();
                if (soundEvent != null) {
                    this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
                }
            }
        }

        public boolean bondWithPlayer(PlayerEntity player) {
            this.setOwnerUuid(player.getUuid());
            this.setTame(true);
            if (player instanceof ServerPlayerEntity) {
                Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
            }
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            return true;
        }

        @Override
        protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
            super.tickControlled(controllingPlayer, movementInput);
            Vec2f vec2f = this.getControlledRotation(controllingPlayer);
            this.setRotation(vec2f.y, vec2f.x);
            this.bodyYaw = this.headYaw = this.getYaw();
            this.prevYaw = this.headYaw;
            if (this.isLogicalSideForUpdatingMovement()) {
                if (movementInput.z <= 0.0) {
                    this.soundTicks = 0;
                }
            }
        }

        protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
            return new Vec2f(controllingPassenger.getPitch() * 0.5f, controllingPassenger.getYaw());
        }

        @Override
        protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
            if (this.isOnGround() && this.isAngry()) {
                return Vec3d.ZERO;
            }
            float f = controllingPlayer.sidewaysSpeed * 0.5f;
            float g = controllingPlayer.forwardSpeed;
            if (g <= 0.0f) {
                g *= 0.25f;
            }
            return new Vec3d(f, 0.0, g);
        }

        @Override
        protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
            return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        }

        @Override
        public boolean canBreedWith(AnimalEntity other) {
            return false;
        }

        protected boolean canBreed() {
            return !this.hasPassengers() && !this.hasVehicle() && this.isTame() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
        }

        @Override
        @Nullable
        public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
            return null;
        }

        protected void setChildAttributes(PassiveEntity other, net.mikov.dinos.entity.custom.AbstractMosaEntity child) {
            this.setChildAttribute(other, child, EntityAttributes.GENERIC_MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
            this.setChildAttribute(other, child, EntityAttributes.GENERIC_MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
        }

        private void setChildAttribute(PassiveEntity other, net.mikov.dinos.entity.custom.AbstractMosaEntity child, EntityAttribute attribute, double min, double max) {
            double d = net.mikov.dinos.entity.custom.AbstractMosaEntity.calculateAttributeBaseValue(this.getAttributeBaseValue(attribute), other.getAttributeBaseValue(attribute), min, max, this.random);
            child.getAttributeInstance(attribute).setBaseValue(d);
        }

        static double calculateAttributeBaseValue(double parentBase, double otherParentBase, double min, double max, Random random) {
            double g;
            if (max <= min) {
                throw new IllegalArgumentException("Incorrect range for an attribute");
            }
            parentBase = MathHelper.clamp(parentBase, min, max);
            otherParentBase = MathHelper.clamp(otherParentBase, min, max);
            double d = 0.15 * (max - min);
            double f = (parentBase + otherParentBase) / 2.0;
            double e = Math.abs(parentBase - otherParentBase) + d * 2.0;
            double h = f + e * (g = (random.nextDouble() + random.nextDouble() + random.nextDouble()) / 3.0 - 0.5);
            if (h > max) {
                double i = h - max;
                return max - i;
            }
            if (h < min) {
                double i = min - h;
                return min + i;
            }
            return h;
        }

        public float getAngryAnimationProgress(float tickDelta) {
            return MathHelper.lerp(tickDelta, this.lastAngryAnimationProgress, this.angryAnimationProgress);
        }

        public float getEatingAnimationProgress(float tickDelta) {
            return MathHelper.lerp(tickDelta, this.lastEatingAnimationProgress, this.eatingAnimationProgress);
        }

        protected void spawnPlayerReactionParticles(boolean positive) {
            DefaultParticleType particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;
            for (int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
            }
        }

        @Override
        public void handleStatus(byte status) {
            if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
                this.spawnPlayerReactionParticles(true);
            } else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
                this.spawnPlayerReactionParticles(false);
            } else {
                super.handleStatus(status);
            }
        }

        @Override
        protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
            super.updatePassengerPosition(passenger, positionUpdater);
            if (this.lastAngryAnimationProgress > 0.0f) {
                float f = MathHelper.sin(this.bodyYaw * ((float)Math.PI / 180));
                float g = MathHelper.cos(this.bodyYaw * ((float)Math.PI / 180));
                float h = 0.7f * this.lastAngryAnimationProgress;
                float i = 0.15f * this.lastAngryAnimationProgress;
                positionUpdater.accept(passenger, this.getX() + (double)(h * f), this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset() + (double)i, this.getZ() - (double)(h * g));
                if (passenger instanceof LivingEntity) {
                    ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
                }
            }
        }

        protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
            return 15.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(9);
        }

        protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
            return ((double)0.45f + randomDoubleGetter.getAsDouble() * 0.3 + randomDoubleGetter.getAsDouble() * 0.3 + randomDoubleGetter.getAsDouble() * 0.3) * 0.25;
        }

        @Override
        public boolean isClimbing() {
            return false;
        }

        @Override
        protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
            return dimensions.height * 0.95f;
        }

        /**
         * Whether this horse has a slot for custom equipment besides a saddle.
         *
         * <p>In the item slot argument type, the slot is referred to as <code>
         * horse.armor</code>. In this horse's screen, it appears in the middle of
         * the left side, and right below the saddle slot if this horse has a saddle
         * slot.
         *
         * <p>This is used by horse armors and llama carpets, but can be
         * refitted to any purpose.
         */
        public boolean hasArmorSlot() {
            return false;
        }

        /**
         * Whether this horse already has an item stack in its horse armor slot.
         *
         * @see #hasArmorSlot()
         */
        public boolean hasArmorInSlot() {
            return !this.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
        }

        /**
         * Whether the given item stack is valid for this horse's armor slot.
         *
         * @see #hasArmorSlot()
         */
        public boolean isHorseArmor(ItemStack item) {
            return false;
        }

        private StackReference createInventoryStackReference(final int slot, final Predicate<ItemStack> predicate) {
            return new StackReference(){

                @Override
                public ItemStack get() {
                    return net.mikov.dinos.entity.custom.AbstractMosaEntity.this.items.getStack(slot);
                }

                @Override
                public boolean set(ItemStack stack) {
                    if (!predicate.test(stack)) {
                        return false;
                    }
                    net.mikov.dinos.entity.custom.AbstractMosaEntity.this.items.setStack(slot, stack);
                    net.mikov.dinos.entity.custom.AbstractMosaEntity.this.updateSaddle();
                    return true;
                }
            };
        }

        @Override
        @Nullable
        public LivingEntity getControllingPassenger() {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof MobEntity) {
                MobEntity mobEntity = (MobEntity)entity;
                return mobEntity;
            }
            if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)entity;
                return playerEntity;
            }
            return null;
        }

        @Nullable
        private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {
            double d = this.getX() + offset.x;
            double e = this.getBoundingBox().minY;
            double f = this.getZ() + offset.z;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            block0: for (EntityPose entityPose : passenger.getPoses()) {
                mutable.set(d, e, f);
                double g = this.getBoundingBox().maxY + 0.75;
                do {
                    double h = this.getWorld().getDismountHeight(mutable);
                    if ((double)mutable.getY() + h > g) continue block0;
                    if (Dismounting.canDismountInBlock(h)) {
                        Box box = passenger.getBoundingBox(entityPose);
                        Vec3d vec3d = new Vec3d(d, (double)mutable.getY() + h, f);
                        if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                    mutable.move(Direction.UP);
                } while ((double)mutable.getY() < g);
            }
            return null;
        }

        @Override
        public Vec3d updatePassengerForDismount(LivingEntity passenger) {
            Vec3d vec3d = net.minecraft.entity.passive.AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0f : -90.0f));
            Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
            if (vec3d2 != null) {
                return vec3d2;
            }
            Vec3d vec3d3 = net.minecraft.entity.passive.AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0f : -90.0f));
            Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
            if (vec3d4 != null) {
                return vec3d4;
            }
            return this.getPos();
        }

        @Override
        @Nullable
        public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
            if (entityData == null) {
                entityData = new PassiveEntity.PassiveData(0.2f);
            }
            this.initAttributes(world.getRandom());
            return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        }

        public boolean areInventoriesDifferent(Inventory inventory) {
            return this.items != inventory;
        }

        public int getMinAmbientStandDelay() {
            return this.getMinAmbientSoundDelay();
        }

        @Override
        public /* synthetic */ EntityView method_48926() {
            return super.getWorld();
        }

    protected boolean hasSelfControl() {
        return true;
    }

    static class SwimToRandomPlaceGoal
            extends SwimAroundGoal {
        private final AbstractMosaEntity mosa;

        public SwimToRandomPlaceGoal(AbstractMosaEntity mosa) {
            super(mosa, 1.0, 40);
            this.mosa = mosa;
        }

        @Override
        public boolean canStart() {
            return this.mosa.hasSelfControl() && super.canStart();
        }
    }

}
