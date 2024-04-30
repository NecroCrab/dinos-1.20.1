package net.mikov.dinos.gui;

import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MountScreenHandler
        extends ScreenHandler {
    private final Inventory inventory;
    private final AbstractHorseEntity entity;

    public MountScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, final AbstractHorseEntity entity) {
        super(null, syncId);
        int l;
        int k;
        this.inventory = inventory;
        this.entity = entity;
        int i = 3;
        inventory.onOpen(playerInventory.player);
        int j = -18;
        this.addSlot(new Slot(inventory, 0, 8, 18){

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.SADDLE) && !this.hasStack() && entity.canBeSaddled();
            }

            @Override
            public boolean isEnabled() {
                return entity.canBeSaddled();
            }
        });
        this.addSlot(new Slot(inventory, 1, 8, 36){

            @Override
            public boolean canInsert(ItemStack stack) {
                return entity.isHorseArmor(stack);
            }

            @Override
            public boolean isEnabled() {
                return entity.hasArmorSlot();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        if (this.hasChest(entity)) {
            for (k = 0; k < 3; ++k) {
                for (l = 0; l < ((TrexEntity)entity).getInventoryColumns(); ++l) {
                    this.addSlot(new Slot(inventory, 2 + l + k * ((TrexEntity)entity).getInventoryColumns(), 80 + l * 18, 18 + k * 18));
                }
            }
        }
        for (k = 0; k < 3; ++k) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 102 + k * 18 + -18));
            }
        }
        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !this.entity.areInventoriesDifferent(this.inventory) && this.inventory.canPlayerUse(player) && this.entity.isAlive() && this.entity.distanceTo(player) < 8.0f;
    }

    private boolean hasChest(AbstractHorseEntity trex) {
        return trex instanceof TrexEntity && ((TrexEntity)trex).hasChest();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            int i = this.inventory.size();
            if (slot < i) {
                if (!this.insertItem(itemStack2, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemStack2) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(itemStack2, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(itemStack2)) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.insertItem(itemStack2, 2, i, false)) {
                int k;
                int j = i;
                int l = k = j + 27;
                int m = l + 9;
                if (slot >= l && slot < m ? !this.insertItem(itemStack2, j, k, false) : (slot >= j && slot < k ? !this.insertItem(itemStack2, l, m, false) : !this.insertItem(itemStack2, l, k, false))) {
                    return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}


