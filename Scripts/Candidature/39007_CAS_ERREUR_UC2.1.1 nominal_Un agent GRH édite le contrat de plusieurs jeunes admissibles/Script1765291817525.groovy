import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.thoughtworks.selenium.webdriven.commands.GetText

def data_erroreditcontrat = TestDataFactory.findTestData("cas_erreur_editcontrat")
def totalRows = data_erroreditcontrat.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_erroreditcontrat.getValue("Prénom du jeune", i)
	String nom = data_erroreditcontrat.getValue("Nom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Rub_Admissibles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneInRub'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Checkbox_Admissible_Jeune'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/button_EditerContrat'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/button_Popup_ConfEditContr_Confirmer'))

	String mess_error = "Le jeune ${nom} ${prenom} n'est pas rattaché à une promotion"
	
	TestObject text_popup_error = findTestObject('Object Repository/Contrat/Edit_Contrat/Text_popup_Erreur')
	TestObject text_msg_error = findTestObject('Object Repository/Contrat/Edit_Contrat/Text_popup_MessageErreur')
	
	String elem1 = WebUI.getText(text_popup_error)
	String elem2 = WebUI.getText(text_msg_error)
	
	
	if (!WebUI.waitForElementPresent(text_popup_error, 10)) {
	    KeywordUtil.markFailed("Échec : Pop-up 'Erreur' introuvable pour ${prenom}")
	} else {
		println ("${elem1} ------ ${elem2}")
	    WebUI.verifyElementText(text_msg_error, mess_error, FailureHandling.STOP_ON_FAILURE)		
	    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/button_popup_Erreur_OK'))
	}
WebUI.refresh()
}
WebUI.closeBrowser()