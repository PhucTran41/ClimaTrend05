package app.classes;

public class Population{

    private String countryID;
    private long population;
    private int year;
    
    public Population() {
    
    }
    public Population(int year, String countryID, long population) {
        this.year = year;
        this.countryID = countryID;
        this.population = population;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }
    public String getCountryID() {
        return countryID;
    }
    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}