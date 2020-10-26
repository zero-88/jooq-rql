package io.github.zero88.rql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import io.github.zero.rql.criteria.AbstractCriteriaBuilder;
import io.github.zero.rql.criteria.ComparisonCriteriaBuilder;
import io.github.zero.rql.parser.ast.ComparisonOperatorProxy;
import io.github.zero88.rql.jooq.JooqArgumentParser;
import io.github.zero88.rql.jooq.JooqFieldMapper;
import io.github.zero88.rql.jooq.JooqQueryContext;
import io.github.zero88.rql.jooq.criteria.JooqCriteriaBuilder;
import io.github.zero88.rql.jooq.criteria.JooqCriteriaBuilderFactory;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractComparisonCriteriaBuilder extends AbstractCriteriaBuilder<ComparisonNode>
    implements JooqCriteriaBuilder<ComparisonNode>, ComparisonCriteriaBuilder<ComparisonOperatorProxy> {

    @NonNull
    private final ComparisonOperatorProxy operator;

    public AbstractComparisonCriteriaBuilder(@NonNull ComparisonNode node) {
        this(node, ComparisonOperatorProxy.asProxy(node.getOperator()));
    }

    protected AbstractComparisonCriteriaBuilder(@NonNull ComparisonNode node,
                                                @NonNull ComparisonOperatorProxy operator) {
        super(node);
        this.operator = operator;
    }

    @Override
    public @NonNull Condition build(@NonNull TableLike table, @NonNull JooqQueryContext queryContext,
                                    @NonNull JooqCriteriaBuilderFactory factory) {
        final JooqFieldMapper fieldMapper = queryContext.fieldMapper();
        final JooqArgumentParser parser = queryContext.argumentParser();
        return fieldMapper.get(table, node().getSelector())
                          .map(f -> compare(f, node().getArguments(), parser))
                          .orElse(DSL.trueCondition());
    }

    protected abstract @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                                  @NonNull JooqArgumentParser parser);

}