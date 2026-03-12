import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_check_cof = TestDataFactory.findTestData("Cof/check_cof")
def totalRows = data_check_cof.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_check_cof.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/COF/Page_COF/Rub_COF_Volontaires'))
	
	TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
	
	Map<String, String> cofStatuts = [
		'COF1': 'Préparation',
		'COF2': 'Préparation',
		'COF3': 'Préparation'
	]
	
	cofStatuts.each { cof, statut ->
		CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofWithStatus'(
			tableObj,
			cof,
			statut
		)
	}
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/COF/Page_COF/Rub_COF'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchInRub'("cof", prenom)
	//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/ComunCI/Page_Simplicit/input_cof_prenom'), prenom)
	
	TestObject tableCOF = findTestObject('Object Repository/COF/Page_COF/Table_COF')
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofs'(
		tableCOF,
		['COF1', 'COF2', 'COF3']
	)
	
}
WebUI.closeBrowser()