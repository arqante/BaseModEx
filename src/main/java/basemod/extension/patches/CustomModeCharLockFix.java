package basemod.extension.patches;

import basemod.BaseMod;
import basemod.extension.BaseModEx;
import basemod.extension.annotations.BaseModPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.characterManager;

/**
 * Prevents modded characters from being unlocked in Custom Mode if they are locked in Standard Mode.
 */
@BaseModPatch
@SpirePatch2(clz = BaseMod.class, method = "generateCustomCharacterOptions")
public class CustomModeCharLockFix {

    public static SpireReturn<ArrayList<CustomModeCharacterButton>> Prefix() {
        ArrayList<CustomModeCharacterButton> options = new ArrayList<>();

        for (AbstractPlayer player : BaseMod.getModdedCharacters()) {
            String unlockKey = BaseModEx.convertToUnlockKey(player.chosenClass);
            boolean isLocked = UnlockTracker.isCharacterLocked(unlockKey);
            AbstractPlayer chosenCharacter = characterManager.setChosenCharacter(player.chosenClass);
            options.add(new CustomModeCharacterButton(chosenCharacter, isLocked));
        }

        return SpireReturn.Return(options);
    }
}
