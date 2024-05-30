package net.mikov.dinos.entity.ai;

import net.mikov.dinos.entity.custom.AbstractMosaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class BondWithPlayerGoal
        extends Goal {
    private final AbstractMosaEntity mosa;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;

    public BondWithPlayerGoal(AbstractMosaEntity mosa, double speed) {
        this.mosa = mosa;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mosa.isTame() || !this.mosa.hasPassengers()) {
            return false;
        }
        Vec3d vec3d = NoPenaltyTargeting.find(this.mosa, 5, 4);
        if (vec3d == null) {
            return false;
        }
        this.targetX = vec3d.x;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z;
        return true;
    }

    @Override
    public void start() {
        this.mosa.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    @Override
    public boolean shouldContinue() {
        return !this.mosa.isTame() && !this.mosa.getNavigation().isIdle() && this.mosa.hasPassengers();
    }

    @Override
    public void tick() {
        if (!this.mosa.isTame() && this.mosa.getRandom().nextInt(this.getTickCount(50)) == 0) {
            Entity entity = this.mosa.getPassengerList().get(0);
            if (entity == null) {
                return;
            }
            if (entity instanceof PlayerEntity) {
                int i = this.mosa.getTemper();
                int j = this.mosa.getMaxTemper();
                if (j > 0 && this.mosa.getRandom().nextInt(j) < i) {
                    this.mosa.bondWithPlayer((PlayerEntity)entity);
                    return;
                }
                this.mosa.addTemper(5);
            }
            this.mosa.removeAllPassengers();
            this.mosa.playAngrySound();
            this.mosa.getWorld().sendEntityStatus(this.mosa, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }
}


