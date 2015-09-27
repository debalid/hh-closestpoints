package com.company.util;

/**
 *  @author debalid
 *
 *  on 26.09.2015.
 */

public class Tuple2 <Type>
{
    public final Type _1, _2;

    protected Tuple2(Type _1, Type _2)
    {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString()
    {
        return '(' + (_1 != null ? _1.toString() : "Nothing") + ", " +(_2 != null ? _2.toString() : "Nothing") + ')';
    }

    public static <T> Tuple2<T> of (T _1, T _2)
    {
        return new Tuple2<>(_1, _2);
    }
}
