package com.company.core;

import com.company.util.Point;
import com.company.util.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

import static com.company.util.Checking.require;

/**
 *  @author debalid
 *
 *  on 26.09.2015.
 *
 *  Watch https://en.wikipedia.org/wiki/Closest_pair_of_points_problem#Planar_case for the inspiration.
 */
public class GeometryAgent
{
    /**
     * Represents the inability to establish connection between any two points in current set
     * For example, just one point or empty set.
     */
    private final Tuple2<Point> EmptyPair = Tuple2.of(null, null);

    public Tuple2<Point> calculateClosestPoints(List<Point> points)
    {
        require(points.size() >= 2, "Should contains at least 2 points!!", points);
        List<Point> res = new ArrayList<>(points);

        res.sort((Point x, Point y) -> Math.round(x._1 - y._1));

        return calc_rec(res);
    }

    private Tuple2<Point> calc_rec(List<Point> points)
    {
        require(points.size() > 0, "May not be less than one point.", points);

        //Termination conditions.
        if (points.size() == 2) return Tuple2.of(points.get(0), points.get(1));
        if (points.size() == 1) return EmptyPair;

        int n = points.size() / 2;
        Point mean = points.get(Math.round(n));

        List<Point> leftHalf = points.subList(0, n);
        List<Point> rightHalf = points.subList(n, points.size());

        //System.out.println(Arrays.toString(points.toArray()));
        //System.out.println(Arrays.toString(leftHalf.toArray()));
        //System.out.println(Arrays.toString(rightHalf.toArray()));

        List<Tuple2<Point>> minimums = Arrays.asList(
                calc_rec(leftHalf),
                calc_rec(rightHalf)
        );

        Tuple2<Point> min = minimum(minimums)
                .orElseThrow(
                        () ->
                                new IllegalStateException("Cannot find minimum in " + Arrays.toString(minimums.toArray()))
                );

        float h = (float)Math.sqrt(distanceSquare(min._1, min._2));

        List<Tuple2<Point>> possibleAlternatives = leftHalf.stream()
                .filter(x -> Math.abs(x._1 - mean._1) < h)
                .flatMap(x -> checkForPossibleMinimums(x, rightHalf, h).stream().map(t -> Tuple2.of(x, t)))
                .collect(Collectors.toList());

        possibleAlternatives.add(min);

        return minimum(possibleAlternatives)
                .orElseThrow(
                        () ->
                                new IllegalStateException("Cannot find minimum in " + Arrays.toString(minimums.toArray()))
                );
    }

    private Optional<Tuple2<Point>> minimum(List<Tuple2<Point>> from)
    {
        require (true, "", from);
        return from.stream()
                .min(
                        (left, right) -> {
                            if (left == EmptyPair) return 1;
                            if (right == EmptyPair) return -1;
                            return (int) Math.round(distanceSquare(left._1, left._2) - distanceSquare(right._1, right._2));
                        }
                );
    }

    private double distanceSquare(Point first, Point second)
    {
        require(true, "", first, second);
        return Math.pow(first._1 - second._1, 2) + Math.pow(first._2 - second._2, 2);
    }

    /**
     * @param p The point from left half of points divided (should be in strip ())
     * @param rightHalf The right half of points divided.
     * @param h The minimum distance of left half minimum and right half minimum.
     */
    private List<Point> checkForPossibleMinimums(Point p, List<Point> rightHalf, float h)
    {
        require(h > 0, "Distance cannot be negative.", p, rightHalf);
        return rightHalf.stream().filter(
                x -> Math.abs(p._1 - x._1) < 2 * h && Math.abs(p._2 - x._2) < h
        ).collect(Collectors.toList());
    }

    public double distance(Point first, Point second)
    {
        require(true, "", first, second);
        return Math.sqrt(distanceSquare(first, second));
    }
}
