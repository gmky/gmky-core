package gmky.core.aop;

import gmky.core.enumeration.FeatureFlagEnum;
import gmky.core.exception.NotFoundException;
import gmky.core.repository.FeatureFlagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class FeatureFlagAspect {
    private final FeatureFlagRepository featureFlagRepository;

    @Before("@annotation(enableFeatureFlag)")
    public void featureFlag(JoinPoint joinPoint, EnableFeatureFlag enableFeatureFlag) {
        var key = enableFeatureFlag.value();
        var defaultValue = enableFeatureFlag.defaultValue();
        var featureFlagState = featureFlagRepository.findByKey(key).map(gmky.core.entity.FeatureFlag::getState).orElse(defaultValue);
        if (featureFlagState == FeatureFlagEnum.OFF) {
            log.warn("Feature flag [{}] is OFF", key);
            throw new NotFoundException();
        }
    }
}
