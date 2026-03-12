import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


def data_elem_cas = TestDataFactory.findTestData("check_elem_cas")
def totalRows = data_elem_cas.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CAS-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_elem_cas.getValue("Prénom du jeune", i)
	String situation_patri = data_elem_cas.getValue("Situation matrimoniale", i)
	String nombre_enf = data_elem_cas.getValue("Nombre d'enfants", i)
	String heber_actu = data_elem_cas.getValue("Hébergement actuel", i)
	String type = data_elem_cas.getValue("Type", i)
	String taille_panta = data_elem_cas.getValue("Taille de pantalon", i)
	String pointure = data_elem_cas.getValue("Pointure", i)
	String dossier_handi = data_elem_cas.getValue("Dossier MDPH ?", i)
	String rqth = data_elem_cas.getValue("Reconnaissance TH (RQTH) ?", i)
	String assr = data_elem_cas.getValue("ASSR ?", i)
	String bsr = data_elem_cas.getValue("BSR ?", i)
	String avis = data_elem_cas.getValue("Avis", i)
	String commentaire_gen = data_elem_cas.getValue("Commentaire général", i)
	

CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_RubriquesAtelierAdmission'))

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> panelTitles = driver.findElements(
	By.xpath("//div[contains(@class,'card-header')]//span")
)

List<String> actualTitles = panelTitles.collect { it.getText().trim() }

println "Titres trouvés : $actualTitles"

// Listes de contrôle
List<String> expectedTitles = ["Situation familiale","Hébergement","Habillement", "Administratif","Mobilité"]
List<String> forbiddenTitles = ["Situation médicale","Antécédents","Antécédents gynécologiques","Biométrie","Etat bucco-dentaire"]

CustomKeywords.'Keywords.commun.UtilsEpide.verifyTitles'(actualTitles, expectedTitles, forbiddenTitles)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/CAS/Elements_CAS/Rub_SituationFamiliale'))
WebUI.selectOptionByLabel(findTestObject('Object Repository/CAS/Elements_CAS/select_SituationMatrimoniale'), situation_patri, false)
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/CAS/Elements_CAS/input_NombreEnfants'), nombre_enf)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/CAS/Elements_CAS/Rub_Hebergement'))
WebUI.selectOptionByLabel(findTestObject('Object Repository/CAS/Elements_CAS/select_HebergementActuel'), heber_actu, false)
WebUI.selectOptionByLabel(findTestObject('Object Repository/CAS/Elements_CAS/select_Heber_Type'), type, false)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/CAS/Elements_CAS/Rub_Habillement'))
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/CAS/Elements_CAS/input_Pointure'), pointure)
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/CAS/Elements_CAS/input_TaillePantalon'), taille_panta)


CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/CAS/Elements_CAS/Rub_Administratif'))
def radioMapDossMPDH = [
	"Oui" : 'Object Repository/CAS/Elements_CAS/radio_OUI_DossierMDPH',
	"Non" : 'Object Repository/CAS/Elements_CAS/radio_NON_DossierMDPH'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(dossier_handi, radioMapDossMPDH)

def radioMapRqth = [
	"Oui" : 'Object Repository/CAS/Elements_CAS/radio_OUI_RQTH',
	"Non" : 'Object Repository/CAS/Elements_CAS/radio_NON_RQTH'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(rqth, radioMapRqth)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/CAS/Elements_CAS/Rub_Mobilite'))
def radioMapAssr = [
	"Oui" : 'Object Repository/CAS/Elements_CAS/radio_OUI_ASSR',
	"Non" : 'Object Repository/CAS/Elements_CAS/radio_NON_ASSR'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(assr, radioMapAssr)

def radioMapBsr = [
	"Oui" : 'Object Repository/CAS/Elements_CAS/radio_OUI_BSR',
	"Non" : 'Object Repository/CAS/Elements_CAS/radio_NON_BSR'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(bsr, radioMapBsr)

CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_AvisAteliersAdmission'))
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/button_Creer'))
//
//WebUI.selectOptionByLabel(findTestObject('Object Repository/Recrutement/Commission_Admission/List_Avis'), avis, true)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Recrutement/Commission_Admission/input_CommentaireGeneralAvis'), commentaire_gen)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()

WebUI.refresh()
}
WebUI.closeBrowser()

