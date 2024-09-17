package gmky.core.aop;

import gmky.core.entity.FeatureFlagEntity;
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

    @Before("@annotation(featureFlag)")
    public void featureFlag(JoinPoint joinPoint, FeatureFlag featureFlag) {
        log.info("Check for method");
        checkFeatureFlag(featureFlag);
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void featureFlag(JoinPoint joinPoint) {
        log.info("Check for class");
        Class<?> targetClass = joinPoint.getTarget().getClass();
        if (targetClass.isAnnotationPresent(FeatureFlag.class)) {
            FeatureFlag featureFlag = targetClass.getAnnotation(FeatureFlag.class);
            checkFeatureFlag(featureFlag);
        }
    }

    private void checkFeatureFlag(FeatureFlag featureFlag) {
        var key = featureFlag.value();
        var defaultValue = featureFlag.defaultValue();
        var featureFlagState = featureFlagRepository.findByKey(key).map(FeatureFlagEntity::getState).orElse(defaultValue);
        if (featureFlagState == FeatureFlagEnum.OFF) {
            log.warn("Feature flag [{}] is OFF", key);
            throw new NotFoundException();
        }
    }
}
