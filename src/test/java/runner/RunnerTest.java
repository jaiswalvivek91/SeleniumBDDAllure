//package runner;
//
//import hooks.AllureSetup;
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import org.testng.annotations.*;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.io.FileUtils;
//
//@CucumberOptions(
//        features = "src/test/resources/features",
//        glue = {"steps", "hooks"},
//        tags = "@test",
//        plugin = {
//                "pretty",
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
//        },
//        monochrome = true
//)
//public class RunnerTest extends AbstractTestNGCucumberTests {
//
//        static {
//                String dummy = AllureSetup.getAllureResultsDir();
//        }
//
//        public static final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        public static final String allureResultsDir = "reports/allure_results_" + timestamp;
//        public static final String allureReportDir = "reports/allure_report_" + timestamp;
//
//        static {
//                System.setProperty("allure.results.directory", allureResultsDir);
//                System.out.println("📌 Allure results directory set to: " + System.getProperty("allure.results.directory"));
//        }
//
//        @BeforeClass
//        public void setup() throws IOException {
//                // Clean default allure_results folder before tests
//                File defaultResults = new File("reports/allure_results");
//                if (defaultResults.exists()) {
//                        FileUtils.deleteDirectory(defaultResults);
//                        System.out.println("Deleted existing reports/allure_results folder.");
//                }
//                // Clean reports/latest folder
//                File latest = new File("reports/latest");
//                if (latest.exists()) {
//                        FileUtils.deleteDirectory(latest);
//                        System.out.println("Deleted existing reports/latest folder.");
//                }
//        }
//
//        @AfterClass
//        public void teardown() {
//                try {
//                        Thread.sleep(2000); // wait for Allure to flush JSON files
//
//                        File defaultResults = new File("reports/allure_results");
//                        File timestampedResults = new File(allureResultsDir);
//
//                        if (defaultResults.exists()) {
//                                FileUtils.moveDirectory(defaultResults, timestampedResults);
//                                System.out.println("Moved allure_results to: " + timestampedResults.getAbsolutePath());
//                        } else {
//                                System.err.println("❌ Default reports/allure_results folder NOT found!");
//                                return;
//                        }
//
//                        String[] cmd = {"allure", "generate", timestampedResults.getAbsolutePath(), "--clean", "-o", allureReportDir};
//                        System.out.println("Running command: " + String.join(" ", cmd));
//                        Process process = Runtime.getRuntime().exec(cmd);
//
//                        // Print process output (optional, useful for debugging)
//                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//
//                        String s;
//                        while ((s = stdInput.readLine()) != null) System.out.println(s);
//                        while ((s = stdError.readLine()) != null) System.err.println(s);
//
//                        int exitCode = process.waitFor();
//                        System.out.println("Allure generate exited with code: " + exitCode);
//
//                        if (exitCode == 0) {
//                                System.out.println("✅ Allure report generated at: " + allureReportDir);
//
//                                File latestReport = new File("reports/latest");
//                                if (latestReport.exists()) {
//                                        FileUtils.deleteDirectory(latestReport);
//                                }
//                                FileUtils.copyDirectory(new File(allureReportDir), latestReport);
//                                System.out.println("📂 Latest report copied to: reports/latest");
//                        } else {
//                                System.err.println("❌ Allure report generation FAILED!");
//                        }
//
//                } catch (IOException | InterruptedException e) {
//                        e.printStackTrace();
//                        System.err.println("❌ Exception during Allure report generation.");
//                }
//        }
//
//        @Override
//        @DataProvider(parallel = false)
//        public Object[][] scenarios() {
//                return super.scenarios();
//        }
//}


package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        tags = "@test",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class RunnerTest extends AbstractTestNGCucumberTests {

        private static final String baseDir = System.getProperty("user.dir");
        private static final String allureResultsDir = Paths.get(baseDir, "allure-results").toString();
        private static final String reportsDir = Paths.get(baseDir, "reports").toString();

        public static final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        public static final String allureReportDir = Paths.get(reportsDir, "allure-report-" + timestamp).toString();
        public static final String latestDir = Paths.get(reportsDir, "latest").toString();

        static {
                System.setProperty("allure.results.directory", allureResultsDir);
                System.out.println("📌 Allure results directory set to: " + allureResultsDir);
        }

        @BeforeClass
        public void setup() throws IOException {
                cleanFolder(allureResultsDir);
                cleanFolder(latestDir);

                // Ensure reports directory exists
                Files.createDirectories(Paths.get(reportsDir));
        }

        @AfterClass
        public void generateAllureReport() {
                try {
                        // Wait for allure results files to be available (max 10 tries with 500ms delay)
                        waitForAllureResults();

                        File results = new File(allureResultsDir);
                        if (!results.exists() || results.listFiles() == null || results.listFiles().length == 0) {
                                System.err.println("❌ Allure results folder not found or empty: " + allureResultsDir);
                                return;
                        }

                        String allureCmd = getAllureCommand();

                        String[] cmd = {
                                allureCmd,
                                "generate",
                                allureResultsDir,
                                "--clean",
                                "-o",
                                allureReportDir
                        };

                        System.out.println("▶️ Running Allure command: " + String.join(" ", cmd));
                        Process process = Runtime.getRuntime().exec(cmd);

                        printProcessOutput(process);

                        int exitCode = process.waitFor();
                        System.out.println("📦 Allure generate exited with code: " + exitCode);

                        if (exitCode == 0) {
                                System.out.println("✅ Allure report created at: " + allureReportDir);
                                copyFolder(allureReportDir, latestDir);
                                System.out.println("📂 Copied report to: " + latestDir);
                        } else {
                                System.err.println("❌ Allure report generation failed!");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("❌ Exception during Allure report generation.");
                }
        }

        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }

        private void cleanFolder(String path) throws IOException {
                File dir = new File(path);
                if (dir.exists()) {
                        FileUtils.deleteDirectory(dir);
                        System.out.println("🧹 Deleted folder: " + path);
                }
        }

        private void copyFolder(String source, String dest) throws IOException {
                File src = new File(source);
                File dst = new File(dest);
                if (dst.exists()) {
                        FileUtils.deleteDirectory(dst);
                }
                FileUtils.copyDirectory(src, dst);
        }

        private void printProcessOutput(Process process) throws IOException {
                try (
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                ) {
                        String s;
                        while ((s = stdInput.readLine()) != null) System.out.println(s);
                        while ((s = stdError.readLine()) != null) System.err.println(s);
                }
        }

        private String getAllureCommand() {
                // Allow overriding allure command via environment variable, fallback to "allure"
                String cmd = System.getenv("ALLURE_CMD_PATH");
                if (cmd == null || cmd.trim().isEmpty()) {
                        cmd = "allure";
                }
                System.out.println("🔧 Using allure command: " + cmd);
                return cmd;
        }

        private void waitForAllureResults() throws InterruptedException {
                int tries = 10;
                int delayMs = 500;
                File resultsFolder = new File(allureResultsDir);

                while (tries > 0) {
                        if (resultsFolder.exists() && resultsFolder.listFiles() != null && resultsFolder.listFiles().length > 0) {
                                System.out.println("✔️ Allure results files found.");
                                return;
                        }
                        System.out.println("⌛ Waiting for allure results files...");
                        Thread.sleep(delayMs);
                        tries--;
                }
                System.err.println("⚠️ Timeout waiting for allure results files.");
        }
}
