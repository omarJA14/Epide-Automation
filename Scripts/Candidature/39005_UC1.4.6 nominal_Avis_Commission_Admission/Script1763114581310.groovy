import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


def data_comadmi = TestDataFactory.findTestData("commission_admission")
def totalRows = data_comadmi.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_comadmi.getValue("Prénom du jeune", i)
	String avis = data_comadmi.getValue("Avis", i)
	String date = data_comadmi.getValue("Date", i)
	String commentaire_gen = data_comadmi.getValue("Commentaire général", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_GestionDesCandidats'))
		
	CustomKeywords.'Keywords.commun.UtilsEpide.searchCandidatAndClick'(prenom)
	
	List<TestObject> boutonObjs = [
	    findTestObject('Object Repository/Recrutement/Commission_Admission/button_Admettre'),
	    findTestObject('Object Repository/Recrutement/Commission_Admission/button_Refuser'),
	    findTestObject('Object Repository/Recrutement/Commission_Admission/button_Reporter')
	]
	
	List<String> boutonNames = ['Admettre', 'Refuser', 'Reporter']
	
	CustomKeywords.'Keywords.commun.UtilsEpide.checkButtons'(boutonObjs, boutonNames)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_RubriquesAtelierAdmission'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_AvisAteliersAdmission'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/button_Creer'))
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Recrutement/Commission_Admission/List_Avis'), avis, true)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Recrutement/Commission_Admission/input_CommentaireGeneralAvis'), commentaire_gen)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
	TestObject btnAdmettre = findTestObject('Recrutement/Commission_Admission/button_Admettre')
	
	WebUI.click(btnAdmettre)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyButtonDisappears'(btnAdmettre, 10)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Jeunes'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeune'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/CAS/Elements_CAS/table_infos_jeune'),
		"Statut du jeune",
		"Admissible"
	)
	WebUI.refresh()
}
WebUI.closeBrowser()
