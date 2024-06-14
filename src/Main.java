import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Here we check if we have correct number of arguments when running jar file
        if (args.length != 3) {
            System.out.println("Invalid command please check correct way to run the project."
                    + "Usage: java -jar projekt.jar <vessel_id> <start_date> <duration>");
            return;
        }

        // Then we use try catch block for handling potential runtime exceptions
        try {
            // Here we pull id, start date and duration from provided arguments, and we also parse them, and later
            // we use catch block for catching potential exceptions
            int vesselId = Integer.parseInt(args[0]);
            LocalDate startDate = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            int duration = Integer.parseInt(args[2]);

            // Here we read our CSV file and put values from it into our map
            Map<Integer, Vessel> vessels = CSVReader.readCSV("../../../cjenik.csv");

            // Here we check if proved vessel id matches any vessel id in our map, our should I say CSV file
            if (!vessels.containsKey(vesselId)) {
                System.out.println("Invalid vessel id");
                return;
            }

            // If everything till now passes we get the vessel and calculate how much it would cost to rent it
            // based on the arguments provided to us
            Vessel vessel = vessels.get(vesselId);
            double price = vessel.calculateRentingPrice(startDate, duration);
            System.out.println("Renting price for " + duration + "  days, starting from " + startDate + " is " + price);

        } catch (NumberFormatException e) {
            System.out.println("Invalid format number for vessel id or duration for renting");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use dd.MM.yyyy format");
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }

    }

}