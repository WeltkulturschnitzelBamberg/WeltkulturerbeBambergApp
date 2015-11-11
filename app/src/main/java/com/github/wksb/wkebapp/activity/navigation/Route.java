package com.github.wksb.wkebapp.activity.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 27.10.2015.
 */
public class Route {
    private final String name;
    private final List<RouteSegment> routeSegmentsList;

    public Route(String name) {
        this.name = name;
        this.routeSegmentsList = new ArrayList<>();
    }

    public void addRouteSegment(RouteSegment routeSegment) {
        routeSegmentsList.add(routeSegment);
    }

    public List<RouteSegment> getRouteSegments() {
        return this.routeSegmentsList;
    }
}
