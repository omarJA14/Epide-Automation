import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_entretien_fse = TestDataFactory.findTestData("Fse/validg_fse")
def totalRows = data_entretien_fse.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CEC-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
	String ref_oblig_enfre = data_entretien_fse.getValue("Références aux obligations enfreintes - conseil discipline", i)
	String avis_conseil_discip = data_entretien_fse.getValue("Avis du conseil de discipline", i)
	String propos_sanct = data_entretien_fse.getValue("Proposition de sanction", i)

	CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
		'Conseil de discipline', 20)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_ConsDisp_ReferencesauxObligationsEnfreintes'), ref_oblig_enfre)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_AvisduConseildeDiscipline'), avis_conseil_discip)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_proposition_sanction'), propos_sanct, true)
		
	TestObject btn_edit_pv = findTestObject('Object Repository/FSE/Page_Simplicit/button_EditerPV')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_edit_pv, 15)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Soumettre'))
	
	WebUI.delay(5)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationDG'),
		'Validation DG', 20)
	
	TestObject btn_exporter = findTestObject('Object Repository/FSE/Page_Simplicit/button_Exporter')
	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_exporter, 15)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_popup_Exporter'))
	
	WebUI.delay(5)
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyDownloadByFirstname'(prenom, 30)
}
WebUI.closeBrowser()