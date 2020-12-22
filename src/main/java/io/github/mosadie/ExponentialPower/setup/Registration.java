package io.github.mosadie.ExponentialPower.setup;

import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.GUIContainer.ContainerEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderStorageTE;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.GeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.StorageTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import io.github.mosadie.ExponentialPower.blocks.*;
import io.github.mosadie.ExponentialPower.items.EnderCell;
import io.github.mosadie.ExponentialPower.items.EnderStorageItem;
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

import static io.github.mosadie.ExponentialPower.items.ItemManager.ITEM_GROUP;

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

    public static final RegistryObject<Block> ENDER_GENERATOR = BLOCKS.register("endergenerator", () -> new EnderGenerator());
    public static final RegistryObject<Block> ADV_ENDER_GENERATOR = BLOCKS.register("advancedendergenerator", () -> new AdvancedEnderGenerator());

    public static final RegistryObject<Block> ENDER_STORAGE = BLOCKS.register("enderstorage", () -> new EnderStorage());
    public static final RegistryObject<Block> ADV_ENDER_STORAGE = BLOCKS.register("advancedenderstorage", () -> new AdvancedEnderStorage());

    // Items

    public static final RegistryObject<Item> ENDER_CELL = ITEMS.register("endercell", EnderCell::new);

    public static final RegistryObject<Item> ENDER_GENERATOR_ITEM = ITEMS.register("endergenerator", () -> new BlockItem(ENDER_GENERATOR.get(), new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ADV_ENDER_GENERATOR_ITEM = ITEMS.register("advancedendergenerator", () -> new BlockItem(ADV_ENDER_GENERATOR.get(), new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ENDER_STORAGE_ITEM = ITEMS.register("enderstorage", () -> new EnderStorageItem(ENDER_STORAGE.get(), StorageTE.StorageTier.REGULAR));
    public static final RegistryObject<Item> ADV_ENDER_STORAGE_ITEM = ITEMS.register("advancedenderstorage", () -> new EnderStorageItem(ADV_ENDER_STORAGE.get(), StorageTE.StorageTier.ADVANCED));

    // Tile Entities

    public static final RegistryObject<TileEntityType<EnderGeneratorTE>> ENDER_GENERATOR_TE = TILES.register("endergenerator", () -> TileEntityType.Builder.create(EnderGeneratorTE::new, ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<AdvancedEnderGeneratorTE>> ADV_ENDER_GENERATOR_TE = TILES.register("advancedendergenerator", () -> TileEntityType.Builder.create(AdvancedEnderGeneratorTE::new, ADV_ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<EnderStorageTE>> ENDER_STORAGE_TE = TILES.register("enderstorage", () -> TileEntityType.Builder.create(EnderStorageTE::new, ENDER_STORAGE.get()).build(null));
    public static final RegistryObject<TileEntityType<AdvancedEnderStorageTE>> ADV_ENDER_STORAGE_TE = TILES.register("advancedenderstorage", () -> TileEntityType.Builder.create(AdvancedEnderStorageTE::new, ADV_ENDER_STORAGE.get()).build(null));

    // Containers

    public static final RegistryObject<ContainerType<ContainerEnderGeneratorTE>> ENDER_GENERATOR_CONTAINER = CONTAINERS.register("endergenerator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        GeneratorTE te = (GeneratorTE) world.getTileEntity(pos);
        return new ContainerEnderGeneratorTE(windowId, inv, te);
    }));
}
