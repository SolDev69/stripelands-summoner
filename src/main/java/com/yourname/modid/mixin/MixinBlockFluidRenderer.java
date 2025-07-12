package com.yourname.modid.mixin;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockFluidRenderer.class)
public class MixinBlockFluidRenderer {
    @Shadow @Final private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
    @Shadow @Final private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
    @Shadow private TextureAtlasSprite atlasSpriteWaterOverlay;


    @Overwrite
    public boolean renderFluid(IBlockAccess p_178270_1_, IBlockState p_178270_2_, BlockPos p_178270_3_, BufferBuilder p_178270_4_) {
        BlockLiquid blockliquid = (BlockLiquid)p_178270_2_.getBlock();
        boolean flag = p_178270_2_.getMaterial() == Material.LAVA;
        TextureAtlasSprite[] atextureatlassprite = flag ? this.atlasSpritesLava : this.atlasSpritesWater;
        int i = ((MixinBlockFluidRendererAccessor)this).getBlockColors().colorMultiplier(p_178270_2_, p_178270_1_, p_178270_3_, 0);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        boolean flag1 = p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.UP);
        boolean flag2 = p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.DOWN);
        boolean[] aboolean = new boolean[]{p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.NORTH), p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.SOUTH), p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.WEST), p_178270_2_.shouldSideBeRendered(p_178270_1_, p_178270_3_, EnumFacing.EAST)};
        if (!flag1 && !flag2 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
            return false;
        } else {
            boolean flag3 = false;
            float f3 = 0.5F;
            float f4 = 1.0F;
            float f5 = 0.8F;
            float f6 = 0.6F;
            Material material = p_178270_2_.getMaterial();
            float f7 = ((MixinBlockFluidRendererAccessor)this).invokeGetFluidHeight(p_178270_1_, p_178270_3_, material);
            float f8 = ((MixinBlockFluidRendererAccessor)this).invokeGetFluidHeight(p_178270_1_, p_178270_3_.south(), material);
            float f9 = ((MixinBlockFluidRendererAccessor)this).invokeGetFluidHeight(p_178270_1_, p_178270_3_.east().south(), material);
            float f10 = ((MixinBlockFluidRendererAccessor)this).invokeGetFluidHeight(p_178270_1_, p_178270_3_.east(), material);
            float d0 = p_178270_3_.getX();
            float d1 = p_178270_3_.getY();
            float d2 = p_178270_3_.getZ();
            float f11 = 0.001F;
            if (flag1) {
                flag3 = true;
                float f12 = BlockLiquid.getSlopeAngle(p_178270_1_, p_178270_3_, material, p_178270_2_);
                TextureAtlasSprite textureatlassprite = f12 > -999.0F ? atextureatlassprite[1] : atextureatlassprite[0];
                f7 -= 0.001F;
                f8 -= 0.001F;
                f9 -= 0.001F;
                f10 -= 0.001F;
                float f13;
                float f14;
                float f15;
                float f16;
                float f17;
                float f18;
                float f19;
                float f20;
                if (f12 < -999.0F) {
                    f13 = textureatlassprite.getInterpolatedU(0.0F);
                    f17 = textureatlassprite.getInterpolatedV(0.0F);
                    f14 = f13;
                    f18 = textureatlassprite.getInterpolatedV(16.0F);
                    f15 = textureatlassprite.getInterpolatedU(16.0F);
                    f19 = f18;
                    f16 = f15;
                    f20 = f17;
                } else {
                    float f21 = MathHelper.sin(f12) * 0.25F;
                    float f22 = MathHelper.cos(f12) * 0.25F;
                    float f23 = 8.0F;
                    f13 = textureatlassprite.getInterpolatedU((8.0F + (-f22 - f21) * 16.0F));
                    f17 = textureatlassprite.getInterpolatedV((8.0F + (-f22 + f21) * 16.0F));
                    f14 = textureatlassprite.getInterpolatedU((8.0F + (-f22 + f21) * 16.0F));
                    f18 = textureatlassprite.getInterpolatedV((8.0F + (f22 + f21) * 16.0F));
                    f15 = textureatlassprite.getInterpolatedU((8.0F + (f22 + f21) * 16.0F));
                    f19 = textureatlassprite.getInterpolatedV((8.0F + (f22 - f21) * 16.0F));
                    f16 = textureatlassprite.getInterpolatedU((8.0F + (f22 - f21) * 16.0F));
                    f20 = textureatlassprite.getInterpolatedV((8.0F + (-f22 - f21) * 16.0F));
                }

                int k2 = p_178270_2_.getPackedLightmapCoords(p_178270_1_, p_178270_3_);
                int l2 = k2 >> 16 & '\uffff';
                int i3 = k2 & '\uffff';
                float f24 = 1.0F * f;
                float f25 = 1.0F * f1;
                float f26 = 1.0F * f2;
                p_178270_4_.pos(d0 + 0.0F, d1 + f7, d2 + 0.0F).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
                p_178270_4_.pos(d0 + 0.0F, d1 + f8, d2 + 1.0F).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
                p_178270_4_.pos(d0 + 1.0F, d1 + f9, d2 + 1.0F).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
                p_178270_4_.pos(d0 + 1.0F, d1 + f10, d2 + 0.0F).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
                if (blockliquid.shouldRenderSides(p_178270_1_, p_178270_3_.up())) {
                    p_178270_4_.pos(d0 + 0.0F, d1 + f7, d2 + 0.0F).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
                    p_178270_4_.pos(d0 + 1.0F, d1 + f10, d2 + 0.0F).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
                    p_178270_4_.pos(d0 + 1.0F, d1 + f9, d2 + 1.0F).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
                    p_178270_4_.pos(d0 + 0.0F, d1 + f8, d2 + 1.0F).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
                }
            }

            if (flag2) {
                float f35 = atextureatlassprite[0].getMinU();
                float f36 = atextureatlassprite[0].getMaxU();
                float f37 = atextureatlassprite[0].getMinV();
                float f38 = atextureatlassprite[0].getMaxV();
                int l1 = p_178270_2_.getPackedLightmapCoords(p_178270_1_, p_178270_3_.down());
                int i2 = l1 >> 16 & '\uffff';
                int j2 = l1 & '\uffff';
                p_178270_4_.pos(d0, d1, d2 + 1.0F).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f35, f38).lightmap(i2, j2).endVertex();
                p_178270_4_.pos(d0, d1, d2).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f35, f37).lightmap(i2, j2).endVertex();
                p_178270_4_.pos(d0 + 1.0F, d1, d2).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f36, f37).lightmap(i2, j2).endVertex();
                p_178270_4_.pos(d0 + 1.0F, d1, d2 + 1.0F).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f36, f38).lightmap(i2, j2).endVertex();
                flag3 = true;
            }

            for(int i1 = 0; i1 < 4; ++i1) {
                int j1 = 0;
                int k1 = 0;
                if (i1 == 0) {
                    --k1;
                }

                if (i1 == 1) {
                    ++k1;
                }

                if (i1 == 2) {
                    --j1;
                }

                if (i1 == 3) {
                    ++j1;
                }

                BlockPos blockpos = p_178270_3_.add(j1, 0, k1);
                TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];
                if (!flag) {
                    IBlockState state = p_178270_1_.getBlockState(blockpos);
                    if (state.getBlockFaceShape(p_178270_1_, blockpos, EnumFacing.VALUES[i1 + 2].getOpposite()) == BlockFaceShape.SOLID) {
                        textureatlassprite1 = this.atlasSpriteWaterOverlay;
                    }
                }

                if (aboolean[i1]) {
                    float f39;
                    float f40;
                    float d3;
                    float d4;
                    float d5;
                    float d6;
                    if (i1 == 0) {
                        f39 = f7;
                        f40 = f10;
                        d3 = d0;
                        d5 = d0 + 1.0F;
                        d4 = d2 + 0.001F;
                        d6 = d2 + 0.001F;
                    } else if (i1 == 1) {
                        f39 = f9;
                        f40 = f8;
                        d3 = d0 + 1.0F;
                        d5 = d0;
                        d4 = d2 + 1.0F - 0.001F;
                        d6 = d2 + 1.0F - 0.001F;
                    } else if (i1 == 2) {
                        f39 = f8;
                        f40 = f7;
                        d3 = d0 + 0.001F;
                        d5 = d0 + 0.001F;
                        d4 = d2 + 1.0F;
                        d6 = d2;
                    } else {
                        f39 = f10;
                        f40 = f9;
                        d3 = d0 + 1.0F - 0.001F;
                        d5 = d0 + 1.0F - 0.001F;
                        d4 = d2;
                        d6 = d2 + 1.0F;
                    }

                    flag3 = true;
                    float f41 = textureatlassprite1.getInterpolatedU(0.0F);
                    float f27 = textureatlassprite1.getInterpolatedU(8.0F);
                    float f28 = textureatlassprite1.getInterpolatedV(((1.0F - f39) * 16.0F * 0.5F));
                    float f29 = textureatlassprite1.getInterpolatedV(((1.0F - f40) * 16.0F * 0.5F));
                    float f30 = textureatlassprite1.getInterpolatedV(8.0F);
                    int j = p_178270_2_.getPackedLightmapCoords(p_178270_1_, blockpos);
                    int k = j >> 16 & '\uffff';
                    int l = j & '\uffff';
                    float f31 = i1 < 2 ? 0.8F : 0.6F;
                    float f32 = 1.0F * f31 * f;
                    float f33 = 1.0F * f31 * f1;
                    float f34 = 1.0F * f31 * f2;
                    p_178270_4_.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f41, f28).lightmap(k, l).endVertex();
                    p_178270_4_.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
                    p_178270_4_.pos(d5, d1 + 0.0F, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
                    p_178270_4_.pos(d3, d1 + 0.0F, d4).color(f32, f33, f34, 1.0F).tex(f41, f30).lightmap(k, l).endVertex();
                    if (textureatlassprite1 != this.atlasSpriteWaterOverlay) {
                        p_178270_4_.pos(d3, d1 + 0.0F, d4).color(f32, f33, f34, 1.0F).tex(f41, f30).lightmap(k, l).endVertex();
                        p_178270_4_.pos(d5, d1 + 0.0F, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
                        p_178270_4_.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
                        p_178270_4_.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f41, f28).lightmap(k, l).endVertex();
                    }
                }
            }

            return flag3;
        }
    }

}
