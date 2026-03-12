import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/prog_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CEC-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	String sanctions = data_entretien_fse.getValue("Sanction(s) disciplinaire(s) demandée(s)", i)
	String prenom_agent = data_entretien_fse.getValue("Prénom de l'agent", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
//	CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElementsFse'(
//    "work",["namFseStatut" : "CREATIONFSE"])
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_CreationFSE'),
	'Création FSE')

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_EntretienCEC'))
	
//	CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElementsFse'(
//		"work",["namFseStatut" : "ENTRETIENCEC"])
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_EntretienCEC'),
	'Entretien CEC')
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_SanctionsDisciplinairesDemandes'), sanctions, true)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_AssocierAgents'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_NomAgent'), prenom_agent)
	
	TestObject elem_prenom_agent = findTestObject('Object Repository/FSE/Page_Simplicit/input_NomAgent')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(elem_prenom_agent, prenom_agent)
	WebUI.sendKeys(elem_prenom_agent, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/checkbox_selectionner_agent'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_selectionner'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/FSE/Page_Simplicit/table_agent_suivi_sanction'), "Nom de l'agent", prenom_agent)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
		findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'), "Statut de FSE", "Validation CSECi")
	
}
WebUI.closeBrowser()