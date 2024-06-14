import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Vessel {

    private int ID;

    private String name;

    private int year;

    private List<PricePeriod> pricePeriods;

    public Vessel(int ID, String name, int year, List<PricePeriod> pricePeriods) {
        this.ID = ID;
        this.name = name;
        this.year = year;
        this.pricePeriods = pricePeriods;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<PricePeriod> getPricePeriods() {
        return pricePeriods;
    }

    public void setPricePeriods(List<PricePeriod> pricePeriods) {
        this.pricePeriods = pricePeriods;
    }

    // Method for calculating price for renting for x amount of days starting from date y
    public double calculateRentingPrice(LocalDate startingDate, int duration) {
        double price = 0;
        LocalDate currentDate = startingDate;

        // We use while loop in case the renting period spans multiple price periods
        while (duration > 0) {
            // We us for each loop to loop through all of our price periods and check if provided date
            // "fits" one of those
            for (PricePeriod pricePeriod : this.pricePeriods) {
                if (pricePeriod.isWithinTimePeriod(currentDate)) {
                    // Here we have to add 1 day to our end date because of inclusion
                    LocalDate endDate = pricePeriod.getEndDate().plusDays(1);
                    // Here we calculate difference between our current date and end date, we also use
                    // type conversion, to convert long to int because between method returns long
                    int daysInPricePeriod = (int) ChronoUnit.DAYS.between(currentDate, endDate);
                    // Here we use min method to get min of duration and daysInPricePeriod to handle cases
                    // where renting period spans multiple price periods
                    int daysToUse = Math.min(duration, daysInPricePeriod);
                    // Because price is weekly we divide it by 7 to get daily price and then multiply it
                    // with the number od days we are renting (in this price period)
                    price += daysToUse * (pricePeriod.getPrice() / 7);
                    // We then subtract days we are renting with duration we are renting, again in case the
                    // renting spans multiple price periods
                    duration -= daysToUse;
                    // And once again in case renting spans multiple periods we add days we rented the boat
                    // to our current date
                    currentDate = currentDate.plusDays(daysToUse);
                    // Finally we break out of for each loop
                    break;
                }
            }
        }

        return  price;
    }
}
