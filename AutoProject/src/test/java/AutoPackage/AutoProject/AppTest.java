package AutoPackage.AutoProject;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import io.cucumber.junit.*;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//Configarate plugin to generate report to \target\SparkReport\Spark.html
@CucumberOptions(
		features ="src/test/java/Features/",  //Run all feature files
//		features ={"src/test/java/Features/FilesDownload.feature"}, //Run only FilesDownload.feature
		glue = {"StepDefinitions"},
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		monochrome = true	)

/**
 * Unit test for simple App.
 */
public class AppTest 
{
 	/**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}

