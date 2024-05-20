package net.mikov.dinos.block.custom;

import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.system.SharedLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExtractorBlock extends Block {

    public ExtractorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.SILT_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            //spawn items from loot table??
            ItemScatterer.spawn(world, pos, DefaultedList
                    .ofSize(1, new ItemStack(Items.SAND)));
            player.addExperience(1);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.FOSSIL_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            DefaultedList<ItemStack> options = DefaultedList.of();
            options.add(new ItemStack(Items.RAW_COPPER));
            options.add(new ItemStack(Blocks.COAL_ORE.asItem()));
            options.add(new ItemStack(Items.RAW_IRON));
            options.add(new ItemStack(Blocks.SAND.asItem()));
            ItemScatterer.spawn(world, pos, options);
            player.addExperience(4);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.DEEPSLATE_FOSSIL_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            LootTable lootTable = Objects.requireNonNull(world.getServer()).getLootManager().getLootTable(LootTables.DESERT_PYRAMID_ARCHAEOLOGY);
            DefaultedList<ItemStack> lootStack = DefaultedList.of();
            lootTable.generateLoot(new LootContextParameterSet.Builder((ServerWorld) world).build(LootContextTypes.EMPTY), lootStack::add);
            ItemScatterer.spawn(world, pos, lootStack);
            player.addExperience(5);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        if (player.getMainHandStack().isEmpty()) {
            return ActionResult.PASS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
