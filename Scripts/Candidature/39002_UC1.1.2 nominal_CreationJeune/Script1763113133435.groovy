import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


def data_createjeune = TestDataFactory.findTestData("creation_jeune")
def totalRows = data_createjeune.getRowNumbers()

String premierProfil = data_createjeune.getValue("Profil_saisi", 1)
CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'(premierProfil)
GlobalVariable.profilCourant = premierProfil

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	String profilencours  = data_createjeune.getValue("Profil_saisi", i)
	
	if (GlobalVariable.profilCourant == profilencours) {
		WebUI.comment("Même profil → rafraîchissement de la page")
		WebUI.refresh()
	} else {
		WebUI.comment("Nouveau profil → fermeture et relance du navigateur")
		WebUI.closeBrowser()
		CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'(profilencours)
		GlobalVariable.profilCourant = profilencours
	}
	
	String statut_candidat = data_createjeune.getValue("Statut de la candidature", i)
	String centre_epide = data_createjeune.getValue("Centre EPIDE", i)
	String nom = data_createjeune.getValue("Nom", i)
	String prenom = data_createjeune.getValue("Prénom", i)
	String login_espace = data_createjeune.getValue("Login Espace Jeune", i)
	String ville_naissance = data_createjeune.getValue("Ville de naissance", i)
	String validationcp = data_createjeune.getValue("Validation code postal", i)	
	String date_naissance = data_createjeune.getValue("Date de naissance", i)
	String genre = data_createjeune.getValue("Genre", i)
	String codepostal_naissance = data_createjeune.getValue("Code postal de naissance", i)
	String nationalite = data_createjeune.getValue("Nationalité", i)
	String pays_residence = data_createjeune.getValue("Pays de résidence", i)
	String email = data_createjeune.getValue("Email", i)
	String email_confirmation = data_createjeune.getValue("Email confirmation", i)
	String telephone_portable = data_createjeune.getValue("Téléphone portable", i)
	String telephone_fixe = data_createjeune.getValue("Téléphone fixe", i)
	String numero_voie = data_createjeune.getValue("Numéro de voie", i)
	String libelle_voie = data_createjeune.getValue("Libellé de voie", i)
	String validationadressenaissance = data_createjeune.getValue("Validation adresse de naissance", i)	
	String complement_adresse = data_createjeune.getValue("Complément d'adresse", i)
	String codepostal = data_createjeune.getValue("Code postal", i)
	String ville = data_createjeune.getValue("Ville", i)
	String nom_banque = data_createjeune.getValue("Nom de la banque", i)
	String dom_banc_horszonesepa = data_createjeune.getValue("Domiciliation bancaire hors zone SEPA", i)
	String bic = data_createjeune.getValue("BIC", i)
	String iban = data_createjeune.getValue("IBAN", i)
	String bac_bp_obtenu = data_createjeune.getValue("BAC,BP ou BT obtenu", i)
	String diplome_eleve_obt = data_createjeune.getValue("Diplome le plus élevé obtenu", i)
	String derniere_classe_frequente = data_createjeune.getValue("Dernière classe fréquentée", i)
	String dernier_etab_sco_frequente = data_createjeune.getValue("Dernier établissement scolaire fréquenté", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_GestionDesCandidats'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_Candidatures'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Button_Creer_Candidatures'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_NomCandidat'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Button_Creer_JeuneC'))
			
	
	//centre epide -- Selectionner Centre A gérer plutard dans d'autres tests
	
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_NomJeune'), nom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_PrenomJeune'), prenom)
	//Login Espace Jeune est grisé
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_VilleDeNaissanceJeune'), ville_naissance)	

	TestObject dateField = findTestObject('Object Repository/Epide_Candidature/Candidatures/input_DateDeNaissanceJeune')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(dateField, date_naissance)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Epide_Candidature/Candidatures/select_Genre'), genre, true);
	
	//ignorer le remplissage du code postal en raison de la validation par la popup
	//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_CodePostalNaissance'), codepostal_naissance)
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Epide_Candidature/Candidatures/Select_Nationalite'), nationalite, false) 
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_PaysDeResidence'), pays_residence)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_EmailJeune'), email)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_EmailConfirmationJeune'), email_confirmation)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_TelephonePortableJeune'), telephone_portable)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_TelephoneFixeJeune'), telephone_fixe)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectInPopupWithOK'(
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Button_RechercherCodePostalNaissance'),
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Valider_CodePostalNaissance_Popup'),
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Text_Rechercher CodePostalNaissance'),
    [ville_naissance],
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Button_OK_CP')
	)
	
	println("Remplissage de rubrique atelier admission")
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_RubriquesAtelierAdmission'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_NumeroDeVoieJeuneRAA'), numero_voie)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_LibelleDeVoieJeuneRAA'), libelle_voie)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_ComplementAdresseJeuneRAA'), complement_adresse)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_CodePostalJeuneRAA'), codepostal)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_VilleRAA'), ville)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectInPopupWithOK'(
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Button_VerifierAdresse'),
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Valider_Adresse_Popup'),
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Text_VerificationAdresse'),
    [libelle_voie, ville],
    findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Button_OK_VerifAdress')
	)
	
	println("Remplissage de rubrique coordonnées bancaires")
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_CoordonneesBancaires'))
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_NomDeLaBanqueCB'), nom_banque)
			
	def radioMap = [
		"Oui" : 'Object Repository/Epide_Candidature/Candidatures/Radio_Oui_DomiciliationBancaireHorsZoneSEPA',
		"Non" : 'Object Repository/Epide_Candidature/Candidatures/Radio_Non_DomiciliationBancaireHorsZoneSEPA'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(dom_banc_horszonesepa, radioMap)
			
			
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_BIC_CB'), bic)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_IBAN_CB'), iban)
	
	println("Remplissage de rubrique parcours scolaire")
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Access_ParcoursScolaire'))
			
	def choixdiplomeMap = [
		"Oui" : 'Object Repository/Epide_Candidature/Candidatures/Radio_Oui_BAC-BP-BTObtenu',
		"Non" : 'Object Repository/Epide_Candidature/Candidatures/Radio_Non_BAC-BP-BTObtenu'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(bac_bp_obtenu, choixdiplomeMap)
			
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Epide_Candidature/Candidatures/Select_DiplomePlusEleveObtenu'), diplome_eleve_obt, false)
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Epide_Candidature/Candidatures/Select_DerniereClasseFrequentePS'), derniere_classe_frequente, false)
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Epide_Candidature/Candidatures/input_DernierEtablissementScolaireFrequente'), dernier_etab_sco_frequente)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/button_EnregistrerFermer'))	
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
		
	CustomKeywords.'Keywords.commun.UtilsEpide.searchCandidat'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/table_candidats'),
		"Etat de la candidature",
		"Nouvelle candidature"
	)
	
}
WebUI.closeBrowser()