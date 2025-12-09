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

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_signeravenant = TestDataFactory.findTestData("Transfert/signer_avenant")
def totalRows = data_signeravenant.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_signeravenant.getValue("Prénom du jeune", i)
	String centre_origine = data_signeravenant.getValue("Centre Epide Origine", i)
	String centre_destination = data_signeravenant.getValue("Centre Epide Souhaité", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Rub_GestionVolontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Rub_Volontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneInRubAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Rub_SituationsContractuelles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_EnCours'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/tab_contrat'),
		"Type",
		"Avenant de transfert de centre"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Avenantdetransfertdecentre'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Etat_AvenantDeTransfertDeCentre'), 
		'Avenant de transfert de centre')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Etat_EnCoursDeCreation'), 
		'En cours de création')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/button_Editer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Statut_A-signer'), 
		'A signer')
	
	TestObject btn_generer_doc = findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/button_GenererDocument')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_generer_doc, 15)
	
	WebUI.uploadFile(
		findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/input_DocumentSignFile'),
		'C:\\Users\\A689947\\Katalon Studio\\EPIDE-Automation\\Data Files\\MODEL_GED_CONTRATINSERTION.docx'
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	TestObject button_signer = findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/button_Signer')
	WebUI.waitForElementPresent(button_signer, 10, FailureHandling.STOP_ON_FAILURE)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(button_signer)
	
	if (!WebUI.waitForElementPresent(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Text_popup_Confirmer-Signer'), 10)) {
		KeywordUtil.markFailed("Échec : popup de confirmation est introuvable.")
	}
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/button_Confirmer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Etat_Signe'), 'Signé')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifierCentres'(centre_origine, centre_destination)
	
	WebUI.refresh()
}
WebUI.closeBrowser()
