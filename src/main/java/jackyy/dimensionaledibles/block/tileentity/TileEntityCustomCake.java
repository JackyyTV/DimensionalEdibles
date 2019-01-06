package jackyy.dimensionaledibles.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCustomCake extends TileEntity {
    private int dimensionID;
    private String cakeName;

    public TileEntityCustomCake() {
	this(0, "Overworld Cake");
    }

    public TileEntityCustomCake(int dimID, String name) {
	this.dimensionID = dimID;
	this.cakeName = name;
    }

    public int getDimensionID() {
	return this.dimensionID;
    }

    public void setDimensionID(int dimID) {
	this.dimensionID = dimID;
    }

    public String getCakeName() {
	return this.cakeName;
    }

    public void setCakeName(String name) {
	this.cakeName = name;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	super.writeToNBT(nbt);
	nbt.setInteger("dimID", this.getDimensionID());
	nbt.setString("cakeName", this.getCakeName());
	return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
	super.readFromNBT(nbt);
	this.dimensionID = nbt.getInteger("dimID");
	this.cakeName = nbt.getString("cakeName");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
	return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
	return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	readFromNBT(pkt.getNbtCompound());
    }

}
