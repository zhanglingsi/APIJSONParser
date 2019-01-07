package zuo.biao.apijson.parser;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangls
 */
public class SQLExplorer {
    private static final String AND = ") \nAND (";
    private static final String OR = ") \nOR (";
    private SQLStatement sql = new SQLStatement();

    @Getter
    @Setter
    private SQLProvider sqlProvider;

    public SQLExplorer(SQLProvider sqlProvider) {
        this.sqlProvider = sqlProvider;
    }

    public String getSQL() throws SQLProviderException {
        feedSQLStatement();
        //异常处理
        if (!sqlProvider.getMessage().getErrors().isEmpty()) {
            throw new SQLProviderException(sqlProvider.getMessage().getErrors());
        }
        StringBuffer sb = new StringBuffer();
        sql.sql(sb);
        return sb.toString();
    }

    /**
     * 将提供器提供的数据刷新到SQLStatement中
     */
    private void feedSQLStatement() {
        sql = new SQLStatement();
        if (sqlProvider.getStatementType() == null) {
            return;
        }

        sql.statementType = sqlProvider.getStatementType();
        sql.sets.addAll(ofNullable(sqlProvider.getSets()));
        sql.select.addAll(ofNullable(sqlProvider.getSelect()));
        sql.tables.addAll(ofNullable(sqlProvider.getTables()));
        sql.join.addAll(ofNullable(sqlProvider.getJoin()));
        sql.innerJoin.addAll(ofNullable(sqlProvider.getInnerJoin()));
        sql.outerJoin.addAll(ofNullable(sqlProvider.getOuterJoin()));
        sql.leftOuterJoin.addAll(ofNullable(sqlProvider.getLeftOuterJoin()));
        sql.rightOuterJoin.addAll(ofNullable(sqlProvider.getRightOuterJoin()));
        sql.where.addAll(ofNullable(sqlProvider.getWhere()));
        sql.having.addAll(ofNullable(sqlProvider.getHaving()));
        sql.groupBy.addAll(ofNullable(sqlProvider.getGroupBy()));
        sql.orderBy.addAll(ofNullable(sqlProvider.getOrderBy()));
        sql.lastList.addAll(ofNullable(sqlProvider.getLastList()));
        sql.columns.addAll(ofNullable(sqlProvider.getColumns()));
        sql.values.addAll(ofNullable(sqlProvider.getValues()));
    }

    private List<String> ofNullable(List<String> list) {
        if (list == null) {
            return Lists.newArrayList();
        }
        return list;
    }

    private static class SafeAppendable {
        private final Appendable a;
        private boolean empty = true;

        public SafeAppendable(Appendable a) {
            super();
            this.a = a;
        }

        public SafeAppendable append(CharSequence s) {
            try {
                if (empty && s.length() > 0) {
                    empty = false;
                }
                a.append(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public boolean isEmpty() {
            return empty;
        }

    }

    @NoArgsConstructor
    private static class SQLStatement {

        private StatementType statementType;
        private List<String> sets = Lists.newArrayList();
        private List<String> select = Lists.newArrayList();
        private List<String> tables = Lists.newArrayList();
        private List<String> join = Lists.newArrayList();
        private List<String> innerJoin = Lists.newArrayList();
        private List<String> outerJoin = Lists.newArrayList();
        private List<String> leftOuterJoin = Lists.newArrayList();
        private List<String> rightOuterJoin = Lists.newArrayList();
        private List<String> where = Lists.newArrayList();
        private List<String> having = Lists.newArrayList();
        private List<String> groupBy = Lists.newArrayList();
        private List<String> orderBy = Lists.newArrayList();
        private List<String> lastList = Lists.newArrayList();
        private List<String> columns = Lists.newArrayList();
        private List<String> values = Lists.newArrayList();
        private boolean distinct;

        private void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close, String conjunction) {
            if (!parts.isEmpty()) {
                if (!builder.isEmpty()) {
                    builder.append("\n");
                }
                builder.append(keyword);
                builder.append(" ");
                builder.append(open);
                String last = "________";
                for (int i = 0, n = parts.size(); i < n; i++) {
                    String part = parts.get(i);
                    if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
                        builder.append(conjunction);
                    }
                    builder.append(part);
                    last = part;
                }
                builder.append(close);
            }
        }

        private String selectSQL(SafeAppendable builder) {
            if (distinct) {
                sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
            } else {
                sqlClause(builder, "SELECT", select, "", "", ", ");
            }

            sqlClause(builder, "FROM", tables, "", "", ", ");
            joins(builder);
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
            sqlClause(builder, "HAVING", having, "(", ")", " AND ");
            sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
            return builder.toString();
        }

        private void joins(SafeAppendable builder) {
            sqlClause(builder, "JOIN", join, "", "", "\nJOIN ");
            sqlClause(builder, "INNER JOIN", innerJoin, "", "", "\nINNER JOIN ");
            sqlClause(builder, "OUTER JOIN", outerJoin, "", "", "\nOUTER JOIN ");
            sqlClause(builder, "LEFT OUTER JOIN", leftOuterJoin, "", "", "\nLEFT OUTER JOIN ");
            sqlClause(builder, "RIGHT OUTER JOIN", rightOuterJoin, "", "", "\nRIGHT OUTER JOIN ");
        }

        private String insertSQL(SafeAppendable builder) {
            sqlClause(builder, "INSERT INTO", tables, "", "", "");
            sqlClause(builder, "", columns, "(", ")", ", ");
            sqlClause(builder, "VALUES", values, "(", ")", ", ");
            return builder.toString();
        }

        private String deleteSQL(SafeAppendable builder) {
            sqlClause(builder, "DELETE FROM", tables, "", "", "");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        private String updateSQL(SafeAppendable builder) {
            sqlClause(builder, "UPDATE", tables, "", "", "");
            joins(builder);
            sqlClause(builder, "SET", sets, "", "", ", ");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        public String sql(Appendable a) {
            SafeAppendable builder = new SafeAppendable(a);
            if (statementType == null) {
                return null;
            }

            String answer;

            switch (statementType) {
                case DELETE:
                    answer = deleteSQL(builder);
                    break;

                case INSERT:
                    answer = insertSQL(builder);
                    break;

                case SELECT:
                    answer = selectSQL(builder);
                    break;

                case UPDATE:
                    answer = updateSQL(builder);
                    break;

                default:
                    answer = null;
            }

            return answer;
        }
    }
}
