import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    static Thread abonents = new Thread(Main::atc, "Абонент");
    static Thread callSpecialist1 = new Thread(Main::specialist, "Специалист №1");
    static Thread callSpecialist2 = new Thread(Main::specialist, "Специалист №2");
    static Thread callSpecialist3 = new Thread(Main::specialist, "Специалист №3");

    static Queue<String> calls = new ConcurrentLinkedQueue();

    static volatile int callsOfAbonents = 0;
    static final int TIME_BETWEEN_CALLS = 1000;
    static final int TIME_BETWEEN_SPECIALISTS = 2000;
    static final int NUMBER_OF_CALLS = 20;
    static boolean asCall;

    public static void main(String[] args) {
        abonents.start();
        callSpecialist1.start();
        callSpecialist2.start();
        callSpecialist3.start();
    }

    public static void atc() {
        while (!asCall) {
            for (int i = 1; i <= NUMBER_OF_CALLS; i++) {
                try {
                    Thread.sleep(TIME_BETWEEN_CALLS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String abonentsCalls = "Звонок от абонента №" + i;
                System.out.println(abonentsCalls);
                calls.offer(abonentsCalls);
            }
            asCall = true;
        }
    }

    public static void specialist() {
        String count;
        while (callsOfAbonents < 20) {
            count = calls.poll();
            try {
                Thread.sleep(TIME_BETWEEN_SPECIALISTS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count != null) {
                System.out.printf("%s принял " + count + "\n", Thread.currentThread().getName());
                callsOfAbonents++;
            }
        }
    }
}
