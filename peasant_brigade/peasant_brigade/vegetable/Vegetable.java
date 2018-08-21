package peasant_brigade.vegetable;


import hospital.human.HumanException;

public abstract class Vegetable {
    private VegetableType type;
    private String damselName;

    public Vegetable(VegetableType type) {
        this.type = type;
    }

    public VegetableType getType() {
        return type;
    }

    public void setDamselName(String name) throws HumanException {
        if (name != null && name.length() >= 2) {
            this.damselName = name;
        }

        throw new HumanException("Invalid credentials for damsel name");
    }

    public String getDamselName() {
        return this.damselName;
    }

    public static synchronized Vegetable createVegetable(VegetableType type) {
        switch (type) {
        case EGGPLANT:
            return new Eggplant();
        case PEPPER:
            return new Pepper();
        default:
            return new Tomato();
        }
    }
}
