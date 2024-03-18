package vfyjxf.gregicprobe.client.renderer;

import gregtech.api.util.TextFormattingUtil;
import gregtech.client.utils.RenderUtil;
import mcjty.theoneprobe.api.IItemStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.FluidStack;

public class ElementFluidStackRender {
    
    public ElementFluidStackRender() {
    }

    public static void render(FluidStack fluidStack, IItemStyle style, int x, int y) {
        GlStateManager.disableBlend();

        RenderUtil.drawFluidForGui(fluidStack, fluidStack.amount, x + (style.getWidth() - 18) / 2, y + (style.getHeight() - 18) / 2, 16, 16);

        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 1);

        String s = TextFormattingUtil.formatLongToCompactString(fluidStack.amount, 4) + "L";

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        fontRenderer.drawStringWithShadow(s, (x + 7) * 2 - fontRenderer.getStringWidth(s) + 19,
                (y + 11) * 2, 0xFFFFFF);

        GlStateManager.popMatrix();
        GlStateManager.enableBlend();
    }
}
