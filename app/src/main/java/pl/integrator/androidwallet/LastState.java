package pl.integrator.androidwallet;

/**
 * Created by luk on 20.09.17.
 */

class LastState {
    private double totalAfter;
    private Operation operation;

    public LastState(double totalAfter) {
        this.totalAfter = totalAfter;
    }

    public double getTotalAfter() {
        return totalAfter;
    }

    public Operation getOperation() {
        return operation;
    }
}
