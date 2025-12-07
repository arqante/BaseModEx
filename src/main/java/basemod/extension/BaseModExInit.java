package basemod.extension;

import basemod.BaseMod;
import basemod.EasyConfigPanel;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@SpireInitializer
public class BaseModExInit implements PostInitializeSubscriber, EditStringsSubscriber {
    public static final ModInfo modInfo;
    public static final Logger logger;

    static {
        modInfo = Arrays.stream(Loader.MODINFOS)
                .filter(modInfo -> modInfo.ID.equals("BaseModEx"))
                .findFirst().orElseThrow(() -> new RuntimeException("Failed to load ModInfo"));

        logger = LogManager.getLogger(BaseModEx.class.getName());
    }

    public BaseModExInit() {
        BaseMod.subscribe(this);
        logger.info("Subscribed to BaseMod");
    }

    public static void initialize() {
        new BaseModExInit();
    }

    @Override
    public void receivePostInitialize() {
        Texture badge = new Texture("basemod/extension/images/ModBadge.png");
        badge.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BaseMod.registerModBadge(badge, modInfo.Name, Arrays.toString(modInfo.Authors),
                modInfo.Description, new Config());
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(UIStrings.class, "basemod/extension/strings/UIStrings.json");
    }

    public static class Config extends EasyConfigPanel {
        public static boolean deleteModdedCharPrefs = true;
        public static boolean autoAddPreventUnlocks = true;

        public Config() {
            super(modInfo.ID, CardCrawlGame.languagePack.getUIString(
                    String.format("%s:Config", modInfo.ID)), modInfo.ID);
        }
    }
}
