package app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforPeriodTracker;
import app.classes.City;
import app.classes.Country;
import app.classes.Global;
import app.classes.State;
import io.javalin.http.Context;
import io.javalin.http.Handler;

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
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">Select</option>";
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

            if (selectBoxfordisplay != null && startyear != null && !startyear.isEmpty() &&
                    yearLength != null && !yearLength.isEmpty() && comparedRange != null && !comparedRange.isEmpty()) {

                html += "<div class='results-container'>";
                html += "<div class='results-inner'>";
                html += "<table>";
                html += "<thead>";
                html += "<tr>";
                html += "<th>" + selectBoxfordisplay.toUpperCase() + "</th>";
                html += "<th>START YEAR</th>";
                html += "<th>END YEAR</th>";
                html += "<th>YEAR LENGTH</th>";
                html += "<th>AVERAGE TEMPERATURE</th>";
                html += "<th>CHANGE</th>";
                html += "</tr>";
                html += "</thead>";
                html += "<tbody>";

                if ("State".equals(selectBoxfordisplay)) {
                    ArrayList<State> states = new ArrayList<>();
                    for (String state : selectedStates) {
                        State s = jdbcPT.getStateAvgTemp(state, startYear, startYear + yearlength);
                        if (s.getAverageTemp() != 0) {
                            states.add(s);
                        }
                    }

                    if (!states.isEmpty()) {
                        Collections.sort(states, Comparator.comparing(State::getAverageTemp).reversed());
                        float baselineTemp = states.get(0).getAverageTemp();

                        for (int i = 0; i < Math.min(comparedRangeInt, states.size()); i++) {
                            State state = states.get(i);
                            html += "<tr>";
                            html += "<td>" + state.getName() + "</td>";
                            html += "<td>" + state.getStartYear() + "</td>";
                            html += "<td>" + state.getEndYear() + "</td>";
                            html += "<td>" + (state.getEndYear() - state.getStartYear()) + "</td>";
                            html += "<td>" + String.format("%.2f", state.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", (state.getAverageTemp() - baselineTemp)) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='6'>No data available for the selected states and period.</td></tr>";
                    }
                } else if ("City".equals(selectBoxfordisplay)) {
                    ArrayList<City> cities = new ArrayList<>();
                    for (String city : selectedCities) {
                        City c = jdbcPT.getCityAvgTemp(city, startYear, startYear + yearlength);
                        if (c.getAverageTemp() != 0) {
                            cities.add(c);
                        }
                    }

                    if (!cities.isEmpty()) {
                        Collections.sort(cities, Comparator.comparing(City::getAverageTemp).reversed());
                        float baselineTemp = cities.get(0).getAverageTemp();

                        for (int i = 0; i < Math.min(comparedRangeInt, cities.size()); i++) {
                            City city = cities.get(i);
                            html += "<tr>";
                            html += "<td>" + city.getName() + "</td>";
                            html += "<td>" + city.getStartYear() + "</td>";
                            html += "<td>" + city.getEndYear() + "</td>";
                            html += "<td>" + (city.getEndYear() - city.getStartYear()) + "</td>";
                            html += "<td>" + String.format("%.2f", city.getAverageTemp()) + "</td>";
                            html += "<td>" + String.format("%.2f", (city.getAverageTemp() - baselineTemp)) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='6'>No data available for the selected cities and period.</td></tr>";
                    }
                } else if ("Country".equals(selectBoxfordisplay)) {
                    ArrayList<Country> countries = new ArrayList<>();
                    for (String country : selectedCountries) {
                        Country c = jdbcPT.getPopulationavgfromcCountry(country, startYear, startYear + yearlength);
                        if (c.getAverageTemp() != 0) {
                            countries.add(c);
                        }
                    }
                    
                    if (!countries.isEmpty()) {
                        Collections.sort(countries, Comparator.comparing(Country::getAverageTemp).reversed());
                        float avgpoptocompare = jdbcPT.getPopulationavgfromcCountry(selectBoxfordisplay, startYear, startYear + yearlength)
                            .getAveragePopulation();
                        
                        
                        for (int i = 0; i < Math.min(comparedRangeInt, countries.size()); i++) {
                            Country country = countries.get(i);
                            html += "<tr>";
                            html += "<td>" + country.getCountryName() + "</td>";
                            html += "<td>" + country.getStartYear() + "</td>";
                            html += "<td>" + country.getEndYear() + "</td>";
                            html += "<td>" + (country.getEndYear() - country.getStartYear()) + "</td>";
                            html += "<td>" + String.format("%.2f", country.getAveragePopulation()) + "</td>";
                            html += "<td>" + String.format("%.2f", (country.getAveragePopulation() - avgpoptocompare)) + "</td>";
                            html += "</tr>";
                        }
                    } else {
                        html += "<tr><td colspan='6'>No data available for the selected countries and period.</td></tr>";
                    }
                }

                html += "</tbody>";
                html += "</table>";
                html += "</div>";
                html += "</div>";
            }
        }

        html = html + "</div>"; // Closing the container
        html = html + "</body>";
        html = html + "</html>";

        context.html(html);
    }

}
