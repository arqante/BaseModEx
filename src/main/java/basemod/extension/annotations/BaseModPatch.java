package basemod.extension.annotations;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A mark for {@link SpirePatch @SpirePatch} patches that modify classes from BaseMod.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BaseModPatch {
}
