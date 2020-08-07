package net.teamhollow.theroofedcanyon.block.helpers.vanilla;

import net.teamhollow.theroofedcanyon.util.TRCSign;
import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TRCSignBlock extends SignBlock implements TRCSign {
    private final Identifier texture;

    public TRCSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
