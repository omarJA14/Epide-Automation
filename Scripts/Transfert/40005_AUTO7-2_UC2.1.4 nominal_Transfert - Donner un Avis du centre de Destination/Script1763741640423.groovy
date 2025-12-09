import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_transfert_avis_centre_desti = TestDataFactory.findTestData("Transfert/soumettre_dg")
def totalRows = data_transfert_avis_centre_desti.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('ASSDIR')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_transfert_avis_centre_desti.getValue("Prénom du jeune", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_GestionVolontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_DemandesTransfert'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchDemandeTransfert'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Element_AvisCentreDestination'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Element_Etat_AvisCentreDestination'),
		'Avis du centre de destination')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/button_SoumettreDG'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Element_Etat_Decision_de_la_DG'),
		'Décision de la DG')
	
WebUI.refresh()
}
WebUI.closeBrowser()