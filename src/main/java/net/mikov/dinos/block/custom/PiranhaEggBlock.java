package net.mikov.dinos.block.custom;

import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.PiranhaEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PiranhaEggBlock extends AbstractFishEggBlock implements Waterloggable {
    private static int minHatchTime = 3600;
    private static int maxHatchTime = 12000;

    public PiranhaEggBlock(Settings settings) {
        super(settings);
    }

    public void spawnFish(ServerWorld world, BlockPos pos, Random random) {
        int i = random.nextBetweenExclusive(2, 6);
        for (int j = 1; j <= i; ++j) {
            PiranhaEntity piranhaEntity = ModEntities.PIRANHA.create(world);
            if (piranhaEntity == null) continue;
            double d = (double)pos.getX() + this.getSpawnOffset(random);
            double e = (double)pos.getZ() + this.getSpawnOffset(random);
            int k = random.nextBetweenExclusive(1, 361);
            piranhaEntity.setBaby(true);
            piranhaEntity.refreshPositionAndAngles(d, (double)pos.getY() - 0.5, e, k, 0.0f);
            piranhaEntity.setPersistent();
            world.spawnEntity(piranhaEntity);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, PiranhaEggBlock.getHatchTime(world.getRandom()));
    }

    private static int getHatchTime(Random random) {
        return random.nextBetweenExclusive(minHatchTime, maxHatchTime);
    }

}
