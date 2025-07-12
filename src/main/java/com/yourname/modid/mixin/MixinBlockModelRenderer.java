package com.yourname.modid.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.List;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {
    @Shadow
    private final BlockColors blockColors;

    protected MixinBlockModelRenderer(BlockColors blockColors) {
        this.blockColors = blockColors;
    }

    @Shadow
    public abstract void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags);
    /**
     * @author
     */
    @Overwrite
    private void renderQuadsSmooth(IBlockAccess blockAccessIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, List<BakedQuad> list, float[] quadBounds, BitSet bitSet, BlockModelRenderer.AmbientOcclusionFace aoFace)
    {
        System.out.println("renderer patches");
        Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
        float d0 = (float)posIn.getX() + (float)vec3d.x;
        float d1 = (float)posIn.getY() + (float)vec3d.y;
        float d2 = (float)posIn.getZ() + (float)vec3d.z;
        int i = 0;

        for (int j = list.size(); i < j; ++i)
        {
            BakedQuad bakedquad = list.get(i);
            this.fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
            aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
            buffer.addVertexData(bakedquad.getVertexData());
            buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
            if(bakedquad.shouldApplyDiffuseLighting())
            {
                float diffuse = net.minecraftforge.client.model.pipeline.LightUtil.diffuseLight(bakedquad.getFace());
                aoFace.vertexColorMultiplier[0] *= diffuse;
                aoFace.vertexColorMultiplier[1] *= diffuse;
                aoFace.vertexColorMultiplier[2] *= diffuse;
                aoFace.vertexColorMultiplier[3] *= diffuse;
            }
            if (bakedquad.hasTintIndex())
            {
                int k = this.blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());

                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (float)(k >> 16 & 255) / 255.0F;
                float f1 = (float)(k >> 8 & 255) / 255.0F;
                float f2 = (float)(k & 255) / 255.0F;
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[0] * f, aoFace.vertexColorMultiplier[0] * f1, aoFace.vertexColorMultiplier[0] * f2, 4);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[1] * f, aoFace.vertexColorMultiplier[1] * f1, aoFace.vertexColorMultiplier[1] * f2, 3);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[2] * f, aoFace.vertexColorMultiplier[2] * f1, aoFace.vertexColorMultiplier[2] * f2, 2);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[3] * f, aoFace.vertexColorMultiplier[3] * f1, aoFace.vertexColorMultiplier[3] * f2, 1);
            }
            else
            {
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[0], aoFace.vertexColorMultiplier[0], aoFace.vertexColorMultiplier[0], 4);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[1], aoFace.vertexColorMultiplier[1], aoFace.vertexColorMultiplier[1], 3);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[2], aoFace.vertexColorMultiplier[2], aoFace.vertexColorMultiplier[2], 2);
                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[3], aoFace.vertexColorMultiplier[3], aoFace.vertexColorMultiplier[3], 1);
            }

            buffer.putPosition(d0, d1, d2);
        }
    }
    
    
    /**
     * @author
     * @reason
     */
    @Overwrite
    private void renderQuadsFlat(IBlockAccess p_187496_1_, IBlockState p_187496_2_, BlockPos p_187496_3_, int p_187496_4_, boolean p_187496_5_, BufferBuilder p_187496_6_, List<BakedQuad> p_187496_7_, BitSet p_187496_8_) {
        Vec3d vec3d = p_187496_2_.getOffset(p_187496_1_, p_187496_3_);
        float d0 = (float)p_187496_3_.getX() + (float)vec3d.x;
        float d1 = (float)p_187496_3_.getY() + (float)vec3d.y;
        float d2 = (float)p_187496_3_.getZ() + (float)vec3d.z;
        int i = 0;

        for(int j = p_187496_7_.size(); i < j; ++i) {
            BakedQuad bakedquad = (BakedQuad)p_187496_7_.get(i);
            if (p_187496_5_) {
                this.fillQuadBounds(p_187496_2_, bakedquad.getVertexData(), bakedquad.getFace(), (float[])null, p_187496_8_);
                BlockPos blockpos = p_187496_8_.get(0) ? p_187496_3_.offset(bakedquad.getFace()) : p_187496_3_;
                p_187496_4_ = p_187496_2_.getPackedLightmapCoords(p_187496_1_, blockpos);
            }

            p_187496_6_.addVertexData(bakedquad.getVertexData());
            p_187496_6_.putBrightness4(p_187496_4_, p_187496_4_, p_187496_4_, p_187496_4_);
            if (bakedquad.hasTintIndex()) {
                int k = this.blockColors.colorMultiplier(p_187496_2_, p_187496_1_, p_187496_3_, bakedquad.getTintIndex());
                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (float)(k >> 16 & 255) / 255.0F;
                float f1 = (float)(k >> 8 & 255) / 255.0F;
                float f2 = (float)(k & 255) / 255.0F;
                if (bakedquad.shouldApplyDiffuseLighting()) {
                    float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                    f *= diffuse;
                    f1 *= diffuse;
                    f2 *= diffuse;
                }

                p_187496_6_.putColorMultiplier(f, f1, f2, 4);
                p_187496_6_.putColorMultiplier(f, f1, f2, 3);
                p_187496_6_.putColorMultiplier(f, f1, f2, 2);
                p_187496_6_.putColorMultiplier(f, f1, f2, 1);
            } else if (bakedquad.shouldApplyDiffuseLighting()) {
                float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                p_187496_6_.putColorMultiplier(diffuse, diffuse, diffuse, 4);
                p_187496_6_.putColorMultiplier(diffuse, diffuse, diffuse, 3);
                p_187496_6_.putColorMultiplier(diffuse, diffuse, diffuse, 2);
                p_187496_6_.putColorMultiplier(diffuse, diffuse, diffuse, 1);
            }

            p_187496_6_.putPosition(d0, d1, d2);
        }

    }

}
