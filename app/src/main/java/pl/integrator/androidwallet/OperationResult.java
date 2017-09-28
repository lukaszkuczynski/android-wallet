package pl.integrator.androidwallet;

/**
 * Created by luk on 28.09.17.
 */

public class OperationResult {

    private String amount_after;
    private String description;
    private String timestamp;

    public String getAmount_after() {
        return amount_after;
    }

    public void setAmount_after(String amount_after) {
        this.amount_after = amount_after;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "amount_after='" + amount_after + '\'' +
                ", description='" + description + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
