package basemod.extension.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.extension.BaseModEx;
import basemod.patches.com.megacrit.cardcrawl.unlock.UnlockTracker.CountModdedUnlockCards;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.FieldAccessMatcher;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/**
 * A group of patches that fix a number of issues with the Character Stats screen.
 */
public class StatsScreenFixes {

    /**
     * <b><i>"Overall: Cards Discovered":</i></b>
     * <p>Cards of custom colors are now counted toward the total count.
     */
    @SpirePatch2(clz = UnlockTracker.class, method = "getCardsSeenString")
    public static class OverallCardsDiscoveredFix {
        public static SpireReturn<String> Prefix() {
            int totalSeenCount = BaseMod.getModdedCharacters().stream()
                    .mapToInt(AbstractPlayer::getSeenCardCount).sum()
                    + CardLibrary.seenRedCards + CardLibrary.seenGreenCards
                    + CardLibrary.seenBlueCards + CardLibrary.seenPurpleCards
                    + CardLibrary.seenColorlessCards + CardLibrary.seenCurseCards;
            return SpireReturn.Return(String.format("%d/%d", totalSeenCount, CardLibrary.totalCardCount));
        }
    }

    /**
     * <b><i>"Overall: Cards Unlocked":</i></b>
     * <p>Cards of custom colors are now counted toward both counts (unlocked / total).
     */
    @SpirePatch2(clz = CharStat.class, method = SpirePatch.CONSTRUCTOR, paramtypez = ArrayList.class)
    public static class OverallCardsUnlockedFix {

        @SpireInsertPatch(locator = Locator.class, localvars = {"unlockedCardCount", "lockedCardCount"})
        public static void Insert(@ByRef int[] unlockedCardCount, @ByRef int[] lockedCardCount) {
            unlockedCardCount[0] += BaseMod.getModdedCharacters().stream()
                    .mapToInt(AbstractPlayer::getUnlockedCardCount).sum();

            lockedCardCount[0] += BaseMod.getModdedCharacters().stream()
                    .mapToInt(player -> CountModdedUnlockCards.getLockedCardCount(
                            player.chosenClass, 0)).sum();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior patchTarget) throws CannotCompileException, PatchingException {
                Matcher matcher = new FieldAccessMatcher(CharStat.class, "info2");
                return new int[] { LineFinder.findAllInOrder(patchTarget, matcher)[1] };
            }
        }
    }

    /**
     * <b><i>"Overall: Relics Discovered":</i></b>
     * <p>Relics of custom colors are now counted toward the total count.
     */
    @SpirePatch2(clz = BaseMod.class, method = "addRelicToCustomPool")
    public static class OverallRelicsDiscoveredFix {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            RelicLibrary.totalRelicCount++;
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior patchTarget) throws CannotCompileException, PatchingException {
                Matcher matcher = new MethodCallMatcher(UnlockTracker.class, "isRelicSeen");
                return LineFinder.findInOrder(patchTarget, matcher);
            }
        }
    }

    /**
     * <b><i>"Overall: Relics Unlocked":</i></b>
     * <p>Relics from custom unlock bundles are now counted toward both counts (unlocked / total).
     */
    @SpirePatch2(clz = UnlockTracker.class, method = "countUnlockedCards")
    public static class OverallRelicsUnlockedFix {
        public static void Postfix() {
            if (CardCrawlGame.characterManager == null) return;

            BaseMod.getModdedCharacters().forEach(player ->
                    IntStream.range(0, BaseMod.getMaxUnlockLevel(player))
                            .mapToObj(unlockLevel -> BaseMod.getUnlockBundleFor(player.chosenClass, unlockLevel))
                            .filter(bundle -> bundle.unlockType == UnlockType.RELIC)
                            .flatMap(bundle -> bundle.getUnlockIDs().stream())
                            .forEach(relic -> {
                                UnlockTracker.addRelic(relic);
                                UnlockTracker.lockedRelicCount++;
                                if (!UnlockTracker.isRelicLocked(relic)) UnlockTracker.unlockedRelicCount++;
                            })
            );
        }
    }

    /**
     * <b><i>"Overall: Cards Discovered":</i></b>
     * <p>Prevents the counters from doubling whenever switching between save slots which is caused
     * by duplicate entries in the {@code ToAdd} lists.
     */
    @SpirePatch2(clz = CardLibrary.class, method = "initialize")
    public static class OverallDoubleCountFix {
        @SpireInsertPatch(rloc = 0)
        public static void Insert() {
            ArrayList<AbstractCard> redToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "redToAdd");
            ArrayList<AbstractCard> greenToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "greenToAdd");
            ArrayList<AbstractCard> blueToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "blueToAdd");
            ArrayList<AbstractCard> purpleToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "purpleToAdd");
            ArrayList<AbstractCard> colorlessToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "colorlessToAdd");
            ArrayList<AbstractCard> curseToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "curseToAdd");
            ArrayList<AbstractCard> customToAdd = ReflectionHacks.getPrivateStatic(BaseMod.class, "customToAdd");
            ReflectionHacks.setPrivateStatic(BaseMod.class, "redToAdd", new ArrayList<>(new TreeSet<>(redToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "greenToAdd", new ArrayList<>(new TreeSet<>(greenToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "blueToAdd", new ArrayList<>(new TreeSet<>(blueToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "purpleToAdd", new ArrayList<>(new TreeSet<>(purpleToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "colorlessToAdd", new ArrayList<>(new TreeSet<>(colorlessToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "curseToAdd", new ArrayList<>(new TreeSet<>(curseToAdd)));
            ReflectionHacks.setPrivateStatic(BaseMod.class, "customToAdd", new ArrayList<>(new TreeSet<>(customToAdd)));
        }
    }

    /**
     * <b><i>"Character: Cards Discovered":</i></b>
     * <p><i>(This bug affects only modded characters.)</i>
     * <p>Prevents the counters from doubling whenever switching between save slots which is caused
     * by the counting maps not being flushed on reload.
     */
    @SpirePatch2(clz = CardLibrary.class, method = "resetForReload")
    public static class ModdedCharDoubleCountFix {
        public static void Postfix() {
            HashMap<AbstractCard.CardColor, Integer> colorCardCountMap =
                    ReflectionHacks.getPrivateStatic(BaseMod.class, "colorCardCountMap");
            HashMap<AbstractCard.CardColor, Integer> colorCardSeenCountMap =
                    ReflectionHacks.getPrivateStatic(BaseMod.class, "colorCardSeenCountMap");

            colorCardCountMap.replaceAll((color, cardCount) -> 0);
            colorCardSeenCountMap.replaceAll((color, cardSeenCount) -> 0);
            ReflectionHacks.setPrivateStatic(BaseMod.class, "colorCardCountMap", colorCardCountMap);
            ReflectionHacks.setPrivateStatic(BaseMod.class, "colorCardSeenCountMap", colorCardSeenCountMap);
        }
    }

    /**
     * <b><i>"Character: Highest Score":</i></b>
     * <p><i>(This bug affects only modded characters.)</i>
     * <p>The highest score is now properly updated instead of always staying at 0.
     * Unfortunately, this fix cannot work retroactively.
     */
    @SpirePatch2(clz = GameOverScreen.class, method = "uploadToSteamLeaderboards")
    public static class ModdedCharHighestScoreFix {
        public static void Postfix(int ___score) {
            if (BaseModEx.isModdedCharacter(player.chosenClass)
                    && !Settings.isTrial && !Settings.seedSet) {
                StatsScreen.updateHighestScore(___score);
            }
        }
    }

    /**
     * <p>With this patch, the stats of modded characters will be hidden until
     * they are unlocked, like it is for the base game characters.
     * <p>May not always work, since there is no universal agreement in the STS
     * modding community on how to format character unlock keys.
     */
    @SpirePatch2(clz = CustomPlayer.class, method = "renderStatScreen")
    public static class ModdedCharStatsVisibilityFix {
        public static SpireReturn<Void> Prefix(CustomPlayer __instance) {
            String unlockKey = BaseModEx.convertToUnlockKey(__instance.chosenClass);
            return UnlockTracker.isCharacterLocked(unlockKey) ?
                    SpireReturn.Return() : SpireReturn.Continue();
        }
    }

    /**
     * <p>Removes any empty gaps between character stats caused by locked characters. This fix
     * may not always work with locked modded characters for the same reason as the patch above.
     */
    public static class CharStatsPositionFix {

        public static boolean isLocked(AbstractPlayer player) {
            String unlockKey = BaseModEx.convertToUnlockKey(player.chosenClass);
            return UnlockTracker.isCharacterLocked(unlockKey);
        }

        /**
         * If a character is locked, moves up the stats of the next characters.
         */
        @SpirePatch2(clz = StatsScreen.class, method = "renderStatScreen")
        public static class MoveCharStats {
            public static ExprEditor Instrument() {
                return new ExprEditor() {
                    private final String replacement = String.format(
                            "{ $proceed($$); if (%s.isLocked($0)) renderY += 400.0F * %s.scale; }",
                            CharStatsPositionFix.class.getName(),
                            Settings.class.getName()
                    );

                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("renderStatScreen")) m.replace(replacement);
                    }
                };
            }
        }

        /**
         * <p>Recalculates the bounds of the scroll bar to take into account locked modded characters.
         * This prevents the bar from being able to scroll past the last unlocked character.
         */
        @SpirePatch2(clz = StatsScreen.class, method = "calculateScrollBounds")
        public static class RecalculateScrollBounds {
            public static void Postfix(StatsScreen __instance) {
                float scrollUpperBound = ReflectionHacks.getPrivate(__instance,
                        StatsScreen.class, "scrollUpperBound");
                long lockedCount = BaseMod.getModdedCharacters().stream()
                        .filter(CharStatsPositionFix::isLocked).count();

                ReflectionHacks.setPrivate(__instance, StatsScreen.class, "scrollUpperBound",
                        scrollUpperBound - 400.0F * lockedCount * Settings.scale);
            }
        }
    }
}
