package app.classes;

public class Global {
    private int year;
    private String name;  // Country or region name
    private double firstYearTemperature;
    private double lastYearTemperature;
    private double change;  // Change in temperature over the years
    private float averageTemp;
    private float minimumTemp;
    private float maximumTemp;
    private int population;
    private int initialYear;
    private int startYear;
    private int endYear;
    private int period;

    // Default constructor
    public Global() {
    }

    // Constructor with year, population, and average temperature
    public Global(int year, int population, float averageTemp) {
        this.year = year;
        this.population = population;
        this.averageTemp = averageTemp;
    }

    // Constructor with additional temperature data and years
    public Global(int year, float averageTemp, float minimumTemp, float maximumTemp, int initialYear, int startYear, int endYear) {
        this.year = year;
        this.averageTemp = averageTemp;
        this.minimumTemp = minimumTemp;
        this.maximumTemp = maximumTemp;
        this.initialYear = initialYear;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    // Getter and Setter for year
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Getter and Setter for name (country or region name)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for first year temperature
    public double getFirstYearTemperature() {
        return firstYearTemperature;
    }

    public void setFirstYearTemperature(double firstYearTemperature) {
        this.firstYearTemperature = firstYearTemperature;
    }

    // Getter and Setter for last year temperature
    public double getLastYearTemperature() {
        return lastYearTemperature;
    }

    public void setLastYearTemperature(double lastYearTemperature) {
        this.lastYearTemperature = lastYearTemperature;
    }

    // Getter and Setter for temperature change
    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    // Getter and Setter for average temperature
    public float getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(float averageTemp) {
        this.averageTemp = averageTemp;
    }

    // Getter and Setter for minimum temperature
    public float getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(float minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    // Getter and Setter for maximum temperature
    public float getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(float maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    // Getter and Setter for population
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }


    //Getter and Setter for getting period

    public int getPeriod() {
        return population;
    }

    public int setPeriod(int period){
        return  this.period = period;
    }

    // Getter and Setter for initial year
    public int getInitialYear() {
        return initialYear;
    }

    public void setInitialYear(int initialYear) {
        this.initialYear = initialYear;
    }

    // Getter and Setter for start year
    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    // Getter and Setter for end year
    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
