package basemod.extension;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomColor {
    private AbstractCard.CardColor cardColor;
    private Color bgColor;
    private Color backColor;
    private Color frameColor;
    private Color frameOutlineColor;
    private Color descBoxColor;
    private Color trailVfxColor;
    private Color glowColor;
    private String attackBg;
    private String skillBg;
    private String powerBg;
    private String energyOrb;
    private String attackBgPortrait;
    private String skillBgPortrait;
    private String powerBgPortrait;
    private String energyOrbPortrait;
    private String cardEnergyOrb;

    public static class Builder {

        /**
         * Copies all colors and paths from an already <b>registered</b> custom
         * {@link AbstractCard.CardColor CardColor} to the calling {@link Builder} object.
         * To register a card color, use BaseMod's {@link BaseMod#addColor addColor} method.
         * <br>If no match could be found, the builder's fields are set to {@code null}
         * (except the enum itself).
         * @param cardColor a modded character's {@link AbstractCard.CardColor CardColor} enum
         * @return this {@link Builder} object
         */
        public Builder copyFrom(AbstractCard.CardColor cardColor) {
            return cardColor(cardColor)
                    .bgColor(BaseMod.getBgColor(cardColor))
                    .backColor(BaseMod.getBackColor(cardColor))
                    .frameColor(BaseMod.getFrameColor(cardColor))
                    .frameOutlineColor(BaseMod.getFrameOutlineColor(cardColor))
                    .descBoxColor(BaseMod.getDescBoxColor(cardColor))
                    .trailVfxColor(BaseMod.getTrailVfxColor(cardColor))
                    .glowColor(BaseMod.getGlowColor(cardColor))
                    .attackBg(BaseMod.getAttackBg(cardColor))
                    .skillBg(BaseMod.getSkillBg(cardColor))
                    .powerBg(BaseMod.getPowerBg(cardColor))
                    .energyOrb(BaseMod.getEnergyOrb(cardColor))
                    .attackBgPortrait(BaseMod.getAttackBgPortrait(cardColor))
                    .skillBgPortrait(BaseMod.getSkillBgPortrait(cardColor))
                    .powerBgPortrait(BaseMod.getPowerBgPortrait(cardColor))
                    .energyOrbPortrait(BaseMod.getEnergyOrbPortrait(cardColor))
                    .cardEnergyOrb(BaseModEx.getCardEnergyOrb(cardColor));
        }

        /**
         * Sets all the colors of the calling {@link Builder} object to a single shared {@link Color}.
         * @param sharedColor new shared color
         * @return this {@link Builder} object
         */
        public Builder allColors(Color sharedColor) {
            return bgColor(sharedColor)
                    .backColor(sharedColor)
                    .frameColor(sharedColor)
                    .frameOutlineColor(sharedColor)
                    .descBoxColor(sharedColor)
                    .trailVfxColor(sharedColor)
                    .glowColor(sharedColor);
        }
    }
}
