package app.page;

import java.util.ArrayList;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
import app.JDBC.JDBCforPeriodTracker;
import app.classes.Global;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class CityTracker implements Handler {

    public static final String URL = "/CityTracker";

    @Override
    public void handle(Context context) throws Exception {

        String html = "<html>";

        // Head
        html = html + "<head>";
        html = html + "<meta charset='UTF-8'>";
        html = html + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html = html + "<title>ClimateTrend Dashboard</title>";
        html = html + "<link rel='stylesheet' href='level2B.css'>";
        html = html + "</head>";

        // Body
        html = html + "<body>";
        html = html + "<div class='container'>"; //open the container

        // Header & Navigation
        html = html + "<div class='header'>";
        html = html + "<div class='logo'>";
        html = html + "<img src='ClimaTrendLogo.png' alt='WorldChanges Logo'>";
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
        html = html + "<form method='post' action='/CityTracker'>"; //Put the form before the search-panel
        html = html + "<div class='search-panel'>";



        // JDBC connection                      //THIS LINE CREATE CONNECTION TO GET METHOD FROM DIFFERENT CLASS
        JDBC jdbc = new JDBC();
        JDBCforGlobalTracker jdbc2 = new JDBCforGlobalTracker();
        JDBCforPeriodTracker jdbcPT = new JDBCforPeriodTracker();

       

        // Get selected value from the form                 //THIS TAKE DATAS FROM THE FILTER   //IMPORTANT

        
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String startyear = context.formParam("startyear");
        String endyear = context.formParam("endyear");
        String outputType = context.formParam("outputType");
        String statistic = context.formParam("statistic");
        


        ArrayList<String> countryname = jdbc2.getCountryName();
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;




  

        

        /* oh u guys can put this in or not because these lines are used for displaying results to the terminal whenever users interact with the filer, try! */
        System.out.println("selectBoxfordisplay: " + selectBoxfordisplay);
        System.out.println("statistic:" + statistic);
        System.out.println("output Type:" + outputType);
        System.out.println("select country:" + outputType);


         // If "Country" is selected, display Country dropdown
         html = html + "<div class='search-section'>";
         html = html + "<div class='search-title'>Country</div>";
         html = html + "<div class='select-wrapper'>";
         html = html + "<input list='countries' name = 'countries' class='select-boxfordisplay'>";

         html += "<datalist id='countries'>";
         for (String name : countryname) {
             html += "<option value='" + name + "' />";
         }

         html = html + "</datalist>";
         html = html + "</div>"; //closing the select-wrapper div
         html = html + "</div>"; //closing search-section div     
        

        // Search Section - Display Region Dropdown
        html = html + "<div class='search-section'>";
        html = html + "<div class='search-title'>Display Region</div>";
        html = html + "<div class='select-wrapper'>";

        // Displaying the form with the dropdown
        
        html = html + "<select name='select-boxfordisplay' class='select-boxfordisplay'>"; 
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">--Select--</option>";
        html = html + "<option value='City' " + ("City".equals(selectBoxfordisplay) ? "selected" : "")
                + ">City</option>";
        html = html + "<option value='State' " + ("State".equals(selectBoxfordisplay) ? "selected" : "")
                + ">State</option>";
        html = html + "</select>";

        html = html + "<div class='select-arrow'></div>";
        html = html + "</div>"; // Closing .select-wrapper
        html = html + "</div>";

  

      
            // Conditionally rendering Start Year or Country dropdowns based on selection


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
            html = html + "</div>";//closing the select-wrapper div
            html = html + "</div>";// closing search-section div    

            // Statistic
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Statistic</div>";
            html = html + "<div class='select-wrapper'>";
            
            html = html + "<select name='statistic' class='select-boxfordisplay'>"; 
            html = html + "<option value='' " + (statistic == null ? "selected" : "") + ">--Select--</option>";
            html = html + "<option value='Minimum Temperature' " + ("Minimum Temperature".equals(statistic) ? "selected" : "")
                    + ">Minimum Temperature</option>";
            html = html + "<option value='Maximum Temperature' " + ("Maximum Temperature".equals(statistic) ? "selected" : "")
                    + ">Maximum Temperature</option>";
            html = html + "<option value='Average Temperature' " + ("Average Temperature".equals(statistic) ? "selected" : "")
                    + ">Average Temperature</option>";
            html = html + "</select>";
    
            html = html + "<div class='select-arrow'></div>";
            html = html + "</div>"; // Closing .select-wrapper
            html = html + "</div>";


            //OutputType (Percentage or Raw)
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Output Type</div>";
            html = html + "<div class='select-wrapper'>";
            
            html = html + "<select name='outputType' class='select-boxfordisplay'>"; 
            html = html + "<option value='' " + (outputType == null ? "selected" : "") + ">--Select--</option>";
            html = html + "<option value='Raw Value' " + ("Raw Value".equals(outputType) ? "selected" : "")
                    + ">Raw Value</option>";
            html = html + "<option value='Percentage' " + ("Percentage".equals(outputType) ? "selected" : "")
                    + ">Percentage</option>";
            html = html + "</select>";
    
            html = html + "<div class='select-arrow'></div>";
            html = html + "</div>"; // Closing .select-wrapper
            html = html + "</div>";

    
           
            html = html + "</div>"; // Closing .search-panel
            

            // Search Button
            html = html + "<button type='submit' class='search-button'>Search</button>";

            html = html + "</form>";
    
        if ( ("City".equals(selectBoxfordisplay) || "State".equals(selectBoxfordisplay)) && selectedCountries != null && selectedCountries.length > 0 && statistic != null && !statistic.isEmpty() 
        && outputType != null && !outputType.isEmpty() && endyear != null && !endyear.isEmpty() && startyear != null && !startyear.isEmpty()) {

            html = html + "<div class='results-container'>"; //open the result-container
            html = html + "<div class='results-inner'>"; //open the results-inner

            html = html + "<table>";
            html = html + "<thead>";
            html = html + "<tr>";
            html = html + "<th>RANK</th>";
            html = html + "<th>" +selectBoxfordisplay.toUpperCase() +"</th>";
            html = html + "<th>START YEAR</th>";
            html = html + "<th>END YEAR</th>";
            html = html + "<th>"+ startyear + statistic + "</th>";
            html = html + "<th>"+ endyear + statistic + "</th>";
            html = html + "<th> CHANGE </th>";
            html = html + "<th> AVERAGE CHANGE </th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody>"; //open the table body 

        if ("City".equals(selectBoxfordisplay)){
            //FIXME: this is not supposed to get the data from GlobalTracker
            List<Global> result = jdbc2.getGlobalDatafromCountry(selectedCountries, endyear, startyear, outputType);

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
                html = html + "<td>" + data.getChange() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }

            html = html + "</tbody>"; //close the table body when if World is selected

        }   else if ("State".equals(selectBoxfordisplay)){


            //FIXME: this is not supposed to get the data from GlobalTracker
            List<Global> result = jdbc2.getGlobalDatafromCountry(selectedCountries, endyear, startyear, outputType);

            int rowNumber = 1;
            for (Global data : result) {
                html = html + "<tr>";
                html = html + "<td>" + data.getName() + "</td>";
                html = html + "<td>" + data.getYear() + "</td>";
                html = html + "<td>" + data.getPeriod() + "</td>";
                html = html + "<td>" + data.getFirstYearTemperature() + "</td>";
                html = html + "<td>" + data.getLastYearTemperature() + "</td>";
                html = html + "<td>" + data.getChange() + "</td>";
                html = html + "<td>" + data.getChange() + "</td>";
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
    



        html = html + "</div>"; // Closing the container
        html = html + "</body>";
        html = html + "</html>";

        context.html(html);
}
     


}

    




