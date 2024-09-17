package app.classes;

public class State {

    private String name;
    private int firstyear;
    private int lastyear;
    private float firstYearTemp;
    private float lastYtemp;
    private float Changes;
    private float ChangeByPercentage;
    private float AverageChange;
    
    private int startYear;
    private int endYear;
    private int initialYear;
    private float AverageTemp;

    public State(){

    }   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFirstyear() {
        return firstyear;
    }
    public void setFirstyear(int firstyear) {
        this.firstyear = firstyear;
    }
    public int getLastyear() {
        return lastyear;
    }
    public void setLastyear(int lastyear) {
        this.lastyear = lastyear;
    }
    public float getFirstYearTemp() {
        return firstYearTemp;
    }
    public void setFirstYearTemp(float firstYearTemp) {
        this.firstYearTemp = firstYearTemp;
    }
    public float getLastYtemp() {
        return lastYtemp;
    }
    public void setLastYtemp(float lastYtemp) {
        this.lastYtemp = lastYtemp;
    }
    public float getChanges() {
        return Changes;
    }
    public void setChanges(float changes) {
        Changes = changes;
    }
    public float getChangeByPercentage() {
        return ChangeByPercentage;
    }
    public void setChangeByPercentage(float changeByPercentage) {
        ChangeByPercentage = changeByPercentage;
    }
    public float getAverageChange() {
        return AverageChange;
    }
    public void setAverageChange(float averageChange) {
        AverageChange = averageChange;
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
    public int getInitialYear() {
        return initialYear;
    }
    public void setInitialYear(int initialYear) {
        this.initialYear = initialYear;
    }
    public float getAverageTemp() {
        return AverageTemp;
    }
    public void setAverageTemp(float averageTemp) {
        AverageTemp = averageTemp;
    }
    
}
