import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_editcontrat = TestDataFactory.findTestData("edit_contrat")
def totalRows = data_editcontrat.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_editcontrat.getValue("Prénom du jeune", i)
	String promotion = data_editcontrat.getValue("Promotion", i)
	String date_arrive = data_editcontrat.getValue("Date d'arrivée", i)
	String section = data_editcontrat.getValue("Section", i)
	String datedébut_E_C = data_editcontrat.getValue("Date de début", i)
			
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/input_Promotion'))
	
	TestObject lib_promotion = findTestObject('Object Repository/Contrat/Edit_Contrat/input_Libelle_Promotion')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(lib_promotion, promotion)
	WebUI.sendKeys(lib_promotion, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Element_promotion_Choix'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/input_Section'))
	
	TestObject lib_section = findTestObject('Object Repository/Contrat/Edit_Contrat/input_Libelle_Section')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(lib_section, section)
	WebUI.sendKeys(lib_section, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Element_Section_Choix'))
		
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Rub_Admissibles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneInRub'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/Checkbox_Admissible_Jeune'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/button_EditerContrat'))
	
	if (!WebUI.waitForElementPresent(findTestObject('Object Repository/Contrat/Edit_Contrat/Text_Popup_ConfirmerEditerContrat'), 10)) {
		KeywordUtil.markFailed("Échec : Pop-up 'Editer contract' est introuvable.")
	}

	TestObject date_debut = findTestObject('Object Repository/Contrat/Edit_Contrat/input_Popup_ConfEditContr_DateDebut')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(date_debut, datedébut_E_C)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Edit_Contrat/button_Popup_ConfEditContr_Confirmer'))
	
	TestObject imgPrinter = findTestObject('Object Repository/Contrat/Edit_Contrat/img_printer')
	WebUI.verifyElementPresent(imgPrinter, 10)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyIconContains'(imgPrinter, "printer")
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/Access_SituationsContractuelles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Contrat/Signer_Contrat/Check_Element_Encours'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Contrat/Signer_Contrat/tab_contrat'),
		"Type",
		"Contrat d'insertion"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Contrat/Signer_Contrat/tab_contrat'),
		"Statut",
		"A signer"
	)
	WebUI.refresh()
}
WebUI.closeBrowser()
