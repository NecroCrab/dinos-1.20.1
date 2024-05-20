package net.mikov.dinos.block.custom;

import com.google.common.collect.Lists;
import net.mikov.dinos.entity.custom.MeganeuraEntity;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MeganeuraHiveBlockEntity
        extends BlockEntity {
    public static final String FLOWER_POS_KEY = "FlowerPos";
    public static final String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";
    public static final String ENTITY_DATA_KEY = "EntityData";
    public static final String TICKS_IN_HIVE_KEY = "TicksInHive";
    public static final String HAS_NECTAR_KEY = "HasNectar";
    public static final String BEES_KEY = "Bees";
    private static final List<String> IRRELEVANT_BEE_NBT_KEYS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");
    public static final int MAX_BEE_COUNT = 5;
    private static final int ANGERED_CANNOT_ENTER_HIVE_TICKS = 400;
    private static final int MIN_OCCUPATION_TICKS_WITH_NECTAR = 2400;
    public static final int MIN_OCCUPATION_TICKS_WITHOUT_NECTAR = 600;
    private final List<MeganeuraHiveBlockEntity.Bee> bees = Lists.newArrayList();
    @Nullable
    private BlockPos flowerPos;

    public MeganeuraHiveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.BEEHIVE, pos, state);
    }

    @Override
    public void markDirty() {
        if (this.isNearFire()) {
            this.angerBees(null, this.world.getBlockState(this.getPos()), MeganeuraHiveBlockEntity.BeeState.EMERGENCY);
        }
        super.markDirty();
    }

    public boolean isNearFire() {
        if (this.world == null) {
            return false;
        }
        for (BlockPos blockPos : BlockPos.iterate(this.pos.add(-1, -1, -1), this.pos.add(1, 1, 1))) {
            if (!(this.world.getBlockState(blockPos).getBlock() instanceof FireBlock)) continue;
            return true;
        }
        return false;
    }

    public boolean hasNoBees() {
        return this.bees.isEmpty();
    }

    public boolean isFullOfBees() {
        return this.bees.size() == 5;
    }

    public void angerBees(@Nullable PlayerEntity player, BlockState state, MeganeuraHiveBlockEntity.BeeState beeState) {
        List<Entity> list = this.tryReleaseBee(state, beeState);
        if (player != null) {
            for (Entity entity : list) {
                if (!(entity instanceof MeganeuraEntity)) continue;
                MeganeuraEntity dragonflyEntity = (MeganeuraEntity) entity;
                if (!(player.getPos().squaredDistanceTo(entity.getPos()) <= 16.0)) continue;
                if (!this.isSmoked()) {
                    dragonflyEntity.setTarget(player);
                    continue;
                }
                dragonflyEntity.setCannotEnterHiveTicks(400);
            }
        }
    }

    private List<Entity> tryReleaseBee(BlockState state, MeganeuraHiveBlockEntity.BeeState beeState) {
        ArrayList<Entity> list = Lists.newArrayList();
        this.bees.removeIf(bee -> MeganeuraHiveBlockEntity.releaseBee(this.world, this.pos, state, bee, list, beeState, this.flowerPos));
        if (!list.isEmpty()) {
            super.markDirty();
        }
        return list;
    }

    public void tryEnterHive(Entity entity, boolean hasNectar) {
        this.tryEnterHive(entity, hasNectar, 0);
    }

    @Debug
    public int getBeeCount() {
        return this.bees.size();
    }

    public static int getHoneyLevel(BlockState state) {
        return state.get(MeganeuraHiveBlock.HONEY_LEVEL);
    }

    @Debug
    public boolean isSmoked() {
        return CampfireBlock.isLitCampfireInRange(this.world, this.getPos());
    }

    public void tryEnterHive(Entity entity, boolean hasNectar, int ticksInHive) {
        if (this.bees.size() >= 3) {
            return;
        }
        entity.stopRiding();
        entity.removeAllPassengers();
        NbtCompound nbtCompound = new NbtCompound();
        entity.saveNbt(nbtCompound);
        this.addBee(nbtCompound, ticksInHive, hasNectar);
        if (this.world != null) {
            MeganeuraEntity dragonflyEntity;
            if (entity instanceof MeganeuraEntity && (dragonflyEntity = (MeganeuraEntity)entity).hasFlower()
                    && (!this.hasFlowerPos() || this.world.random.nextBoolean())) {
                this.flowerPos = dragonflyEntity.getFlowerPos();
            }
            BlockPos blockPos = this.getPos();
            this.world.playSound(null, (double)blockPos.getX(), (double)blockPos.getY(), blockPos.getZ(),
                    SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(entity, this.getCachedState()));
        }
        entity.discard();
        super.markDirty();
    }

    public void addBee(NbtCompound nbtCompound, int ticksInHive, boolean hasNectar) {
        this.bees.add(new MeganeuraHiveBlockEntity.Bee(nbtCompound, ticksInHive, hasNectar ? 2400 : 600));
    }

    private static boolean releaseBee(World world, BlockPos pos, BlockState state, MeganeuraHiveBlockEntity.Bee bee,
                                      @Nullable List<Entity> entities, MeganeuraHiveBlockEntity.BeeState beeState, @Nullable BlockPos flowerPos) {
        boolean bl;
        if ((world.isNight() || world.isRaining()) && beeState != MeganeuraHiveBlockEntity.BeeState.EMERGENCY) {
            return false;
        }
        NbtCompound nbtCompound = bee.entityData.copy();
        MeganeuraHiveBlockEntity.removeIrrelevantNbtKeys(nbtCompound);
        nbtCompound.put("HivePos", NbtHelper.fromBlockPos(pos));
        nbtCompound.putBoolean("NoGravity", true);
        Direction direction = state.get(BeehiveBlock.FACING);
        BlockPos blockPos = pos.offset(direction);
        boolean bl2 = bl = !world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty();
        if (bl && beeState != MeganeuraHiveBlockEntity.BeeState.EMERGENCY) {
            return false;
        }
        Entity entity2 = EntityType.loadEntityWithPassengers(nbtCompound, world, entity -> entity);
        if (entity2 != null) {
            if (!entity2.getType().isIn(EntityTypeTags.BEEHIVE_INHABITORS)) {
                return false;
            }
            if (entity2 instanceof MeganeuraEntity) {
                MeganeuraEntity dragonflyEntity = (MeganeuraEntity)entity2;
                if (flowerPos != null && !dragonflyEntity.hasFlower() && world.random.nextFloat() < 0.9f) {
                    dragonflyEntity.setFlowerPos(flowerPos);
                }
                if (beeState == MeganeuraHiveBlockEntity.BeeState.HONEY_DELIVERED) {
                    int i;
                    dragonflyEntity.onHoneyDelivered();
                    if (state.isIn(BlockTags.BEEHIVES, statex -> statex.contains(MeganeuraHiveBlock.HONEY_LEVEL))
                            && (i = MeganeuraHiveBlockEntity.getHoneyLevel(state)) < 5) {
                        int j;
                        int n = j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 5) {
                            --j;
                        }
                        world.setBlockState(pos, (BlockState)state.with(MeganeuraHiveBlock.HONEY_LEVEL, i + j));
                    }
                }
                MeganeuraHiveBlockEntity.ageBee(bee.ticksInHive, dragonflyEntity);
                if (entities != null) {
                    entities.add(dragonflyEntity);
                }
                float f = entity2.getWidth();
                double d = bl ? 0.0 : 0.55 + (double)(f / 2.0f);
                double e = (double)pos.getX() + 0.5 + d * (double)direction.getOffsetX();
                double g = (double)pos.getY() + 0.5 - (double)(entity2.getHeight() / 2.0f);
                double h = (double)pos.getZ() + 0.5 + d * (double)direction.getOffsetZ();
                entity2.refreshPositionAndAngles(e, g, h, entity2.getYaw(), entity2.getPitch());
            }
            world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity2, world.getBlockState(pos)));
            return world.spawnEntity(entity2);
        }
        return false;
    }

    static void removeIrrelevantNbtKeys(NbtCompound compound) {
        for (String string : IRRELEVANT_BEE_NBT_KEYS) {
            compound.remove(string);
        }
    }

    private static void ageBee(int ticks, BeeEntity bee) {
        int i = bee.getBreedingAge();
        if (i < 0) {
            bee.setBreedingAge(Math.min(0, i + ticks));
        } else if (i > 0) {
            bee.setBreedingAge(Math.max(0, i - ticks));
        }
        bee.setLoveTicks(Math.max(0, bee.getLoveTicks() - ticks));
    }

    private boolean hasFlowerPos() {
        return this.flowerPos != null;
    }

    private static void tickBees(World world, BlockPos pos, BlockState state, List<MeganeuraHiveBlockEntity.Bee> bees, @Nullable BlockPos flowerPos) {
        boolean bl = false;
        Iterator<MeganeuraHiveBlockEntity.Bee> iterator = bees.iterator();
        while (iterator.hasNext()) {
            MeganeuraHiveBlockEntity.Bee bee = iterator.next();
            if (bee.ticksInHive > bee.minOccupationTicks) {
                MeganeuraHiveBlockEntity.BeeState beeState;
                MeganeuraHiveBlockEntity.BeeState beeState2 = beeState = bee.entityData.getBoolean(HAS_NECTAR_KEY) ?
                        MeganeuraHiveBlockEntity.BeeState.HONEY_DELIVERED : MeganeuraHiveBlockEntity.BeeState.BEE_RELEASED;
                if (MeganeuraHiveBlockEntity.releaseBee(world, pos, state, bee, null, beeState, flowerPos)) {
                    bl = true;
                    iterator.remove();
                }
            }
            ++bee.ticksInHive;
        }
        if (bl) {
            MeganeuraHiveBlockEntity.markDirty(world, pos, state);
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, MeganeuraHiveBlockEntity blockEntity) {
        MeganeuraHiveBlockEntity.tickBees(world, pos, state, blockEntity.bees, blockEntity.flowerPos);
        if (!blockEntity.bees.isEmpty() && world.getRandom().nextDouble() < 0.005) {
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5;
            world.playSound(null, d, e, f, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.bees.clear();
        NbtList nbtList = nbt.getList(BEES_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            MeganeuraHiveBlockEntity.Bee bee = new MeganeuraHiveBlockEntity.Bee(nbtCompound.getCompound(ENTITY_DATA_KEY),
                    nbtCompound.getInt(TICKS_IN_HIVE_KEY), nbtCompound.getInt(MIN_OCCUPATION_TICKS_KEY));
            this.bees.add(bee);
        }
        this.flowerPos = null;
        if (nbt.contains(FLOWER_POS_KEY)) {
            this.flowerPos = NbtHelper.toBlockPos(nbt.getCompound(FLOWER_POS_KEY));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put(BEES_KEY, this.getBees());
        if (this.hasFlowerPos()) {
            nbt.put(FLOWER_POS_KEY, NbtHelper.fromBlockPos(this.flowerPos));
        }
    }

    public NbtList getBees() {
        NbtList nbtList = new NbtList();
        for (MeganeuraHiveBlockEntity.Bee bee : this.bees) {
            NbtCompound nbtCompound = bee.entityData.copy();
            nbtCompound.remove("UUID");
            NbtCompound nbtCompound2 = new NbtCompound();
            nbtCompound2.put(ENTITY_DATA_KEY, nbtCompound);
            nbtCompound2.putInt(TICKS_IN_HIVE_KEY, bee.ticksInHive);
            nbtCompound2.putInt(MIN_OCCUPATION_TICKS_KEY, bee.minOccupationTicks);
            nbtList.add(nbtCompound2);
        }
        return nbtList;
    }

    public static enum BeeState {
        HONEY_DELIVERED,
        BEE_RELEASED,
        EMERGENCY;

    }

    static class Bee {
        final NbtCompound entityData;
        int ticksInHive;
        final int minOccupationTicks;

        Bee(NbtCompound entityData, int ticksInHive, int minOccupationTicks) {
            MeganeuraHiveBlockEntity.removeIrrelevantNbtKeys(entityData);
            this.entityData = entityData;
            this.ticksInHive = ticksInHive;
            this.minOccupationTicks = minOccupationTicks;
        }
    }
}
