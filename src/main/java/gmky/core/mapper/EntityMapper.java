package gmky.core.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SuppressWarnings("unused")
public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntityList(List<D> dtoList);

    List<D> toDtoList(List<E> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E partialUpdate(D dto, @MappingTarget E entity);

    default LocalDateTime toLocalDateTime(Instant instant) {
        if (instant == null) return null;
        return LocalDateTime.from(instant);
    }

    default Instant toInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
