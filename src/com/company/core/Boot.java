package com.company.core;

import com.company.util.Point;
import com.company.util.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 *  @author debalid
 *
 *  on 26.09.2015.
 */
class Boot
{
    public static void main(String[] args)
    {
        List<Point> input = Arrays.asList(new Point(0, 1), new Point(0, 3), new Point(0,10));
        GeometryAgent agent = new GeometryAgent();
        Tuple2<Point> closest = agent.calculateClosestPoints(input);
        System.out.println(closest + " :: " + agent.distance(closest._1, closest._2));
    }
}
