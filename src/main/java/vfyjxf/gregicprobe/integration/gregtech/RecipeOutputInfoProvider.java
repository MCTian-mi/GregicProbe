package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.elements.ElementItemStack;
import mcjty.theoneprobe.apiimpl.elements.ElementText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import vfyjxf.gregicprobe.config.GregicProbeConfig;
import vfyjxf.gregicprobe.elements.ElementFluidStack;
import vfyjxf.gregicprobe.elements.ElementRightAlignedText;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RecipeOutputInfoProvider extends CapabilityInfoProvider<IWorkable> {
    public RecipeOutputInfoProvider() {

    }

    @Override
    protected Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        if (capability.getProgress() > 0 && capability instanceof AbstractRecipeLogic) {

            List<ItemStack> itemOutputs = new ArrayList<>(ObfuscationReflectionHelper.getPrivateValue(AbstractRecipeLogic.class, (AbstractRecipeLogic) capability, "itemOutputs"));
            showOutputItems(probeInfo, itemOutputs, ShouldShowInCompactMode(itemOutputs, entityPlayer) ? OutputRenderStyle.COMPACT : OutputRenderStyle.DETAIL);

            List<FluidStack> fluidOutputs = new ArrayList<>(ObfuscationReflectionHelper.getPrivateValue(AbstractRecipeLogic.class, (AbstractRecipeLogic) capability, "fluidOutputs"));
            showOutputFluids(probeInfo, fluidOutputs, ShouldShowInCompactMode(itemOutputs, entityPlayer) ? OutputRenderStyle.COMPACT : OutputRenderStyle.DETAIL);
        }
    }

    private boolean ShouldShowInCompactMode(List stacks, EntityPlayer player) {
        boolean isPlayerSneaking = player.isSneaking();
        return GregicProbeConfig.allowCompactDisplay && !isPlayerSneaking;
    }

    private  enum OutputRenderStyle {
        COMPACT, DETAIL
    }

    private  static void showOutputFluids(IProbeInfo probeInfo, List<FluidStack> stacks, OutputRenderStyle style) {
        if (stacks.isEmpty()) {
            return;
        }
        probeInfo.text(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregicprobe.top.output.fluid").getFormattedText());
        int rows = 0;
        int idx = 0;

        switch (style) {
            case DETAIL:
                IProbeInfo horizontal = probeInfo.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
                IProbeInfo verticalStacks = horizontal.vertical(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_CENTER));
                IProbeInfo verticalTexts = horizontal.vertical(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_BOTTOMRIGHT));
                for (FluidStack stack : stacks) {
                    verticalStacks.element(new ElementFluidStack(stack, probeInfo.defaultItemStyle().width(20)));
                    verticalTexts.element(new ElementRightAlignedText(stack.getLocalizedName() + " * " + TextComponentUtil.stringWithColor(TextFormatting.YELLOW, TextFormattingUtil.formatLongToCompactString(stack.amount, 4) + "L").getFormattedText(), 100));
                }
                break;
            case COMPACT:
                IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(GregicProbeConfig.borderColorProgress));
                IProbeInfo horizontal2 = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_CENTER));
                for (FluidStack stack : stacks) {
                    if (idx % 10 == 0) {
                        horizontal2 = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
                        rows++;
                        if (rows > 4) {
                            break;
                        }
                    }
                    horizontal2.element(new ElementFluidStack(stack, probeInfo.defaultItemStyle().width(20)));
                    idx++;
                }
                break;
        }
    }

    private  static void showOutputItems(IProbeInfo probeInfo, List<ItemStack> stacks, OutputRenderStyle style) {
        if (stacks.isEmpty()) {
            return;
        }
        probeInfo.text(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregicprobe.top.output.item").getFormattedText());
        int rows = 0;
        int idx = 0;

        switch (style) {
            case DETAIL:
                IProbeInfo horizontal = probeInfo.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
                IProbeInfo verticalStacks = horizontal.vertical(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_CENTER));
                IProbeInfo verticalTexts = horizontal.vertical(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_BOTTOMRIGHT));
                for (ItemStack stack : stacks) {
                    verticalStacks.element(new ElementItemStack(stack, probeInfo.defaultItemStyle().width(20)));
                    verticalTexts.element(new ElementRightAlignedText(stack.getDisplayName() + " * " + TextComponentUtil.stringWithColor(TextFormatting.YELLOW, String.valueOf(stack.getCount())).getFormattedText(), 100));
                }
                break;
            case COMPACT:
                IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(GregicProbeConfig.borderColorProgress));
                IProbeInfo horizontal2 = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0).alignment(ElementAlignment.ALIGN_CENTER));
                for (ItemStack stack : stacks) {
                    if (idx % 10 == 0) {
                        horizontal2 = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
                        rows++;
                        if (rows > 4) {
                            break;
                        }
                    }
                    horizontal2.element(new ElementItemStack(stack, probeInfo.defaultItemStyle().width(20)));
                    idx++;
                }
                break;
        }
    }

    @Override
    public String getID() {
        return "gregicprobe:recipe_info_fluid_output";
    }
}
