package app.classes;

public class Population{

    private String countryID;
    private int population;
    private int year;
    
    public Population() {
    
    }
    public Population(int year, String countryID, int population) {
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

    public void setPopulation(int population) {
        this.population = population;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    

}