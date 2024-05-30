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
import java.util.concurrent.ThreadLocalRandom;

public class ExtractorBlock extends Block {

    public ExtractorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.SILT_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            DefaultedList<ItemStack> options = DefaultedList.of();
            options.add(new ItemStack(Items.RAW_COPPER));
            options.add(new ItemStack(Blocks.COAL_ORE.asItem()));
            options.add(new ItemStack(Items.COAL));
            options.add(new ItemStack(Items.FLINT));
            options.add(new ItemStack(Items.RAW_IRON));
            options.add(new ItemStack(Items.IRON_NUGGET));
            options.add(new ItemStack(Blocks.SAND.asItem()));
            options.add(new ItemStack(Items.RAW_GOLD));
            options.add(new ItemStack(Items.GOLD_NUGGET));
            options.add(new ItemStack(Items.SCUTE));
            options.add(new ItemStack(Blocks.CLAY.asItem()));
            options.add(new ItemStack(Items.BRICK));
            options.add(new ItemStack(Blocks.GRANITE.asItem()));
            options.add(new ItemStack(Blocks.ANDESITE.asItem()));
            options.add(new ItemStack(Blocks.DIORITE.asItem()));
            options.add(new ItemStack(Blocks.STONE.asItem()));
            options.add(new ItemStack(Items.EMERALD));
            options.add(new ItemStack(Items.LAPIS_LAZULI));
            options.add(new ItemStack(Items.REDSTONE));
            options.add(new ItemStack(Items.PRISMARINE_SHARD));
            options.add(new ItemStack(Items.AMETHYST_SHARD));
            options.add(new ItemStack(Blocks.BUDDING_AMETHYST.asItem()));
            options.add(new ItemStack(Blocks.CALCITE.asItem()));
            options.add(new ItemStack(Items.DIAMOND));
            options.add(new ItemStack(Blocks.PETRIFIED_OAK_SLAB.asItem()));
            options.add(new ItemStack(Blocks.DEAD_BUSH.asItem()));
            options.add(new ItemStack(Blocks.DEAD_BRAIN_CORAL_BLOCK.asItem()));
            options.add(new ItemStack(Blocks.DEAD_BUBBLE_CORAL_BLOCK.asItem()));
            options.add(new ItemStack(Blocks.DEAD_FIRE_CORAL_BLOCK.asItem()));
            options.add(new ItemStack(Blocks.DEAD_TUBE_CORAL_BLOCK.asItem()));
            options.add(new ItemStack(Blocks.DEAD_HORN_CORAL_BLOCK.asItem()));
            options.add(new ItemStack(Items.NAUTILUS_SHELL));
            options.add(new ItemStack(Blocks.BONE_BLOCK.asItem()));
            options.add(new ItemStack(Items.BONE));
            options.add(new ItemStack(Items.GUNPOWDER));
            int choice = ThreadLocalRandom.current().nextInt(options.size());
            DefaultedList<ItemStack> selected = DefaultedList.ofSize(1, options.get(choice));
            ItemScatterer.spawn(world, pos, selected);
            player.addExperience(1);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.FOSSIL_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            DefaultedList<ItemStack> options = DefaultedList.of();
            options.add(new ItemStack(Blocks.PETRIFIED_OAK_SLAB.asItem()));
            options.add(new ItemStack(ModBlocks.TRILOBITE_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.PLANT_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.AMBER_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.TREX_SKULL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.TRIKE_SKULL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.RAPTOR_CLAW_BLOCK.asItem()));
            int choice = ThreadLocalRandom.current().nextInt(options.size());
            DefaultedList<ItemStack> selected = DefaultedList.ofSize(1, options.get(choice));
            ItemScatterer.spawn(world, pos, selected);
            player.addExperience(4);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        if (player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.DEEPSLATE_FOSSIL_BLOCK))) {
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            DefaultedList<ItemStack> options = DefaultedList.of();
            options.add(new ItemStack(Blocks.PETRIFIED_OAK_SLAB.asItem()));
            options.add(new ItemStack(ModBlocks.TRILOBITE_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.PLANT_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.AMBER_FOSSIL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.TREX_SKULL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.TRIKE_SKULL_BLOCK.asItem()));
            options.add(new ItemStack(ModBlocks.RAPTOR_CLAW_BLOCK.asItem()));
            int choice = ThreadLocalRandom.current().nextInt(options.size());
            DefaultedList<ItemStack> selected = DefaultedList.ofSize(1, options.get(choice));
            ItemScatterer.spawn(world, pos, selected);
            /*if (!world.isClient()) {
                LootTable lootTable = Objects.requireNonNull(world.getServer()).getLootManager().getLootTable(LootTables.DESERT_PYRAMID_ARCHAEOLOGY);
                DefaultedList<ItemStack> lootStack = DefaultedList.of();
                lootTable.generateLoot(new LootContextParameterSet.Builder((ServerWorld) world).build(LootContextTypes.EMPTY), lootStack::add);
                ItemScatterer.spawn(world, pos, lootStack);
            }*/
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
