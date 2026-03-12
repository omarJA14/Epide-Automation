import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_retfin = TestDataFactory.findTestData("Retenue_Financiere/demande_retenuefinanciere")
def totalRows = data_retfin.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CEC-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_retfin.getValue("Prénom du jeune", i)
	String nom_directeur = data_retfin.getValue("Nom du directeur du centre ou son représentant", i)
	String nom_csmg = data_retfin.getValue("Nom du CSMG ou son représentant", i)
	String nom_agent_accomp = data_retfin.getValue("Nom de l'agent accompagnant", i)
	String nom_volontaire_accomp = data_retfin.getValue("Nom du volontaire accompagnant", i)
	String taux_finan = data_retfin.getValue("Taux forfaitaire", i)
	String montant_echeancier1 = data_retfin.getValue("Montant échéancier 1", i)
	String date_echeancier1 = data_retfin.getValue("Date échéancier 1", i)
	String montant_echeancier2 = data_retfin.getValue("Montant échéancier 2", i)
	String commentaire_retenue = data_retfin.getValue("Commentaire sur la retenue", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Rub_RetenuesFinancieres'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/button_Creer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_Statut_AvaliderD1'),
		'A valider D1', 20)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_NomduDirecteurduCentre'))
	
	TestObject nom_agent_d = findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_Nom_Select_Agent')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(nom_agent_d, nom_directeur)
	WebUI.sendKeys(nom_agent_d, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_prenom_cec'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_NomduCSMG'))
	TestObject nom_agent_csmg = findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_Nom_Select_Agent')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(nom_agent_csmg, nom_csmg)
	WebUI.sendKeys(nom_agent_csmg, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_prenom_cec'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_NomdeAgentAccompagnant'))
	TestObject nom_agent_ac = findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_Nom_Select_Agent')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(nom_agent_ac, nom_agent_accomp)
	WebUI.sendKeys(nom_agent_ac, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_prenom_cec'))
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/select_Taux_forfaitaire'), taux_finan, false)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_MontantEcheancier1'), montant_echeancier1)
	
    TestObject champ_datedebut = findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/input_DateEcheancier1')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_datedebut, date_echeancier1)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/button_Valider'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_AvaliderDIORRE'),
		'A valider DIORRE', 20)
	
}
WebUI.closeBrowser()
