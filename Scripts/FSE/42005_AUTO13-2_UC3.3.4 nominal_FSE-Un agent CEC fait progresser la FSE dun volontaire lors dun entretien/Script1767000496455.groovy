import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("creer_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	String sanctions = data_entretien_fse.getValue("Sanction(s) disciplinaire(s) demandée(s)", i)
	String prenom_agent = data_entretien_fse.getValue("Prénom de l'agent", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElementsFse'(
    "work",["namFseStatut" : "CREATIONFSE"])

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_EntretienCEC'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElementsFse'(
		"work",["namFseStatut" : "ENTRETIENCEC"])
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_TypedeFSE'), sanctions, true)
	
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