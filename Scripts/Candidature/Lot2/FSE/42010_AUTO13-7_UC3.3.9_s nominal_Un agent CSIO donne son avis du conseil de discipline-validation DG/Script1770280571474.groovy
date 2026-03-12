import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/validg_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CSIO')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationDG'),
		'Validation DG', 20)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Valider_Clos'))
	
	WebUI.delay(5)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_Clos'),
		'Clos', 20)
	
	TestObject btn_exporter = findTestObject('Object Repository/FSE/Page_Simplicit/button_Exporter')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_exporter, 15)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_popup_Exporter'))
	
	WebUI.delay(5)
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyDownloadByFirstname'(prenom, 30)
}
WebUI.closeBrowser()