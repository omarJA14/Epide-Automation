import com.kms.katalon.core.annotation.*
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration

class Listener_CloseBrowserOnFailure {

    @AfterTestCase
    def afterTestCase(TestCaseContext testCaseContext) {

        if (testCaseContext.getTestCaseStatus() == "FAILED") {

            String path = RunConfiguration.getProjectDir() + "/Screenshots/Fail_" +
                          testCaseContext.getTestCaseId().replaceAll("/", "_") + "_" +
                          System.currentTimeMillis() + ".png"

            try {
                WebUI.takeScreenshot(path)
                println("Screenshot pris : " + path)
            } catch (Exception e) {
                println("Impossible de prendre le screenshot : " + e.message)
            }

            try {
                WebUI.closeBrowser()
                println("Navigateur fermé suite à l'échec du Test Case")
            } catch (Exception e) {
                println("Impossible de fermer le navigateur : " + e.message)
            }
        }
    }
}
