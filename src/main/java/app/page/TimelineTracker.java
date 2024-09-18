package app.page;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class TimelineTracker implements Handler {

    public static final String URL = "/TimelineTracker";


    @Override
    public void handle(Context context) throws Exception {

        String html = "";

        // Head
        html = html + "<head>";
        html = html + "<meta charset='UTF-8'>";
        html = html + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html = html + "<title>ClimateTrend Dashboard</title>";
        html = html + "<link rel='stylesheet' href='Level3A(Country).css'>";
        html = html + "</head>";

        // Body
        html = html + "<body>";

        // Header
        html = html + """
            <div class='container'>
                <div class='header'>
                    <div class='logo'>
                        <img src='ClimaTrendLogo.png' alt='ClimaTrendLogo'>
                    </div>
                    <div class='header-elements'>
                        <a href='/'>Home Page</a>
                        <a href='/GlobalTracker'>Global Tracker</a>
                        <a href='/CityTracker'>City Tracker</a>
                        <a href='/TimeLineTracker'>Timeline Tracker</a>
                        <a href='/PeriodTracker'>Periods Tracker</a>
                    </div>
                </div>
            """;

        // Shadow
        html = html + "<div class='shadow'></div>";

        //HTM Form
        html += "<form action='/findingtempandpopbycountryworld' method='post' class='form-container'>";

        String typeofview = context.formParam("typeofview");
        
        // Search Panel
        html = html + """
            <div class='search-panel'>
                <div class='search-section'>
                    <div class='search-title'>Display Region</div>
                    <div class='select-wrapper'> 
                    """;
                    //content

                    html += "<select id='typeofview' name='typeofview' onchange='this.form.submit()' class='form-select'>";    
                    html += "<option value='' " + (typeofview != null ? "selected" : "") + ">--Select--</option>";
                    html += "<option value='World' " + (typeofview.equals("World") ? "selected" : "") + ">World</option>";
                    html += "<option value='Country' " + (typeofview.equals("Country") ? "selected" : "") + ">Country</option>";
                    html += "</select>";
                    

                    if (typeofview != null) {
                            if (typeofview.equals("Country")) {
                                // Sorting
                                html += "<label for='listOfCountry'>Country</label>";
                                html += "<select id='listOfCountry' name='listOfCountry' class='form-select'>";
                                html += " <option value='1999'>1999</option>";
                                html += "<option value='2000'>2000</option>";
                                html += "<option value='1999'>1939</option>";
                                html += "</select>";
            
                            }
                    }


                     
                 html += """
                    <div class='select-arrow'></div>
                    </div>
                </div> 
                


  
              <div class='search-section'>
                    <div class='search-title'>Start Year</div>
                    <div class='select-wrapper'>
                        <form>
                            <select class='select-multiple' multiple>
                                <option value='1999'>1999</option>
                                <option value='2000'>2000</option>
                                <option value='1999'>1939</option>
                                <option value='2000'>2030</option>
                                <option value='1999'>1239</option>
                                <option value='2000'>2530</option>
                                <option value='1999'>1223</option>
                                <option value='2000'>2520</option>
                                <option value='1999'>1039</option>
                                <option value='2000'>2130</option>
                                <option value='1999'>1539</option>
                                <option value='2000'>2830</option>
                            </select>
                        </form>
                    </div>
                </div>

                <div class='search-section'>
                    <div class='search-title'>Periods</div>
                    <div class='select-wrapper'>
                        <input type='text' id='period' name='period' placeholder='Enter'>
                    </div>
                </div>
            </div>
            """;

        // Search Button
        html = html + "<button class='search-button'>Search</button>";



        
        // Results Container
        html = html + """
            <div class='results-container'>
                <div class='results-inner'>
                    <table>
                        <thead>
                          
                            <tr>
                                <th>NO</th>
                                <th>NAME</th>
                                <th>YEAR</th>
                                <th>PERIOD</th>
                                <th>FIRST YEAR <br/> TEMPERATURE</th>
                                <th>LAST YEAR <br/> TEMPERATURE</th>
                                <th>CHANGE</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>City name</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>City name</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                                <td>null</td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
            </div>
            """;

        // Close Body and HTML
        html = html + "</body></html>";

        // Output HTML
        context.html(html);
    }

}
