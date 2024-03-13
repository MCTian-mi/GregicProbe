package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import vfyjxf.gregicprobe.config.GregicProbeConfig;

import java.util.ArrayList;
import java.util.List;

public class RecipeFluidOutputInfoProvider extends CapabilityInfoProvider<IWorkable> {
    public RecipeFluidOutputInfoProvider() {

    }

    @Override
    protected Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        if (capability.getProgress() > 0 && capability instanceof AbstractRecipeLogic) {
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            List<FluidStack> fluidOutputs = new ArrayList<>(ObfuscationReflectionHelper.getPrivateValue(AbstractRecipeLogic.class, (AbstractRecipeLogic) capability, "fluidOutputs"));
            if (!fluidOutputs.isEmpty()) {
                horizontalPane.text(TextStyleClass.INFO + "{*gregicprobe.top.fluid.outputs*} ");;
                for (FluidStack fluidOutput : fluidOutputs) {
                    if (fluidOutput != null && fluidOutput.amount > 0) {
                        IProbeInfo horizontal = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                        if (entityPlayer.isSneaking() ^ GregicProbeConfig.InvertOutputDisplayStyle) {
                            horizontal.text(TextStyleClass.INFO + fluidOutput.getLocalizedName() + " * " + FormatFluidAmount(fluidOutput));
                        } else {
                            horizontal.icon(fluidOutput.getFluid().getStill(), -1, -1, 16, 16, probeInfo.defaultIconStyle().width(20));
                            if (GregicProbeConfig.displayFluidName) {
                                horizontal.text(TextStyleClass.INFO + fluidOutput.getLocalizedName() + ' ');
                            }
                            horizontal.text(FormatFluidAmount(fluidOutput));
                        }
                    }
                }
            }

        }
    }

    private String FormatFluidAmount(FluidStack fluidOutput) {
        if (fluidOutput.amount >= 1000) {
            return (fluidOutput.amount / 1000) + "B";
        } else {
            return fluidOutput.amount + "mB";
        }
    }
    @Override
    public String getID() {
        return "gregicprobe:recipe_info_fluid_output";
    }
}
