package vfyjxf.gregicprobe.client.renderer;

import mcjty.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;

public class ElementRightAlignedTextRenderer{

    public ElementRightAlignedTextRenderer() {
    }

    public static void render(String text, int x, int y, int panelWidth) {
        RenderHelper.renderText(Minecraft.getMinecraft(), getRenderPosition(x, panelWidth, text), y, text);
    }

    public static int getWidth(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    private static int getRenderPosition(int x, int panelWidth, String text) {
        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        if (x + textWidth <= panelWidth - 5) {
            return panelWidth - textWidth;
        } else {
            return x + 5;
        }
    }
}
