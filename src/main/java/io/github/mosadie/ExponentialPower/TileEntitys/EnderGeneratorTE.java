package io.github.mosadie.ExponentialPower.TileEntitys;

import io.github.mosadie.ExponentialPower.Items.ItemManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import szewek.mcflux.U;
import szewek.mcflux.api.ex.EX;
import szewek.mcflux.api.ex.IEnergy;

public class EnderGeneratorTE extends TileEntity implements IEnergy, ITickable, IInventory {
	
	
	public long currentOutput = 0;
	public ItemStack[] Inventory;
	public String customName;
	
	public EnderGeneratorTE() {
		this.Inventory = new ItemStack[this.getSizeInventory()];
		this.Inventory[0] = new ItemStack(ItemManager.enderCell);
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
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }

	    if (nbt.hasKey("CustomName", 8)) {
	        this.setCustomName(nbt.getString("CustomName"));
	    }
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
		return currentOutput;
	}

	@Override
	public long getEnergy() {
		return currentOutput;
	}

	@Override
	public long getEnergyCapacity() {
		return currentOutput;
	}

	@Override
	public void update() {
		if (Inventory[0] == null) Inventory[0] = new ItemStack(ItemManager.enderCell);
		if (Inventory[0].getItem() != ItemManager.enderCell) return;
		currentOutput = (long) Math.pow(2,(Inventory[0].stackSize-1));
		for (int i = 0; i<6;i++) {
			EnumFacing f = EnumFacing.VALUES[i];
			TileEntity te = worldObj.getTileEntity(pos.offset(f, 1));
			if (te == null)
				continue;
			f = f.getOpposite();
			IEnergy ea = te.getCapability(EX.CAP_ENERGY, f);
			if (ea == null)
				continue;
			U.transferEnergy(this, ea, currentOutput);
		}
		
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
	        return null;
	    return this.Inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
	    if (this.getStackInSlot(index) != null) {
	        ItemStack itemstack;

	        if (this.getStackInSlot(index).stackSize <= count) { //Inventory has less or same as amount asked for
	            itemstack = this.getStackInSlot(index);
	            itemstack.stackSize -= 1;
	            this.setInventorySlotContents(index, new ItemStack(ItemManager.enderCell,1));
	            this.markDirty();
	            if (itemstack.stackSize > 0) return itemstack;
	            else return null;
	        } else { //More in slot then asked for
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).stackSize <= 0) {
	                this.setInventorySlotContents(index, new ItemStack(ItemManager.enderCell,1));
	            } else {
	                //Just to show that changes happened
	                this.setInventorySlotContents(index, this.getStackInSlot(index));
	            }

	            this.markDirty();
	            return itemstack;
	        }
	    } else {
	        return null;
	    }
	}


	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        stack.stackSize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.stackSize == 0)
	        stack = null;

	    this.Inventory[index] = stack;
	    this.markDirty();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
	        return null;
		ItemStack whatWasThere = this.Inventory[index];
		this.Inventory[index] = null;
	    this.markDirty();
	    return whatWasThere;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64; //2^(64-1) is max long
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		 return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
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
	        this.setInventorySlotContents(i, null);
	}
	
	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
}
