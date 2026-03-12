import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_conv_stage = TestDataFactory.findTestData("ConventionStage/creer_conv_stage")
def totalRows = data_conv_stage.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_conv_stage.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Rub_ConventionsdeStage'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
		findTestObject('Object Repository/Convention_Stage/Page_Simplicit/table_ConventionsdeStage'),
		"Statut de la convention de stage",
		"A signer"
	)	
	TestObject btn_telecharger_conv_stage = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/button_TelechargerlaConventiondeStage')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_telecharger_conv_stage, 15)
	
	WebUI.uploadFile(
		findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_Upload_ConventiondeStageSigne'),
		'C:\\Users\\A689947\\Katalon Studio\\EPIDE-Automation\\Data Files\\MODEL_GED_CONTRATINSERTION.docx'
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/button_Signer_conv_stage'))
	
	WebUI.delay(5)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Elem_Signe_conv_stage'),
		'Signé', 20)
	
}
WebUI.closeBrowser()