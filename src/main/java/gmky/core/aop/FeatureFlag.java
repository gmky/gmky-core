package gmky.core.aop;

import gmky.core.enumeration.FeatureFlagEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureFlag {
    String value() default "";

    FeatureFlagEnum defaultValue() default FeatureFlagEnum.OFF;
}
