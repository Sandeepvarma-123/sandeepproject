package sanproj1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONObject;

public class Mytest1 {

	public static void main(String[] args) throws InterruptedException, IOException {
		//To give path for property file
		FileInputStream fis=new FileInputStream("C:\\Users\\siraz\\eclipse-workspace\\Demoproject\\src\\test\\java\\data.properties");
		Properties p= new Properties();
		p.load(fis);
		
		//creation of objects for using property file data
		String url=p.getProperty("myurl");
		String input = p.getProperty("htmldata");
		
		
		WebDriver driver = new ChromeDriver();                                              // to declare chrome driver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\siraz\\Downloads\\sandeep\\chromedriver_win32.exe");
		driver.get(url);                                                                    // to navigate to given URL
		driver.manage().window().maximize();                                                // to maximize the current window
		driver.findElement(By.xpath("/html/body/div/div[3]/details/summary")).click();      // to click table data
		WebElement inputField = driver.findElement(By.id("jsondata"));                      // to find input field
        inputField.clear();
        Thread.sleep(5000);
        inputField.sendKeys(input);
        Thread.sleep(5000);
        driver.findElement(By.id("refreshtable")).click();
        
        //To find table and compare data in the table
        WebElement table = driver.findElement(By.id("dynamictable"));
        String vtmlData = table.getText();
        String jsonData = input;
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonItem = jsonArray.getJSONObject(i);
            String expectedData = jsonItem.getString("name") + " " + jsonItem.getString("gender") + " " + jsonItem.getInt("age");
            Assert.assertTrue(vtmlData.contains(expectedData));
        }
       

	}

}