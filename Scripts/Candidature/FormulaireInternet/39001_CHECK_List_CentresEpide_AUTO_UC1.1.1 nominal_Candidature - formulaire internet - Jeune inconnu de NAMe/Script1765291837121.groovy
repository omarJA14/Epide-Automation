import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'Keywords.commun.UtilsEpide.Access_formulaire_internet_epide'()

def data_centre = TestDataFactory.findTestData("FormulaireInternet/centres_epide")
def totalRows = data_centre.getRowNumbers()

List<String> expectedCenters = []

for (int i = 1; i <= totalRows; i++) {
	println "i = ${i} → " + data_centre.getValue("Centres", i)
	expectedCenters.add(data_centre.getValue("Centres", i).trim())
}

println("Centres attendus depuis CSV : " + expectedCenters)

CustomKeywords.'Keywords.commun.UtilsEpide.HandleCentreEpide'(
	"//mat-select[@id='epideCenterSelect']",
	expectedCenters
)

WebUI.closeBrowser()