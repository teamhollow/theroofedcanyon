package net.teamhollow.theroofedcanyon.block.helpers.vanilla;

import net.teamhollow.theroofedcanyon.util.TRCSign;
import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TRCWallSignBlock extends WallSignBlock implements TRCSign {
    private final Identifier texture;

    public TRCWallSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
