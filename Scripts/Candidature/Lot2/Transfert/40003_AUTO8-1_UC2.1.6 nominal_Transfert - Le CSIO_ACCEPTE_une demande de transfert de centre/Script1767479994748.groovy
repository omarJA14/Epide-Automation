import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_transfert_avis_dg = TestDataFactory.findTestData("Transfert/valider_transfert")
def totalRows = data_transfert_avis_dg.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CSIO')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_transfert_avis_dg.getValue("Prénom du jeune", i)
	String decision = data_transfert_avis_dg.getValue("Décision", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchDemandeTransfert'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_DecisiondelaDG'))
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_Etat_DecisiondelaDG'), "Décision de la DG")
		
	List<TestObject> boutonObjs = [
		findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Accepter'),
		findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Refuser'),
	]
	
	List<String> boutonNames = ['Accepter', 'Refuser']
	
	CustomKeywords.'Keywords.commun.UtilsEpide.checkButtons'(boutonObjs, boutonNames)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/input_field_Decision'), decision)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/button_Accepter'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/CSIO/Page_Simplicit/Element_Etat_Valide'), "Validée")
		
WebUI.refresh()
}
WebUI.closeBrowser()