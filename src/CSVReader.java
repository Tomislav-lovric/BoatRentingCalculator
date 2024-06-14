import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    public static Map<Integer, Vessel> readCSV(String filePath) throws IOException {
        Map<Integer, Vessel> vessels = new HashMap<>();
        // Here we define date formatter to mach date format in our CSV file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // It's not necessary in our task to trim, but I think it's a good practice to do it just in case,
            // so I did it anyway here as well as through the rest of the task few more times
            String line = br.readLine().trim();
            // We split line into the string array with comma delimiter, I tried splitting with ; delimiter like it
            // said in the task but that didn't work, so I split it with comma instead
            String[] headers = line.split(",");

            /*
             Here we are checking if our CSV file has at least 4 columns (ID, vessel name, year and price period)
             as well as if the first 3 columns of our first row are empty like it is specified in the task
             if either of those is incorrect we throw and exception
            */
            if (headers.length < 4 || !headers[0].isEmpty() || !headers[1].isEmpty() || !headers[2].isEmpty()) {
                throw new IOException("CSV file format is incorrect!");
            }

            // First we create and array list of PricePeriods where we will store our start and end dates
            List<PricePeriod> periods = new ArrayList<>();
            // Then we use for loop starting from 3 because our first 3 columns in our row are empty
            for (int i = 3; i < headers.length; i++) {
                String period = headers[i].trim();
                // Then we split our string with "-" delimiter because our dates in CSV file are set up like that
                // ex. "01.01.2021-01.04.2021"
                String[] dates = period.split("-");
                // Then we extract our start and end date for our price periods and parse it to LocalDate
                // as well format it
                LocalDate startDate = LocalDate.parse(dates[0].trim(), formatter);
                LocalDate endDate = LocalDate.parse(dates[1].trim(), formatter);
                /*
                 And finally create our PricePeriod and add it to our list, but because actual prices aren't
                 necessary here, because we only want dates because dates in all our vessels will be the same
                 but prices won't, so we put 0 for price
                */
                periods.add(new PricePeriod(startDate, endDate, 0));
            }

            // Here we continue with reading next lines in our CSV file
            while ((line = br.readLine()) != null) {
                // Just like before we split with "," delimiter
                String[] values = line.trim().split(",");
                // Then we check if our row has less than 4, and if it does we skip it because it is invalid
                if (values.length < 4) {
                    continue;
                }

                // Then we get our id, name and year out of current row we are in
                int id = Integer.parseInt(values[0].trim());
                String name = values[1].trim();
                int year = Integer.parseInt(values[2].trim());

                // We create another list for our price periods, in which we will have both dates and prices
                // for those dates unlike in the previous list
                List<PricePeriod> pricePeriods = new ArrayList<>();
                // Similar process like before we skip first 3 columns (those being id, name, yea)
                for (int i = 3; i < values.length; i++) {
                    // Then we extract our price for that period and parse it to be doubly type
                    double price = Double.parseDouble(values[i].trim());
                    // Then we create new PricePeriod using dates from our previous list (periods) and price from now
                    // Also we use "periods.get(i - 3)" because we are starting from 3 not 0
                    PricePeriod pricePeriod = new PricePeriod(
                            periods.get(i - 3).getStartDate(),
                            periods.get(i - 3).getEndDate(),
                            price
                    );
                    pricePeriods.add(pricePeriod);
                }

                // Finally we create our vessel with all values extracted from the row and add it to our Map
                Vessel vessel = new Vessel(id, name, year, pricePeriods);
                vessels.put(id, vessel);
            }

        }

        return vessels;
    }

}
