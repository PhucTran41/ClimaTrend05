package app.page;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
import app.JDBC.JDBCforPeriodTracker;
import app.classes.Global;
import app.classes.State;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import app.classes.City;
import app.classes.Country;

public class PeriodTracker implements Handler {

    public static final String URL = "/PeriodTracker";

    @Override
    public void handle(Context context) throws Exception {

        String html = "<html>";

        // Head
        html = html + "<head>";
        html = html + "<meta charset='UTF-8'>";
        html = html + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html = html + "<title>ClimateTrend Dashboard</title>";
        html = html + "<link rel='stylesheet' href='Level3B.css'>";
        html = html + "</head>";

        // Body
        html = html + "<body>";
        html = html + "<div class='container'>"; // open the container

        // Header & Navigation
        html = html + "<div class='header'>";
        html = html + "<div class='logo'>";
        html = html + "<img src='ClimaTrendLogo.png' alt='ClimaTrendLogo'>";
        html = html + "</div>";
        html = html + "<div class='header-elements'>";
        html = html + "<a href='/'>Home</a>";
        html = html + "<a href='/GlobalTracker'>Global Tracker</a>";
        html = html + "<a href='/CityTracker'>City Tracker</a>";
        html = html + "<a href='/TimelineTracker'>Timeline Tracker</a>";
        html = html + "<a href='/PeriodTracker'>Periods Tracker</a>";
        html = html + "</div>";
        html = html + "</div>";

        html = html + "<div class='shadow'></div>";

        // IMPORTANT PART BE CAREFUL !
        /* u guys must carefully open the div and close the div properly */

        // Search Panel
        html = html + "<form method='post' action='/PeriodTracker'>"; // Put the form before the search-panel
        html = html + "<div class='search-panel'>";

        // JDBC connection //THIS LINE CREATE CONNECTION TO GET METHOD FROM DIFFERENT
        // CLASS

        JDBC jdbc = new JDBC();
        JDBCforPeriodTracker jdbcPT = new JDBCforPeriodTracker();

        // Get selected value from the form //THIS TAKE DATAS FROM THE FILTER
        // //IMPORTANT

        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String term = context.formParam("Term");
        String startyear = context.formParam("startyear");
        String yearLength = context.formParam("yearLength");
        String comparedRange = context.formParam("comparedRange");
        int startYear = startyear != null && !startyear.isEmpty() ? Integer.parseInt(startyear) : 0;
        int yearlength = yearLength != null && !yearLength.isEmpty() ? Integer.parseInt(yearLength) : 0;
        String period = context.formParam("period");
        int comparedRangeInt = comparedRange != null && !comparedRange.isEmpty() ? Integer.parseInt(comparedRange) : 0;
        
        

        ArrayList<String> countryname = jdbcPT.getCountryName();
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])
                : null;

        ArrayList<String> cityName = jdbcPT.getCityName();
        List<String> selectedCitiesList = context.formParams("cities");
        String[] selectedCities = selectedCitiesList != null ? selectedCitiesList.toArray(new String[0]) : null;

        ArrayList<String> stateName = jdbcPT.getStateName();
        List<String> selectedStateList = context.formParams("states");
        String[] selectedStates = selectedStateList != null ? selectedStateList.toArray(new String[0]) : null;

        /*
         * oh u guys can put this in or not because these lines are used for displaying
         * results to the terminal whenever users interact with the filer, try!
         */
        System.out.println("selectBoxfordisplay: " + selectBoxfordisplay);
        System.out.println("selectCity" + Arrays.toString(selectedCities));
        System.out.println("selectCountry" + Arrays.toString(selectedCountries));
        System.out.println("selectState" + Arrays.toString(selectedStates));
        System.out.println("term: " + term);
        System.out.println("startyear: " + startyear);
        System.out.println("yearLength: " + yearLength);
        System.out.println("comparedRange: " + comparedRange);
        

        // Search Section - Display Region Dropdown
        html = html + "<div class='search-section'>";
        html = html + "<div class='search-title'>Display Region</div>";
        html = html + "<div class='select-wrapper'>";

        // Displaying the form with the dropdown

        html = html + "<select name='select-boxfordisplay' class='select-boxfordisplay' onchange='this.form.submit()'>";
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">--Select--</option>";
        html = html + "<option value='Country' " + ("Country".equals(selectBoxfordisplay) ? "selected" : "")
                + ">Country</option>";
        html = html + "<option value='State' " + ("State".equals(selectBoxfordisplay) ? "selected" : "")
                + ">State</option>";
        html = html + "<option value='City' " + ("City".equals(selectBoxfordisplay) ? "selected" : "")
                + ">City</option>";
        html = html + "</select>";

        html = html + "<div class='select-arrow'></div>";
        html = html + "</div>"; // Closing .select-wrapper
        html = html + "</div>";

        if (selectBoxfordisplay != null) {
            // Conditionally rendering Start Year or Country dropdowns based on selection
            if ("City".equals(selectBoxfordisplay)) {
                // If "City" is selected, display Order and Sorting dropdown

                // Output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>City</div>";
                html = html + "<div class='select-wrapper'>";

                // displaying multiple cities
                html = html + "<input list='cities' name = 'cities' class='select-boxfordisplay'>";
                html += "<datalist id='cities'>";
                for (String city : cityName) {
                    html += "<option value='" + city + "' />";
                }
                html += "</datalist>";

                html = html + "</div>"; // closing the select-wrapper div
                html = html + "</div>"; // closing search-section div

            } else if ("Country".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Country</div>";
                html = html + "<div class='select-wrapper'>";

                // displaying multiple countries
                html = html + "<input list='countries' name = 'countries' class='select-boxfordisplay'>";
                html += "<datalist id='countries'>";
                for (String name : countryname) {
                    html += "<option value='" + name + "' />";
                }

                html = html + "</datalist>";

                html = html + "</div>"; // closing the select-wrapper div
                html = html + "</div>"; // closing search-section div

                // display term
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Term</div>";
                html = html + "<div class='select-wrapper'>";

                html = html + "<select class='select-boxfordisplay' id ='Term' name = 'Term'>";
                html = html + "<option value='' " + (term == null ? "selected" : "") + ">--Select--</option>";
                html = html + "<option value= 'averageTemprature'"
                        + ("Average Temperature".equals(term) ? "selected" : "") + ">Average Temperature </option>";
                html = html + "<option value= 'population'" + ("Population".equals(term) ? "selected" : "")
                        + ">Population </option>";
                html = html + "<option value= 'both'"
                        + ("Average Temperature and Population".equals(term) ? "selected" : "")
                        + ">Average Temperature and Population </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>";// closing the select-wrapper div
                html = html + "</div>";// closing search-section div

            } else if ("State".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>State</div>";
                html = html + "<div class='select-wrapper'>";

                // displaying multiple countries
                html = html + "<input list='states' name = 'states' class='select-boxfordisplay'>";
                html += "<datalist id='states'>";
                for (String state : stateName) {
                    html += "<option value='" + state + "' />";
                }

                html = html + "</datalist>";

                html = html + "</div>"; // closing the select-wrapper div
                html = html + "</div>"; // closing search-section div
            }

            // Start Year
            Global firstyear = jdbcPT.getFirstYearTemp();
            Global lastyear = jdbcPT.getLastYearTemp();
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Start Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='startyear' name='startyear' class='select-type' min='"
                    + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";
            html = html + "</div>";


            if (firstyear != null && lastyear != null) {
                int start = firstyear.getYear();
                int end = lastyear.getYear();
                for (int year = start; year <= end; year++) {
                    html += "<option value='" + year + "' " + (Integer.toString(year).equals(startyear) ? "selected" : "") + ">" + year + "</option>";
                }
            } else {
                html += "<option value=''>No years available</option>";
            }

            // Year length
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Year Length</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='yearLength' name='yearLength' class='select-type' value='"
                    + (yearLength != null ? yearLength : "") + "'min='1' max='99'>";
            html = html + "</div>";// closing the select-wrapper div
            html = html + "</div>";// closing search-section div

            // Comparision Range
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Comparision Change</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='comparedRange' name='comparedRange' class='select-type'  value='"
                    + (comparedRange != null ? comparedRange : "") + "'min='1' max='15'>";
            html = html + "</div>";// closing the select-wrapper div
            html = html + "</div>";// closing search-section div

            html = html + "</div>"; // Closing .search-panel

            // Search Button
            html = html + "<button type='submit' class='search-button'>Search</button>";

            html = html + "</form>";

            if (selectBoxfordisplay != null &&
                    ((("City".equals(selectBoxfordisplay) && selectedCities != null && selectedCities.length > 0) ||
                            ("Country".equals(selectBoxfordisplay) && selectedCountries != null
                                    && selectedCountries.length > 0)
                            ||
                            ("State".equals(selectBoxfordisplay) && selectedStates != null
                                    && selectedStates.length > 0))
                            &&
                            startyear != null && !startyear.isEmpty() &&
                            yearLength != null && !yearLength.isEmpty() &&
                            comparedRange != null && !comparedRange.isEmpty())) {

                html = html + "<div class='results-container'>"; // open the result-container
                html = html + "<div class='results-inner'>"; // open the results-inner

                html = html + "<table>";
                html = html + "<thead>";
                html = html + "<tr>";
                html = html + "<th>" + selectBoxfordisplay.toUpperCase() + "</th>";
                html = html + "<th>START YEAR</th>";
                html = html + "<th>END YEAR</th>";
                html = html + "<th>YEAR LENGTH</th>";
                html = html + "<th>AVERAGE TEMPERATURE</th>";
                html = html + "<th>CHANGE</th>";
                html = html + "</tr>";
                html = html + "</thead>";
                html = html + "<tbody>"; // open the table body

                // Displaying the results
                int samplespace = 0;

                
        if (startyear != null && !startyear.isEmpty() && period != null
        && !period.isEmpty() && comparedRange != null && !comparedRange.isEmpty()
        && selectBoxfordisplay != null) {

                if("State".equals(selectBoxfordisplay) && jdbcPT.getStateAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength).getAverageTemp() != 0) {
                    ArrayList<State> states = new ArrayList<>();
                    for (String state : jdbcPT.getStateName()) {
                        
                        for (int i = startYear; i + yearlength < 2013; i++) {
                            State s = jdbcPT.getStateAvgTemp(state, i, i + yearlength);
                            s.setName(state);
                            if (s.getEndYear() - s.getStartYear() == yearlength) {
                                states.add(s);
                            }
                        }
                        System.out.println("States retrieved: " + states.size());


                    }
                    float avgtempcompare = jdbcPT.getStateAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength)
                            .getAverageTemp();
                            if ("State".equals(selectBoxfordisplay)) {
                                System.out.println("line 311 is running");
                                ArrayList<State> statess = new ArrayList<>();
                                for (String state : jdbcPT.getStateName()) {
                                    State s = jdbcPT.getStateAvgTemp(state, startYear, startYear + yearlength);
                                    if (s.getAverageTemp() != 0) {
                                        states.add(s);
                                    }
                                }
                
                                if (!states.isEmpty()) {
                                    Collections.sort(states, Comparator.comparing(State::getAverageTemp));
                                    for (int i = 0; i < Math.min(comparedRangeInt, states.size()); i++) {
                                        State state = states.get(i);
                                        html += "<tr>";
                                        html += "<td>" + state.getName() + "</td>";
                                        html += "<td>" + state.getStartYear() + "</td>";
                                        html += "<td>" + state.getEndYear() + "</td>";
                                        html += "<td>" + yearlength + "</td>";
                                        html += "<td>" + String.format("%.2f", state.getAverageTemp()) + "</td>";
                                        html += "<td>" + String.format("%.2f", (state.getAverageTemp() - states.get(0).getAverageTemp())) + "</td>";
                                        html += "</tr>";
                                    }
                                } else {
                                    html += "<tr><td colspan='6'>No data available for the selected period.</td></tr>";
                                }
                            }
    
                        html += "</tbody>";
                    

                }


                if ("City".equals(selectBoxfordisplay) && jdbcPT
                        .getCityAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength).getAverageTemp() != 0) {
                            System.out.println("line 345 is running");
                    ArrayList<City> cities = new ArrayList<>();
                    for (String city : jdbcPT.getCityName()) {
                        for (int i = startYear; i + yearlength < 2013; i++) {
                            City c = jdbcPT.getCityAvgTemp(city, i, i + yearlength);
                            c.setName(city);
                            if (c.getEndYear() - c.getStartYear() == yearlength) {
                                cities.add(c);
                            }
                        }
                        System.out.println("Cities retrieved: " + cities.size());
                    }
                    float avgtempcompare = jdbcPT.getCityAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength)
                            .getAverageTemp();

                    if (!cities.isEmpty()) {
                        Collections.sort(cities, new Comparator<City>() {
                            @Override
                            public int compare(City c1, City c2) {
                                float diff1 = Math.abs(c1.getAverageTemp() - avgtempcompare);
                                float diff2 = Math.abs(c2.getAverageTemp() - avgtempcompare);
                                return Float.compare(diff1, diff2);
                            }
                        });

                        // Table
                        html += "<table>";
                        html += "<tr><th>City</th><th>Start Year</th><th>End Year</th><th>Period</th><th>Average Temperature</th><th>Avg Temperature Difference</th></tr>";

                        for (City city : cities) {
                            html += "<tr>";
                            html += "<td>" + city.getName() + "</td>";
                            html += "<td>" + city.getStartYear() + "</td>";
                            html += "<td>" + city.getEndYear() + "</td>";
                            html += "<td>" + (city.getEndYear() - city.getStartYear()) + "</td>";
                            html += "<td>" + city.getAverageTemp() + "</td>";
                            html += "<td>" + (city.getAverageTemp() - avgtempcompare) + "</td>";
                            html += "</tr>";
                        }
                        

                        html += "</tbody>";
                    }
                } else if (jdbcPT.getCityAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength)
                        .getAverageTemp() == 0
                        && selectBoxfordisplay.equals("City")) {
                    html += "<h2>There is no temperature data for your chosen period.</h2>";

                }

                if ("Country".equals(selectBoxfordisplay)
                        && jdbcPT.getAvgCountryTemp(selectBoxfordisplay, startYear, startYear + yearlength)
                                .getAverageTemp() != 0) {
                                    System.out.println("line 398 is running");
                    ArrayList<Country> countries = new ArrayList<>();
                    for (String country : jdbcPT.getCountryName()) {
                        for (int i = startYear; i + yearlength < 2013; i++) {
                            Country coun = jdbcPT.getAvgCountryTemp(country, i, i + yearlength);
                            coun.setCountryName(country);
                            countries.add(coun);

                        }

                    }
                    float avgtempcompare = jdbcPT.getCityAvgTemp(selectBoxfordisplay, startYear, startYear + yearlength)
                            .getAverageTemp();

                    if (!countries.isEmpty()) {
                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country c1, Country c2) {
                                float diff1 = Math.abs(c1.getAverageTemp() - avgtempcompare);
                                float diff2 = Math.abs(c2.getAverageTemp() - avgtempcompare);
                                return Float.compare(diff1, diff2);
                            }
                        });
                        html += "<h2>Country Temperature Data</h2>";
                        html += "<table>";
                        html += "<tr><th>Country</th><th>Start Year</th><th>End Year</th><th>Period</th><th>Average Temperature</th><th>Avg Temperature Difference</th></tr>";

                        for (int i = 0; i < Math.min((samplespace + 1), countries.size()); i++) {
                            Country country = countries.get(i);
                            html += "<tr>";
                            html += "<td>" + country.getCountryName() + "</td>";
                            html += "<td>" + country.getStartYear() + "</td>";
                            html += "<td>" + (country.getStartYear() + yearlength) + "</td>";
                            html += "<td>" + yearlength + " years</td>";
                            html += "<td>" + String.format("%.4f&deg;C", country.getAveragePopulation()) + "</td>";
                            html += "<td>"
                                    + String.format("%.4f", Math.abs(country.getAveragePopulation() - avgtempcompare))
                                    + "</td>";
                            html += "</tr>";
                        }

                        html += "</tbody>";
                    } else {
                        html += "<h2>There is no population data for your chosen period.</h2>";
                    }
                }else if (jdbcPT.getAvgCountryTemp(selectBoxfordisplay, startYear, startYear + yearlength).getAveragePopulation() == 0
                && selectBoxfordisplay.equals("country")) {
            html += "<h2>There is no population data for  your chosen period.</h2>";
        }
                html += "</table>";
                html = html + "</div>";// close the results-inner
                html = html + "</div>";// close the results-container
            }
        }
        }

        
      

        html = html + "</div>"; // Closing the container
        html = html + "</body>";
        html = html + "</html>";

        context.html(html);
    }

}
