package com.litsynp.todoapp.global.config

import com.p6spy.engine.common.P6Util
import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class P6SpyLogMessageFormatConfig {

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat =
            P6spySqlFormatConfiguration::class.java.name
    }
}

class P6spySqlFormatConfiguration : MessageFormattingStrategy {

    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        _sql: String,
        url: String
    ): String {
        var sql: String? = _sql
        sql = formatSql(category, sql)
        return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(
            prepared
        ) + sql
    }

    private fun formatSql(category: String, _sql: String?): String? {
        var sql = _sql
        if (sql == null || sql.trim { it <= ' ' } == "") {
            return sql
        }

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.name == category) {
            val tmpsql = sql.trim { it <= ' ' }.lowercase()
            sql =
                if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                    FormatStyle.DDL.formatter.format(sql)
                } else {
                    FormatStyle.BASIC.formatter.format(sql)
                }
            sql = "|\nFormatSql(P6Spy sql, Hibernate format):$sql"
        }
        return sql
    }
}
