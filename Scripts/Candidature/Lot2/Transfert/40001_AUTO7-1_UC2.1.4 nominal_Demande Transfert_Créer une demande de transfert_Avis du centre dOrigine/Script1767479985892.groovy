import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_demande_transfert = TestDataFactory.findTestData("Transfert/demande_transfert")
def totalRows = data_demande_transfert.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_demande_transfert.getValue("Prénom du jeune", i)
	String select_centre = data_demande_transfert.getValue("Centre EPIDE", i)
	String date_demande_transf = data_demande_transfert.getValue("Date de transfert souhaitée", i)
	String motiv_demande_transf = data_demande_transfert.getValue("Motivation du transfert", i)
	String besoin_heberg_demande_transf = data_demande_transfert.getValue("Besoin d'hébergement", i)
	String besoin_paquetage_demande_transf = data_demande_transfert.getValue("Besoin de paquetage", i)
	String observa_avis_demande_transf = data_demande_transfert.getValue("Observation", i)
				
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/button_DemandeTransfert'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/input_CentreEPIDESouhaite'))
	
	TestObject centre_epide_souhaite = findTestObject('Object Repository/Demande_Transfert/input_Selectionner_CentreEPIDE')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(centre_epide_souhaite, select_centre)
	WebUI.sendKeys(centre_epide_souhaite, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Element_Ville_Choix'))
		
	TestObject dateField = findTestObject('Object Repository/Demande_Transfert/input_DateTransfertSouhaitee')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(dateField, date_demande_transf)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Demande_Transfert/input_MotivationTransfert'), motiv_demande_transf)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Demande_Transfert/input_BesoinHebergement'), besoin_heberg_demande_transf)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Demande_Transfert/input_BesoinPaquetage'), besoin_paquetage_demande_transf)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/button_Creer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Demande_Transfert/input_Avis_Observation'), observa_avis_demande_transf)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Demande_Transfert/Check_AvisduCentredorigine'), "Avis du centre d'origine")
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/button_SoumettreCentreDestination'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_DemandesTransfert'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyDemandeTransfertTable'(
		findTestObject('Object Repository/Demande_Transfert/table_demande_transfert'),
		"Etat",
		"Avis du centre de destination"
	)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Element_AvisCentreDestination'))
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Demande_Transfert/Check_AvisduCentreDestination'), 
		'Avis du centre de destination')
	
	WebUI.refresh()
}
WebUI.closeBrowser()