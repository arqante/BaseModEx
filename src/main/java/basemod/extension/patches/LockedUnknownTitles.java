package basemod.extension.patches;

import basemod.abstracts.CustomCard;
import basemod.extension.annotations.BaseModPatch;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

/**
 * A couple of patches that restore the missing "Locked" and "Unknown" card titles in the Card Library.
 * They are present in the base game, but not when playing with mods.
 */
public class LockedUnknownTitles {

    /**
     * If a card is locked or haven't been seen, replaces its regular title with
     * "Locked" or "Unknown", respectively, like in the base game.
     */
    @BaseModPatch
    @SpirePatch2(clz = CardModifierManager.class, method = "onRenderTitle")
    public static class ReplaceRegularTitle {
        public static SpireReturn<String> Prefix(AbstractCard card) {
            UIStrings strings = CardCrawlGame.languagePack.getUIString("AbstractCard");
            return card.isLocked ? SpireReturn.Return(strings.TEXT[0])
                    : (!card.isSeen ? SpireReturn.Return(strings.TEXT[1]) : SpireReturn.Continue());
        }
    }

    /**
     * <i>(This patch is only required for custom cards.)</i>
     * <p>Prevents the font size of the "Locked" and "Unknown" text from being affected
     * by the font size of a custom card's regular title.
     */
    @BaseModPatch
    @SpirePatch2(clz = CustomCard.class, method = "getTitleFont")
    public static class TitleFontSizeFix {
        public static SpireReturn<BitmapFont> Prefix(CustomCard __instance) {
            if (__instance.isLocked || !__instance.isSeen) return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }
}
