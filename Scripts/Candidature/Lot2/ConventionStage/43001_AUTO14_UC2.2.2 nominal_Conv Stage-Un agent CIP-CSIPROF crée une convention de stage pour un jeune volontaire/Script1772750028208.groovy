import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_conv_stage = TestDataFactory.findTestData("ConventionStage/creer_conv_stage")
def totalRows = data_conv_stage.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CIP-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_conv_stage.getValue("Prénom du jeune", i)
	String date_debut = data_conv_stage.getValue("Date de début", i)
	String date_fin_init = data_conv_stage.getValue("Date de fin initiale", i)
	String date_fin_annul = data_conv_stage.getValue("Date de fin annulation", i)
	String horaires = data_conv_stage.getValue("Horaires", i)
	String champ_label_rome = data_conv_stage.getValue("Label ROME", i)
	String objectif = data_conv_stage.getValue("Objectif", i)
	String besoin_spec = data_conv_stage.getValue("Besoins spécifiques", i)
	String observations = data_conv_stage.getValue("Observations", i)
	String motif_annul = data_conv_stage.getValue("Motifs d’annulation", i)
	String champ_siret = data_conv_stage.getValue("SIRET/SIREN", i)
	String prenom_agent = data_conv_stage.getValue("Prénom Agent", i)
	String stage_consis = data_conv_stage.getValue("Le stage consiste à", i)
	String date_effet_stage = data_conv_stage.getValue("Date d’effet du stage", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Rub_ConventionsdeStage'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/button_Creer'))
	
	TestObject champ_date_debut = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_Datedebut')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_date_debut, date_debut)
	
	TestObject champ_date_fin = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_DateFinInitiale')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_date_fin, date_fin_init)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_Metier'))
	
	TestObject label_rome = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_LabelRome')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(label_rome, champ_label_rome)
	WebUI.sendKeys(label_rome, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Elem_OrganisationEvenementiel'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_RaisonSocialePartenaire'))
	
	TestObject siret = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_SIRET_SIREN')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(siret, champ_siret)
	WebUI.sendKeys(siret, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Elem_SIRET_SIREN'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_nom_agent'))
	
	TestObject select_prenom = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_selec_prenom')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(select_prenom, prenom_agent)
	WebUI.sendKeys(select_prenom, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Elem_nom'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_LeStageConsisteA'), stage_consis)
	
	TestObject champ_effet_stage = findTestObject('Object Repository/Convention_Stage/Page_Simplicit/input_DateEffetduStage')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_effet_stage, date_effet_stage)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/button_Editer'))
	
	WebUI.delay(5)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Convention_Stage/Page_Simplicit/Elem_A_signer'),
		'A signer', 20)
	
}
WebUI.closeBrowser()