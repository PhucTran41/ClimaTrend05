package app.classes;

public class Country {

    private int year;
    private String countryName;
    private float AverageTemp;
    private float MinimumTemp;
    private float MaximumTemp;
    private int startYear;
    private int endYear;
    private float AveragePopulation;
    private int initialYear;
    private float startYearTemperature;
    private float endYearTemperature;
    private float change;
    private long startYearPopulation;
    private long endYearPopulation;
    private double populationChange;

    public Country(){
        
    }

    public Country(int year, String countryName, float averageTemp) {
        this.year = year;
        this.countryName = countryName;
        AverageTemp = averageTemp;
    
    }

    public long getStartYearPopulation(){
        return startYearPopulation;
    }

    public void setStartYearPopulation(long startYearPopulation){
        this.startYearPopulation = startYearPopulation;
    }


    public long getEndYearPopulation(){
        return endYearPopulation;
    }

    public void setEndYearPopulation(long endYearPopulation){
        this.endYearPopulation = endYearPopulation;
    }

    public double getPopulationChange(){
        return populationChange;
    }

    public void setPopulationChange(double populationChange){
        this.populationChange = populationChange;
    }


    public int getInitialYear() {
        return initialYear;
    }
    public void setInitialYear(int initialYear) {
        this.initialYear = initialYear;
    }
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public float getAverageTemp() {
        return AverageTemp;
    }

    public void setAverageTemp(float averageTemp) {
        AverageTemp = averageTemp;
    }

    public float getMinimumTemp() {
        return MinimumTemp;
    }

    public void setMinimumTemp(float minimumTemp) {
        MinimumTemp = minimumTemp;
    }

    public float getMaximumTemp() {
        return MaximumTemp;
    }

    public void setMaximumTemp(float maximumTemp) {
        MaximumTemp = maximumTemp;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public float getStartYearTemperature() {
        return startYearTemperature;
    }

    public void setStartYearTemperature(float startYearTemperature) {
        this.startYearTemperature = startYearTemperature;
    }

    public float getEndYearTemperature() {
        return endYearTemperature;
    }

    public void setEndYearTemperature(float endYearTemperature) {
        this.endYearTemperature = endYearTemperature;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getAveragePopulation() {
        return AveragePopulation;
    }

    public void setAveragePopulation(float averagePopulation) {
        AveragePopulation = averagePopulation;
    }

  
}
