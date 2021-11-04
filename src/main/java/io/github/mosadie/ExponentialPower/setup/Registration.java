package io.github.mosadie.exponentialpower.setup;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.AdvancedEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.AdvancedEnderStorageTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
import io.github.mosadie.exponentialpower.tiles.EnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.EnderStorageTE;
import io.github.mosadie.exponentialpower.blocks.*;
import io.github.mosadie.exponentialpower.items.EnderCell;
import io.github.mosadie.exponentialpower.items.EnderStorageItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static io.github.mosadie.exponentialpower.items.ItemManager.ITEM_GROUP;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExponentialPower.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExponentialPower.MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ExponentialPower.MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExponentialPower.MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Blocks

    public static final RegistryObject<Block> ENDER_GENERATOR = BLOCKS.register("ender_generator", () -> new EnderGenerator());
    public static final RegistryObject<Block> ADV_ENDER_GENERATOR = BLOCKS.register("advanced_ender_generator", () -> new AdvancedEnderGenerator());

    public static final RegistryObject<Block> ENDER_STORAGE = BLOCKS.register("ender_storage", () -> new EnderStorage());
    public static final RegistryObject<Block> ADV_ENDER_STORAGE = BLOCKS.register("advanced_ender_storage", () -> new AdvancedEnderStorage());

    // Items

    public static final RegistryObject<Item> ENDER_CELL = ITEMS.register("ender_cell", EnderCell::new);

    public static final RegistryObject<Item> ENDER_GENERATOR_ITEM = ITEMS.register("ender_generator", () -> new BlockItem(ENDER_GENERATOR.get(), new Item.Properties().isImmuneToFire().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ADV_ENDER_GENERATOR_ITEM = ITEMS.register("advanced_ender_generator", () -> new BlockItem(ADV_ENDER_GENERATOR.get(), new Item.Properties().isImmuneToFire().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ENDER_STORAGE_ITEM = ITEMS.register("ender_storage", () -> new EnderStorageItem(ENDER_STORAGE.get(), StorageTE.StorageTier.REGULAR));
    public static final RegistryObject<Item> ADV_ENDER_STORAGE_ITEM = ITEMS.register("advanced_ender_storage", () -> new EnderStorageItem(ADV_ENDER_STORAGE.get(), StorageTE.StorageTier.ADVANCED));

    // Tile Entities

    public static final RegistryObject<TileEntityType<EnderGeneratorTE>> ENDER_GENERATOR_TE = TILES.register("ender_generator", () -> TileEntityType.Builder.create(EnderGeneratorTE::new, ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<AdvancedEnderGeneratorTE>> ADV_ENDER_GENERATOR_TE = TILES.register("advanced_ender_generator", () -> TileEntityType.Builder.create(AdvancedEnderGeneratorTE::new, ADV_ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<EnderStorageTE>> ENDER_STORAGE_TE = TILES.register("ender_storage", () -> TileEntityType.Builder.create(EnderStorageTE::new, ENDER_STORAGE.get()).build(null));
    public static final RegistryObject<TileEntityType<AdvancedEnderStorageTE>> ADV_ENDER_STORAGE_TE = TILES.register("advanced_ender_storage", () -> TileEntityType.Builder.create(AdvancedEnderStorageTE::new, ADV_ENDER_STORAGE.get()).build(null));

    // Containers

    public static final RegistryObject<ContainerType<ContainerEnderGeneratorTE>> ENDER_GENERATOR_CONTAINER = CONTAINERS.register("ender_generator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        GeneratorTE te = (GeneratorTE) world.getTileEntity(pos);
        return new ContainerEnderGeneratorTE(windowId, inv, te);
    }));
}
