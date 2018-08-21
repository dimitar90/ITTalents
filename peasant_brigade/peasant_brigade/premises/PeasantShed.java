package peasant_brigade.premises;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import peasant_brigade.human.PeasantArchiver;
import peasant_brigade.human.TypeArchiver;
import peasant_brigade.utils.IArchiver;
import peasant_brigade.vegetable.Vegetable;
import peasant_brigade.vegetable.VegetableType;

public class PeasantShed {
    private static PeasantShed instance;

    private ConcurrentHashMap<VegetableType, BlockingQueue<Vegetable>> basketOfVegetables;

    private PeasantShed() {
        for (VegetableType type : VegetableType.values()) {
            this.basketOfVegetables.put(type, new ArrayBlockingQueue<>(40));
        }
    }

    public static PeasantShed getInstance() {
        if (instance == null) {
            instance = new PeasantShed();
        }

        return instance;
    }

    public void putVeggiesInBaskets(List<Vegetable> vegetables, VegetableType type) throws InterruptedException {
        if (vegetables.isEmpty()) {
            IArchiver archiver = PeasantArchiver.factoryArchiver(TypeArchiver.DB_NABRANO);
            archiver.process(vegetables);

            for (Vegetable vegetable : vegetables) {
                this.basketOfVegetables.get(type).put(vegetable);
            }

        }
    }

    public Vegetable getVeggie() throws InterruptedException {
        Random random = new Random();

        VegetableType type = VegetableType.values()[random.nextInt(VegetableType.values().length)];

        return this.basketOfVegetables.get(type).take();
    }
}
