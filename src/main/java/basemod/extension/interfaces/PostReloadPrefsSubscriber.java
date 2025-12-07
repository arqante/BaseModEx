package basemod.extension.interfaces;

import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.core.CardCrawlGame;

/**
 * Classes that implement this interface can inject their code at the end
 * of the {@link CardCrawlGame#reloadPrefs()} method.
 */
public interface PostReloadPrefsSubscriber extends ISubscriber {

    void receivePostReloadPrefs();
}
