package com.yourname.modid.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;
import java.util.BitSet;

@Mixin(BlockModelRenderer.class)
public interface MixinBlockModelRendererAccessor {
    @Accessor("blockColors")
    BlockColors getBlockColors();

    @Invoker
    void invokeFillQuadBounds(IBlockState p_187494_1_, int[] p_187494_2_, EnumFacing p_187494_3_, @Nullable float[] p_187494_4_, BitSet p_187494_5_);
}