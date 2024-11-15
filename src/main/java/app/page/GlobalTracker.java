package app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
import app.classes.Country;
import app.classes.Global;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class GlobalTracker implements Handler {

    public static final String URL = "/GlobalTracker";

    @Override
    public void handle(Context context) throws Exception {

        String html = "<html>";

        // Head
        html = html + "<head>";
        html = html + "<meta charset='UTF-8'>";
        html = html + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html = html + "<title>ClimateTrend Dashboard</title>";
        html = html + "<link rel='stylesheet' href='Level2A.css'>";
        html = html + "</head>";

        // Body
        html = html + "<body>";
        html = html + "<div class='container'>"; //open the container

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


        //IMPORTANT PART BE CAREFUL ! 
        /* Must carefully open the div and close the div properly */

        // Search Panel
        html = html + "<form method='post' action='/GlobalTracker'>"; //Put the form before the search-panel
        html = html + "<div class='search-panel'>";



        // JDBC connection                      //THIS LINE CREATE CONNECTION TO GET METHOD FROM DIFFERENT CLASS
        JDBC jdbc = new JDBC();
        JDBCforGlobalTracker jdbc2 = new JDBCforGlobalTracker();

       

        // Get selected value from the form                 //THIS TAKE DATAS FROM THE FILTER   //IMPORTANT

        ArrayList<String> countryname = jdbc2.getCountryName();
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String output = context.formParam("output");
        String orderby = context.formParam("orderby");
        String OutputType = context.formParam("OutputType");
        String startyear = context.formParam("startyear");
        String endyear = context.formParam("endyear");
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;
        

        /* oh u guys can put this in or not because these lines are used for displaying results to the terminal whenever users interact with the filer, try! */
        System.out.println("selectBoxfordisplay: " + selectBoxfordisplay);
        System.out.println("output: " + output);
        System.out.println("orderby: " + orderby);
        System.out.println("startYear: " + startyear);
        System.out.println("endYear: " + endyear);
        System.out.println("Countries: " + Arrays.toString(selectedCountries));
        System.out.println("OutputType: " + OutputType);


        // Search Section - Display Region Dropdown
        html = html + "<div class='search-section'>";
        html = html + "<div class='search-title'>Display Region</div>";
        html = html + "<div class='select-wrapper'>";

        // Displaying the form with the dropdown
        
        html = html + "<select name='select-boxfordisplay' class='select-boxfordisplay' onchange='this.form.submit()'>"; 
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">Select</option>";
        html = html + "<option value='World' " + ("World".equals(selectBoxfordisplay) ? "selected" : "")
                + ">World</option>";
        html = html + "<option value='Country' " + ("Country".equals(selectBoxfordisplay) ? "selected" : "")
                + ">Country</option>";
        html = html + "</select>";

        html = html + "<div class='select-arrow'></div>";
        html = html + "</div>"; // Closing .select-wrapper
        html = html + "</div>";

  

        
        if (selectBoxfordisplay != null) {
            // Conditionally rendering Start Year or Country dropdowns based on selection
            if ("World".equals(selectBoxfordisplay)) {
                // If "World" is selected, display Order and Sorting dropdown
                // Order
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Order</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select class='select-boxfordisplay' id ='orderby' name ='orderby'>";
                html = html + "<option value='' " + (orderby == null ? "selected" : "") + ">Select</option>";
                html = html + "<option value= 'Ascending'" + ("Ascending".equals(orderby) ? "selected":"") +">Ascending </option>";
                html = html + "<option value= 'Descending'" + ("Descending".equals(orderby) ? "selected":"") +">Descending </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>"; //closing select-wrapper
                html = html + "</div>"; //closing search-section
                
                
                //Output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Output</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select class='select-boxfordisplay' id= 'output' name = 'output' >";
                html = html + "<option value='' " + (output == null ? "selected" : "") + ">Select</option>";
                html = html + "<option value = 'Year' " + ("Year".equals(output) ? "selected" : "") 
                    + ">Year</option>";
                html = html + "<option value = 'Population' " + ("Population".equals(output) ? "selected" : "") 
                    + ">Population</option>";
                html = html + "<option value ='Average Temperature' " + ("Average Temperature".equals(output) ? "selected" : "") 
                    + ">Average Temperature</option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>"; // closing the select-wrapper div
                html = html + "</div>"; // closing search-section div                
               

            } else if ("Country".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Country</div>";
                html = html + "<div class='select-wrapper'>";

                //displaying multiple countries
                html = html + "<select multiple id = 'countries' name = 'countries' class='select-boxfordisplay'>";

                for (String name : countryname) {
                    html += "<option value='" + name + "' "
                            + (selectedCountries != null && Arrays.asList(selectedCountries).contains(name) ? "selected"
                                    : "")
                            + ">" + name + "</option>";
                }

                html = html + "</select>";
                //
           
                html = html + "</div>"; //closing the select-wrapper div
                html = html + "</div>"; //closing search-section div     

                //display type of output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Type</div>";
                html = html + "<div class='select-wrapper'>";

                html = html + "<select class='select-boxfordisplay' id ='OutputType' name = 'OutputType'>";
                html = html + "<option value='' " + (OutputType == null ? "selected" : "") + ">Select</option>";
                html = html + "<option value= 'Raw Value'" + ("Raw Value".equals(OutputType) ? "selected":"") +">Raw Value </option>";
                html = html + "<option value= 'Proportion'" + ("Proportion".equals(OutputType) ? "selected":"") +">Proportion </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>";//closing the select-wrapper div
                html = html + "</div>";// closing search-section div     
            }

            // Start Year
            Global firstyear = jdbc.getFirstYearTemp();
            Global lastyear = jdbc.getLastYearTemp();
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Start Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='startyear' name='startyear' class='select-type' value= '"+startyear+"' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";
            html = html + "</div>";

            // End Year
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>End Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='endyear' name='endyear' class='select-type' value= '"+endyear+"' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";//closing the select-wrapper div
            html = html + "</div>";// closing search-section div    

           
           
            html = html + "</div>"; // Closing .search-panel
            

            // Search Button
            html = html + "<button type='submit' class='search-button'>Search</button>";

            html = html + "</form>";

        if ("World".equals(selectBoxfordisplay) && output != null && !output.isEmpty() && orderby != null && !orderby.isEmpty() && 
        startyear != null && !startyear.isEmpty() && endyear != null && !endyear.isEmpty()){
            html = html + "<div class='results-container'>"; //open the result-container
            html = html + "<div class='results-inner'>"; //open the results-inner
            //table display 
            html = html + "<table>"; //open the table if World is selected
            html = html + "<thead>";
            html = html + "<tr>";
            html = html + "<th>YEAR</th>";
            html = html + "<th>AVERAGE TEMPERATURE</th>";
            html = html + "<th>POPULATION</th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody>"; //open the table body if World is selected
            List<Global> results = jdbc2.getGlobalDatafromWord(output, orderby, startyear, endyear);

            if (results.isEmpty()) {
                // If no data is available, display a message
                html = html + "<tr>";
                html = html + "<td colspan='6' style='text-align:center;'>No data available for the selected criteria</td>";
                html = html + "</tr>";
            } else {
            int rowNumber = 1;
            for (Global data : results) {
                html = html + "<tr>";
            
                // YEAR (should never be null, so we just display it)
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getAverageTemp() + " °C</td>";
            
            
                // POPULATION (SQLite INTEGER cannot be null, so we assume 0 indicates no data)
                if (data.getPopulation() != 0) {
                    html = html + "<td>" + String.format("%,d", data.getPopulation()) + "</td>";
                } else {
                    html = html + "<td>No data</td>";  // Handle 0 as "No data"
                }
            
                html = html + "</tr>";
            }
            }
        

            html = html + "</tbody>"; //close the table body when if World is selected
            html = html + "</table>"; //close the table 

         
        }

        else if ("Country".equals(selectBoxfordisplay) && selectedCountries != null && selectedCountries.length > 0 && OutputType != null && !OutputType.isEmpty() && 
        startyear != null && !startyear.isEmpty() && endyear != null && !endyear.isEmpty()){

       
        html = html + "<div class='results-container'>";

        html = html + "<div class='results-inner'>"; //first table
        html = html + "<table>"; //open the table if country was selected
        html = html + "<thead><tr>";
        html = html + "<th>NAME</th>";
        html = html + "<th>START YEAR</th>";
        html = html + "<th>END YEAR</th>";
        html = html + "<th>" +startyear +"<br/> TEMPERATURE</th>";
        html = html + "<th>" +endyear +"<br/> TEMPERATURE</th>";
        html = html + "<th>CHANGE</th>";
        html = html + "</tr>";
        html = html + "</thead>";

        html = html + "<tbody>"; //open the table body if country was selected
        List<Country> result = jdbc2.getGlobalDatafromCountry(selectedCountries, OutputType, startyear, endyear);

        int rowNumber = 1;
        for (Country data : result) {

        int availableStartYear = data.getStartYear();  // Year when data starts
        int availableEndYear = data.getEndYear();      // Year when data ends
        
        // Check for missing years at the beginning of the range
        if (Integer.parseInt(startyear) < availableStartYear) {
            html = html + "<tr><td colspan='6'>No data available for " + data.getCountryName() + " from " + startyear + " to " + (availableStartYear - 1) + "</td></tr>";
        }
            
            html = html + "<tr>";
            html = html + "<td>" + data.getCountryName() + "</td>";
            html = html + "<td>" + data.getStartYear() + "</td>";
            html = html + "<td>" + data.getEndYear() + "</td>";
            html = html + "<td>" + data.getStartYearTemperature() + "</td>";
            html = html + "<td>" + data.getEndYearTemperature() + "</td>";

            if ("Proportion".equals(OutputType)) {

                html = html + "<td>" + String.format("%.2f%%", data.getChange()) + "</td>";
            } else {

                html = html + "<td>" + String.format("%.2f", data.getChange()) + "</td>";
            }
            html = html + "</tr>";

            if (Integer.parseInt(endyear) > availableEndYear) {
                html = html + "<tr><td colspan='6'>No data available for " + data.getCountryName() + " from " + (availableEndYear + 1) + " to " + endyear + "</td></tr>";
            }

            rowNumber++;

          
            // Check for missing years at the beginning of the range
            
        }

        if (result.isEmpty()) {
            html = html + "<tr><td colspan='6'>No data available from " + startyear + " to " + (Integer.parseInt(endyear) - 1) + "</td></tr>";
        }
    
        html = html + "</tbody>"; //close the table body if country was selected
        html = html + "</table>"; //close the table 
        html = html + "</div>";//close div of the first result-inner

        html = html + "<div class='results-inner'>"; //second table
        html = html + "<table>"; //open the table if country was selected
        html = html + "<thead><tr>";
        html = html + "<th>NAME</th>";
        html = html + "<th>START YEAR</th>";
        html = html + "<th>END YEAR</th>";
        html = html + "<th>" +startyear +"<br/> POPULATION</th>";
        html = html + "<th>" +endyear +"<br/> POPULATION</th>";
        html = html + "<th>CHANGE</th>";
        html = html + "</tr>";
        html = html + "</thead>";

        html = html + "<tbody>"; //open the table body if country was selected
        List<Country> results = jdbc2.getGlobalDatafromCountry(selectedCountries, OutputType, startyear, endyear);

        rowNumber = 1;
        
        for (Country data : results) {
            int availableStartYear = data.getStartYear();  // Year when data starts
            int availableEndYear = data.getEndYear();      // Year when data ends
            
            // Check for missing years at the beginning of the range
            if (Integer.parseInt(startyear) < availableStartYear) {
                html = html + "<tr><td colspan='6'>No data available for " + data.getCountryName() + " from " + startyear + " to " + (availableStartYear - 1) + "</td></tr>";
            }
            html = html + "<tr>";
            html = html + "<td>" + data.getCountryName() + "</td>";
            html = html + "<td>" + data.getStartYear() + "</td>";
            html = html + "<td>" + data.getEndYear() + "</td>";
            html = html + "<td>" +  String.format("%,d",data.getStartYearPopulation()) + "</td>";
            html = html + "<td>" + String.format("%,d",data.getEndYearPopulation()) + "</td>";

            if ("Proportion".equals(OutputType)) {

                html = html + "<td>" + String.format("%.2f%%", data.getPopulationChange()) + "</td>";
                
            } else {

                html = html + "<td>" + String.format("%.2f", data.getPopulationChange()) + "</td>";
            }
            html = html + "</tr>";

            if (Integer.parseInt(endyear) > availableEndYear) {
                html = html + "<tr><td colspan='6'>No data available for " + data.getCountryName() + " from " + (availableEndYear + 1) + " to " + endyear + "</td></tr>";
            }
    
            rowNumber++;

        
        
        }

        if (results.isEmpty()) {
            html = html + "<tr><td colspan='6'>No data available from " + startyear + " to " + endyear + "</td></tr>";
        }
    
        html = html + "</tbody>"; //close the table body if country was selected
        html = html + "</table>"; //close the table 
        html = html + "</div>";//close the second results-inner
    }

   
     
        html = html + "</div>";//close the result-container

     }


        html = html + "</div>"; // Closing the container
        html = html + "</body>";
        html = html + "</html>";
       
        context.html(html);
        }

        

        // Send the generated HTML to the client
        
    
}

