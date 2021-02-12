package ru.heatalways.mtuci_labs.lab3;

import java.util.Comparator;
import java.util.HashMap;

public class AStarState {
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    /**
     * Containers that keeps locations of opened and closed nodes
     */
    private final HashMap<Location, Waypoint> closedWaypoints = new HashMap<>();
    private final HashMap<Location, Waypoint> openedWaypoints = new HashMap<>();


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        return openedWaypoints
                .values()
                .stream()
                .min(Comparator.comparing(Waypoint::getTotalCost)).orElse(null);
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location newLocation = newWP.loc;

        if (!openedWaypoints.containsKey(newLocation)) {
            openedWaypoints.put(newLocation, newWP);
            return true;
        } else {
            Waypoint currentWaypoint = openedWaypoints.get(newLocation);
            if (newWP.getRemainingCost() < currentWaypoint.getRemainingCost()) {
                openedWaypoints.put(newLocation, newWP);
                return true;
            }
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openedWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint removedWaypoint = openedWaypoints.get(loc);
        openedWaypoints.remove(loc);
        closedWaypoints.put(loc, removedWaypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedWaypoints.containsKey(loc);
    }
}
