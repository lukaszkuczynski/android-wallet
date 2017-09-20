package pl.integrator.androidwallet;

/**
 * Created by luk on 20.09.17.
 */

class Operation {
    private double amount;
    private String description;

    public Operation(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
