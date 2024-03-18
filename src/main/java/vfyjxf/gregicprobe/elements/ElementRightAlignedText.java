package vfyjxf.gregicprobe.elements;

import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.client.ElementTextRender;
import mcjty.theoneprobe.network.NetworkTools;
import vfyjxf.gregicprobe.client.renderer.ElementRightAlignedTextRenderer;

public class ElementRightAlignedText implements IElement {

    private final String text;
    private final int panelWidth;

    public ElementRightAlignedText(String text, int panelWidth) {
        this.text = text;
        this.panelWidth = panelWidth;
    }

    public ElementRightAlignedText(ByteBuf buf) {
        this.text = NetworkTools.readStringUTF8(buf);
        this.panelWidth = buf.readInt();
    }

    public void render(int x, int y) {
        //ElementRightAlignedTextRenderer.render(this.text, x, y, this.panelWidth);
        ElementTextRender.render(this.text, x, y + 5);
    }

    public int getWidth() {
        // TODO: may not true
        return ElementRightAlignedTextRenderer.getWidth(this.text);
    }

    public int getHeight() {
        return 20;
    }

    public void toBytes(ByteBuf buf) {
        NetworkTools.writeStringUTF8(buf, this.text);
        buf.writeInt(this.panelWidth);
    }

    public int getID() {
        return TheOneProbe.theOneProbeImp.registerElementFactory(ElementRightAlignedText::new);
    }
}
