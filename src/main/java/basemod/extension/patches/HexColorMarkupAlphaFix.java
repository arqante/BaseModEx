package basemod.extension.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * <p>Fixes an issue with the vanilla game where any card text that was colorized using the hex color
 * markup feature from LibGDX (the one where you surround it with {@code [#rrggbb]text[]}) would ignore
 * the transparency value of the regular, non-marked-up text.
 * <p>This allows hex-colored text to fade out with the rest of the card description it belongs to,
 * for example, when a card that has it is briefly shown during an upgrade with the War Paint or the
 * Whetstone relics.
 */
@SpirePatch2(clz = FontHelper.class, method = "renderRotatedText")
public class HexColorMarkupAlphaFix {
    private static final Matrix4 ROTATED_TEXT_MATRIX = new Matrix4();

    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> Insert(SpriteBatch sb, BitmapFont font, String msg, Color c) {
        BitmapFontCache cache = font.getCache();
        cache.clear();
        cache.addText(msg, -FontHelper.layout.width / 2.0F, FontHelper.layout.height / 2.0F);
        cache.setAlphas(c.a);
        cache.draw(sb);

        sb.end();
        sb.setTransformMatrix(ROTATED_TEXT_MATRIX);
        sb.begin();
        return SpireReturn.Return();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior patchTarget) throws CannotCompileException, PatchingException {
            Matcher matcher = new MethodCallMatcher(BitmapFont.class, "draw");
            return LineFinder.findInOrder(patchTarget, matcher);
        }
    }
}
