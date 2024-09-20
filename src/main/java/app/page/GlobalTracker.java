package app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
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
        html = html + "<div class='container'>";

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

        // Search Panel
        html = html + "<form method='post' action='/GlobalTracker'>";
        html = html + "<div class='search-panel'>";

        // JDBC connection setup (if needed)
        JDBC jdbc = new JDBC();
        JDBCforGlobalTracker jdbc2 = new JDBCforGlobalTracker();

        ArrayList<String> countryname = jdbc2.getCountryName();

        // Get selected value from the form
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String output = context.formParam("output");
        String orderby = context.formParam("orderby");
        String OutputType = context.formParam("OutputType");
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;
        String startyear = context.formParam("startyear");
        String endyear = context.formParam("endyear");

        System.out.println("selectBoxfordisplay: " + selectBoxfordisplay);
        System.out.println("output: " + output);
        System.out.println("orderby: " + orderby);
        System.out.println("startYear: " + startyear);
        System.out.println("endYear: " + endyear);

        // Search Section - Display Region Dropdown
        html = html + "<div class='search-section'>";
        html = html + "<div class='search-title'>Display Region</div>";
        html = html + "<div class='select-wrapper'>";

        // Displaying the form with the dropdown
        
        html = html + "<select name='select-boxfordisplay' class='select-boxfordisplay' onchange='this.form.submit()'>"; 
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">--Select--</option>";
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
                // If "Global" is selected, display Order and Sorting dropdown
                // Order
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Order</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select class='select-boxfordisplay' id ='orderby' name ='orderby'>";
                html = html + "<option value='' " + (orderby == null ? "selected" : "") + ">--Select--</option>";
                html = html + "<option value= 'Ascending'" + ("Ascending".equals(orderby) ? "selected":"") +">Ascending </option>";
                html = html + "<option value= 'Descending'" + ("Descending".equals(orderby) ? "selected":"") +">Descending </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>";
                html = html + "</div>";
                
                
                //Output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Output</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select class='select-boxfordisplay' id= 'output' name = 'output' ><option>Select</option>";
                html = html + "<option value='' " + (output == null ? "selected" : "") + ">--Select--</option>";
                html = html + "<option value = ' Year' " + ("Year".equals(output) ? "selected" : "") 
                    + ">Year</option>";
                html = html + "<option value = 'Population' " + ("Population".equals(output) ? "selected" : "") 
                    + ">Population</option>";
                html = html + "<option value =' Average Temperature' " + ("Average Temperature".equals(output) ? "selected" : "") 
                    + ">Average Temperature</option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div></div>";
                
               

            } else if ("Country".equals(selectBoxfordisplay)) {
                // If "Country" is selected, display Country dropdown
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Country</div>";
                html = html + "<div class='select-wrapper'>";

                //displaying multiple countries
                html = html + "<select multiple id = 'multiple-selects' class='select-boxfordisplay'>";

               

                for (String name : countryname) {
                    html += "<option value='" + name + "' "
                            + (selectedCountries != null && Arrays.asList(selectedCountries).contains(name) ? "selected"
                                    : "")
                            + ">" + name + "</option>";
                }

                html = html + "</select>";
                
           
                html = html + "</div>";
                html = html + "</div>";

                //display type of output
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Type</div>";
                html = html + "<div class='select-wrapper'>";

                html = html + "<select class='select-boxfordisplay' id ='OutputType'>";
                html = html + "<option value='' " + (OutputType == null ? "selected" : "") + ">--Select--</option>";
                html = html + "<option value= 'Raw Value'" + ("Raw Value".equals(OutputType) ? "selected":"") +">Raw Value </option>";
                html = html + "<option value= 'Proportion'" + ("Proportion".equals(OutputType) ? "selected":"") +">Proportion </option>";
                html = html + "</select>";
                html = html + "<div class='select-arrow'></div>";
                html = html + "</div>";
                html = html + "</div>";
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

            // End Year
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>End Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input type='number' id='endyear' name='endyear' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";
            html = html + "</div>";

           
            // Closing .search-section
            html = html + "</div>"; // Closing .search-panel
            

            // Search Button
            html = html + "<button type='submit' class='search-button'>Search</button>";

            html = html + "</form>";
    
            html = html + "<div class='results-container'><div class='results-inner'>";
            html = html + "<table>";
            html = html + "<thead>";
            html = html + "<tr>";
            html = html + "<th>YEAR</th>";
            html = html + "<th>AVERAGE TEMPERATURE</th>";
            html = html + "<th>POPULATION</th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody>";
 

        if (output != null && !output.isEmpty() && orderby != null && !orderby.isEmpty() && 
        startyear != null && !startyear.isEmpty() && endyear != null && !endyear.isEmpty()){
        
            List<Global> results = jdbc2.getGlobalDatafromWord(output, orderby, startyear, endyear);

            int rowNumber = 1;
            for (Global data : results) {
                html = html + "<tr>";
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getAverageTemp() + "</td>";
                html = html + "<td>" + data.getPopulation() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }

           

        
         
        }
        else if ("Country".equals(selectBoxfordisplay)){

        List<Global> result = jdbc2.getGlobalDatafromWord(output, orderby, startyear, endyear);

        html = html + "<div class='results-container'><div class='results-inner'>";
        html = html + "<table>";
        html = html + "<thead><tr>";
        html = html + "<th>NO</th>";
        html = html + "<th>NAME</th>";
        html = html + "<th>YEAR</th>";
        html = html + "<th>PERIOD</th>";
        html = html + "<th>FIRST YEAR <br/> TEMPERATURE</th>";
        html = html + "<th>LAST YEAR <br/> TEMPERATURE</th>";
        html = html + "<th>CHANGE</th>";
        html = html + "</tr></thead>";

        html = html + "<tbody>";
        int rowNumber = 1;
        for (Global data : result) {
            html = html + "<tr>";
            html = html + "<td>" + rowNumber + "</td>";
            html = html + "<td>" + data.getName() + "</td>";
            html = html + "<td>" + data.getYear() + "</td>";
            html = html + "<td>" + data.getPeriod() + "</td>";
            html = html + "<td>" + data.getFirstYearTemperature() + "</td>";
            html = html + "<td>" + data.getLastYearTemperature() + "</td>";
            html = html + "<td>" + data.getChange() + "</td>";
            html = html + "</tr>";
            rowNumber++;
        }

        //display results in table

        html = html + "</tbody>";
        html = html + "</table>";

    }


        html = html + "</div></div>";
     } // Closing .results-container

        html = html + "</div>"; // Closing .container
        html = html + "</body>";
        html = html + "</html>";
       
        context.html(html);
        }

        
    
        // Send the generated HTML to the client
        
    
}

