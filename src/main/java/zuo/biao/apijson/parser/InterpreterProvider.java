package zuo.biao.apijson.parser;

import lombok.Getter;

import java.util.List;

/**
 * 该类延伸出来转换方法，让继承的子类实现
 * toXXX方法时转换逻辑，需要继承实现
 *
 * @author Zerounary
 */
public abstract class InterpreterProvider<T> extends BaseProvider {

    @Getter
    private T statementTypeHandle;
    @Getter
    private T setsHandle;
    @Getter
    private T selectHandle;
    @Getter
    private T tableHandle;
    @Getter
    private T joinHandle;
    @Getter
    private T innerJoinHandle;
    @Getter
    private T valuesHandle;
    @Getter
    private T outerJoinHandle;
    @Getter
    private T leftOuterJoinHandle;
    @Getter
    private T rightOuterJoinHandle;
    @Getter
    private T whereHandle;
    @Getter
    private T havingHandle;
    @Getter
    private T groupByHandle;
    @Getter
    private T orderByHandle;
    @Getter
    private T columnsHandle;
    @Getter
    private T lastListHandle;
    @Getter
    private T distinctHandle;

    public void setStatementTypeHandle(T statementTypeHandle) {
        setStatementType(toSatementType(statementTypeHandle));
        this.statementTypeHandle = statementTypeHandle;
    }

    public abstract StatementType toSatementType(T charger);

    public void setSetsHandle(T setsHandle) {
        setSets(toSets(setsHandle));
        this.setsHandle = setsHandle;
    }

    public abstract List<String> toSets(T charger);

    public void setSelectHandle(T selectHandle) {
        setSelect(toSelect(selectHandle));
        this.selectHandle = selectHandle;
    }

    public abstract List<String> toSelect(T charger);

    public void setTableHandle(T charger) {
        setTables(toTables(charger));
        this.tableHandle = charger;
    }

    public abstract List<String> toTables(T charger);

    public void setJoinHandle(T charger) {
        setJoin(toJoin(charger));
        this.joinHandle = charger;
    }

    public abstract List<String> toJoin(T charger);

    public void setInnerJoinHandle(T charger) {
        setInnerJoin(toInnerJoin(charger));
        this.innerJoinHandle = charger;
    }

    public abstract List<String> toInnerJoin(T charger);

    public void setValuesHandle(T charger) {
        setValues(toValues(charger));
        this.valuesHandle = charger;
    }

    public abstract List<String> toValues(T charger);

    public void setOuterJoinHandle(T charger) {
        setOuterJoin(toOuterJoin(charger));
        this.outerJoinHandle = charger;
    }

    public abstract List<String> toOuterJoin(T charger);

    public void setLeftOuterJoinHandle(T charger) {
        setLeftOuterJoin(toLeftOuterJoin(charger));
        this.leftOuterJoinHandle = charger;
    }

    public abstract List<String> toLeftOuterJoin(T charger);

    public void setRightOuterJoinHandle(T charger) {
        setRightOuterJoin(toRightOuterJoin(charger));
        this.rightOuterJoinHandle = charger;
    }

    public abstract List<String> toRightOuterJoin(T charger);

    public void setWhereHandle(T charger) {
        setWhere(toWhere(charger));
        this.whereHandle = charger;
    }

    public abstract List<String> toWhere(T charger);

    public void setHavingHandle(T charger) {
        setHaving(toHaving(charger));
        this.havingHandle = charger;
    }

    public abstract List<String> toHaving(T charger);

    public void setGroupByHandle(T charger) {
        setGroupBy(toGroupBy(charger));
        this.groupByHandle = charger;
    }

    public abstract List<String> toGroupBy(T charger);

    public void setOrderByHandle(T charger) {

        this.orderByHandle = charger;
    }

    public abstract List<String> toOrderBy(T charger);

    public void setColumnsHandle(T charger) {
        setColumns(toColumns(charger));
        this.columnsHandle = charger;
    }

    public abstract List<String> toColumns(T charger);

    public void setLastListHandle(T charger) {
        setLastList(toLastList(charger));
        this.lastListHandle = charger;
    }

    public abstract List<String> toLastList(T charger);

    public void setDistinctHandle(T charger) {
        super.setDistinct(toDistinct(charger));
        this.distinctHandle = charger;
    }

    public abstract boolean toDistinct(T charger);

}
