package basemod.extension.patches;

import basemod.BaseMod;
import basemod.extension.BaseModEx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.screens.MasterDeckSortHeader;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/**
 * With this patch, the color of the Deck Screen sorting bar for the Watcher
 * as well as modded characters will now match the color of the bar from the
 * Card Library instead of being simply gray.
 */
@SpirePatch2(clz = MasterDeckSortHeader.class, method = "render")
public class ColoredDeckScreenBar {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(SpriteBatch sb) {
        if (player.chosenClass == PlayerClass.WATCHER) {
            sb.setColor(new Color(0.37F, 0.22F, 0.49F, 1.0F));
        } else if (BaseModEx.isModdedCharacter(player.chosenClass)) {
            sb.setColor(BaseMod.getTrailVfxColor(player.getCardColor()));
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior patchTarget) throws CannotCompileException, PatchingException {
            Matcher matcher = new MethodCallMatcher(SpriteBatch.class, "draw");
            return LineFinder.findInOrder(patchTarget, matcher);
        }
    }
}
