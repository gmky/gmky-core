package gmky.core.web.rest.v1;

import gmky.core.aop.EnableFeatureFlag;
import gmky.core.api.MetadataApi;
import gmky.core.enumeration.UserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static gmky.core.common.Constants.FF_METADATA;
import static gmky.core.utils.ResponseUtil.data;

@Slf4j
@RestController
public class MetadataResource implements MetadataApi {
    @Override
    @EnableFeatureFlag(FF_METADATA)
    public ResponseEntity<List<UserStatusEnum>> getUserStatuses() {
        var enumValues = Arrays.stream(UserStatusEnum.values()).toList();
        return data(enumValues);
    }
}
