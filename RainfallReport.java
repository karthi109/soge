import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainfallReport {

	//Write the required business logic as expected in the question description
	public List<AnnualRainfall> generateRainfallReport(String filePath) {
	    
		//fill the code
		List<AnnualRainfall> annualRainfallList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			Scanner sc = new Scanner(br);
			while(sc.hasNext()) {
				String[] records=sc.nextLine().split(",");
				String cityPincodeStr = records[0];
				boolean correctPin = false;
				try {
					correctPin = validate(cityPincodeStr);
					if(correctPin) {
						int cityPincode = Integer.parseInt(cityPincodeStr);
						String cityName = records[1];
						double[] monthlyRainfalls = new double[12];
						for(int i=0;i<12;i++) {
							double monthlyRainfall = Double.parseDouble(records[i+2]);
							monthlyRainfalls[i] = monthlyRainfall;
						}
						AnnualRainfall annualRainfall = new AnnualRainfall();
						annualRainfall.setCityPincode(cityPincode);
						annualRainfall.setCityName(cityName);
						annualRainfall.calculateAverageAnnualRainfall(monthlyRainfalls);
						annualRainfallList.add(annualRainfall);
					}
				}
				catch(InvalidCityPincodeException e){
					System.out.println(e.getMessage());
				}
			}
			sc.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return annualRainfallList;
	}
	
	public List<AnnualRainfall>  findMaximumRainfallCities() {
	
		//fill the code
		List<AnnualRainfall> maximumRainfallCities = new ArrayList<>();
		try {
			Connection conn = new DBHandler().establishConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select *\n"+"from AnnualRainfall\n"+"where average_annual_rainfall = (\n"+"select max(average_annual_rainfall)\n"+"from AnnualRainfall\n"+");");
			while(rs.next()) {
				int cityPincode = rs.getInt(1);
				String cityName = rs.getString(2);
				double averageAnnualRainfall = rs.getDouble(3);
				AnnualRainfall annualRainfall = new AnnualRainfall();
				annualRainfall.setCityPincode(cityPincode);
				annualRainfall.setCityName(cityName);
				annualRainfall.setAverageAnnualRainfall(averageAnnualRainfall);
				maximumRainfallCities.add(annualRainfall);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return maximumRainfallCities;
	}
	
	
	public boolean validate(String cityPincode) throws InvalidCityPincodeException {
	
		//fill the code
		Pattern pattern = Pattern.compile("^\\d{5}$");
		Matcher matcher = pattern.matcher(cityPincode);
    	if(matcher.matches()) {
    		return true;
    	}
    	else {
    		throw new InvalidCityPincodeException("Invalid City Pincode");
    	}
	}

}
