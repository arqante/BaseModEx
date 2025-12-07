package basemod.extension.patches;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.extension.annotations.BaseModPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.util.Properties;

import static basemod.extension.BaseModExInit.modInfo;

/**
 * While this patch is active, custom cards added via {@link AutoAdd#cards()} will
 * be marked as seen but not as unlocked. The patch is enabled by default but can
 * be turned off in the mod's config menu.
 */
@BaseModPatch
@SpirePatch2(clz = AutoAdd.class, method = "cards")
public class AutoAddPreventUnlocks {

    public static SpireReturn<Void> Prefix(AutoAdd __instance) {
        if (!isConfigEnabled()) return SpireReturn.Continue();

        __instance.any(AbstractCard.class, ((info, card) -> {
            BaseMod.addCard(card);
            if (info.seen) {
                UnlockTracker.seenPref.putInteger(card.cardID, 1);
                UnlockTracker.seenPref.flush();
            }
        }));
        return SpireReturn.Return();
    }

    private static boolean isConfigEnabled() {
        try {
            Properties defaults = new Properties();
            defaults.setProperty("autoAddPreventUnlocks", "true");
            SpireConfig config = new SpireConfig(modInfo.ID, modInfo.ID, defaults);
            return config.getBool("autoAddPreventUnlocks");
        } catch (IOException e) {
            return false;
        }
    }
}
