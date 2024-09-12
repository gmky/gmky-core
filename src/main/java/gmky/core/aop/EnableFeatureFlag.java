package gmky.core.aop;

import gmky.core.enumeration.FeatureFlagEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableFeatureFlag {
    String value() default "";

    FeatureFlagEnum defaultValue() default FeatureFlagEnum.OFF;
}
