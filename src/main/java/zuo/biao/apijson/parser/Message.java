package zuo.biao.apijson.parser;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息通知类，通过这个类型把Provider的消息传递给SQLBuilder
 * 比如：当我们自定义操作遇到异常需要抛出异常的时候，往这个类里面塞StackTraceElement异常
 * 之后传到SQLBuilder中getSQL方法，统一抛出异常。
 *
 * @author Zerounary
 */
public class Message {

    @Getter
    @Setter
    private List<ErrorElement> errors = Lists.newArrayList();


    public void error(String errorMsg, StackTraceElement stack) {
        errors.add(new ErrorElement(errorMsg, stack));
    }

    public void cleanErrors() {
        errors.clear();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ErrorElement {
        private String errorMsg;
        private StackTraceElement stack;
    }
}

