package app.page;

import java.util.ArrayList;
import java.util.List;

import app.JDBC.JDBCforCityTracker;
import app.classes.City;
import app.classes.Global;
import app.classes.State;
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
        JDBCforCityTracker jdbc2 = new JDBCforCityTracker();


       

        // Get selected value from the form                 //THIS TAKE DATAS FROM THE FILTER   //IMPORTANT

        
        String selectBoxfordisplay = context.formParam("select-boxfordisplay");
        String startyear = context.formParam("startyear");
        String endyear = context.formParam("endyear");
        String outputType = context.formParam("outputType");
        String statistic = context.formParam("statistic");
        


        ArrayList<String> countryname = jdbc2.getCountryNames();
        List<String> selectedCountriesList = context.formParams("countries");
        String selectOnecountry = context.formParam("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;


        /* oh u guys can put this in or not because these lines are used for displaying results to the terminal whenever users interact with the filer, try! */
        System.out.println("select regoin: " + selectBoxfordisplay);
        System.out.println("statistic:" + statistic);
        System.out.println("output Type:" + outputType);
        System.out.println("select type:" + outputType);


         // If "Country" is selected, display Country dropdown
         html = html + "<div class='search-section'>";
         html = html + "<div class='search-title'>Country</div>";
         html = html + "<div class='select-wrapper'>";
         html = html + "<input placeholder='Enter' list='countries' name = 'countries' class='select-boxfordisplay' value='"+ (selectOnecountry != null ? selectOnecountry :"") + "' >";

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
        html = html + "<option value='' " + (selectBoxfordisplay == null ? "selected" : "") + ">Select</option>";
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
            Global firstyear = jdbc2.getFirstYearTemp();
            Global lastyear = jdbc2.getLastYearTemp();
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Start Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input placeholder='Enter' type='number' value ='"+startyear+"' id='startyear' name='startyear' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";
            html = html + "</div>";

            // End Year
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>End Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<input placeholder='Enter' type='number' value ='"+endyear+"' id='endyear' name='endyear' class='select-type' min='" + firstyear.getYear() + "' max='" + lastyear.getYear() + "'/>";
            html = html + "</div>";//closing the select-wrapper div
            html = html + "</div>";// closing search-section div    

            // Statistic
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Statistic</div>";
            html = html + "<div class='select-wrapper'>";
            
            html = html + "<select name='statistic' class='select-boxfordisplay'>"; 
            html = html + "<option value='' " + (statistic == null ? "selected" : "") + ">Select</option>";
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
            html = html + "<option value='' " + (outputType == null ? "selected" : "") + ">Select</option>";
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
    
        if ( (("City".equals(selectBoxfordisplay) || "State".equals(selectBoxfordisplay)))&& selectedCountries != null && selectedCountries.length > 0 && statistic != null && !statistic.isEmpty() 
        && outputType != null && !outputType.isEmpty() && endyear != null && !endyear.isEmpty() && startyear != null && !startyear.isEmpty()) {
            System.out.println("Displaying the results...");
            html = html + "<div class='results-container'>"; //open the result-container
            html = html + "<div class='results-inner'>"; //open the results-inner

            html = html + "<table>";
            html = html + "<thead>";
            html = html + "<tr>";
            html = html + "<th>RANK</th>";
            html = html + "<th> NAME </th>";
            html = html + "<th>START YEAR</th>";
            html = html + "<th>END YEAR</th>";
            html = html + "<th>" + startyear  + " <br> " + statistic.toUpperCase()+ "</th>";
            html = html + "<th>" + endyear + " <br> " +  statistic.toUpperCase() + "</th>";
            html = html + "<th> CHANGE </th>";
            html = html + "<th> AVERAGE CHANGE </th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody>"; //open the table body 

            if ("City".equals(selectBoxfordisplay)) {
                System.out.println("City is selected");
                int startYear = Integer.parseInt(startyear);
                int endYear = Integer.parseInt(endyear);
            
                List<City> result = jdbc2.getCityDatafromCountry(selectOnecountry, endYear, startYear,statistic, outputType);
            
                if (result.isEmpty()) {
                    html = html + "<tr><td colspan='8'>No data available for the selected criteria.</td></tr>";
                } else {
                int rowNumber = 1;
                for (City data : result) {
                    html = html + "<tr>";
                    html = html + "<td>" + rowNumber + "</td>";
                    html = html + "<td>" + data.getName() + "</td>";
                    html = html + "<td>" + data.getFirstyear() + "</td>";
                    html = html + "<td>" + data.getEndYear() + "</td>";
                    if ("Minimum Temperature".equals(statistic)) {
                        html += "<td>" + String.format("%.2f", data.getFirstYearTemp()) + "</td>";
                        html += "<td>" + String.format("%.2f", data.getLastYtemp()) + "</td>";
                    } else if ("Maximum Temperature".equals(statistic)) {
                        html += "<td>" + String.format("%.2f", data.getFirstYearTemp()) + "</td>";
                        html += "<td>" + String.format("%.2f", data.getLastYtemp()) + "</td>";
                    } else if ("Average Temperature".equals(statistic)) {
                        html += "<td>" + String.format("%.2f", data.getAverageTemp()) + "</td>";
                        html += "<td>" + String.format("%.2f", data.getAverageTemp()) + "</td>";
                    }
                    html = html + "<td>" + String.format("%.2f",data.getChanges()) + "</td>";    
                
                    if ("Percentage".equals(outputType)) {
                        if (!Float.isNaN(data.getAverageChange()) && !Float.isInfinite(data.getAverageChange())) {
                            html += "<td>" + String.format("%.2f%%", data.getAverageChange() * 100) + "</td>"; 
                        } else {
                            html += "<td>N/A</td>";
                        }
                    } else {
                        html = html + "<td>" + String.format("%.2f", data.getAverageChange()) + "</td>";
                    }
                    html = html + "</tr>";
                    rowNumber++;
                }
            }
                html += "</tbody>" ;
        }   else if ("State".equals(selectBoxfordisplay)){
            System.out.println("State is selected");

        

            int startYear = Integer.parseInt(startyear);
            int endYear = Integer.parseInt(endyear);

            List<State> resultforstate = jdbc2.getStateDatafromCountry(selectOnecountry, endYear, startYear, outputType);
            if (resultforstate.isEmpty()) {
                html = html + "<tr><td colspan='8'>No data available for the selected criteria.</td></tr>";
            } else {
            int rowNumber = 1;
            for (State data : resultforstate) {
                System.out.println("printing out the table...");

                html = html + "<tr>";
                html = html + "<td>" + rowNumber + "</td>";
                html = html + "<td>" + data.getName() + "</td>";
                html = html + "<td>" + data.getFirstyear() + "</td>";
                html = html + "<td>" + data.getEndYear() + "</td>";
                if("Minimum Temperature".equals(statistic)){
                    html += "<td>" + data.getFirstYearTemp() + "</td>";
                } else if("Maximum Temperature".equals(statistic)){
                    html += "<td>" + data.getLastYtemp() + "</td>";
                } else if("Average Temperature".equals(statistic)){
                    html += "<td>" + data.getAverageTemp() + "</td>";
                }
                html = html + "<td>" + data.getAverageChange() + "</td>";
                html = html + "<td>" + data.getChanges() + "</td>";
                html = html + "</tr>";
                rowNumber++;
            }
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

    




