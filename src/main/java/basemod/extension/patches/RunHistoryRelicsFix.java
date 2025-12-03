package basemod.extension.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;

/**
 * Removes colored outlines from modded relics in the Run History screen
 * to match the relics from the base game.
 */
public class RunHistoryRelicsFix {
    private static boolean isRunHistory;

    @SpirePatch2(clz = RunHistoryScreen.class, method = "renderRelics")
    public static class SetFlag {
        public static void Prefix() { isRunHistory = true; }
        public static void Postfix() { isRunHistory = false; }
    }

    @SpirePatch2(clz = AbstractRelic.class, method = "render",
            paramtypez = {SpriteBatch.class, boolean.class, Color.class})
    public static class CheckFlag {
        @SpireInsertPatch(rloc = 0)
        public static void Insert(@ByRef Color[] outlineColor) {
            if (isRunHistory) outlineColor[0] = Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR;
        }
    }
}
