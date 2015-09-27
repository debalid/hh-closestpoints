package com.company.util;

/**
 *  @author debalid
 *
 *  on 26.09.2015.
 */
public class Checking
{
    public static void require(boolean condition, String message, Object... notNull)
    {
        for (Object obj: notNull)
        {
            if (obj == null) throw new IllegalArgumentException("Argument should not be null.");
        }
        if (!condition) throw new IllegalArgumentException(message);
    }
}
