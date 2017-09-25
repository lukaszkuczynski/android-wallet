package pl.integrator.androidwallet;

import android.location.Location;

/**
 * Created by luk on 20.09.17.
 */

class Operation {
    private final Location location;
    private double amount;
    private String description;

    public Operation(double amount, String description, Location location) {
        this.amount = amount;
        this.description = description;
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }
}
