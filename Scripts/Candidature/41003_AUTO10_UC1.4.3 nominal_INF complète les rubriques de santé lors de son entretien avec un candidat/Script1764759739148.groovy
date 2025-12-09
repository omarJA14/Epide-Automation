import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


def data_elem_inf = TestDataFactory.findTestData("check_elem_inf")
def totalRows = data_elem_inf.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('INF-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String prenom = data_elem_inf.getValue("Prénom du jeune", i)
	String medec_trait = data_elem_inf.getValue("Médecin traitant ?", i)
	String pres_carn = data_elem_inf.getValue("Présentation carnet de vaccination/attestation ?", i)
	String ante_ord_fam = data_elem_inf.getValue("Antécédents d'ordre familiaux ? Père, mère, autres (diabète, asthme, allergie, épilepsie)", i)
	String cond_urgence = data_elem_inf.getValue("Avez-vous déjà été conduit aux urgences ?", i)
	String moyen_contra = data_elem_inf.getValue("Utilisez-vous un moyen de contraception ?", i)
	String gyneco = data_elem_inf.getValue("Avez-vous un suivi gynécologique ?", i)
	String taille = data_elem_inf.getValue("Taille (m)", i)
	String poids = data_elem_inf.getValue("Poids (kg)", i)
	String port_lunette = data_elem_inf.getValue("Portez-vous des lunettes ?", i)
	String port_lentille = data_elem_inf.getValue("Portez-vous des lentilles ?", i)
	String derniere_consult_dentaire = data_elem_inf.getValue("Dernière consultation dentaire", i)
	String necessite_consult_dentaire = data_elem_inf.getValue("Nécessite une consultation dentaire ?", i)
	String avis = data_elem_inf.getValue("Avis", i)
	String commentaire_gen = data_elem_inf.getValue("Commentaire général", i)
	
CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneAndClick'(prenom)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_RubriquesAtelierAdmission'))

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> panelTitles = driver.findElements(
	By.xpath("//div[contains(@class,'card-header')]//span")
)

List<String> actualTitles = panelTitles.collect { it.getText().trim() }

println "Titres trouvés : $actualTitles"

// Listes de contrôle
List<String> expectedTitles = ["Situation médicale","Antécédents","Antécédents gynécologiques","Biométrie","Acuité visuelle","Etat bucco-dentaire"]
List<String> forbiddenTitles = ["Situation familiale","Hébergement","Administratif"]

CustomKeywords.'Keywords.commun.UtilsEpide.verifyTitles'(actualTitles, expectedTitles, forbiddenTitles)
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_SituationMedicale'))
//
def radioMapMT = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_MedecinTraitant',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_MedecinTraitant'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(medec_trait, radioMapMT)

def radioMapPresCar = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_PresentationCarnetVaccinationAttestation',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_PresentationCarnetVaccinationAttestation'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(pres_carn, radioMapPresCar)

//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_Antecedents'))
//
def radioMapAntOrdreFam = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_AntecedentsFamiliaux',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_AntecedentsFamiliaux'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(ante_ord_fam, radioMapAntOrdreFam)

def radioMapUrgence = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_Avez-vousDejaEtEconduitAuxUrgences',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_Avez-vousDejaEtEconduitAuxUrgences'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(cond_urgence, radioMapUrgence)
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_AntecedentsGynecologiques'))
//
def radioMapMoyContr = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_Utilisez-vousUnMoyenDeContraception',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_Utilisez-vousUnMoyenDeContraception'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(moyen_contra, radioMapMoyContr)

def radioMapSuiviGynec = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_Avez-vousUnSuiviGynecologique',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_Avez-vousUnSuiviGynecologique'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(gyneco, radioMapSuiviGynec)
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_Biometrie'))
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/INF/Elements_INF/input_B_Taille'), taille)
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/INF/Elements_INF/input_B_Poids'), poids)
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_AcuiteVisuelle'))
//
def radioMapPortLune = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_PorteLunette',
	"Non" : 'Object Repository/INF/Page_Simplicit/Radio_NON_PorteLunette'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(port_lunette, radioMapPortLune)

def radioMapPortLent = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_PorteLentilles',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_PorteLentilles'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(port_lentille, radioMapPortLent)
//
CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/INF/Elements_INF/Rub_EtatBucco-Dentaire'))
//
WebUI.selectOptionByLabel(findTestObject('Object Repository/INF/Elements_INF/select_DerniereConsultationDentaire'), derniere_consult_dentaire, false)

def radioMapConsDent = [
	"Oui" : 'Object Repository/INF/Page_Simplicit/radio_OUI_NecessiteUneConsultationDentaire',
	"Non" : 'Object Repository/INF/Page_Simplicit/radio_NON_NecessiteUneConsultationDentaire'
]
CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(necessite_consult_dentaire, radioMapConsDent)

CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/Access_AvisAteliersAdmission'))

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Recrutement/Commission_Admission/button_Creer'))

WebUI.selectOptionByLabel(findTestObject('Object Repository/Recrutement/Commission_Admission/List_Avis'), avis, true)

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Recrutement/Commission_Admission/input_CommentaireGeneralAvis'), commentaire_gen)

CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
WebUI.refresh()
}
WebUI.closeBrowser()

