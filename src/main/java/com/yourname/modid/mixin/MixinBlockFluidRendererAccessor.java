package com.yourname.modid.mixin;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockFluidRenderer.class)
public interface MixinBlockFluidRendererAccessor {

    @Accessor("blockColors")
    BlockColors getBlockColors();

    @Invoker
    float invokeGetFluidHeight(IBlockAccess p_178269_1_, BlockPos p_178269_2_, Material p_178269_3_);

}
