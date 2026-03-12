import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_volontaire = TestDataFactory.findTestData("verif_code_tiers")
def totalRows = data_volontaire.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_volontaire.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Volontaire/Page_Simplicit/Rub_RelevesMensuels'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Volontaire/Page_Simplicit/table_RelevesMensuels'),
		"Code Tiers",
		"0086040"
	)
	//Verifier que le champ n'est pas vide
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Volontaire/Page_Simplicit/Elem_CodeTiers'))
		
	def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
	
	def expectedValues = [
		"namRmlCodeTiers"                     : "0086040"
	]
	//Verifier que le champ n'est pas vide
	CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form, expectedValues)
	
WebUI.refresh()
}
WebUI.closeBrowser()
