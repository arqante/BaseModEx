package basemod.extension.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

/**
 * <p>With this patch, the playtime counters from the Save Slots screen will
 * now account for the extra playtime with modded characters.
 * <p>In some cases, however, the timers won't update immediately:
 * <br>&emsp;1. You've enabled this mod for the very first time.
 * <br>&emsp;2. You've re-enabled the mod after playing some time without it.
 * <br>&emsp;3. You've switched between ModTheSpire and the base game.
 * <p>To update the "stuck" timer for any particular save slot, switch to any
 * other slot and then back to that original save slot.
 */
@SpirePatch2(clz = UnlockTracker.class, method = "getTotalPlaytime")
public class SaveSlotsPlaytimeFix {

    public static SpireReturn<Long> Prefix() {
        return SpireReturn.Return(CardCrawlGame.characterManager.getAllCharacterStats()
                .stream().mapToLong(charStat -> charStat.playTime).sum());
    }
}
