package vfyjxf.gregicprobe.mixins.late;

import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.network.NetworkTools;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

import java.io.IOException;

@Mixin(value = NetworkTools.class, priority = 500, remap = false)
public class TOPNetworkToolsMixin {
}
