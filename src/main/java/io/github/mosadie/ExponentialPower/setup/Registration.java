package io.github.mosadie.exponentialpower.setup;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.blocks.AdvancedEnderGenerator;
import io.github.mosadie.exponentialpower.blocks.AdvancedEnderStorage;
import io.github.mosadie.exponentialpower.blocks.EnderGenerator;
import io.github.mosadie.exponentialpower.blocks.EnderStorage;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorBE;
import io.github.mosadie.exponentialpower.items.EnderCell;
import io.github.mosadie.exponentialpower.items.EnderStorageItem;
import io.github.mosadie.exponentialpower.entities.AdvancedEnderGeneratorBE;
import io.github.mosadie.exponentialpower.entities.AdvancedEnderStorageBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.StorageBE;
import io.github.mosadie.exponentialpower.entities.EnderGeneratorBE;
import io.github.mosadie.exponentialpower.entities.EnderStorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.mosadie.exponentialpower.items.ItemManager.ITEM_GROUP;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExponentialPower.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExponentialPower.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExponentialPower.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExponentialPower.MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Blocks

    public static final RegistryObject<Block> ENDER_GENERATOR = BLOCKS.register("ender_generator", EnderGenerator::new);
    public static final RegistryObject<Block> ADV_ENDER_GENERATOR = BLOCKS.register("advanced_ender_generator", AdvancedEnderGenerator::new);

    public static final RegistryObject<Block> ENDER_STORAGE = BLOCKS.register("ender_storage", EnderStorage::new);
    public static final RegistryObject<Block> ADV_ENDER_STORAGE = BLOCKS.register("advanced_ender_storage", AdvancedEnderStorage::new);

    // Items

    public static final RegistryObject<Item> ENDER_CELL = ITEMS.register("ender_cell", EnderCell::new);

    public static final RegistryObject<Item> ENDER_GENERATOR_ITEM = ITEMS.register("ender_generator", () -> new BlockItem(ENDER_GENERATOR.get(), new Item.Properties().fireResistant().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> ADV_ENDER_GENERATOR_ITEM = ITEMS.register("advanced_ender_generator", () -> new BlockItem(ADV_ENDER_GENERATOR.get(), new Item.Properties().fireResistant().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> ENDER_STORAGE_ITEM = ITEMS.register("ender_storage", () -> new EnderStorageItem(ENDER_STORAGE.get(), StorageBE.StorageTier.REGULAR));
    public static final RegistryObject<Item> ADV_ENDER_STORAGE_ITEM = ITEMS.register("advanced_ender_storage", () -> new EnderStorageItem(ADV_ENDER_STORAGE.get(), StorageBE.StorageTier.ADVANCED));

    // Tile Entities

    public static final RegistryObject<BlockEntityType<EnderGeneratorBE>> ENDER_GENERATOR_BE = BLOCK_ENTITIES.register("ender_generator", () -> BlockEntityType.Builder.of(EnderGeneratorBE::new, ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<AdvancedEnderGeneratorBE>> ADV_ENDER_GENERATOR_BE = BLOCK_ENTITIES.register("advanced_ender_generator", () -> BlockEntityType.Builder.of(AdvancedEnderGeneratorBE::new, ADV_ENDER_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnderStorageBE>> ENDER_STORAGE_BE = BLOCK_ENTITIES.register("ender_storage", () -> BlockEntityType.Builder.of(EnderStorageBE::new, ENDER_STORAGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<AdvancedEnderStorageBE>> ADV_ENDER_STORAGE_BE = BLOCK_ENTITIES.register("advanced_ender_storage", () -> BlockEntityType.Builder.of(AdvancedEnderStorageBE::new, ADV_ENDER_STORAGE.get()).build(null));

    // Containers

    public static final RegistryObject<MenuType<ContainerEnderGeneratorBE>> ENDER_GENERATOR_CONTAINER = CONTAINERS.register("ender_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getLevel();
        GeneratorBE te = (GeneratorBE) level.getBlockEntity(pos);
        return new ContainerEnderGeneratorBE(windowId, inv, te);
    }));
}
