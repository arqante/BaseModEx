package basemod.extension.patches;

import basemod.extension.BaseModExInit;
import basemod.extension.annotations.BaseModPatch;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * In the Card Library, replaces tab bars for modded characters with better looking ones.
 */
@BaseModPatch
@SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
public class CardLibraryBetterTabBars {

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            private final String replacement = String.format(
                    "{ $1 = %s.modBar; $2 = 32.0F * %s.scale; " +
                            "$6 = 250.0F; $13 = 250; $_ = $proceed($$); }",
                    BaseModExInit.class.getName(), Settings.class.getName()
            );

            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("draw")) m.replace(replacement);
            }
        };
    }
}
