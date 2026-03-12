import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_retfin = TestDataFactory.findTestData("Retenue_Financiere/demande_retenuefinanciere")
def totalRows = data_retfin.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CSIO')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_retfin.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Rub_RetenuesFinancieres'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnStatutRF'("A valider DIORRE")
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/button_Valider'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/RetenueFinanciere/Page_Simplicit/Elem_AvaliderSEF'),
		'A valider SEF', 20)
	
}
WebUI.closeBrowser()
