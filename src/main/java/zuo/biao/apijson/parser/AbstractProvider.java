package zuo.biao.apijson.parser;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangls
 */
@Data
public abstract class AbstractProvider implements SQLProvider {

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

    private Message message = new Message();

    /**
     * 接收错误消息
     *
     * @param err
     */
    public void error(String err) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];

        this.message.error(err, ste);
    }


}
