package app.classes;


public class Global {
    private int year;
    private float averageTemp;
    private float MinimumTemp;
    private float MaximumTemp;
    private long population;
    private int initialYear;
    private int startYear;
    private int endYear;

    public Global() {
    }

    public Global(int year, long population, float averageTemp) {
        this.year = year;
        this.population = population;
        this.averageTemp = averageTemp;
    }
    
    public Global(int year, float averageTemp, float minimumTemp, float maximumTemp, int initialYear, int startYear, int endYear) {
        this.year = year;
        this.averageTemp = averageTemp;
        MinimumTemp = minimumTemp;
        MaximumTemp = maximumTemp;
        this.initialYear = initialYear;
        this.startYear = startYear;
        this.endYear = endYear;

    }


    public long getPopulation() {
        return population;
    }
    public void setPopulation(long population) {
        this.population = population;
    }
    public int getInitialYear() {
        return initialYear;
    }
    public void setInitialYear(int initialYear) {
        this.initialYear = initialYear;
    }
    public int getStartYear() {
        return startYear;
    }
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    public int getEndYear() {
        return endYear;
    }
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public float getAverageTemp() {
        return averageTemp;
    }
    public void setAverageTemp(float averageTemp) {
        this.averageTemp = averageTemp;
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
}
