import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_demande_transfert = TestDataFactory.findTestData("Transfert/verifier_transfert")
def totalRows = data_demande_transfert.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Lanrodec')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_demande_transfert.getValue("Prénom du jeune", i)
	String centre_epide = data_demande_transfert.getValue("Centre EPIDE", i)
			
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyValue'(findTestObject('Object Repository/Transfert/GRH-Lanrodec/Page_Simplicit/input_CentreEPIDE'),
		centre_epide)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Transfert/GRH-Lanrodec/Page_Simplicit/Rub_Situations contractuelles'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Transfert/GRH-Lanrodec/Page_Simplicit/tab_situations_contractuelles'),
		"Statut de la situation contractuelle",
		"En cours"
	)
	
	WebUI.refresh()
}
WebUI.closeBrowser()