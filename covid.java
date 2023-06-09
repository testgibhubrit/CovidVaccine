package dkk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String password;
    private List<VaccinationSlot> bookedSlots;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookedSlots = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<VaccinationSlot> getBookedSlots() {
        return bookedSlots;
    }
}

class VaccinationCentre {
    private String name;
    private String address;
    private String workingHours;
    private List<VaccinationSlot> availableSlots;

    public VaccinationCentre(String name, String address, String workingHours) {
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
        this.availableSlots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public List<VaccinationSlot> getAvailableSlots() {
        return availableSlots;
    }
}

class VaccinationSlot {
    private String date;
    private int availableCapacity;

    public VaccinationSlot(String date, int availableCapacity) {
        this.date = date;
        this.availableCapacity = availableCapacity;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}

public class Covid {
	private static List<User> users = new ArrayList<>();
    private static List<VaccinationCentre> vaccinationCentres;
    private static User loggedInUser;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        vaccinationCentres = new ArrayList<>();
        loggedInUser = null;

        showWelcomeMessage();
        mainMenu();
    }

    private static void showWelcomeMessage() {
        System.out.println("Welcome to Covid Vaccination Booking System");
    }

    private static void mainMenu() {
        System.out.println("\n---- Main Menu ----");
        System.out.println("1. User Login");
        System.out.println("2. Admin Login");
        System.out.println("3. Sign Up");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                userLogin();
                break;
            case 2:
                adminLogin();
                break;
            case 3:
                signUp();
                break;
            case 4:
                System.out.println("Thank you for using the system. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                mainMenu();
                break;
        }
    }

    private static void userLogin() {
        System.out.println("\n---- User Login ----");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (loggedInUser == null) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    loggedInUser = user;
                    System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!");
                    userMenu();
                    return;
                }
            }
            System.out.println("Invalid username or password. Please try again.");
        } else {
            System.out.println("A user is already logged in. Please logout first to login as a different user.");
        }

        mainMenu();
    }
    private static void adminLogin() {
        System.out.println("\n---- Admin Login ----");
        System.out.print("Enter Admin Username: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.nextLine();

        if (adminUsername.equals("admin") && adminPassword.equals("admin123")) {
            System.out.println("Admin login successful. Welcome!");
            adminMenu();
        } else {
            System.out.println("Invalid admin username or password. Please try again.");
            mainMenu();
        }
    }

    private static void signUp() {
        System.out.println("\n---- Sign Up ----");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("User registered successfully.");

        mainMenu();
    }

    private static void userMenu() {
        System.out.println("\n---- User Menu ----");
        System.out.println("1. Search Vaccination Centres");
        System.out.println("2. Apply for Vaccination Slot");
        System.out.println("3. My Bookings");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                searchVaccinationCentres();
                break;
            case 2:
                applyForVaccinationSlot();
                break;
            case 3:
                displayBookings();
                break;
            case 4:
                loggedInUser = null;
                System.out.println("Logged out successfully.");
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                userMenu();
                break;
        }
    }

    private static void searchVaccinationCentres() {
        System.out.println("\n---- Search Vaccination Centres ----");
        System.out.print("Enter Date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Time: ");
        String time = scanner.nextLine();

        List<VaccinationCentre> availableCentres = findAvailableCentres(date, time);
        if (!availableCentres.isEmpty()) {
            System.out.println("Available Vaccination Centres:");
            for (VaccinationCentre centre : availableCentres) {
                System.out.println("Centre Name: " + centre.getName());
                System.out.println("Address: " + centre.getAddress());
                System.out.println("Working Hours: " + centre.getWorkingHours());
                System.out.println("Available Slots: " + centre.getAvailableSlots().size());
                System.out.println("---------------------");
            }
        } else {
            System.out.println("No vaccination centres available for the specified date and time.");
        }

        userMenu();
    }

    private static List<VaccinationCentre> findAvailableCentres(String date, String time) {
        List<VaccinationCentre> availableCentres = new ArrayList<>();

        for (VaccinationCentre centre : vaccinationCentres) {
            List<VaccinationSlot> slots = centre.getAvailableSlots();
            for (VaccinationSlot slot : slots) {
                if (slot.getDate().equals(date) && slot.getAvailableCapacity() > 0) {
                    availableCentres.add(centre);
                    break;
                }
            }
        }

        return availableCentres;
    }

    private static void applyForVaccinationSlot() {
        System.out.println("\n---- Apply for Vaccination Slot ----");
        System.out.print("Enter Date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Time: ");
        String time = scanner.nextLine();

        List<VaccinationCentre> availableCentres = findAvailableCentres(date, time);
        if (!availableCentres.isEmpty()) {
            System.out.println("Available Vaccination Centres:");
            for (int i = 0; i < availableCentres.size(); i++) {
                System.out.println((i + 1) + ". " + availableCentres.get(i).getName());
            }

            System.out.print("Enter Centre Number: ");
            int centreNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (centreNumber > 0 && centreNumber <= availableCentres.size()) {
                VaccinationCentre selectedCentre = availableCentres.get(centreNumber - 1);
                List<VaccinationSlot> slots = selectedCentre.getAvailableSlots();
                VaccinationSlot selectedSlot = null;

                for (VaccinationSlot slot : slots) {
                    if (slot.getDate().equals(date) && slot.getAvailableCapacity() > 0) {
                        selectedSlot = slot;
                        break;
                    }
                }

                if (selectedSlot != null) {
                    selectedSlot.setAvailableCapacity(selectedSlot.getAvailableCapacity() - 1);
                    loggedInUser.getBookedSlots().add(selectedSlot);
                    System.out.println("Vaccination slot booked successfully!");
                } else {
                    System.out.println("No available slots for the specified date and time.");
                }
            } else {
                System.out.println("Invalid centre number. Please try again.");
            }
        } else {
            System.out.println("No vaccination centres available for the specified date and time.");
        }

        userMenu();
    }

    private static void displayBookings() {
        System.out.println("\n---- My Bookings ----");
        List<VaccinationSlot> bookings = loggedInUser.getBookedSlots();

        if (!bookings.isEmpty()) {
            System.out.println("Your Bookings:");
            for (VaccinationSlot slot : bookings) {
                System.out.println("Date: " + slot.getDate());
                System.out.println("Centre: " + findCentreNameBySlot(slot));
                System.out.println("---------------------");
            }
        } else {
            System.out.println("You have no bookings.");
        }

        userMenu();
    }

    private static String findCentreNameBySlot(VaccinationSlot slot) {
        for (VaccinationCentre centre : vaccinationCentres) {
            if (centre.getAvailableSlots().contains(slot)) {
                return centre.getName();
            }
        }
        return "";
    }

    private static void adminMenu() {
        System.out.println("\n---- Admin Menu ----");
        System.out.println("1. Add Vaccination Centre");
        System.out.println("2. Get Dosage Details");
        System.out.println("3. Remove Vaccination Centre");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                addVaccinationCentre();
                break;
            case 2:
                getDosageDetails();
                break;
            case 3:
                removeVaccinationCentre();
                break;
            case 4:
                System.out.println("Logged out successfully.");
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                adminMenu();
                break;
        }
    }

    private static void addVaccinationCentre() {
        System.out.println("\n---- Add Vaccination Centre ----");
        System.out.print("Enter Centre Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Centre Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Working Hours: ");
        String workingHours = scanner.nextLine();

        VaccinationCentre newCentre = new VaccinationCentre(name, address, workingHours);
        vaccinationCentres.add(newCentre);
        System.out.println("Vaccination Centre added successfully.");

        adminMenu();
    }

    private static void getDosageDetails() {
        System.out.println("\n---- Get Dosage Details ----");
        System.out.println("Dosage Details:");

        for (VaccinationCentre centre : vaccinationCentres) {
            System.out.println("Centre: " + centre.getName());
            System.out.println("Total Slots: " + centre.getAvailableSlots().size());
            System.out.println("---------------------");
        }

        adminMenu();
    }

    private static void removeVaccinationCentre() {
        System.out.println("\n---- Remove Vaccination Centre ----");
        System.out.print("Enter Centre Name: ");
        String centreName = scanner.nextLine();

        VaccinationCentre centreToRemove = null;

        for (VaccinationCentre centre : vaccinationCentres) {
            if (centre.getName().equalsIgnoreCase(centreName)) {
                centreToRemove = centre;
                break;
            }
        }

        if (centreToRemove != null) {
            vaccinationCentres.remove(centreToRemove);
            System.out.println("Vaccination Centre removed successfully.");
        } else {
            System.out.println("Vaccination Centre not found.");
        }

        adminMenu();
    }
}
