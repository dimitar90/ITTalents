package peasant_brigade.human;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.TreeMap;

import hospital.human.HumanException;
import peasant_brigade.demo.Demo;
import peasant_brigade.premises.BatchOfVegetables;
import peasant_brigade.premises.PeasantKitchen;
import peasant_brigade.premises.VegetableException;
import peasant_brigade.utils.IArchiver;
import peasant_brigade.vegetable.Vegetable;

public class GrandMother extends Human {

    private static final int TIME_GRANDMOTHER_PROCESS_VEGGIES = 10_000;
    private static final int MAX_KILOS_VEGETABLES = 13;
    private static final int MIN_KILOS_VEGETABLES = 3;

    private PeasantKitchen kitchen = PeasantKitchen.getInstance();

    private static Map<LocalDate, Set<BatchOfVegetables>> basement = new TreeMap<>();;
    public static ConcurrentHashMap<GrandMother, Integer> grandMothersProducedAmountLutenica = new ConcurrentHashMap<GrandMother, Integer>();

    public GrandMother(String name, int age) throws HumanException {
        super(name, age);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Set<Vegetable> vegetables = kitchen.getBatchOfVegetables();

                Thread.sleep(TIME_GRANDMOTHER_PROCESS_VEGGIES);

                int productionQuantity = Demo.random(MIN_KILOS_VEGETABLES, MAX_KILOS_VEGETABLES);

                BatchOfVegetables batch = new BatchOfVegetables(vegetables, productionQuantity);

                LocalDate date = LocalDate.now();

                if (!GrandMother.basement.containsKey(GrandMother.basement.get(date))) {
                    GrandMother.basement.put(date, new HashSet<>());
                }

                GrandMother.basement.get(date).add(batch);

                IArchiver archiver = PeasantArchiver.factoryArchiver(TypeArchiver.DB_LIUTENICA);
                GrandMother.addProducedAmount(this, productionQuantity);
                archiver.process(date, productionQuantity, this.getHumanName());
            } catch (InterruptedException | VegetableException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void addProducedAmount(GrandMother grandMother, int curAmount) {
        if (!grandMothersProducedAmountLutenica.containsKey(grandMother.getHumanName())) {
            grandMothersProducedAmountLutenica.put(grandMother, 0);
        }
        int prevAmount = grandMothersProducedAmountLutenica.get(grandMother);

        grandMothersProducedAmountLutenica.put(grandMother, prevAmount + curAmount);
    }

    public static void listBatchs() {
        for (Entry<LocalDate, Set<BatchOfVegetables>> entry : basement.entrySet()) {
            int countBatchs = entry.getValue().size();
            int totalKilos = 0;
            System.out.println(entry.getKey() + " :");
            for (BatchOfVegetables batch : entry.getValue()) {
                totalKilos += batch.getKilos();
            }

            System.out.println(String.format(" - count batchs -> %d. Total kilos -> %d", countBatchs, totalKilos));
        }
    }
}
