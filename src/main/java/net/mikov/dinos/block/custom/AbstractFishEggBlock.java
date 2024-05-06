package net.mikov.dinos.block.custom;

import com.google.common.annotations.VisibleForTesting;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.CoelEntity;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TadpoleEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class AbstractFishEggBlock extends Block implements Waterloggable {

    private static final int MIN_FISH = 2;
    private static final int MAX_FISH = 5;
    private static final int MIN_HATCH_TIME = 3600;
    private static final int MAX_HATCH_TIME = 12000;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 2.0, 0.0, 14.0, 12, 14.0);
    private static int minHatchTime = 3600;
    private static int maxHatchTime = 12000;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AbstractFishEggBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canPlaceAt(state, world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED).booleanValue()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return AbstractFishEggBlock.canLayAt(world, pos.down());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, AbstractFishEggBlock.getHatchTime(world.getRandom()));
    }

    private static int getHatchTime(Random random) {
        return random.nextBetweenExclusive(minHatchTime, maxHatchTime);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canPlaceAt(state, world, pos)) {
            this.breakWithDrop(world, pos);
            return;
        }
        this.hatch(world, pos, random);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.breakWithDrop(world, pos);
        }
    }

    public static boolean canLayAt(BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.WATER;
    }

    public void hatch(ServerWorld world, BlockPos pos, Random random) {
        this.breakWithoutDrop(world, pos);
        world.playSound(null, pos, SoundEvents.BLOCK_FROGSPAWN_HATCH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        this.spawnFish(world, pos, random);
    }

    public void breakWithDrop(World world, BlockPos pos) {
        world.breakBlock(pos, true);
    }

    public void breakWithoutDrop(World world, BlockPos pos) {
        world.breakBlock(pos, false);
    }

    public void spawnFish(ServerWorld world, BlockPos pos, Random random) {
        int i = random.nextBetweenExclusive(2, 6);
        for (int j = 1; j <= i; ++j) {
            CoelEntity coelEntity = ModEntities.COEL.create(world);
            if (coelEntity == null) continue;
            double d = (double)pos.getX() + this.getSpawnOffset(random);
            double e = (double)pos.getZ() + this.getSpawnOffset(random);
            int k = random.nextBetweenExclusive(1, 361);
            coelEntity.setBaby(true);
            coelEntity.refreshPositionAndAngles(d, (double)pos.getY() - 0.5, e, k, 0.0f);
            coelEntity.setPersistent();
            world.spawnEntity(coelEntity);
        }
    }

    public double getSpawnOffset(Random random) {
        double d = TadpoleEntity.WIDTH / 2.0f;
        return MathHelper.clamp(random.nextDouble(), d, 1.0 - d);
    }

    @VisibleForTesting
    public static void setHatchTimeRange(int min, int max) {
        minHatchTime = min;
        maxHatchTime = max;
    }

    @VisibleForTesting
    public static void resetHatchTimeRange() {
        minHatchTime = 3600;
        maxHatchTime = 12000;
    }
}
