package peasant_brigade.human;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import peasant_brigade.utils.DBLutenicaArchiver;
import peasant_brigade.utils.DBNabranoArchiver;
import peasant_brigade.utils.FileArchiver;
import peasant_brigade.utils.IArchiver;

public class PeasantArchiver extends Thread {
    private static PeasantArchiver instance;

    private static Set<Damsel> damsels = new HashSet<Damsel>();
    private static Set<Chappie> chappies = new HashSet<Chappie>();
    private static Set<GrandMother> grandmothers = new HashSet<GrandMother>();
    private static List<Human> brigadge;
    
    private PeasantArchiver() {
        this.setDaemon(true);
    }

    public static IArchiver factoryArchiver(TypeArchiver type) {
        switch (type) {
        case DB_LIUTENICA:
            return DBLutenicaArchiver.getInstance();
        case DB_NABRANO:
            return DBNabranoArchiver.getInstance();
        default:
            return FileArchiver.getInstance();
        }
    }

    public static PeasantArchiver getInstance() {
        if (instance == null) {
            instance = new PeasantArchiver();
        }

        return instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30_000);

                this.getTheDamselWithMaxCountOfPickedVeggies();

                this.getTheChappieWithMinProcessTimeAmount();

                this.listBatchsByDate();

                this.getHumanWithAverageAge();
                this.getGrandMotherMostProducedAmountOfLutenica();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void getHumanWithAverageAge() {
        Map<String, Double> humans = new HashMap<>();
        // find average age
        double averageAge = brigadge
                .parallelStream()
                .filter(p -> p.getAge() > 18 && p.getAge() < 65)
                .mapToDouble(p -> p.getAge())
                .average()
                .getAsDouble();
        // create map with name for key and age for value
        humans = brigadge.stream().collect(Collectors.groupingBy(Human::getHumanName, Collectors.averagingInt(Human::getAge)));

        for (Entry<String, Double> human : humans.entrySet()) {
            System.out.println(human.getKey() + " - " + human.getValue());
        }
        
        Map<Double, String> result = new HashMap<>();
        // Another map with key age and value name
        result = humans
                .entrySet()
                .stream()
                .collect(Collectors
                        .toMap(e -> e.getValue(), e -> e.getKey()));

//        for (Entry<Double, String> human : result.entrySet()) {
//            System.out.println(human.getKey() + " - " + human.getValue());
//        }

        System.out.println("Result =  " + result.get(averageAge));
    }

    
    public static void addChappie(Chappie chappie) {
        if (chappie != null) {
            chappies.add(chappie);
        }
    }

    public static void addDamsels(Damsel damsel) {
        if (damsel != null) {
            damsels.add(damsel);
        }
    }

    public static void addGrandMother(GrandMother grandMother) {
        if (grandMother != null) {
            grandmothers.add(grandMother);
        }
    }

    public static void addBrigade(Set<Human> brigade) {
        if (!brigade.isEmpty()) {
            brigadge = new ArrayList<>(brigade);
        }
    }

    private void getGrandMotherMostProducedAmountOfLutenica() {
        GrandMother.grandMothersProducedAmountLutenica.entrySet()
        .stream()
        .sorted((e1,e2) -> Integer.compare(e1.getValue(), e2.getValue()))
        .limit(1)
        .forEach(e -> System.out.println(
                String.format("GrandMother with name %s and age %s produced %d amount of lutenica", e.getKey().getHumanName(),e.getKey().getAge(),e.getValue())));
    }
    
    private void getTheChappieWithMinProcessTimeAmount() {
        Damsel.damselsPickedVeggiesCounter.entrySet()
        .stream()
        .sorted((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()))
        .limit(1)
        .forEach(e -> System.out.println(
                String.format("Chappie with name %s and age %d. Process time veggies %d", e.getKey().getHumanName(),e.getKey().getAge(), e.getValue())));
    }

    private void getTheDamselWithMaxCountOfPickedVeggies() {
        Damsel.damselsPickedVeggiesCounter.entrySet()
        .stream()
        .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
        .limit(1)
        .forEach(e -> System.out.println(
                        String.format("Damsel with name %s and age %d. Count veggies %d", e.getKey().getHumanName(), e.getKey().getAge(), e.getValue())));
    }
    
    private void listBatchsByDate() {
        GrandMother.listBatchs();
    }
}
