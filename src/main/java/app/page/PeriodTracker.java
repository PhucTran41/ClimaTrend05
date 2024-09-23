package app.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.JDBC.JDBCforPeriodTracker;
import app.classes.City;
import app.classes.Country;
import app.classes.State;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PeriodTracker implements Handler {

    public static final String URL = "/PeriodTracker";

    @Override
    public void handle(Context context) throws Exception {

        String html = "<html>";

        // Head
        html += "<head>";
        html += "<meta charset='UTF-8'>";
        html += "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html += "<title>ClimateTrend Dashboard</title>";
        html += "<link rel='stylesheet' href='Level3B.css'>";
        html += "</head>";

        // Body
        html += "<body>";
        html += "<div class='container'>"; // open the container

        // Header & Navigation
        html += "<div class='header'>";
        html += "<div class='logo'>";
        html += "<img src='ClimaTrendLogo.png' alt='ClimaTrendLogo'>";
        html += "</div>";
        html += "<div class='header-elements'>";
        html += "<a href='/'>Home</a>";
        html += "<a href='/GlobalTracker'>Global Tracker</a>";
        html += "<a href='/CityTracker'>City Tracker</a>";
        html += "<a href='/TimelineTracker'>Timeline Tracker</a>";
        html += "<a href='/PeriodTracker'>Periods Tracker</a>";
        html += "</div>";
        html += "</div>";

        html += "<div class='shadow'></div>";

        // Search Panel
        html += "<form method='post' action='/PeriodTracker'>";
        html += "<div class='search-panel'>";

        JDBCforPeriodTracker jdbcPT = new JDBCforPeriodTracker();

        // Get selected value from the form
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String startyear = context.formParam("startyear");
        String yearLength = context.formParam("yearLength");
        String comparedRange = context.formParam("comparedRange");
        int startYear = startyear != null && !startyear.isEmpty() ? Integer.parseInt(startyear) : 0;
        int yearlength = yearLength != null && !yearLength.isEmpty() ? Integer.parseInt(yearLength) : 0;
        int comparedRangeInt = comparedRange != null && !comparedRange.isEmpty() ? Integer.parseInt(comparedRange) : 0;
        String term = context.formParam("Term");
        String selectedRegion = context.formParam("selectedRegion");

        // Search Section - Display Region Dropdown
        html += "<div class='search-section'>";
        html += "<div class='search-title'>Display Region</div>";
        html += "<div class='select-wrapper'>";

        html += "<select name='select-boxfordisplay' class='select-boxfordisplay' onchange='this.form.submit()'>";
        html += "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">Select</option>";
        html += "<option value='Country' " + ("Country".equals(selectBoxfordisplay) ? "selected" : "") + ">Country</option>";
        html += "<option value='State' " + ("State".equals(selectBoxfordisplay) ? "selected" : "") + ">State</option>";
        html += "<option value='City' " + ("City".equals(selectBoxfordisplay) ? "selected" : "") + ">City</option>";
        html += "</select>";

        html += "<div class='select-arrow'></div>";
        html += "</div>"; // Closing .select-wrapper
        html += "</div>";

        if ("Country".equals(selectBoxfordisplay)) {
            // Term Selection
            html += "<div class='search-section'>";
            html += "<div class='search-title'>Term</div>";
            html += "<div class='select-wrapper'>";

            html += "<select class='select-boxfordisplay' id='Term' name='Term'>";
            html += "<option value='' " + (term == null ? "selected" : "") + ">--Select--</option>";
            html += "<option value='averageTemperature'" + ("averageTemperature".equals(term) ? " selected" : "") + ">Average Temperature</option>";
            html += "<option value='population'" + ("population".equals(term) ? " selected" : "") + ">Population</option>";
            html += "<option value='both'" + ("both".equals(term) ? " selected" : "") + ">Average Temperature and Population</option>";
            html += "</select>";

            html += "<div class='select-arrow'></div>";
            html += "</div>"; // closing the select-wrapper div
            html += "</div>"; // closing search-section div
        }

        if (selectBoxfordisplay != null && !selectBoxfordisplay.isEmpty()) {
            // Region Selection
            html += "<div class='search-section'>";
            html += "<div class='search-title'>" + selectBoxfordisplay + "</div>";
            html += "<div class='select-wrapper'>";

            html += "<select name='selectedRegion' class='select-boxfordisplay'>";
            List<String> regionNames = new ArrayList<>();
            if ("Country".equals(selectBoxfordisplay)) {
                regionNames = jdbcPT.getCountryName();
            } else if ("State".equals(selectBoxfordisplay)) {
                regionNames = jdbcPT.getStateName();
            } else if ("City".equals(selectBoxfordisplay)) {
                regionNames = jdbcPT.getCityName();
            }

            for (String name : regionNames) {
                html += "<option value='" + name + "' " + (name.equals(selectedRegion) ? "selected" : "") + ">" + name + "</option>";
            }

            html += "</select>";

            html += "<div class='select-arrow'></div>";
            html += "</div>"; // closing the select-wrapper div
            html += "</div>"; // closing search-section div

            // Start Year
            html += "<div class='search-section'>";
            html += "<div class='search-title'>Start Year</div>";
            html += "<div class='select-wrapper'>";
            html += "<input type='number' id='startyear' name='startyear' class='select-type' min='1750' max='2012' value='" + (startyear != null ? startyear : "") + "'/>";
            html += "</div>";
            html += "</div>";

            // Year Length
            html += "<div class='search-section'>";
            html += "<div class='search-title'>Year Length</div>";
            html += "<div class='select-wrapper'>";
            html += "<input type='number' id='yearLength' name='yearLength' class='select-type' min='1' max='99' value='" + (yearLength != null ? yearLength : "") + "'/>";
            html += "</div>"; // closing the select-wrapper div
            html += "</div>"; // closing search-section div

            // Comparison Range
            html += "<div class='search-section'>";
            html += "<div class='search-title'>Comparison Range</div>";
            html += "<div class='select-wrapper'>";
            html += "<input type='number' id='comparedRange' name='comparedRange' class='select-type' min='1' max='15' value='" + (comparedRange != null ? comparedRange : "") + "'/>";
            html += "</div>"; // closing the select-wrapper div
            html += "</div>"; // closing search-section div

            html += "</div>"; // Closing .search-panel

            // Search Button
            html += "<button type='submit' class='search-button'>Search</button>";

            html += "</form>";

            if (startyear != null && !startyear.isEmpty() && yearLength != null && !yearLength.isEmpty() && comparedRange != null && !comparedRange.isEmpty() && selectedRegion != null && !selectedRegion.isEmpty()) {

                html += "<div class='results-container'>";
                html += "<div class='results-inner'>";
                html += "<table>";
                html += "<thead>";
                html += "<tr>";
                html += "<th>" + selectBoxfordisplay.toUpperCase() + "</th>";
                html += "<th>START YEAR</th>";
                html += "<th>END YEAR</th>";
                html += "<th>START AVG TEMP (°C)</th>";
                html += "<th>END AVG TEMP (°C)</th>";
                html += "<th>AVG TEMP CHANGE (°C)</th>";
                html += "<th>CHANGE DIFFERENCE</th>";
                html += "</tr>";
                html += "</thead>";
                html += "<tbody>";
    
                // Initialize variables
                float userRegionTempChange = 0;
    
                if ("City".equals(selectBoxfordisplay)) {
                    // Get user's selected city average temperature change
                    float userStartTemp = jdbcPT.getCityAvgTemp(selectedRegion, startYear, startYear).getAverageTemp();
                    float userEndTemp = jdbcPT.getCityAvgTemp(selectedRegion, startYear + yearlength, startYear + yearlength).getAverageTemp();
    
                    if (userStartTemp != 0 && userEndTemp != 0) {
                        userRegionTempChange = userEndTemp - userStartTemp;
    
                        ArrayList<City> cities = new ArrayList<>();
                        for (String city : jdbcPT.getCityName()) {
                            for (int i = 1750; i <= 2012 - yearlength; i++) {
                                float startTemp = jdbcPT.getCityAvgTemp(city, i, i).getAverageTemp();
                                float endTemp = jdbcPT.getCityAvgTemp(city, i + yearlength, i + yearlength).getAverageTemp();
    
                                if (startTemp != 0 && endTemp != 0) {
                                    float tempChange = endTemp - startTemp;
                                    float changeDifference = Math.abs(tempChange - userRegionTempChange);
    
                                    City cityData = new City();
                                    cityData.setName(city);
                                    cityData.setStartYear(i);
                                    cityData.setEndYear(i + yearlength);
                                    cityData.setAverageTemp(startTemp);
                                    cityData.setAverageTemp(endTemp);
                                    cityData.setAverageChange(tempChange);
                                    cityData.setChanges(changeDifference);
    
                                    cities.add(cityData);
                                }
                            }
                        }
    
                        // Sort based on change difference
                        Collections.sort(cities, Comparator.comparing(City::getChanges));
    
                        // Display results
                        for (int i = 0; i < Math.min(comparedRangeInt, cities.size()); i++) {
                            City city = cities.get(i);
                            html += "<tr>";
                            html += "<td>" + city.getName() + "</td>";
                            html += "<td>" + city.getStartYear() + "</td>";
                            html += "<td>" + city.getEndYear() + "</td>";
                            html += "<td>" + String.format("%.2f", city.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", city.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", city.getAverageChange()) + "</td>";
                            html += "<td>" + String.format("%.2f", city.getChanges()) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='7'>No data available for the selected city and period.</td></tr>";
                    }
                } else if ("State".equals(selectBoxfordisplay)) {
                    // Get user's selected state average temperature change
                    float userStartTemp = jdbcPT.getStateAvgTemp(selectedRegion, startYear, startYear).getAverageTemp();
                    float userEndTemp = jdbcPT.getStateAvgTemp(selectedRegion, startYear + yearlength, startYear + yearlength).getAverageTemp();
    
                    if (userStartTemp != 0 && userEndTemp != 0) {
                        userRegionTempChange = userEndTemp - userStartTemp;
    
                        ArrayList<State> states = new ArrayList<>();
                        for (String state : jdbcPT.getStateName()) {
                            for (int i = 1750; i <= 2012 - yearlength; i++) {
                                float startTemp = jdbcPT.getStateAvgTemp(state, i, i).getAverageTemp();
                                float endTemp = jdbcPT.getStateAvgTemp(state, i + yearlength, i + yearlength).getAverageTemp();
    
                                if (startTemp != 0 && endTemp != 0) {
                                    float tempChange = endTemp - startTemp;
                                    float changeDifference = Math.abs(tempChange - userRegionTempChange);
    
                                    State stateData = new State();
                                    stateData.setName(state);
                                    stateData.setStartYear(i);
                                    stateData.setEndYear(i + yearlength);
                                    stateData.setAverageTemp(startTemp);
                                    stateData.setAverageTemp(endTemp);
                                    stateData.setAverageChange(tempChange);
                                    stateData.setChanges(changeDifference);
    
                                    states.add(stateData);
                                }
                            }
                        }
    
                        // Sort based on change difference
                        Collections.sort(states, Comparator.comparing(State::getAverageChange));
    
                        // Display results
                        for (int i = 0; i < Math.min(comparedRangeInt, states.size()); i++) {
                            State state = states.get(i);
                            html += "<tr>";
                            html += "<td>" + state.getName() + "</td>";
                            html += "<td>" + state.getStartYear() + "</td>";
                            html += "<td>" + state.getEndYear() + "</td>";
                            html += "<td>" + String.format("%.2f", state.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", state.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", state.getAverageChange()) + "</td>";
                            html += "<td>" + String.format("%.2f", state.getChanges()) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='7'>No data available for the selected state and period.</td></tr>";
                    }
                } else if ("Country".equals(selectBoxfordisplay)) {
                    // Get user's selected country average temperature change
                    float userStartTemp = jdbcPT.getAvgCountryTemp(selectedRegion, startYear, startYear).getAverageTemp();
                    float userEndTemp = jdbcPT.getAvgCountryTemp(selectedRegion, startYear + yearlength, startYear + yearlength).getAverageTemp();
    
                    if (userStartTemp != 0 && userEndTemp != 0) {
                        userRegionTempChange = userEndTemp - userStartTemp;
                        
    
                        ArrayList<Country> countries = new ArrayList<>();
                        for (String country : jdbcPT.getCountryName()) {
                            for (int i = 1750; i <= 2012 - yearlength; i++) {
                                float startTemp = jdbcPT.getAvgCountryTemp(country, i, i).getAverageTemp();
                                float endTemp = jdbcPT.getAvgCountryTemp(country, i + yearlength, i + yearlength).getAverageTemp();
    
                                if (startTemp != 0 && endTemp != 0) {
                                    float tempChange = endTemp - startTemp;
                                    float changeDifference = Math.abs(tempChange - userRegionTempChange);
    
                                    Country countryData = new Country();
                                    countryData.setCountryName(country);
                                    countryData.setStartYear(i);
                                    countryData.setEndYear(i + yearlength);
                                    countryData.setAverageTemp(startTemp);
                                    countryData.setEndYearTemperature(endTemp);
                                    countryData.setChange(tempChange);
                                    countryData.setChange(changeDifference);
    
                                    countries.add(countryData);
                                }
                            }
                        }
    
                        // Sort based on change difference
                        Collections.sort(countries, Comparator.comparing(Country::getAverageTemp));
    
                        // Display results
                        for (int i = 0; i < Math.min(comparedRangeInt, countries.size()); i++) {
                            Country country = countries.get(i);
                            html += "<tr>";
                            html += "<td>" + country.getCountryName() + "</td>";
                            html += "<td>" + country.getStartYear() + "</td>";
                            html += "<td>" + country.getEndYear() + "</td>";
                            html += "<td>" + String.format("%.2f", country.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", country.getEndYearTemperature()) + "</td>";
                            html += "<td>" + String.format("%.2f", country.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", country.getChange()) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='7'>No data available for the selected country and period.</td></tr>";
                    }
                }
    
                html += "</tbody>";
                html += "</table>";
                html += "</div>";
                html += "</div>";
            }
    
        }

        html += "</div>"; // Closing the container
        html += "</body>";
        html += "</html>";

        context.html(html);
    }
}
