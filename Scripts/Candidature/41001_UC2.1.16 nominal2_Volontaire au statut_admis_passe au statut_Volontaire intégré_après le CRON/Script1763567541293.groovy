import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_volontaire = TestDataFactory.findTestData("check_volontaire")
def totalRows = data_volontaire.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_volontaire.getValue("Prénom du jeune", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_Gestion des volontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_Volontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneInRub'(prenom)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/CAS/Elements_CAS/table_infos_jeune'),
		"Statut du jeune",
		"Volontaire intégré"
	)
WebUI.refresh()
}
WebUI.closeBrowser()