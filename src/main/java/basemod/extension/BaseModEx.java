package basemod.extension;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomUnlockBundle;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static basemod.extension.BaseModExInit.logger;

public class BaseModEx {

    // -------------------------- CARD COLOR METHODS --------------------------
    // ------------------------------------------------------------------------
    /**
     * <p>A wrapper for BaseMod's {@link BaseMod#addColor addColor} method that allows passing
     * all the arguments as a single {@link CustomColor} object. In order to create one, use the
     * {@link CustomColor#builder()} method.
     * <p>Here is a basic example:
     * <pre>{@code
     * CustomColor customColor = CustomColor.builder()
     *                                      .copyFrom(SOME_CUSTOM_COLOR_ENUM)
     *                                      .allColors(Color.GOLDENROD)
     *                                      .bgColor(Color.YELLOW)
     *                                      .skillBg("Path-to-skillBg")
     *                                      .build();
     * BaseModEx.addColor(customColor);
     * }</pre>
     * @param customColor a {@link CustomColor} object
     */
    public static void addColor(CustomColor customColor) {
        BaseMod.addColor(
                customColor.getCardColor(),
                customColor.getBgColor(),
                customColor.getBackColor(),
                customColor.getFrameColor(),
                customColor.getFrameOutlineColor(),
                customColor.getDescBoxColor(),
                customColor.getTrailVfxColor(),
                customColor.getGlowColor(),
                customColor.getAttackBg(),
                customColor.getSkillBg(),
                customColor.getPowerBg(),
                customColor.getEnergyOrb(),
                customColor.getAttackBgPortrait(),
                customColor.getSkillBgPortrait(),
                customColor.getPowerBgPortrait(),
                customColor.getEnergyOrbPortrait(),
                customColor.getCardEnergyOrb()
        );
    }

    /**
     * Returns the path to a modded character's {@code cardEnergyOrb} (used in card descriptions).
     * @param cardColor the character's {@link AbstractCard.CardColor CardColor} enum
     * @return the {@link String} containing the path to the character's {@code cardEnergyOrb}
     */
    public static String getCardEnergyOrb(AbstractCard.CardColor cardColor) {
        HashMap<AbstractCard.CardColor, String> map = ReflectionHacks.getPrivateStatic(
                BaseMod.class, "colorCardEnergyOrbMap");
        return map.get(cardColor);
    }

    // --------------------------- Replace methods ----------------------------
    /**
     * Replaces all the colors of a modded character with one shared {@link Color}.
     * @param cardColor the character's {@link AbstractCard.CardColor CardColor} enum
     * @param sharedColor new shared color
     */
    public static void replaceAllColors(AbstractCard.CardColor cardColor, Color sharedColor) {
        replaceBgColor(cardColor, sharedColor);
        replaceBackColor(cardColor, sharedColor);
        replaceFrameColor(cardColor, sharedColor);
        replaceFrameOutlineColor(cardColor, sharedColor);
        replaceDescBoxColor(cardColor, sharedColor);
        replaceTrailVfxColor(cardColor, sharedColor);
        replaceGlowColor(cardColor, sharedColor);
    }

    public static void replaceBgColor(AbstractCard.CardColor cardColor, Color bgColor) {
        replaceColor(cardColor, bgColor, "colorBgColorMap");
    }

    public static void replaceBackColor(AbstractCard.CardColor cardColor, Color backColor) {
        replaceColor(cardColor, backColor, "colorBackColorMap");
    }

    public static void replaceFrameColor(AbstractCard.CardColor cardColor, Color frameColor) {
        replaceColor(cardColor, frameColor, "colorFrameColorMap");
    }

    public static void replaceFrameOutlineColor(AbstractCard.CardColor cardColor, Color frameOutlineColor) {
        replaceColor(cardColor, frameOutlineColor, "colorFrameOutlineColorMap");
    }

    public static void replaceDescBoxColor(AbstractCard.CardColor cardColor, Color descBoxColor) {
        replaceColor(cardColor, descBoxColor, "colorDescBoxColorMap");
    }

    public static void replaceTrailVfxColor(AbstractCard.CardColor cardColor, Color trailVfxColor) {
        replaceColor(cardColor, trailVfxColor, "colorTrailVfxMap");
    }

    public static void replaceGlowColor(AbstractCard.CardColor cardColor, Color glowColor) {
        replaceColor(cardColor, glowColor, "colorGlowColorMap");
    }

    public static void replaceAttackBg(AbstractCard.CardColor cardColor, String attackBg) {
        replacePath(cardColor, attackBg, "colorAttackBgMap");
    }

    public static void replaceSkillBg(AbstractCard.CardColor cardColor, String skillBg) {
        replacePath(cardColor, skillBg, "colorSkillBgMap");
    }

    public static void replacePowerBg(AbstractCard.CardColor cardColor, String powerBg) {
        replacePath(cardColor, powerBg, "colorPowerBgMap");
    }

    public static void replaceEnergyOrb(AbstractCard.CardColor cardColor, String energyOrb) {
        replacePath(cardColor, energyOrb, "colorEnergyOrbMap");
    }

    public static void replaceAttackBgPortrait(AbstractCard.CardColor cardColor, String attackBgPortrait) {
        replacePath(cardColor, attackBgPortrait, "colorAttackBgPortraitMap");
    }

    public static void replaceSkillBgPortrait(AbstractCard.CardColor cardColor, String skillBgPortrait) {
        replacePath(cardColor, skillBgPortrait, "colorSkillBgPortraitMap");
    }

    public static void replacePowerBgPortrait(AbstractCard.CardColor cardColor, String powerBgPortrait) {
        replacePath(cardColor, powerBgPortrait, "colorPowerBgPortraitMap");
    }

    public static void replaceEnergyOrbPortrait(AbstractCard.CardColor cardColor, String energyOrbPortrait) {
        replacePath(cardColor, energyOrbPortrait, "colorEnergyOrbPortraitMap");
    }

    public static void replaceCardEnergyOrb(AbstractCard.CardColor cardColor, String cardEnergyOrb) {
        replacePath(cardColor, cardEnergyOrb, "colorCardEnergyOrbMap");
    }

    private static void replaceColor(AbstractCard.CardColor key, Color value, String type) {
        HashMap<AbstractCard.CardColor, Color> map = ReflectionHacks.getPrivateStatic(BaseMod.class, type);
        map.replace(key, value);
        ReflectionHacks.setPrivateStatic(BaseMod.class, type, map);
    }

    private static void replacePath(AbstractCard.CardColor key, String value, String type) {
        HashMap<AbstractCard.CardColor, String> map = ReflectionHacks.getPrivateStatic(BaseMod.class, type);
        map.replace(key, value);
        ReflectionHacks.setPrivateStatic(BaseMod.class, type, map);
    }
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------


    // ---------------------------- UNLOCK METHODS ----------------------------
    // ------------------------------------------------------------------------
    /**
     * <p>Converts a character's {@link AbstractPlayer.PlayerClass PlayerClass}
     * enum to their character unlock key.
     * <p>This method assumes the key is formatted in this way:
     * <pre>{@code
     * 1. DEFECT -> "Defect", WATCHER -> "Watcher", etc.
     * 2. THE_CHAMP -> "Champ", THE_COLLECTOR -> "Collector", etc.
     * 3. THE_SILENT -> "The Silent" //Silent is a special case
     * }</pre>
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return the {@link String} containing the character's unlock key
     */
    public static String convertToUnlockKey(AbstractPlayer.PlayerClass playerClass) {
        if (playerClass == AbstractPlayer.PlayerClass.THE_SILENT) return "The Silent";

        String key = playerClass.toString().toLowerCase();
        key = key.startsWith("the_") ? key.substring(4) : key;
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    /**
     * Same as {@link BaseMod#getMaxUnlockLevel(AbstractPlayer.PlayerClass) BaseMod.getMaxUnlockLevel}
     * but returns 5 for the base game characters.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return the character's max unlock level
     */
    public static int getMaxUnlockLevel(AbstractPlayer.PlayerClass playerClass) {
        if (!isModdedCharacter(playerClass)) return 5;
        return BaseMod.getMaxUnlockLevel(playerClass);
    }

    // -------- Unlocking methods that require a PlayerClass parameter --------
    /**
     * Unlocks a character and all of their unlock bundles. Additionally, sets
     * the character's unlock progress to level their max level (usually 5).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void unlockAllFor(AbstractPlayer.PlayerClass playerClass) {
        unlockCharacter(playerClass);
        unlockAllBundles(playerClass);
    }

    /**
     * Unlocks a character. This method is similar to {@link UnlockTracker#hardUnlockOverride};
     * however, it also removes the character from the {@code lockedCharacters} list.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void unlockCharacter(AbstractPlayer.PlayerClass playerClass) {
        String unlockKey = BaseModEx.convertToUnlockKey(playerClass);
        UnlockTracker.lockedCharacters.remove(unlockKey);
        UnlockTracker.unlockPref.putInteger(unlockKey, 2);
        UnlockTracker.unlockPref.flush();
        logger.info("Unlocked character: {}", playerClass);
    }

    /**
     * Unlocks all unlock bundles of a character and marks their contents as seen.
     * Additionally, sets the character's unlock progress to their max level (usually 5).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void unlockAllBundles(AbstractPlayer.PlayerClass playerClass) {
        for (int i = 0; i < BaseModEx.getMaxUnlockLevel(playerClass); i++) {
            BaseModEx.unlockBundle(playerClass, i);
        }
        maxUnlockProgress(playerClass);
        logger.info("Unlocked all bundles for: {}", playerClass);
    }

    /**
     * Unlocks a character's unlock bundle and marks its contents as seen.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @param unlockLevel the level of the bundle to be unlocked
     */
    public static void unlockBundle(AbstractPlayer.PlayerClass playerClass, int unlockLevel) {
        AbstractUnlock.UnlockType unlockType;
        List<String> unlockKeys;

        if (isModdedCharacter(playerClass)) {
            CustomUnlockBundle bundle = BaseMod.getUnlockBundleFor(playerClass, unlockLevel);
            unlockType = bundle.unlockType;
            unlockKeys = bundle.getUnlockIDs();
        } else {
            ArrayList<AbstractUnlock> bundle = UnlockTracker.getUnlockBundle(playerClass, unlockLevel);
            unlockType = bundle.stream().findFirst().orElse(new AbstractUnlock()).type;
            unlockKeys = bundle.stream().map(unlock -> unlock.key).collect(Collectors.toList());
        }

        unlockKeys.forEach(unlockKey -> BaseModEx.unlockByType(unlockKey, unlockType));
    }

    /**
     * Sets a character's unlock progress to their max level (usually 5).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void maxUnlockProgress(AbstractPlayer.PlayerClass playerClass) {
        int totalScore = 50;
        int currentCost = 300;
        int unlockLevel = BaseModEx.getMaxUnlockLevel(playerClass);

        for (int i = 0; i < unlockLevel; i++) {
            totalScore += currentCost;
            currentCost = UnlockTracker.incrementUnlockRamp(currentCost);
        }
        int highScore = UnlockTracker.unlockProgress.getInteger(playerClass + "HighScore", 1000);

        UnlockTracker.unlockProgress.putInteger(playerClass + "UnlockLevel", unlockLevel);
        UnlockTracker.unlockProgress.putInteger(playerClass + "Progress", 50);
        UnlockTracker.unlockProgress.putInteger(playerClass + "CurrentCost", currentCost);
        UnlockTracker.unlockProgress.putInteger(playerClass + "TotalScore", totalScore);
        UnlockTracker.unlockProgress.putInteger(playerClass + "HighScore", highScore);
        UnlockTracker.unlockProgress.flush();
    }

    // ---------- Unlocking methods that require a String parameter -----------
    /**
     * Unlocks either a card, a relic, or a character by their string key.
     * @param key a {@link String} containing an unlock key
     * @param type the type of unlock
     */
    public static void unlockByType(String key, AbstractUnlock.UnlockType type) {
        if (type == AbstractUnlock.UnlockType.CARD) {
            UnlockTracker.unlockCard(key);
        } else if (type == AbstractUnlock.UnlockType.RELIC) {
            BaseModEx.unlockRelic(key);
        } else if (type == AbstractUnlock.UnlockType.CHARACTER) {
            UnlockTracker.hardUnlockOverride(key);
        } else {
            logger.info("Unsupported unlock type: {}", type);
        }
    }

    /**
     * Unlocks a relic by its string key and marks it as seen. This method is basically
     * a combination of {@link UnlockTracker#hardUnlockOverride hardUnlockOverride} and
     * {@link UnlockTracker#markRelicAsSeen markRelicAsSeen} but with no checks and
     * console messages.
     * @param key a {@link String} containing a relic unlock key
     */
    public static void unlockRelic(String key) {
        UnlockTracker.unlockPref.putInteger(key, 2);
        UnlockTracker.unlockPref.flush();
        UnlockTracker.relicSeenPref.putInteger(key, 1);
        UnlockTracker.relicSeenPref.flush();
        RelicLibrary.getRelic(key).isSeen = true;
    }
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------


    // -------------------------- CHARACTER METHODS ---------------------------
    // ------------------------------------------------------------------------
    /**
     * A method of checking if a character is a modded one that is less
     * prone to {@link NullPointerException}.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return {@code true} if the character is a modded character, otherwise {@code false}
     */
    public static boolean isModdedCharacter(AbstractPlayer.PlayerClass playerClass) {
        switch (playerClass) {
            case IRONCLAD:
            case THE_SILENT:
            case DEFECT:
            case WATCHER:
                return false;
            default:
                return true;
        }
    }

    /**
     * Finds a modded character by their {@link AbstractPlayer.PlayerClass PlayerClass} enum. This method is
     * similar to BaseMod's {@link BaseMod#findCharacter(AbstractPlayer.PlayerClass) findCharacter} method
     * but returns a {@link CustomPlayer} object instead.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return a {@link CustomPlayer} object, or {@code null} if no character could be found
     */
    public static CustomPlayer findModdedCharacter(AbstractPlayer.PlayerClass playerClass) {
        return (CustomPlayer) BaseMod.getModdedCharacters().stream()
                .filter(player -> player.chosenClass == playerClass)
                .findFirst().orElse(null);
    }
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
}
