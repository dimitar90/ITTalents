package peasant_brigade.human;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import hospital.human.HumanException;
import peasant_brigade.premises.PeasantKitchen;
import peasant_brigade.premises.PeasantShed;
import peasant_brigade.utils.IArchiver;
import peasant_brigade.vegetable.Vegetable;

public class Chappie extends Human {

    private PeasantShed shed = PeasantShed.getInstance();
    private PeasantKitchen kitchen = PeasantKitchen.getInstance();
    private int processTime;
    public static ConcurrentHashMap<Chappie, Integer> chappiesProcessedVeggiesCounter = new ConcurrentHashMap<>();

    public Chappie(String name, int age) throws HumanException {
        super(name, age);
    }

    @Override
    public void run() {
        while (true) {
            try {
                // each chappie gets vegetable and process it based on its type
                Vegetable vegetable = shed.getVeggie();
                int processDuration = vegetable.getType().getSeconds();

//              this.increaseProcessTime(processDuration);
                Chappie.addProcessedTimeForVeg(this, processDuration);
                
                Thread.sleep(processDuration);
                IArchiver archiver = PeasantArchiver.factoryArchiver(TypeArchiver.FILE);

                archiver.process(this.getHumanName(), processDuration);
                kitchen.putVegetableInKitchen(vegetable);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public int getProcessTime() {
        return this.processTime;
    }

    private void increaseProcessTime(int time) {
        if (time > 0) {
            this.processTime += time;
        }
    }

    public static void addProcessedTimeForVeg(Chappie chappie, int curAmount) {
        if (!chappiesProcessedVeggiesCounter.containsKey(chappie.getHumanName())) {
            chappiesProcessedVeggiesCounter.put(chappie, 0);
        }
        
        int prevAmount = chappiesProcessedVeggiesCounter.get(chappie);

        chappiesProcessedVeggiesCounter.put(chappie, prevAmount + curAmount);
    }
}