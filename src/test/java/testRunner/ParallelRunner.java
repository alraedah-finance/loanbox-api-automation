package testRunner;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParallelRunner {


    @Test
    public void executeKarateTest() {
        Runner.Builder aRunner = new Runner.Builder();
        Results result = aRunner.path("classpath:features").tags("@regression").parallel(5);
        System.out.println("Total Feature => " + result.getFeatureCount());
        System.out.println("Total Scenarios => " + result.getScenarioCount());
        System.out.println("Passed Scenarios => " + result.getPassCount());
        generateCucumberReport(result.getReportDir());
        Assertions.assertEquals(0, result.getFailCount(), "There are Some Failed Scenarios ");

    }

    private void generateCucumberReport(String reportDirLocation) {
        File reportDir = new File(reportDirLocation);
        Collection<File> jsonCollection = FileUtils.listFiles(reportDir, new String[]{"json"}, true);

        List<String> jsonFiles = new ArrayList<String>();
        jsonCollection.forEach(file -> jsonFiles.add(file.getAbsolutePath()));

        Configuration configuration = new Configuration(reportDir, "Core Component");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
