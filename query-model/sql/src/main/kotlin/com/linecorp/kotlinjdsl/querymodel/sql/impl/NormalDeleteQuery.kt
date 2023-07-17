package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference

@Internal
data class NormalDeleteQuery<T : Any>(
    val table: TableReference<T>,
    val where: Predicate?,
    val orderBy: Collection<Expression<*>>?,
) : DeleteQuery<T>
