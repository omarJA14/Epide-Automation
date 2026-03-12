import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject

def data_volontaire = TestDataFactory.findTestData("check_cof")
def totalRows = data_volontaire.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_volontaire.getValue("Prénom du jeune", i)
	
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
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchInRub'(prenom)
	
	TestObject tableCOF = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofs'(
		tableCOF,
		['COF1', 'COF2', 'COF3']
	)
	
}