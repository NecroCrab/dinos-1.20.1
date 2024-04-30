package net.mikov.dinos.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class MountScreen
        extends HandledScreen<MountScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/horse.png");
    private final AbstractHorseEntity entity;
    private float mouseX;
    private float mouseY;

    public MountScreen(MountScreenHandler handler, PlayerInventory inventory, AbstractHorseEntity entity) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        TrexEntity trexEntity;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.entity instanceof TrexEntity && (trexEntity = (TrexEntity) this.entity).hasChest()) {
            context.drawTexture(TEXTURE, i + 79, j + 17, 0, this.backgroundHeight, trexEntity.getInventoryColumns() * 18, 54);
        }
        if (this.entity.canBeSaddled()) {
            context.drawTexture(TEXTURE, i + 7, j + 35 - 18, 18, this.backgroundHeight + 54, 18, 18);
        }
        InventoryScreen.drawEntity(context, i + 51, j + 60, 17, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, (LivingEntity)this.entity);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}


