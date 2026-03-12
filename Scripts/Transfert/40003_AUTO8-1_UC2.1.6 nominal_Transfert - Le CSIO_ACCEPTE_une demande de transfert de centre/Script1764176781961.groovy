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

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_transfert_avis_dg = TestDataFactory.findTestData("Transfert/valider_transfert")
def totalRows = data_transfert_avis_dg.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CSIO')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_transfert_avis_dg.getValue("Prénom du jeune", i)
	String decision = data_transfert_avis_dg.getValue("Décision", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchDemandeTransfert'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_DecisiondelaDG'))
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_Etat_DecisiondelaDG'), "Décision de la DG")
		
	List<TestObject> boutonObjs = [
		findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Accepter'),
		findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Refuser'),
	]
	
	List<String> boutonNames = ['Accepter', 'Refuser']
	
	CustomKeywords.'Keywords.commun.UtilsEpide.checkButtons'(boutonObjs, boutonNames)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/input_field_Decision'), decision)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Accepter'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_Etat_Valide'), "Validée")
		
WebUI.refresh()
}
WebUI.closeBrowser()