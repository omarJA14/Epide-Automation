import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/prog_d1_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('D1-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	String directeur = data_entretien_fse.getValue("Nom du directeur du centre ou son représentant", i)
	String secretaire = data_entretien_fse.getValue("Nom du secrétaire", i)
	String rapporteur = data_entretien_fse.getValue("Nom du rapporteur", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationConseilD1'),
		'Validation conseil D1')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Valider'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
		'Convocation conseil de discipline')
	
}
WebUI.closeBrowser()