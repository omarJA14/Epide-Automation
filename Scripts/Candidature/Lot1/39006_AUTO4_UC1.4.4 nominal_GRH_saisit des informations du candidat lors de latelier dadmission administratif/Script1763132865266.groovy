import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.support.Color

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_infogrh = TestDataFactory.findTestData("piece_administrative")
def totalRows = data_infogrh.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_infogrh.getValue("Prénom du jeune", i)
	String typedocum = data_infogrh.getValue("Type de document", i)
	String date_demande = data_infogrh.getValue("Date de la demande", i)
	String etat_trans = data_infogrh.getValue("Etat de transmission", i)
	String date_fourni = data_infogrh.getValue("Date de fourniture", i)
	String date_expi = data_infogrh.getValue("Date d'expiration", i)
			
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_RubriquesAtelierAdmission'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/GRH/Access_PiecesAdministratives'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/GRH/button_CreerPA'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/GRH/input_TypeDocument'))
	
	TestObject lib_typedoc = findTestObject('Object Repository/GRH/input_Libelle_Type_Document')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(lib_typedoc, typedocum)
	WebUI.sendKeys(lib_typedoc, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/GRH/Element_CNI_exem'))
		
	TestObject date_d = findTestObject('Object Repository/GRH/input_DateDemande')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(date_d, date_demande)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/GRH/Element_EtatTransmission'), etat_trans, false) 
	
	TestObject date_f = findTestObject('Object Repository/GRH/input_DateFourniture')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(date_f, date_fourni)
	
	TestObject date_exp = findTestObject('Object Repository/GRH/input_DateExpiration')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(date_exp, date_expi)
	
	WebUI.uploadFile(
		findTestObject('Object Repository/GRH/input_fichier_Importer_file'),
		'C:\\Users\\A689947\\Katalon Studio\\EPIDE-Automation\\Data Files\\facture_katalon_test_39006.pdf'
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()

	TestObject etatTransmissionElement = findTestObject('Object Repository/GRH/Element_ET_Transmis')
	
	String cssbg = WebUI.getCSSValue(etatTransmissionElement, 'background-color')
	String bghex = Color.fromString(cssbg).asHex()
	
	if (bghex != "#5cb85c") {
	    KeywordUtil.markFailed("Échec : Etat de transmission n'est pas validé")
	}	
	WebUI.refresh()
}
WebUI.closeBrowser()
