import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI 

def data_actionrecrut = TestDataFactory.findTestData("action_recrutement")
def totalRows = data_actionrecrut.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')
	
for (int i = 1; i <= totalRows; i++) {
	
	String dataheure_actionrecrut = data_actionrecrut.getValue("Date et heure", i)
	String typactionrecrutement = data_actionrecrut.getValue("Type d'action de recrutement", i)
	String centre_epide = data_actionrecrut.getValue("Centre EPIDE", i)
	String rappel_auto = data_actionrecrut.getValue("Rappel automatique", i)
	String lieu = data_actionrecrut.getValue("Lieu", i)
	String prenomjeune_selec = data_actionrecrut.getValue("Prénom du jeune à sélectionner", i)
	String statutpresentabsent = data_actionrecrut.getValue("Présent / Absent", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_GestionDesCandidats'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/button_AR_ActionsRecrutement'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/button_AR_Creer_Recrutement'))
	
	TestObject dateField = findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/input_AR_DateHeure')
	String date_ar = CustomKeywords.'Keywords.commun.UtilsEpide.getDateForm'(dataheure_actionrecrut)
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(dateField, date_ar)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/List_AR_TypeActionRecrutement'), typactionrecrutement, false);
			
	def radioMaprappelauto = [
		"Oui" : 'Object Repository/Recrutement/Page_Action_Recrutement/Radio_AR_Oui_RappelAutomatique',
		"Non" : 'Object Repository/Recrutement/Page_Action_Recrutement/Radio_AR_Non_RappelAutomatique'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(rappel_auto, radioMaprappelauto)
	
	//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/input_AR_CentreEPIDE'), centre_epide)
	
	//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/input_AR_Lieu'), lieu)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/button_AR_AssocierJeunes'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeune'(prenomjeune_selec)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/Checkbox_AR_Jeune'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/button_AR_Selectionner'))
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Recrutement/Page_Action_Recrutement/List_AR_PresentAbsent'), statutpresentabsent, false);
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Jeunes'))
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeune'(prenomjeune_selec)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/CAS/Elements_CAS/table_infos_jeune'),
		"Statut du jeune",
		"Convoqué"
	)
	
WebUI.refresh()
	
}
WebUI.closeBrowser()