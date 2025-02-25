import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private int availableSeats;
    private final Lock lock = new ReentrantLock(); // Lock to ensure synchronization

    public TicketBookingSystem(int seats) {
        this.availableSeats = seats;
    }

    public void bookTicket(String customerName) {
        lock.lock(); // Ensure only one thread can book a seat at a time
        try {
            if (availableSeats > 0) {
                System.out.println(customerName + " booked a seat. Remaining seats: " + (--availableSeats));
                Thread.sleep(1000); // Simulating booking delay
            } else {
                System.out.println("Sorry, " + customerName + ". No seats available.");
            }
        } catch (InterruptedException e) {
            System.out.println("Booking interrupted for " + customerName);
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private TicketBookingSystem system;
    private String customerName;

    public BookingThread(TicketBookingSystem system, String customerName, int priority) {
        this.system = system;
        this.customerName = customerName;
        setPriority(priority); // Set thread priority (higher for VIP)
    }

    public void run() {
        system.bookTicket(customerName);
    }
}

public class TicketBooking {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem(5); // Total 5 seats

        // Creating threads for VIP and Regular customers
        BookingThread vip1 = new BookingThread(system, "VIP Customer 1", Thread.MAX_PRIORITY);
        BookingThread vip2 = new BookingThread(system, "VIP Customer 2", Thread.MAX_PRIORITY);
        BookingThread regular1 = new BookingThread(system, "Regular Customer 1", Thread.NORM_PRIORITY);
        BookingThread regular2 = new BookingThread(system, "Regular Customer 2", Thread.NORM_PRIORITY);
        BookingThread regular3 = new BookingThread(system, "Regular Customer 3", Thread.NORM_PRIORITY);
        BookingThread regular4 = new BookingThread(system, "Regular Customer 4", Thread.NORM_PRIORITY);

        // Start threads
        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();
        regular3.start();
        regular4.start();
    }
}
