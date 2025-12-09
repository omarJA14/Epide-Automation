import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entr_cas = TestDataFactory.findTestData("entretien_cas")
def totalRows = data_entr_cas.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CAS-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String Commentaire_Social = "//*[@id='field_namEiCommentaireSocial']"
	String Commentaire_Medical = "//*[@id='field_namEiCommentaireMedical']"
	
	String prenom = data_entr_cas.getValue("Prénom du jeune", i)
	String date_entretien = data_entr_cas.getValue("Date de l'entretien", i)
	String objet_entretien = data_entr_cas.getValue("Objet de l'entretien", i)
	String comm_gen = data_entr_cas.getValue("Commentaire général", i)
	String comm_medical = data_entr_cas.getValue("Commentaire social (accès réservé au professionnel social)", i)
	String date_rv_prochain = data_entr_cas.getValue("Date du prochain RDV", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/ComunCI/Page_Simplicit/Rub_EntretiensIndividuels'))
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/ComunCI/Page_Simplicit/button_Creer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyElementInPage'(
		Commentaire_Social,
		true,
		"Commentaire_Social"
	)
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyElementInPage'(
		Commentaire_Medical,
		false,
		"Commentaire_Medical"
	)
	
	TestObject dateEntretien = findTestObject('Object Repository/ComunCI/Page_Simplicit/input_DateEntretien')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(dateEntretien, date_entretien)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/ComunCI/Page_Simplicit/input_ObjetEntretien'), objet_entretien)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/ComunCI/Page_Simplicit/input_CommentaireGeneral'), comm_gen)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/ComunCI/Page_Simplicit/input_CommentaireSocial'), comm_medical)
	
	TestObject dateProchainRV = findTestObject('Object Repository/ComunCI/Page_Simplicit/input_DateProchainRDV')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(dateProchainRV, date_rv_prochain)
	
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
WebUI.refresh()
}
WebUI.closeBrowser()