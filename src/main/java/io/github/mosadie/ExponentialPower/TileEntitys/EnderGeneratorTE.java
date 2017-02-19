package io.github.mosadie.ExponentialPower.TileEntitys;

import javax.annotation.Nullable;

import io.github.mosadie.ExponentialPower.Items.ItemManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.asm.transformers.ItemStackTransformer;
import szewek.mcflux.api.MCFluxAPI;
import szewek.mcflux.api.ex.*;

public class EnderGeneratorTE extends TileEntity implements IEnergy, ITickable, IInventory, ICapabilityProvider {


	public long currentOutput = 0;
	public long energy = 0;
	public NonNullList<ItemStack> Inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	public String customName;

	public EnderGeneratorTE() {
	}
	
	@Override public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing f) {
		return cap == EX.CAP_ENERGY;
	}

	@SuppressWarnings("unchecked")
	@Override public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing f) {
		return cap == EX.CAP_ENERGY ? (T) this : null;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);

		if (this.hasCustomName()) {
			nbt.setString("CustomName", this.getCustomName());
		}

		return nbt;
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, new ItemStack(stackTag));
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}
	}

	@Override
	public void update() {
		if (Inventory != null) {
			if (Inventory.get(0).getItem() == ItemManager.enderCell) {
				energy = currentOutput;
				currentOutput = longPow(2L,Math.round((63*Inventory.get(0).getCount())/((double)64)))-1L;
			}
		}
		handleSendingEnergy();
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.exponentialpower_endergenerator_tile_entity";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return ItemStack.EMPTY;
		return this.Inventory.get(0);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).getCount() <= count) { //Inventory has less or same as amount asked for
				itemstack = this.getStackInSlot(index);
				//itemstack.shrink(1);
				this.setInventorySlotContents(index, ItemStack.EMPTY);//new ItemStack(ItemManager.enderCell,1));
				this.markDirty();
				if (itemstack.getCount() > 0) return itemstack;
				else return ItemStack.EMPTY;
			} else { //More in slot then asked for
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).getCount() <= 0) {
					this.setInventorySlotContents(index, ItemStack.EMPTY);//new ItemStack(ItemManager.enderCell,1));
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
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.getCount() > this.getInventoryStackLimit())
			stack.setCount(this.getInventoryStackLimit());

		if (stack != null && stack.getCount() == 0)
			stack = ItemStack.EMPTY;

		this.Inventory.set(0, stack);
		this.markDirty();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return ItemStack.EMPTY;
		ItemStack whatWasThere = this.Inventory.get(0);
		this.Inventory.set(0,ItemStack.EMPTY);
		this.markDirty();
		return whatWasThere;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64; //2^(63)-1 is max long
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, ItemStack.EMPTY);
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	private void handleSendingEnergy() {
		if (!world.isRemote) {
			if (energy <= 0) {
				return;
			}
			for (EnumFacing dir : EnumFacing.values()) {
				BlockPos targetBlock = getPos().add(dir.getDirectionVec());

				TileEntity tile = world.getTileEntity(targetBlock);
				if (tile != null) {
					IEnergy receiver = MCFluxAPI.getEnergySafely(tile, dir.getOpposite());
					if (receiver != null) {
						if (receiver.canInputEnergy()) {
							long tosend = outputEnergy(this.currentOutput, true);
							long used = receiver.inputEnergy(tosend, false);
							outputEnergy(used, false);
							if (used > 0) {
								this.markDirty();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return Inventory.get(0).getCount() == 0;
	}

	@Override
	public boolean canInputEnergy() {
		return false;
	}

	@Override
	public boolean canOutputEnergy() {
		return true;
	}

	@Override
	public long inputEnergy(long amount, boolean sim) {
		return 0;
	}

	@Override
	public long outputEnergy(long amount, boolean sim) {
		if (energy <= amount) {
			long temp = energy;
			if (!sim) energy = 0;
			return temp;
		}
		else {
			long temp = energy - amount;
			if (!sim) energy -= amount;
			return temp;
		}
	}

	@Override
	public long getEnergy() {
		return energy;
	}

	@Override
	public long getEnergyCapacity() {
		return currentOutput;
	}
	
	private long longPow (long a, long b) {
		long temp = 1;
		if (b == 0) return 1L;
		if (b == 1) return a;
		for (long i = 0; i<b ;i++) {
			temp = temp*a;
		}
		return temp;
	}
}
