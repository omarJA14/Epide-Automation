import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/valid_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CSECi-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationCSECi'),
	'Validation CSECi')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Valider'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationConseilD1'),
		'Validation conseil D1')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyRadioSelection'(
		"namFseValidCseciValidation",
		"oui"
	)
	
}
WebUI.closeBrowser()