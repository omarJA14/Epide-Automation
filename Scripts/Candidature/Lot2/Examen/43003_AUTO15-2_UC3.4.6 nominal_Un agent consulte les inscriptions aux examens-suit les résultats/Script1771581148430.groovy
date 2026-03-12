import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_exam = TestDataFactory.findTestData("Examen/consult_inscription_exam")
def totalRows = data_exam.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CIP-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_exam.getValue("Prénom du jeune", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.AccessInscriptionExamens'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchInRub'("inscription_exam",prenom)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/button_EditerlaListe'))
	
	TestObject element_sais = findTestObject('Object Repository/Examen/Page_Simplicit/input_NbModValid_InscriptionExam')
	
	WebUI.verifyElementNotHasAttribute(element_sais, "readonly", 5)
		
}
WebUI.closeBrowser()