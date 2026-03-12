import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_exam = TestDataFactory.findTestData("Sortie/sortie_anticipee")
def totalRows = data_exam.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_exam.getValue("Prénom du jeune", i)
	String typesortie = data_exam.getValue("Type de sortie", i)
	String motifsortie = data_exam.getValue("Motif sortie anticipée", i)
	String rechercheemploi = data_exam.getValue("En recherche d'emploi", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/Access_SituationsContractuelles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/Check_Element_Encours'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/Rub_SortieduVolontaire'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/button_SortieAnticipee'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Sortie/Page_Simplicit/statut_Encours'),
		'En cours', 20)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Sortie/Page_Simplicit/select_TypedeSortie'), typesortie, false)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Sortie/Page_Simplicit/select_MotifSortieAnticipee'), motifsortie, false)
	
	def radioMaprechercheemploi = [
		"Oui" : 'Object Repository/Sortie/Page_Simplicit/radio_Oui_enRechecheEmploi',
		"Non" : 'Object Repository/Sortie/Page_Simplicit/radio_Non_enRechecheEmploi'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(rechercheemploi, radioMaprechercheemploi)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/button_ValiderlaSortie'))
		
	TestObject text_popup = findTestObject('Object Repository/Sortie/Page_Simplicit/Text_ConfirmerValiderlaSortie_popup')
	
	if (WebUI.waitForElementPresent(text_popup, 10)) {
		CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Sortie/Page_Simplicit/button_Confirmer_popup'))
	} else {
		println ("Popup ${text_popup} introuvabe")
	}
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Sortie/Page_Simplicit/Element_Validee_Sortie'),
		'Validée', 20)
	
	// Check dans Notification
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyNotificationSortieFromAccueil'(
		"GRH-Bordeaux",
		prenom,
		"Une sortie est prévue pour le jeune",
		10
	)
		
}
WebUI.closeBrowser()