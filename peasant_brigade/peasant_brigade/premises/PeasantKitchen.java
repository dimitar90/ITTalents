package peasant_brigade.premises;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import peasant_brigade.vegetable.Vegetable;
import peasant_brigade.vegetable.VegetableType;

public class PeasantKitchen {

    private static PeasantKitchen instance;

    private ConcurrentHashMap<VegetableType, BlockingQueue<Vegetable>> pansForVegetables;

    private PeasantKitchen() {
        for (VegetableType type : VegetableType.values()) {
            this.pansForVegetables.put(type, new ArrayBlockingQueue<>(30));
        }
    }

    public static PeasantKitchen getInstance() {
        if (instance == null) {
            instance = new PeasantKitchen();
        }

        return instance;
    }

    public void putVegetableInKitchen(Vegetable vegetable) throws InterruptedException {
        if (vegetable != null) {
            VegetableType type = vegetable.getType();
            this.pansForVegetables.get(type).put(vegetable);
        }
    }

    public Set<Vegetable> getBatchOfVegetables() throws InterruptedException {
        Set<Vegetable> batch = new HashSet<Vegetable>();

        for (VegetableType type : VegetableType.values()) {
            for (int count = 1; count <= 5; count++) {
                Vegetable vegetable = this.pansForVegetables.get(type).take();
                batch.add(vegetable);
            }
        }

        return batch;
    }
}
