package peasant_brigade.premises;

import java.util.HashSet;
import java.util.Set;

import peasant_brigade.vegetable.Vegetable;

public class BatchOfVegetables {
    private int kilos;
    private Set<Vegetable> batch;
    
    public BatchOfVegetables(Set<Vegetable> batch, int quantity) throws VegetableException {
        this.kilos = verify(quantity);
        if (!batch.isEmpty()) {
            this.batch = new HashSet<>(batch);
        }
    }

    private int verify(int quantity) throws VegetableException {
        if (quantity > 0) {
            return quantity;
        }

        throw new VegetableException("Vegetable credentials problem");
    }

    public int getKilos() {
        return this.kilos;
    }
}
