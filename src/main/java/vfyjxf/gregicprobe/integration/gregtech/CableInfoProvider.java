package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.GTValues;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vfyjxf.gregicprobe.GregicProbe;

public class CableInfoProvider implements IProbeInfoProvider {

    public CableInfoProvider() {
    }

    @Override
    public String getID() {
        return GregicProbe.MODID + ":cable_info";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world,
                             IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock() instanceof BlockCable cable) {
            var cableTile = (TileEntityCable) cable.getPipeTileEntity(world, iProbeHitData.getPos());
            if (cableTile != null) {
                String voltage = TextFormattingUtil.formatNumbers(cableTile.getAverageVoltage());
                String amperage = TextFormattingUtil.formatNumbers(cableTile.getAverageAmperage());
                String tier = GTValues.VNF[GTUtility.getTierByVoltage(cableTile.getCurrentMaxVoltage())];
                iProbeInfo.text(I18n.format("gregicprobe.top.pipe.energy", voltage, tier, amperage));
            }
        }
    }
}
