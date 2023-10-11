
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Car {
    private int id;

    public Car(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

class CarWashLocation {
    private int id;
    private Queue<Car> waitingQueue;

    public CarWashLocation(int id) {
        this.id = id;
        this.waitingQueue = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public synchronized void addCarToQueue(Car car) {
        waitingQueue.add(car);
        System.out.println("Car " + car.getId() + " is waiting at Location " + id + ".");
    }

    public synchronized void washCar() {
        if (!waitingQueue.isEmpty()) {
            Car car = waitingQueue.poll();
            System.out.println("Car " + car.getId() + " is being washed at Location " + id);
            try {
                
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Car " + car.getId() + " is done washing at Location " + id);
        }
    }
}

class CarWashStation {
    private CarWashLocation[] locations;
    private Random random;

    public CarWashStation(int numLocations) {
        locations = new CarWashLocation[numLocations];
        for (int i = 1; i <= numLocations; i++) {
            locations[i - 1] = new CarWashLocation(i);
        }
        this.random = new Random();
    }

    public void washCar(Car car) {
        CarWashLocation randomLocation = locations[random.nextInt(locations.length)];
        randomLocation.addCarToQueue(car);
        randomLocation.washCar();
    }
}

public class CarWashSimulation {
    public static void main(String[] args) {
        int numLocations = 5; 
        CarWashStation carWashStation = new CarWashStation(numLocations);

        
        for (int i = 1; i <= 6; i++) {
            Car car = new Car(i);
            Thread carThread = new Thread(() -> {
                carWashStation.washCar(car);
            });
            carThread.start();
        }
    }
}
