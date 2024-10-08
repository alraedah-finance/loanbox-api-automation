package testRunner;


import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@KarateOptions(tags = "@sanity")
public class TestRunner {

    @BeforeClass
    public static void before() {

        String env = System.getProperty("env");
        if (env == null) {
            System.setProperty("karate.env", "qa");
        } else {
            System.out.println("environment " + env);
            System.setProperty("karate.env", env);
        }
    }


    @Test
    public void testRunner() {
        Runner.Builder aRunner = new Runner.Builder();
//        aRunner.path("classpath:features");
        aRunner.path("classpath:features").tags("@mob_demo");
        Results results = Runner.parallel(getClass(), 5);
        generateCucumberReport(results.getReportDir());
        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);

    }

    private void generateCucumberReport(String reportDirLocation) {
        File reportDir = new File(reportDirLocation);
        Collection<File> jsonCollection = FileUtils.listFiles(reportDir, new String[]{"json"}, true);
        List<String> jsonFiles = new ArrayList<String>();
        jsonCollection.forEach(file -> jsonFiles.add(file.getAbsolutePath()));

       Configuration configuration = new Configuration(reportDir, "Core-Eldorado-API ");
//        Configuration configuration = new Configuration(new File("target"), "Core-Eldorado-API ");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
