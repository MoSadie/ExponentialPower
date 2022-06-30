package io.github.mosadie.exponentialpower.tiles.BaseClasses;

import io.github.mosadie.exponentialpower.Config;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.energy.generator.ForgeEnergyConnection;
import io.github.mosadie.exponentialpower.items.EnderCell;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class GeneratorTE extends BaseContainerBlockEntity implements ICapabilityProvider {


	public enum GeneratorTier {
		REGULAR,
		ADVANCED,
	}
	public final GeneratorTier tier;

	public double currentOutput = 0;
	public double energy = 0;

	public NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	public Component customName;

	private ForgeEnergyConnection fec;
	private final LazyOptional<ForgeEnergyConnection> fecOptional = LazyOptional.of(() -> fec);


	public GeneratorTE(GeneratorTier tier, BlockPos pos, BlockState state) {
		super(tier == GeneratorTier.ADVANCED ? Registration.ADV_ENDER_GENERATOR_TE.get() : Registration.ENDER_GENERATOR_TE.get(), pos, state);
		this.tier = tier;
		fec = new ForgeEnergyConnection(this, true, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction f) {
		if (cap == CapabilityEnergy.ENERGY) return fecOptional.cast();
		return super.getCapability(cap, f);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		//super.writeToNBT recreated and modified here.
//		ResourceLocation resourcelocation = new ResourceLocation(ExponentialPower.MODID + (tier == GeneratorTier.REGULAR ? ":endergenerator_tile_entity" : ":advancedendergenerator_tile_entity"));
//		nbt.setString("id", resourcelocation.toString());
//		nbt.setInteger("x", this.pos.getX());
//		nbt.setInteger("y", this.pos.getY());
//		nbt.setInteger("z", this.pos.getZ());
//		nbt.setTag("ForgeData", this.getTileData());

//		ListNBT list = new ListNBT();
//		for (int i = 0; i < this.getSizeInventory(); ++i) {
//			if (this.getStackInSlot(i) != null) {
//				CompoundNBT stackTag = new CompoundNBT();
//				stackTag.putByte("Slot", (byte) i);
//				this.getStackInSlot(i).write(stackTag);
//				list.add(stackTag);
//			}
//		}
//		nbt.put("Items", list);

		ListTag nbtTagList = new ListTag();
		int slotsSize = getContainerSize();
		for (int i = 0; i < slotsSize; i++) {
			if (!getItem(i).isEmpty())  {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putInt("Slot", i);
				getItem(i).save(itemTag);
				nbtTagList.add(itemTag);
			}
		}
		CompoundTag invNbt = new CompoundTag();
		invNbt.put("Items", nbtTagList);
		nbt.put("Items", invNbt);


//		nbt.put("Items", .serializeNBT());

//		if (this.hasCustomName()) {
//			nbt.putString("CustomName", this.getCustomName().getString());
//		}
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

//		ListNBT list = nbt.getList("Items", 10);
//		for (int i = 0; i < list.size(); ++i) {
//			CompoundNBT stackTag = list.getCompound(i);
//			int slot = stackTag.getByte("Slot") & 255;
//			this.setInventorySlotContents(slot, ItemStack.read(stackTag));
//		}

		if (nbt.contains("Items", Tag.TAG_COMPOUND)) {
			ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
			for (int i = 0; i < tagList.size(); i++) {
				CompoundTag itemTags = tagList.getCompound(i);
				int slot = itemTags.getInt("Slot");

				if (slot >= 0 && slot < getContainerSize()) {
					setItem(slot, ItemStack.of(itemTags));
				}
			}
		}

//		if (nbt.contains("CustomName", 8)) {
//			this.setCustomName(new TextComponent(nbt.getString("CustomName")));
//		}
	}


	public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (t instanceof GeneratorTE generator) {
			if (generator.getItem(0).getItem() instanceof EnderCell) { //TODO check this check
				generator.energy = generator.currentOutput;
				generator.currentOutput = generator.calculateEnergy(generator.getItem(0).getCount());
				if (generator.currentOutput == 0) generator.currentOutput = 1;
			} else {
				generator.currentOutput = 0;
				generator.energy = 0;
			}
			generator.handleSendingEnergy();
		}
	}

	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}

//	@Override
//	public int getSizeInventory() {
//		return itemStackHandler.getSlots();
//	}

	@Override
	public void clearContent() {
		for (int i = 0; i < this.getContainerSize(); i++)
			this.setItem(i, ItemStack.EMPTY);
	}

	@Override
	protected Component getDefaultName() {
		if (tier == GeneratorTE.GeneratorTier.ADVANCED)
			return new TranslatableComponent(Registration.ADV_ENDER_GENERATOR.get().getDescriptionId());
		else
			return new TranslatableComponent(Registration.ENDER_GENERATOR.get().getDescriptionId());
	}

	@Override
	protected AbstractContainerMenu createMenu(int windowId, Inventory playerInv) {
		return new ContainerEnderGeneratorTE(windowId, playerInv, this);
	}

	public void setCustomName(Component customName) {
		this.customName = customName;
	}

	private void handleSendingEnergy() {
		if (!level.isClientSide) {
			if (energy <= 0) {
				return;
			}
			for (Direction dir : Direction.values()) {
				BlockPos targetBlock = getBlockPos().relative(dir);

				BlockEntity tile = level.getBlockEntity(targetBlock);
				if (tile != null) {
					if (tile instanceof StorageTE) {
						StorageTE storage = (StorageTE) tile;
						energy -= storage.acceptEnergy(energy);
					}
					else {
						//if (tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())) {
						tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite()).resolve().ifPresent((cap) -> {
							if (cap.canReceive()) {
								energy -= cap.receiveEnergy((int) (energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : energy), false);
							}
						});
					}
				}
			}
		}
	}

	@Override
	public int getContainerSize() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return getItem(0).getCount() == 0;
	}

	@Override
	public ItemStack getItem(int slot) {
		return slot < getContainerSize() && slot > 0 ? inventory.get(slot) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItem(int slot, int count) {
		if (slot < getContainerSize() && slot > 0) {
			ItemStack stack = getItem(slot);

			if (count > stack.getCount()) {
				setItem(slot, ItemStack.EMPTY);
				return stack;
			} else {
				ItemStack newStack = stack.copy();
				newStack.setCount(count);
				stack.setCount(stack.getCount() - count);
				return newStack;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		ItemStack stack = getItem(slot);
		setItem(slot, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		if (slot < getContainerSize() && slot > 0) {
			inventory.set(slot, item);
		}
	}

	@Override
	public boolean stillValid(Player p_18946_) {
		return true;
	}

	public double getBase() {
		switch (tier) {
		case REGULAR:
			return Config.ENDER_GENERATOR_BASE.get();

		case ADVANCED:
			return Config.ADV_ENDER_GENERATOR_BASE.get();

		default:
			return 2.0;
		}
	}

	public int getMaxStack() {
		switch (tier) {
		case REGULAR:
			return Config.ENDER_GENERATOR_MAX_STACK.get();

		case ADVANCED:
			return Config.ADV_ENDER_GENERATOR_MAX_STACK.get();

		default:
			return 64;
		}
	}

	public double calculateEnergy(int itemStackCount) {
		switch (tier) {
		case REGULAR:
			return longPow(getBase(),(63*((itemStackCount)/(double)getMaxStack())))-1L;

		case ADVANCED:
			return Math.pow(getBase(),1023*(itemStackCount/(double)getMaxStack())) * (2-Math.pow(2,-52));

		default:
			return 1.0;
		}
	}

	private long longPow (double a, double b) {
		if (b == 0) return 1L;
		if (b == 1) return (long)a;
		return (long)Math.pow(a, b);
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return stack.getItem() == Registration.ENDER_CELL.get();
	}


	public Component getTitle() {
		if (hasCustomName()) return getCustomName();
		return getDefaultName();
	}
}
