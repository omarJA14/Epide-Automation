import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


def data_signercontrat = TestDataFactory.findTestData("signer_contrat")
def totalRows = data_signercontrat.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_signercontrat.getValue("Prénom du jeune", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/Access_SituationsContractuelles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/Check_Element_Encours'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Contrat/Signer_Contrat/tab_contrat'),
		"Type",
		"Contrat d'insertion"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/Element_ContratInsertion'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Contrat/Signer_Contrat/Element_A signer'), 'A signer')
	
	TestObject btn_generer_doc = findTestObject('Object Repository/Contrat/Signer_Contrat/button_GenererDocument')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_generer_doc, 15)
	
	WebUI.uploadFile(
		findTestObject('Object Repository/Contrat/Signer_Contrat/input_upload_Document'),
		'C:\\Users\\A689947\\Katalon Studio\\EPIDE-Automation\\Data Files\\MODEL_GED_CONTRATINSERTION.docx'
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	TestObject button_signer = findTestObject('Object Repository/Contrat/Signer_Contrat/button_Signer')
	
	WebUI.waitForElementPresent(button_signer, 10, FailureHandling.STOP_ON_FAILURE)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(button_signer)
	
	if (!WebUI.waitForElementPresent(findTestObject('Object Repository/Contrat/Signer_Contrat/Text_popup_ConfirmerSigner'), 10)) {
		KeywordUtil.markFailed("Échec : popup de confirmation est introuvable.")
	}
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/button_Confirmer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Contrat/Signer_Contrat/Element_Sign'), 'Signé')

	WebUI.refresh()
	}
WebUI.closeBrowser()