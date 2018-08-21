package peasant_brigade.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hospital.human.HumanException;
import peasant_brigade.human.Chappie;
import peasant_brigade.human.Damsel;
import peasant_brigade.human.GrandMother;
import peasant_brigade.human.Human;
import peasant_brigade.human.PeasantArchiver;

public class Demo {

    private static final int MAX_AGE_CHAPPIE = 32;

    private static final int MIN_AGE_CHAPPIE = 18;

    private static final int MAX_AGE_DAMSEL = 32;

    private static final int MIN_AGE_DAMSEL = 18;

    private static final int GRANDMOTHERS_COUNT = 3;

    private static final int DAMSELS_COUNT = 5;

    private static final int CHAPPIES_COUNT = 5;

    private static final String[] FIRST_NAMES = { "Zahir", "Boni", "Neven", "Nikifora", "Kolio", "Shibil", "Karagios", "Martin", "Sebastian", "Rumqna", "Rumen",
            "Hristina", "Kalin", "Malin", "Elvira", "Ivan", "Koko", "Mitko", "Deqn", "Rosen", "Stefka" };

    private static final String[] SURNAMES = { "Hadzhiev", "Sabev", "Mitev", "Ushev", "Stoqnov", "Ilqnov", "Mormonov" };

    private static String getName() {
        String firstName = FIRST_NAMES[new Random().nextInt(FIRST_NAMES.length - 1)];
        String lastName = SURNAMES[new Random().nextInt(FIRST_NAMES.length - 1)];

        return firstName + lastName;
    }
    
    public static int random(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static void main(String[] args) {

        List<Chappie> chappies = new ArrayList<>();
        List<Damsel> damsels = new ArrayList<>();
        List<GrandMother> grandmothers = new ArrayList<>();
        Set<Human> brigade = new HashSet<>();
        
        try {
            for (int count = 0; count < CHAPPIES_COUNT; count++) {
                chappies.add(new Chappie(Demo.getName(), Demo.random(MIN_AGE_DAMSEL, MAX_AGE_DAMSEL)));
            }

            for (int count = 0; count < DAMSELS_COUNT; count++) {
                damsels.add(new Damsel(Demo.getName(), Demo.random(MIN_AGE_CHAPPIE, MAX_AGE_CHAPPIE)));
            }

            for (int count = 0; count < GRANDMOTHERS_COUNT; count++) {
                grandmothers.add(new GrandMother(Demo.getName(), Demo.random(60, 75)));
            }

            for (Damsel damsel : damsels) {
                PeasantArchiver.addDamsels(damsel);
                brigade.add(damsel);
                damsel.run();
            }

            for (Chappie chappie : chappies) {
                PeasantArchiver.addChappie(chappie);
                brigade.add(chappie);
                chappie.run();
            }

            for (GrandMother grandmother : grandmothers) {
                PeasantArchiver.addGrandMother(grandmother);
                brigade.add(grandmother);
                grandmother.run();
            }
            

            PeasantArchiver.addBrigade(brigade);
            
            PeasantArchiver.getInstance().run();
        } catch (HumanException e) {
            e.printStackTrace();
            return;
        }
    }

}
