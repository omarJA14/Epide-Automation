import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.time.LocalDate
import java.time.format.DateTimeFormatter

def data_creation_fse = TestDataFactory.findTestData("Fse/creer_fse")
def totalRows = data_creation_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CEC-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_creation_fse.getValue("Prénom du jeune", i)
	String typefse = data_creation_fse.getValue("Type de FSE", i)
	String circon = data_creation_fse.getValue("Circonstances", i)
	String lieu_incid = data_creation_fse.getValue("Lieu de l'incident", i)
	String lesfaits = data_creation_fse.getValue("Les faits", i)
	String lavict = data_creation_fse.getValue("La victime", i)
	String ref_obli_enf = data_creation_fse.getValue("Références aux obligations enfreintes", i)
	String reaction = data_creation_fse.getValue("Réactions", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/Rub_FSE_Volontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_FSE_Creer'))
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_TypedeFSE'), typefse, true)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_lesFaits'), lesfaits)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/Rub_FSE'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
		findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'),
		"Type de FSE",
		"Détérioration"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchInRub'("fse", prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
		findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'),
		"Numéro de FSE",
		"notEmpty"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_FSE_Type'))
	String today = LocalDate.now().format(
		DateTimeFormatter.ofPattern("dd/MM/yyyy")
	)
	def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
	
	def expectedValues = [
		"namFseNum" : "notEmpty",
		"namFseDateEvenement"   : today,
		"namFseType"   : "DETERIORATION"
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElements'(form, expectedValues)
	
	WebUI.closeBrowser()
	
	//Ajouter la vérification dans la rubrique 'notification'

	CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('ASSDIR')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Notification/Page_Notification/Rub_AdministrationDesNotifications'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchNotification'('Nouvelle FSE')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
		findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
		"Message",
		"Une nouvelle FSE a été créée pour le jeune"
	)
	
	}
	WebUI.closeBrowser()