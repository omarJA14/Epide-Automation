import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_exam = TestDataFactory.findTestData("Examen/inscription_exam")
def totalRows = data_exam.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CIP-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String intitul_exam = data_exam.getValue("Intitulé Types d'examen", i)
	String date_heure = data_exam.getValue("Date et heure", i)
	String duree = data_exam.getValue("Durée (en heures)", i)
	String montant = data_exam.getValue("Montant", i)
	String prenom = data_exam.getValue("Prénom du jeune", i)
	String abs_pres = data_exam.getValue("Absent / Présent", i)
	String obtenu = data_exam.getValue("Obtenu", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.AccessExamens'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/button_Creer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/input_type_exam'))
	
	TestObject intitule = findTestObject('Object Repository/Examen/Page_Simplicit/input_intitule_select_typeExam')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(intitule, intitul_exam)
	WebUI.sendKeys(intitule, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/Elem_S.S.T'))
	
	TestObject champ_date = findTestObject('Object Repository/Examen/Page_Simplicit/input_DateHeure')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_date, date_heure)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Examen/Page_Simplicit/input_Duree'), duree)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Examen/Page_Simplicit/input_Montant'), montant)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	TestObject text_popup = findTestObject('Object Repository/Examen/Page_Simplicit/popup_Quitter')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/button_Creer'))
	
	if (WebUI.waitForElementPresent(text_popup, 10)) {
		CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/button_popup_Enregistrer'))
	} else {
		println ("Popup ${text_popup} introuvabe")
	}
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/input_CentreEPIDE'))
	
	TestObject prenom_jeune = findTestObject('Object Repository/Examen/Page_Simplicit/input_select_jeune_prenom')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(prenom_jeune, prenom)
	WebUI.sendKeys(prenom_jeune, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/Elem_prenom_select_jeune'))
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Examen/Page_Simplicit/select_Absent_Present'), abs_pres, false)
	
	def radioObtMap = [
		"Oui" : 'Object Repository/Examen/Page_Simplicit/input_Oui_Obtenu',
		"Non" : 'Object Repository/Examen/Page_Simplicit/input_Non_Obtenu'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(obtenu, radioObtMap)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldSmart'(
		findTestObject('Object Repository/Examen/Page_Simplicit/table_inscrip_exam'),
		"Prénom",
		prenom
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
}
WebUI.closeBrowser()