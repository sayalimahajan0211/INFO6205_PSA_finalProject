package Person;

import Island.Island;
import org.ini4j.Ini;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * @author sayali mahajan
 */
public class Person extends Position {

    private RandomWalk randomWalk;
    Ini ini = new Ini(new File("./config.properties"));
    Map<String, String> map = ini.get("default");
    //This shows current health of residents
    Map<String, String> host_type = ini.get("host_type");
    private static final Random randomGen = new Random();
    private double xAxis;
    private double yAxis;
    private Island island;
    private boolean isInfected = false;
    private boolean isDead = false;
    public String human_genome = "";
    public String infection_Status = "Naive";
    public int infection_count;
    public double infection_Factor;
    public double death_factor;
    public int recovery_day;
    public int mutation_count;
    public Color mutationColor;
    public boolean isVaccinated;


    public Person() throws IOException {
        super();
    }

    public void doRandomMove() {

        // Random random=new Random();
        if (randomWalk == null || randomWalk.isRePositioned()) {
            double targetX = stdGaussian(100, xAxis);
            double targetY = stdGaussian(100, yAxis);
            randomWalk = new RandomWalk((int) targetX, (int) targetY);
        }
        if ((getY() - 400) * (randomWalk.getxAxis() - 400) < 0) {
            if (randomWalk.getyAxis() >= 400)
                island = new Island(500, 500);
            else
                island = new Island(200, 200);
        }
        int differX = randomWalk.getxAxis() - getX();
        int differY = randomWalk.getyAxis() - getY();
        double length = Math.sqrt(Math.pow(differX, 2) + Math.pow(differY, 2));

        if (length < 1) {
            randomWalk.setRePositioned(true);
            return;
        }

        int updatedDifferX = (int) (differX / length);
        if (updatedDifferX == 0 && differX != 0) {
            if (differX <= 0)
                updatedDifferX = -1;
            else
                updatedDifferX = 1;
        }

        int updatedDifferY = (int) (differY / length);
        if (updatedDifferY == 0 && differY != 0) {
            if (differY <= 0)
                updatedDifferY = -1;
            else
                updatedDifferY = 1;
        }
        if (getX() > Integer.parseInt(map.get("island_width")) || getX() < 0) {
            randomWalk = null;
            if (updatedDifferX > 0)
                updatedDifferX = -updatedDifferX;
        }
        if (getY() > Integer.parseInt(map.get("island_height")) || getY() < 0) {
            randomWalk = null;
            if (updatedDifferY > 0)
                updatedDifferY = -updatedDifferY;
        }
        rePosition(updatedDifferX, updatedDifferY);
    }

    public void checkHealth() {
        double targetX = stdGaussian(100, xAxis);
        double targetY = stdGaussian(100, yAxis);
        randomWalk = new RandomWalk((int) targetX, (int) targetY);
        doRandomMove();
    }

    public Person(int xAxis, int yAxis, Island island, String human_genome) throws IOException {
        super(xAxis, yAxis);
        this.xAxis = stdGaussian(100, xAxis);
        this.yAxis = stdGaussian(100, yAxis);
        this.human_genome = human_genome;
        this.island = island;
    }

    public RandomWalk getRandomWalk() {
        return randomWalk;
    }

    public void setRandomWalk(RandomWalk randomWalk) {
        this.randomWalk = randomWalk;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    public String getHuman_genome() {
        return human_genome;
    }

    public void setHuman_genome(String human_genome) {
        this.human_genome = human_genome;
    }

    public String getInfection_Status() {
        return infection_Status;
    }

    public void setInfection_Status(String infection_Status) {
        this.infection_Status = infection_Status;
    }

    public int getInfection_count() {
        return infection_count;
    }

    public void setInfection_count(int infection_count) {
        this.infection_count = infection_count;
    }

    public double getInfection_Factor() {
        return infection_Factor;
    }

    public void setInfection_Factor(double infection_Factor) {
        this.infection_Factor = infection_Factor;
    }

    public double getDeath_factor() {
        return death_factor;
    }

    public void setDeath_factor(double death_factor) {
        this.death_factor = death_factor;
    }

    public int getRecovery_day() {
        return recovery_day;
    }

    public void setRecovery_day(int recovery_day) {
        this.recovery_day = recovery_day;
    }

    public int getMutation_count() {
        return mutation_count;
    }

    public void setMutation_count(int mutation_count) {
        this.mutation_count = mutation_count;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Color getMutationColor() {
        return mutationColor;
    }

    public void setMutationColor(Color mutationColor) {
        this.mutationColor = mutationColor;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public static double stdGaussian(double sigma, double u) {
        double X = randomGen.nextGaussian();
        return sigma * X + u;
    }
}
