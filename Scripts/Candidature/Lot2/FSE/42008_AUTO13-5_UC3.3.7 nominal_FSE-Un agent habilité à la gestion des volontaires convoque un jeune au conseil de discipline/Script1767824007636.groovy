import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/convoc_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('ASSAD-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	String directeur = data_entretien_fse.getValue("Nom du directeur du centre ou son représentant", i)
	String secretaire = data_entretien_fse.getValue("Nom du secrétaire", i)
	String rapporteur = data_entretien_fse.getValue("Nom du rapporteur", i)
	String circon = data_entretien_fse.getValue("Circonstances - conseil discipline", i)
	String faits = data_entretien_fse.getValue("Les faits - conseil discipline", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConvocationConseildeDiscipline'),
		'Convocation conseil de discipline')
	
	TestObject dateField =
	findTestObject('Object Repository/FSE/Page_Simplicit/input_DatedeConvocation')
	
	String dateTime =
	CustomKeywords.'Keywords.commun.UtilsEpide.getDateTimePlusWorkingDays'(4, "10:00")
	
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateTimeField'(
	dateField,
	dateTime
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduDirecteurduCentre'),
		directeur
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduSecretaire'),
		secretaire
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduRapporteur'),
		rapporteur
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/textarea_CirconstancesConseilDiscipline'), circon)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/textarea_Les faitsConseilDiscipline'), faits)
	
	TestObject convoc_conseil_discip = findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConvocationConseildeDiscipline')
	WebElement element = WebUI.findWebElement(convoc_conseil_discip)
	WebUI.executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", Arrays.asList(element))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(convoc_conseil_discip,
		'Convocation conseil de discipline')
	
	TestObject btn_edit_convoc = findTestObject('Object Repository/FSE/Page_Simplicit/button_EditerConvocation')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_edit_convoc, 15)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Convoquer'))
	
	WebUI.delay(5)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
		'Conseil de discipline', 20)
		
}
WebUI.closeBrowser()