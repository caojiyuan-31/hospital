package cn.ganwuwang.hospital.domain.query;

import java.io.Serializable;


public class Query<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public Query(T t){
        this.query = t;
    }

    @Override
    public String toString() {

        return "Query{" +
                "query=" + String.valueOf(query) +
                '}';
    }
}