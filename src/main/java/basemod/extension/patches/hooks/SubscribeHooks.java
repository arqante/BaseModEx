package basemod.extension.patches.hooks;

import basemod.BaseMod;
import basemod.extension.annotations.BaseModPatch;
import basemod.extension.interfaces.PostReloadPrefsSubscriber;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

import java.util.ArrayList;
import java.util.List;

public class SubscribeHooks {
    static List<PostReloadPrefsSubscriber> postReloadPrefsSubscribers;

    @BaseModPatch
    @SpirePatch2(clz = BaseMod.class, method = "initializeSubscriptions")
    public static class InitializeSubscriptions {
        public static void Postfix() {
            postReloadPrefsSubscribers = new ArrayList<>();
        }
    }

    @BaseModPatch
    @SpirePatch2(clz = BaseMod.class, method = "subscribe", paramtypez = ISubscriber.class)
    public static class SubscribeAll {
        public static void Postfix(ISubscriber sub) {
            if (sub instanceof PostReloadPrefsSubscriber) {
                postReloadPrefsSubscribers.add((PostReloadPrefsSubscriber) sub);
            }
        }
    }

    @BaseModPatch
    @SpirePatch2(clz = BaseMod.class, method = "subscribe", paramtypez = { ISubscriber.class, Class.class })
    public static class SubscribeOne {
        public static SpireReturn<Void> Prefix(ISubscriber sub, Class<? extends ISubscriber> additionClass) {
            if (additionClass.equals(PostReloadPrefsSubscriber.class)) {
                postReloadPrefsSubscribers.add((PostReloadPrefsSubscriber) sub);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @BaseModPatch
    @SpirePatch2(clz = BaseMod.class, method = "unsubscribe", paramtypez = ISubscriber.class)
    public static class UnsubscribeAll {
        public static void Postfix(ISubscriber sub) {
            if (sub instanceof PostReloadPrefsSubscriber) {
                postReloadPrefsSubscribers.remove((PostReloadPrefsSubscriber) sub);
            }
        }
    }

    @BaseModPatch
    @SpirePatch2(clz = BaseMod.class, method = "unsubscribe", paramtypez = { ISubscriber.class, Class.class })
    public static class UnsubscribeOne {
        public static SpireReturn<Void> Prefix(ISubscriber sub, Class<? extends ISubscriber> removalClass) {
            if (removalClass.equals(PostReloadPrefsSubscriber.class)) {
                postReloadPrefsSubscribers.remove((PostReloadPrefsSubscriber) sub);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
