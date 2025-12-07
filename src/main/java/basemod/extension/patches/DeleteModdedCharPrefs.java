package basemod.extension.patches;

import basemod.BaseMod;
import basemod.extension.BaseModExInit.Config;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor;

import static basemod.extension.BaseModExInit.logger;
import static com.megacrit.cardcrawl.core.CardCrawlGame.publisherIntegration;

/**
 * <p>While this patch is active, whenever a save slot is deleted, the preference files of
 * modded characters for that same slot will be removed as well.
 * <p>This prevents the Compendium and the Statistics menus from being unlocked instantly
 * whenever a new profile is created in a slot that has any leftover preference files of
 * modded characters from the previous profiles that used to be in that same slot.
 * <p>This patch is enabled by default but can be turned off in the mod's config menu.
 */
@SpirePatch2(clz = SaveHelper.class, method = "deletePrefs")
public class DeleteModdedCharPrefs {
    private static final String PREFS_DIR = !Settings.isBeta ? "preferences" : "betaPreferences";

    public static void Postfix(int slot) {
        if (!Config.deleteModdedCharPrefs) return;

        BaseMod.getModdedCharacters().stream()
                .map(player -> SaveHelper.slotName(player.chosenClass.toString(), slot))
                .map(slotName -> String.format("%s/%s", PREFS_DIR, slotName))
                .forEach(fileName -> {
                    Gdx.files.local(fileName).delete();
                    Gdx.files.local(fileName + ".backUp").delete();
                    logger.info("Deleted preference file: {}", fileName);
                });
    }
}
