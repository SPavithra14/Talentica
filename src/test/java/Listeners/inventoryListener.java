package Listeners;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class inventoryListener implements ITestListener{
	public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    
    private ByteArrayOutputStream logStream;
    private PrintStream originalOut;
    private PrintStream customOut;

    private final String report = System.getProperty("user.dir") + "/report/inventoryReport.html";
	
	public void onStart(ITestContext context) {
		System.out.println("Test Execution started....");
		System.out.println(report);
		sparkReporter = new ExtentSparkReporter(report);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Inventory Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Computer Name", "localhost");
        extent.setSystemInfo("Environment", "sprint");
        extent.setSystemInfo("Test name", "Inventory");
        extent.setSystemInfo("OS", "Windows 10");
        extent.setSystemInfo("Browser Name", "Chrome");

        logStream = new ByteArrayOutputStream();
        originalOut = System.out;
        customOut = new PrintStream(logStream);
        System.setOut(customOut);
	}
	
    public void onTestStart(ITestResult result) {
    	test = extent.createTest(result.getName());
	}

    public void onTestSuccess(ITestResult result) {
    	test.log(Status.PASS, "Test case Passed is: " + result.getName());
        System.setOut(originalOut);
        test.info(logStream.toString().replaceAll("\n", "<br>"));
        clearLogStream();
        System.setOut(customOut);
    }
    public void onTestFailure(ITestResult result) {
    	test.log(Status.FAIL, "Test case Failed is: " + result.getName());
        test.log(Status.FAIL, "Cause: " + result.getThrowable());
        System.setOut(originalOut);
        test.info(logStream.toString().replaceAll("\n", "<br>"));
        clearLogStream();
        System.setOut(customOut);
    }
   public void onTestSkipped(ITestResult result) {
	   test.log(Status.SKIP, "Test case Skipped is: " + result.getName());
       System.setOut(originalOut);
       test.info(logStream.toString().replaceAll("\n", "<br>"));
       clearLogStream();
       System.setOut(customOut);
    }
   public void onFinish(ITestContext context) {
	   System.out.println("Test Execution Completed....");
	   extent.flush(); 
       System.setOut(originalOut);
         try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    ///   EmailSender.sendMail(reportPath); 
   }
	
   private void clearLogStream() {
       logStream.reset();
   }

}
