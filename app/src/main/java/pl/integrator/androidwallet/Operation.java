package pl.integrator.androidwallet;

import android.location.Location;

/**
 * Created by luk on 20.09.17.
 */

class Operation {
    private static final String OPERATION_TYPE_SPENDING = "spending";
    private final Location location;
    private double amount;
    private String description;
    private String op_type = OPERATION_TYPE_SPENDING;


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


    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", op_type='" + op_type + '\'' +
                '}';
    }
}
