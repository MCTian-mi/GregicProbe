package vfyjxf.gregicprobe.elements;

import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IItemStyle;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import vfyjxf.gregicprobe.client.renderer.ElementFluidStackRender;

import java.io.IOException;

import static net.minecraftforge.fluids.FluidStack.loadFluidStackFromNBT;

public class ElementFluidStack implements IElement {

    private final FluidStack fluidStack;
    private final IItemStyle style;

    public ElementFluidStack(FluidStack fluidStack, IItemStyle style) {
        this.fluidStack = fluidStack;
        this.style = style;
    }

    public ElementFluidStack(ByteBuf buf) {
        if (buf.readBoolean()) {
            this.fluidStack = readFluidStack(buf);
        } else {
            this.fluidStack = null;
        }
        this.style = new ItemStyle()
                .width(buf.readInt())
                .height(buf.readInt());
    }

    @Override
    public void render(int x, int y) {
        if (this.fluidStack.getFluid() != null) {
            ElementFluidStackRender.render(this.fluidStack, this.style,x, y);
        }
    }

    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (this.fluidStack.getFluid() != null) {
            buf.writeBoolean(true);
            writeFluidStack(buf, this.fluidStack);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeInt(style.getWidth());
        buf.writeInt(style.getHeight());
    }

    @Override
    public int getID() {
        return TheOneProbe.theOneProbeImp.registerElementFactory(ElementFluidStack::new);
    }

    public static FluidStack readFluidStack(ByteBuf dataIn) {
        PacketBuffer buf = new PacketBuffer(dataIn);

        try {
            NBTTagCompound nbt = buf.readCompoundTag();
            FluidStack stack = loadFluidStackFromNBT(nbt);
            stack.amount = buf.readInt();
            return stack;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void writeFluidStack(ByteBuf dataOut, FluidStack fluidStack) {
        PacketBuffer buf = new PacketBuffer(dataOut);
        NBTTagCompound nbt = new NBTTagCompound();
        fluidStack.writeToNBT(nbt);

        try {
            buf.writeCompoundTag(nbt);
            buf.writeInt(fluidStack.amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
