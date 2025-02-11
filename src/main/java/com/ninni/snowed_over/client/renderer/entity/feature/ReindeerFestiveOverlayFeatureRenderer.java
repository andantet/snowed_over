package com.ninni.snowed_over.client.renderer.entity.feature;

import com.ninni.snowed_over.client.model.entity.ReindeerEntityModel;
import com.ninni.snowed_over.entity.ReindeerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

import static com.ninni.snowed_over.SnowedOver.*;

@Environment(EnvType.CLIENT)
public class ReindeerFestiveOverlayFeatureRenderer extends EyesFeatureRenderer<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> {
    private static final RenderLayer OVERLAY = RenderLayer.getEyes(new Identifier(MOD_ID, "textures/entity/reindeer/reindeer_festive_overlay.png"));

    public ReindeerFestiveOverlayFeatureRenderer(FeatureRendererContext<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> context) { super(context); }

    @Override
    public RenderLayer getEyesTexture() {
        return OVERLAY;
    }
}
