package io.github.zero88.rql.jooq;

import java.util.Optional;

import org.jooq.Field;
import org.jooq.TableLike;

import io.github.zero88.rql.FieldMapper;
import io.github.zero88.utils.Strings;

import lombok.NonNull;

/**
 * The interface Field mapper.
 *
 * @since 1.0.0
 */
public interface JooqFieldMapper extends FieldMapper {

    /**
     * Represents default mapper that keep {@code request field} as it is
     */
    JooqFieldMapper DEFAULT = (t, f) -> Optional.ofNullable(t.field(f));

    /**
     * Represents UPPERCASE_MAPPER that transforms {@code request field} to snake upper case {@code database field}
     */
    JooqFieldMapper SNAKE_UPPERCASE_MAPPER = (t, f) -> Optional.ofNullable(t.field(Strings.toSnakeCaseUC(f)));

    /**
     * Get {@code database field} from {@code request field}.
     *
     * @param table the table
     * @param field the request field
     * @return the database field in optional
     * @since 1.0.0
     */
    Optional<Field> get(@NonNull TableLike table, @NonNull String field);

}
