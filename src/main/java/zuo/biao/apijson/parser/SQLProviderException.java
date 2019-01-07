package zuo.biao.apijson.parser;


import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zuo.biao.apijson.parser.Message.ErrorElement;

/**
 * @author zhangls
 */
@NoArgsConstructor
public class SQLProviderException extends Exception {

    @Getter
    @Setter
    private List<ErrorElement> errors;
    @Getter
    @Setter
    private Set<String> errorMsgs = Sets.newHashSet();


    public SQLProviderException(String message) {
        super(message);
    }

    public SQLProviderException(Throwable cause) {
        super(cause);
    }

    public SQLProviderException(List<ErrorElement> errors) {
        List<StackTraceElement> stackTrace = Lists.newArrayList();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < errors.size(); i++) {
            errorMsgs.add(errors.get(i).getErrorMsg() + Utils.NEW_LINE);
            sb.append(i + ".Error message: ----> " + errors.get(i).getErrorMsg() + Utils.NEW_LINE);
            stackTrace.add(errors.get(i).getStack());
        }
        System.err.print(sb.toString());
        StackTraceElement[] stack = new StackTraceElement[stackTrace.size()];
        super.setStackTrace(stackTrace.toArray(stack));
        this.errors = errors;
    }
}
