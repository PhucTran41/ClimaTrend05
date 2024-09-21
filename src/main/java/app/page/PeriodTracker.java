package app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
import app.JDBC.JDBCforPeriodTracker;
import app.classes.Global;
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
        html = html + "<div class='container'>"; //open the container

        // Header & Navigation
        html = html + "<div class='header'>";
        html = html + "<div class='logo'>";
        html = html + "<img src='images/World_Tracker_Logo__1_-removebg-preview.png' alt='WorldChanges Logo'>";
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


        //IMPORTANT PART BE CAREFUL ! 
        /* u guys must carefully open the div and close the div properly */

        // Search Panel
        html = html + "<form method='post' action='/PeriodTracker'>"; //Put the form before the search-panel
        html = html + "<div class='search-panel'>";



        // JDBC connection                      //THIS LINE CREATE CONNECTION TO GET METHOD FROM DIFFERENT CLASS
        JDBC jdbc = new JDBC();
        JDBCforGlobalTracker jdbc2 = new JDBCforGlobalTracker();
        JDBCforPeriodTracker jdbcPT = new JDBCforPeriodTracker();

       

        // Get selected value from the form                 //THIS TAKE DATAS FROM THE FILTER   //IMPORTANT

        
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String term = context.formParam("Term");
        String startyear = context.formParam("startyear");
        String yearLength = context.formParam("yearLength");
        String comparedRange = context.formParam("comparedRange");
        


        ArrayList<String> countryname = jdbc2.getCountryName();
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;

        ArrayList<String> cityName = jdbc2.getCityNameFromCountry();
        List<String> selectedCitiesList = context.formParams("cities");
        String[] selectedCities = selectedCitiesList != null ? selectedCitiesList.toArray(new String[0])  : null;

        ArrayList<String> stateName = jdbc2.getStateNameFromCountry();
        List<String> selectedStateList = context.formParams("states");
        String[] selectedStates = selectedStateList != null ? selectedStateList.toArray(new String[0])  : null;



  

        

        /* oh u guys can put this in or not because these lines are used for displaying results to the terminal whenever users interact with the filer, try! */
        System.out.println("selectBoxfordisplay: " + selectBoxfordisplay);
        System.out.println("selectCity" + Arrays.toString(selectedCities));




        // Search Section - Display Region Dropdown
        html = html + "<div class='search-section'>";
        html = html + "<div class='search-title'>Display Region</div>";
        html = html + "<div class='select-wrapper'>";

        // Displaying the form with the dropdown
        
        html = html + "<select name='select-boxfordisplay' class='select-boxfordisplay' onchange='this.form.submit()'>"; 
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">--Select--</option>";
        html = html + "<option value='City' " + ("City".equals(selectBoxfordisplay) ? "selected" : "")
                + ">City</option>";
        html = html + "<option value='State' " + ("State".equals(selectBoxfordisplay) ? "selected" : "")
                + ">State</option>";
        html = html + "<option value='Country' " + ("Country".equals(selectBoxfordisplay) ? "selected" : "")
                + ">Country</option>";
        html = html + "</select>";

        html = html + "<div class='select-arrow'></div>";
        html = html + "</div>"; // Closing .select-wrapper
        html = html + "</div>";

  

        
        if (selectBoxfordisplay != null) {
            // Conditionally rendering Start Year or Country dropdowns based on selection
            if ("City".equals(selectBoxfordisplay)) {
                // If "City" is selected, display Order and Sorting dropdown
              
                //Output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>City</div>";
                html = html + "<div class='select-wrapper'>";

                //displaying multiple cities
                html = html + "<input list='cities'  name = 'cities' class='select-boxfordisplay'>";
                html += "<datalist id='cities'>";
                for (String city : cityName) {
                    html += "<option value='" + city + "' />";
                }       
                html += "</datalist>";

                html = html + "</div>"; //closing the select-wrapper div
                html = html + "</div>"; //closing search-section div           
               

            } else if ("Country".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Country</div>";
                html = html + "<div class='select-wrapper'>";

                //displaying multiple countries
                html = html + "<input list='countries' name = 'countries' class='select-boxfordisplay'>";
                html += "<datalist id='countries'>";
                for (String name : countryname) {
                    html += "<option value='" + name + "' />";
                }

                html = html + "</datalist>";

                html = html + "</div>"; //closing the select-wrapper div
                html = html + "</div>"; //closing search-section div     

                //display term
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Term</div>";
                html = html + "<div class='select-wrapper'>";

                html = html + "<select class='select-boxfordisplay' id ='Term' name = 'Term'>";
                html = html + "<option value='' " + (term == null ? "selected" : "") + ">--Select--</option>";
                html = html + "<option value= 'averageTemprature'" + ("Average Temperature".equals(term) ? "selected":"") +">Average Temperature </option>";
                html = html + "<option value= 'population'" + ("Population".equals(term) ? "selected":"") +">Population </option>";
                html = html + "<option value= 'both'" + ("Average Temperature and Population".equals(term) ? "selected":"") +">Average Temperature and Population </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>";//closing the select-wrapper div
                html = html + "</div>";// closing search-section div     


            } else if ("State".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>State</div>";
                html = html + "<div class='select-wrapper'>";

                //displaying multiple countries
                html = html + "<input list='states' name = 'states' class='select-boxfordisplay'>";
                html += "<datalist id='states'>";
                for (String state : stateName) {
                    html += "<option value='" + state + "' />";
                }

                html = html + "</datalist>";

                html = html + "</div>"; //closing the select-wrapper div
                html = html + "</div>"; //closing search-section div     
            }

            // Start Year
            Global firstyear = jdbc.getFirstYearTemp();
            Global lastyear = jdbc.getLastYearTemp();
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Start Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='startyear' name='startyear' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";
            html = html + "</div>";

            // Year length 
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Year Length</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='yearLength' name='yearLength' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";//closing the select-wrapper div
            html = html + "</div>";// closing search-section div    

            // Comparision Range 
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Comparision Change</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='comparedRange' name='comparedRange' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";//closing the select-wrapper div
            html = html + "</div>";// closing search-section div    
            
        
            
           
            html = html + "</div>"; // Closing .search-panel
            

            // Search Button
            html = html + "<button type='submit' class='search-button'>Search</button>";

            html = html + "</form>";
    
        if (("City".equals(selectBoxfordisplay) && selectedCities != null && selectedCities.length > 0 && yearLength != null && !yearLength.isEmpty() && 
        startyear != null && !startyear.isEmpty() && comparedRange != null && !comparedRange.isEmpty()) ||
        ("Country".equals(selectBoxfordisplay) && selectedCountries != null && selectedCountries.length > 0 && term != null && !term.isEmpty() && 
        startyear != null && !startyear.isEmpty() && comparedRange != null && !comparedRange.isEmpty() && yearLength != null && !yearLength.isEmpty()) ||
        ("State".equals(selectBoxfordisplay) && selectedCountries != null && selectedCountries.length > 0 && startyear != null && !startyear.isEmpty() && 
        comparedRange != null && !comparedRange.isEmpty() && yearLength != null && !yearLength.isEmpty())) {

            html = html + "<div class='results-container'>"; //open the result-container
            html = html + "<div class='results-inner'>"; //open the results-inner

            html = html + "<table>";
            html = html + "<thead>";
            html = html + "<tr>";
            html = html + "<th>" +selectBoxfordisplay.toUpperCase() +"</th>";
            html = html + "<th>START YEAR</th>";
            html = html + "<th>END YEAR</th>";
            html = html + "<th> YEAR LENGTH </th>";
            html = html + "<th> AVERAGE TEMPERATURE </th>";
            html = html + "<th> CHANGE </th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody>"; //open the table body 

        if ("City".equals(selectBoxfordisplay) && selectedCities != null && selectedCities.length > 0 && yearLength != null && !yearLength.isEmpty() && 
        startyear != null && !startyear.isEmpty() && comparedRange != null && !comparedRange.isEmpty()){

            //FIXME: this is not supposed to get the data from GlobalTracker
            List<Global> result = jdbc2.getGlobalDatafromCountry(selectedCountries, yearLength, startyear, comparedRange);

            int rowNumber = 1;
            for (Global data : result) {
                html = html + "<tr>";
                html = html + "<td>" + data.getName() + "</td>";
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getPeriod() + "</td>";
                html = html + "<td>" + data.getFirstYearTemperature() + "</td>";
                html = html + "<td>" + data.getLastYearTemperature() + "</td>";
                html = html + "<td>" + data.getChange() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }

            html = html + "</tbody>"; //close the table body when if World is selected

         
        }

        else if ("Country".equals(selectBoxfordisplay) && selectedCountries != null && selectedCountries.length > 0 && term != null && !term.isEmpty() && 
        startyear != null && !startyear.isEmpty() && comparedRange != null && !comparedRange.isEmpty() && yearLength != null && !yearLength.isEmpty()){


            //FIXME: this is not supposed to get the data from GlobalTracker
            List<Global> result = jdbc2.getGlobalDatafromCountry(selectedCountries, yearLength, startyear, comparedRange);

            int rowNumber = 1;
            for (Global data : result) {
                html = html + "<tr>";
                html = html + "<td>" + data.getName() + "</td>";
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getPeriod() + "</td>";
                html = html + "<td>" + data.getFirstYearTemperature() + "</td>";
                html = html + "<td>" + data.getLastYearTemperature() + "</td>";
                html = html + "<td>" + data.getChange() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }

        
        html = html + "</tbody>"; //close the table body if country was selected

    }
        else if ("State".equals(selectBoxfordisplay) && selectedCountries != null && selectedCountries.length > 0 && startyear != null && !startyear.isEmpty() && comparedRange != null && !comparedRange.isEmpty() && yearLength != null && !yearLength.isEmpty()){


            //FIXME: this is not supposed to get the data from GlobalTracker
            List<Global> result = jdbc2.getGlobalDatafromCountry(selectedCountries, yearLength, startyear, comparedRange);

            int rowNumber = 1;
            for (Global data : result) {
                html = html + "<tr>";
                html = html + "<td>" + data.getName() + "</td>";
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getPeriod() + "</td>";
                html = html + "<td>" + data.getFirstYearTemperature() + "</td>";
                html = html + "<td>" + data.getLastYearTemperature() + "</td>";
                html = html + "<td>" + data.getChange() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }

        
        html = html + "</tbody>"; //close the table body if country was selected

    }
}
                    //table display 
                    //open the table if World is selected


        html = html + "</table>"; //close the table 
        html = html + "</div>";//close the results-inner
        html = html + "</div>";//close the results-container
    }

        html = html + "</div>"; // Closing the container
        html = html + "</body>";
        html = html + "</html>";
       
        context.html(html);
        }   

    }




