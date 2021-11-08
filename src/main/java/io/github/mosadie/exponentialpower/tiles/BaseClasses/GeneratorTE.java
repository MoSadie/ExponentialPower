package io.github.mosadie.exponentialpower.tiles.BaseClasses;

import io.github.mosadie.exponentialpower.Config;
import io.github.mosadie.exponentialpower.energy.generator.ForgeEnergyConnection;
import io.github.mosadie.exponentialpower.items.EnderCell;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class GeneratorTE extends TileEntity implements ITickableTileEntity, IInventory, ICapabilityProvider {

	public enum GeneratorTier {
		REGULAR,
		ADVANCED,
	}
	public final GeneratorTier tier;

	public double currentOutput = 0;
	public double energy = 0;

	//public NonNullList<ItemStack> Inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	public ITextComponent customName;

	private ForgeEnergyConnection fec;
	private final LazyOptional<ForgeEnergyConnection> fecOptional = LazyOptional.of(() -> fec);

	private final ItemStackHandler itemStackHandler = createItemStackHandler();
	private final LazyOptional<ItemStackHandler> itemStackHandlerOptional = LazyOptional.of(() -> itemStackHandler);

	public GeneratorTE(GeneratorTier tier) {
		super(tier == GeneratorTier.ADVANCED ? Registration.ADV_ENDER_GENERATOR_TE.get() : Registration.ENDER_GENERATOR_TE.get());
		this.tier = tier;
		fec = new ForgeEnergyConnection(this, true, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction f) {
		if (cap == CapabilityEnergy.ENERGY) return fecOptional.cast();
		else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return itemStackHandlerOptional.cast();
		return super.getCapability(cap, f);
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);
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

		nbt.put("Items", itemStackHandler.serializeNBT());

		if (this.hasCustomName()) {
			nbt.putString("CustomName", this.getCustomName().getString());
		}

		return nbt;
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);

//		ListNBT list = nbt.getList("Items", 10);
//		for (int i = 0; i < list.size(); ++i) {
//			CompoundNBT stackTag = list.getCompound(i);
//			int slot = stackTag.getByte("Slot") & 255;
//			this.setInventorySlotContents(slot, ItemStack.read(stackTag));
//		}

		if (nbt.contains("Items", Constants.NBT.TAG_COMPOUND))
			itemStackHandler.deserializeNBT(nbt.getCompound("Items"));

		if (nbt.contains("CustomName", 8)) {
			this.setCustomName(new StringTextComponent(nbt.getString("CustomName")));
		}
	}

	@Override
	public void tick() {
		if (itemStackHandler.getStackInSlot(0).getItem() instanceof EnderCell) { //TODO check this check
			energy = currentOutput;
			currentOutput = calculateEnergy(itemStackHandler.getStackInSlot(0).getCount());
			if (currentOutput == 0) currentOutput = 1;
		}
		else {
			currentOutput = 0;
			energy = 0;
		}
		handleSendingEnergy();
	}

	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}

	@Override
	public int getSizeInventory() {
		return itemStackHandler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return itemStackHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).getCount() <= count) { //Inventory has less or same as amount asked for
				itemstack = this.getStackInSlot(index);
				//itemstack.shrink(1);
				this.setInventorySlotContents(index, ItemStack.EMPTY);
				this.markDirty();
				if (itemstack.getCount() > 0) return itemstack;
				else return ItemStack.EMPTY;
			} else { //More in slot then asked for
				itemstack = this.getStackInSlot(index).split(count);

				if (this.getStackInSlot(index).getCount() <= 0) {
					this.setInventorySlotContents(index, ItemStack.EMPTY);
				} else {
					//Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}


	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		itemStackHandler.setStackInSlot(index, stack);
		this.markDirty();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return ItemStack.EMPTY;
		ItemStack whatWasThere = itemStackHandler.getStackInSlot(index);
		itemStackHandler.setStackInSlot(index, ItemStack.EMPTY);
		this.markDirty();
		return whatWasThere;
	}

	@Override
	public int getInventoryStackLimit() {
		return getMaxStack();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return this.world.getTileEntity(getPos()) == this && getPos().add(0.5,0.5,0.5).withinDistance(player.getPosition(), 64);
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, ItemStack.EMPTY);
	}

	public ITextComponent getCustomName() {
		return customName;
	}

	public void setCustomName(ITextComponent customName) {
		this.customName = customName;
	}

	private void handleSendingEnergy() {
		if (!world.isRemote) {
			if (energy <= 0) {
				return;
			}
			for (Direction dir : Direction.values()) {
				BlockPos targetBlock = getPos().add(dir.getDirectionVec());

				TileEntity tile = world.getTileEntity(targetBlock);
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
	public boolean isEmpty() {
		return itemStackHandler.getStackInSlot(0).getCount() == 0;
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

	private ItemStackHandler createItemStackHandler() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return stack.getItem() == Registration.ENDER_CELL.get();
			}
		};
	}

	public ITextComponent getTitle() {
		if (hasCustomName()) return getCustomName();
		if (tier == GeneratorTE.GeneratorTier.ADVANCED)
			return new TranslationTextComponent(Registration.ADV_ENDER_GENERATOR.get().getTranslationKey());
		else
			return new TranslationTextComponent(Registration.ENDER_GENERATOR.get().getTranslationKey());
	}
}
