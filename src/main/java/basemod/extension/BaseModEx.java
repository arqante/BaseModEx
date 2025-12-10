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

import java.util.HashMap;
import java.util.Map;

import static basemod.extension.BaseModExInit.logger;

public class BaseModEx {

    // ------------------------- CARD COLOR FUNCTIONS -------------------------
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

    // -------------------------- Replace functions ---------------------------
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


    // --------------------------- UNLOCK FUNCTIONS ---------------------------
    // ------------------------- Unlocking functions --------------------------
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
     * Unlocks either a card, a relic, or a character via their unlock key.
     * @param unlockKey a {@link String} containing an unlock key
     * @param unlockType the type of unlock
     */
    public static void unlockByType(String unlockKey, AbstractUnlock.UnlockType unlockType) {
        if (unlockType == AbstractUnlock.UnlockType.CARD) {
            unlockCard(unlockKey);
        } else if (unlockType == AbstractUnlock.UnlockType.RELIC) {
            unlockRelic(unlockKey);
        } else if (unlockType == AbstractUnlock.UnlockType.CHARACTER) {
            unlockCharacter(unlockKey);
        } else {
            throw new IllegalArgumentException("Unsupported unlock type: " + unlockType);
        }
    }

    /**
     * Unlocks a character. This method is similar to {@link UnlockTracker#hardUnlockOverride}
     * but also removes the character from the {@code lockedCharacters} list.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void unlockCharacter(AbstractPlayer.PlayerClass playerClass) {
        unlockCharacter(convertToUnlockKey(playerClass));
        logger.info("Unlocked character: {}", playerClass);
    }

    /**
     * Unlocks a character. This method is similar to {@link UnlockTracker#hardUnlockOverride}
     * but also removes the character from the {@code lockedCharacters} list.
     * @param unlockKey a {@link String} containing a character unlock key
     */
    public static void unlockCharacter(String unlockKey) {
        UnlockTracker.lockedCharacters.remove(unlockKey);
        UnlockTracker.unlockPref.putInteger(unlockKey, 2);
        UnlockTracker.unlockPref.flush();
    }

    /**
     * Unlocks all unlock bundles of a character and marks their contents as seen.
     * Additionally, sets the character's unlock progress to their max level (usually 5).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void unlockAllBundles(AbstractPlayer.PlayerClass playerClass) {
        for (int i = 0; i < getMaxUnlockLevel(playerClass); i++) {
            unlockBundle(playerClass, i);
        }
        setUnlockProgressToMax(playerClass);
        logger.info("Unlocked all bundles for: {}", playerClass);
    }

    /**
     * Unlocks a character's unlock bundle and marks its contents as seen.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @param unlockLevel the level of the bundle to be unlocked
     */
    public static void unlockBundle(AbstractPlayer.PlayerClass playerClass, int unlockLevel) {
        getUnlocks(playerClass, unlockLevel).forEach(BaseModEx::unlockByType);
    }

    /**
     * Unlocks a card and marks it as seen. This method is basically
     * a wrapper around {@link UnlockTracker#unlockCard}.
     * @param unlockKey a {@link String} containing a card unlock key
     */
    public static void unlockCard(String unlockKey) {
        UnlockTracker.unlockCard(unlockKey);
    }

    /**
     * Unlocks a relic and marks it as seen. This method combines
     * {@link UnlockTracker#hardUnlockOverride hardUnlockOverride}
     * with {@link UnlockTracker#markRelicAsSeen markRelicAsSeen}
     * but without any checks and console messages.
     * @param unlockKey a {@link String} containing a relic unlock key
     */
    public static void unlockRelic(String unlockKey) {
        UnlockTracker.unlockPref.putInteger(unlockKey, 2);
        UnlockTracker.unlockPref.flush();
        UnlockTracker.relicSeenPref.putInteger(unlockKey, 1);
        UnlockTracker.relicSeenPref.flush();
        RelicLibrary.getRelic(unlockKey).isSeen = true;
    }

    // -------------------------- Locking functions ---------------------------
    /**
     * Locks a character and all of their unlock bundles. Additionally, resets
     * the character's unlock progress to 0.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void lockAllFor(AbstractPlayer.PlayerClass playerClass) {
        lockCharacter(playerClass);
        lockAllBundles(playerClass);
    }

    /**
     * Locks either a card, a relic, or a character via their unlock key.
     * @param unlockKey a {@link String} containing an unlock key
     * @param unlockType the type of unlock
     */
    public static void lockByType(String unlockKey, AbstractUnlock.UnlockType unlockType) {
        if (unlockType == AbstractUnlock.UnlockType.CARD) {
            lockCard(unlockKey);
        } else if (unlockType == AbstractUnlock.UnlockType.RELIC) {
            lockRelic(unlockKey);
        } else if (unlockType == AbstractUnlock.UnlockType.CHARACTER) {
            lockCharacter(unlockKey);
        } else {
            throw new IllegalArgumentException("Unsupported unlock type: " + unlockType);
        }
    }

    /**
     * Locks a character unconditionally. This method allows to relock a once unlocked character.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void lockCharacter(AbstractPlayer.PlayerClass playerClass) {
        lockCharacter(convertToUnlockKey(playerClass));
        logger.info("Locked character: {}", playerClass);
    }

    /**
     * Locks a character unconditionally. This method allows to relock a once unlocked character.
     * @param unlockKey a {@link String} containing a character unlock key
     */
    public static void lockCharacter(String unlockKey) {
        UnlockTracker.lockedCharacters.add(unlockKey);
        UnlockTracker.unlockPref.data.remove(unlockKey);
        UnlockTracker.unlockPref.flush();
    }

    /**
     * Locks all unlock bundles of a character and marks their contents as unseen.
     * Additionally, resets the character's unlock progress to 0.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void lockAllBundles(AbstractPlayer.PlayerClass playerClass) {
        for (int i = 0; i < getMaxUnlockLevel(playerClass); i++) {
            lockBundle(playerClass, i);
        }
        UnlockTracker.resetUnlockProgress(playerClass);
        logger.info("Locked all bundles for: {}", playerClass);
    }

    /**
     * Locks a character's unlock bundle and marks its contents as unseen.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @param unlockLevel the level of the bundle to be locked
     */
    public static void lockBundle(AbstractPlayer.PlayerClass playerClass, int unlockLevel) {
        getUnlocks(playerClass, unlockLevel).forEach(BaseModEx::lockByType);
    }

    /**
     * Locks a card and marks it as unseen.
     * @param unlockKey a {@link String} containing a card unlock key
     */
    public static void lockCard(String unlockKey) {
        UnlockTracker.lockedCards.add(unlockKey);
        UnlockTracker.unlockPref.data.remove(unlockKey);
        UnlockTracker.seenPref.data.remove(unlockKey);
        UnlockTracker.unlockPref.flush();
        UnlockTracker.seenPref.flush();
    }

    /**
     * Locks a relic and marks it as unseen.
     * @param unlockKey a {@link String} containing a relic unlock key
     */
    public static void lockRelic(String unlockKey) {
        UnlockTracker.lockedRelics.add(unlockKey);
        UnlockTracker.unlockPref.data.remove(unlockKey);
        UnlockTracker.relicSeenPref.data.remove(unlockKey);
        UnlockTracker.unlockPref.flush();
        UnlockTracker.relicSeenPref.flush();
    }

    // ------------------------- Auxiliary functions --------------------------
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

        String name = playerClass.toString().toLowerCase();
        name = name.startsWith("the_") ? name.substring(4) : name;
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Returns the contents of a character's unlock bundle as a {@link Map}.
     * Each entry represents a mapping between a {@link String} unlock key
     * and an unlock type (either a card or a relic unlock).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @param unlockLevel the level of the unlock bundle
     * @return a {@link Map} containing unlock keys and unlock types
     */
    public static Map<String, AbstractUnlock.UnlockType> getUnlocks(
            AbstractPlayer.PlayerClass playerClass, int unlockLevel) {
        Map<String, AbstractUnlock.UnlockType> unlocks = new HashMap<>();

        if (isModdedCharacter(playerClass)) {
            CustomUnlockBundle bundle = BaseMod.getUnlockBundleFor(playerClass, unlockLevel);
            for (String unlockKey : bundle.getUnlockIDs()) {
                unlocks.put(unlockKey, bundle.unlockType);
            }
        } else {
            for (AbstractUnlock unlock : UnlockTracker.getUnlockBundle(playerClass, unlockLevel)) {
                unlocks.put(unlock.key, unlock.type);
            }
        }
        return unlocks;
    }

    /**
     * Same as {@link BaseMod#getMaxUnlockLevel} but returns 5 for the base game characters.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return the character's max unlock level
     */
    public static int getMaxUnlockLevel(AbstractPlayer.PlayerClass playerClass) {
        if (!isModdedCharacter(playerClass)) return 5;
        return BaseMod.getMaxUnlockLevel(playerClass);
    }

    /**
     * Sets a character's unlock progress to their max level (usually 5).
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     */
    public static void setUnlockProgressToMax(AbstractPlayer.PlayerClass playerClass) {
        int totalScore = 50;
        int currentCost = 300;
        int unlockLevel = getMaxUnlockLevel(playerClass);

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
    // ------------------------------------------------------------------------


    // ------------------------- CHARACTER FUNCTIONS --------------------------
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
     * Finds a modded character by their {@link AbstractPlayer.PlayerClass PlayerClass} enum.
     * This method is similar to BaseMod's {@link BaseMod#findCharacter} method but returns
     * a {@link CustomPlayer} object instead.
     * @param playerClass the character's {@link AbstractPlayer.PlayerClass PlayerClass} enum
     * @return a {@link CustomPlayer} object, or {@code null} if no character could be found
     */
    public static CustomPlayer findModdedCharacter(AbstractPlayer.PlayerClass playerClass) {
        return (CustomPlayer) BaseMod.getModdedCharacters().stream()
                .filter(player -> player.chosenClass == playerClass)
                .findFirst().orElse(null);
    }
}
