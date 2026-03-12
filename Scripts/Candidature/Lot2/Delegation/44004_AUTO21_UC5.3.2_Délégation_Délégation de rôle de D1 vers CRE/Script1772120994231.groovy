import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_exam = TestDataFactory.findTestData("Delegation/delegation_role")
def totalRows = data_exam.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('D1-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_exam.getValue("Prénom du jeune", i)
	String datedebut = data_exam.getValue("Date de début", i)
	String datefin = data_exam.getValue("Date de fin", i)
	String nomagent = data_exam.getValue("Nom de l'agent", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Delegation/Page_Simplicit/Rub_Delegationderole'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Delegation/Page_Simplicit/button_Creer'))
	
	TestObject champ_datedebut = findTestObject('Object Repository/Delegation/Page_Simplicit/input_DatedeDebut')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_datedebut, datedebut)
	
	TestObject champ_datefin = findTestObject('Object Repository/Delegation/Page_Simplicit/input_DatedeFin')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_datefin, datefin)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Delegation/Page_Simplicit/input_NomduMandataire'))
	
	TestObject nom_agent = findTestObject('Object Repository/Delegation/Page_Simplicit/input_nom_select_agent')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(nom_agent, nomagent)
	WebUI.sendKeys(nom_agent, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Delegation/Page_Simplicit/Element_prenom_cre'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Delegation/Page_Simplicit/Rub_Delegationderole'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.delegationRowExists'(
		"nom d1",
		"prénom d1",
		"Directeur(trice) de centre",
		"prénom cre",
		"nom cre"
	)
	
	WebUI.closeBrowser()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRE-Bordeaux')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchCandidatAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_RubriquesAtelierAdmission'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_AvisAteliersAdmission'))
		
	TestObject boutonCreer = findTestObject('Object Repository/Delegation/Page_Simplicit/button_Creer_candidat')	
	WebUI.verifyElementVisible(boutonCreer, FailureHandling.STOP_ON_FAILURE)
			
}
WebUI.closeBrowser()