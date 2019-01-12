package jackyy.dimensionaledibles.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileDimensionCake extends TileEntity {

    private int dimensionID;
    private String cakeName;

    public TileDimensionCake() {
        this(0, "Overworld");
    }

    public TileDimensionCake(int dimID, String name) {
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
    
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
	return oldState.getBlock() != newState.getBlock();
    }

}
