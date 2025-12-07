package basemod.extension.patches.hooks;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.extension.interfaces.PostReloadPrefsSubscriber;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static basemod.extension.BaseModExInit.logger;

@SpirePatch2(clz = CardCrawlGame.class, method = "reloadPrefs")
public class PostReloadPrefsHook {

    public static void Postfix() {
        logger.info("Publish PostReloadPrefs");

        for (PostReloadPrefsSubscriber sub : SubscribeHooks.postReloadPrefsSubscribers) {
            sub.receivePostReloadPrefs();
        }
        ArrayList<ISubscriber> toRemove = ReflectionHacks.getPrivateStatic(BaseMod.class, "toRemove");
        toRemove.stream().filter(sub -> sub instanceof PostReloadPrefsSubscriber)
                .forEach(sub -> BaseMod.unsubscribe(sub, PostReloadPrefsSubscriber.class));
    }
}
