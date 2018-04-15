package test;

import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes.Name;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.csvreader.CsvReader;
import com.google.common.collect.Table.Cell;

public class Lab2 {
  int e=0;
  private InputStream inputStream;
  private String filepath="C://Users//rodar//Desktop//input.xlsx";
  public XSSFWorkbook xssFWorkbook;
  private XSSFSheet xssfSheet;
  private HashMap<String, String> hashMap=new HashMap<>();
  
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id=null;
  private String pwd=null;
  private String url=null;
  static Thread thread=new Thread();

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.firefox.marionette","C://Users//rodar//Desktop//geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://psych.liebes.top/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testLab2() throws Exception {
	inputStream=new FileInputStream(new File(filepath));
	xssFWorkbook=new XSSFWorkbook(inputStream);
    xssfSheet=xssFWorkbook.getSheetAt(0);
    for(int i=0;i<=xssfSheet.getLastRowNum();i++){
    	if(xssfSheet.getRow(i).getCell(0).getCellType()==0){
    		String kkk=String.valueOf((long)xssfSheet.getRow(i).getCell(0).getNumericCellValue());
    		String vvv=String.valueOf(xssfSheet.getRow(i).getCell(1).getStringCellValue());
    		hashMap.put(kkk, vvv);
    	}
    	else{
        	if(xssfSheet.getRow(i).getCell(0).getStringCellValue().equals("3015218150")){
        		continue;
        	}
    		hashMap.put(xssfSheet.getRow(i).getCell(0).getStringCellValue(), xssfSheet.getRow(i).getCell(1).getStringCellValue());
    	}
    }
    
    Iterator iterator=hashMap.entrySet().iterator();
    while(iterator.hasNext()){
    	Map.Entry<String, String> entry=(Map.Entry<String, String>)iterator.next();
    	id=entry.getKey();
    	pwd=entry.getKey().substring(id.length()-6, id.length());
    	url=entry.getValue();
        driver.get(baseUrl + "/st");
        driver.findElement(By.id("username")).sendKeys(id);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("submitButton")).click();
        String url2=driver.findElement(By.xpath("//div[@class='login-box-body']/a/p[@class='login-box-msg']")).getText();
        if(!url2.equals(url)){
        	System.out.println(id+"ÊÇ´íµÄ");
        }
        else{
        	assertEquals(url, url2);
        }
    }
	inputStream.close();
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
