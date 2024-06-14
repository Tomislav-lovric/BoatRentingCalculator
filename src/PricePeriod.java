import java.time.LocalDate;

public class PricePeriod {

    private LocalDate startDate;

    private LocalDate endDate;

    // We are using double here because price in CSV file is set for the whole week not for the single day which
    // means we are going to need to divide it by 7 during calculation of renting boats
    private double price;

    public PricePeriod(LocalDate startDate, LocalDate endDate, double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Here we check and return true if date provided falls within time periods we have in our CSV file
    // otherwise return false
    public boolean isWithinTimePeriod(LocalDate date) {
        return (date.isEqual(this.startDate) || date.isAfter(this.startDate)) &&
                (date.isEqual(this.endDate) || date.isBefore(this.endDate));
    }
}
