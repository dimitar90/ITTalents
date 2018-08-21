package peasant_brigade.human;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import hospital.human.HumanException;
import peasant_brigade.demo.Demo;
import peasant_brigade.premises.PeasantShed;
import peasant_brigade.vegetable.Vegetable;
import peasant_brigade.vegetable.VegetableType;

public class Damsel extends Human {
    private static final int MAX_NUMBER_VEGGIES = 7;
    private static final int MIN_NUMBER_VAGGIES = 3;
    private static PeasantShed shed = PeasantShed.getInstance();
    private int countVeggies;
    public static ConcurrentHashMap<Damsel, Integer> damselsPickedVeggiesCounter = new ConcurrentHashMap<>();

    public Damsel(String name, int age) throws HumanException {
        super(name, age);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                // Each damsel comes out and picking any number of veggies (between 3 and 6)
                List<Vegetable> vegetables = new ArrayList<>();
                int numberOfVeggies = Demo.random(MIN_NUMBER_VAGGIES, MAX_NUMBER_VEGGIES);

                VegetableType type = VegetableType.values()[VegetableType.values().length];

                for (int count = 0; count < numberOfVeggies; count++) {
                    Vegetable vegetable = Vegetable.createVegetable(type);
                    vegetable.setDamselName(this.getHumanName());

                    vegetables.add(vegetable);
                }

                int currentCountVeggies = vegetables.size();
                
                Damsel.addCountVeggie(this, currentCountVeggies);

                shed.putVeggiesInBaskets(vegetables, type);
            } catch (InterruptedException | HumanException e) {
                e.printStackTrace();
                return;
            }
        }
    }


    public static void addCountVeggie(Damsel damsel, int curCount) {
        if (!damselsPickedVeggiesCounter.containsKey(damsel.getHumanName())) {
            damselsPickedVeggiesCounter.put(damsel, 0);
        }
        int prevCount = damselsPickedVeggiesCounter.get(damsel);
        
        damselsPickedVeggiesCounter.put(damsel, prevCount + curCount);
    }

    public int getCountVeggies() {
        return this.countVeggies;
    }

}
