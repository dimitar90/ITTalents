package peasant_brigade.human;

import hospital.human.HumanException;

public abstract class Human implements Runnable {
    private String name;
    private int age;

    public Human(String name, int age) throws HumanException {
        this.name = this.validateName(name);
        this.age = this.validateAge(age);
    }

    private int validateAge(int age) throws HumanException {
        if (age > 0 && age < 100) {
            return age;
        }

        throw new HumanException("Invalid credentials form age");
    }

    private String validateName(String name) throws HumanException {
        if (name != null && name.length() >= 2) {
            return name;
        }

        throw new HumanException("Invalid credentials form name");
    }

    protected String getHumanName() {
        return this.name;
    }

    protected int getAge() {
        return this.age;
    }
}
