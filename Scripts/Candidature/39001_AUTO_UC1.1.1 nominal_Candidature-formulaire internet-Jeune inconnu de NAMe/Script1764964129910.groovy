import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_createcandidat = TestDataFactory.findTestData("FormulaireInternet/creation_candidat")
def totalRows = data_createcandidat.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_formulaire_internet_epide'()


for (int i = 1; i <= totalRows; i++) {
	
	String nom = data_createcandidat.getValue("Nom", i)
	String prenom = data_createcandidat.getValue("Prénom", i)
	String genre = data_createcandidat.getValue("Genre", i)
	String date_naissance = data_createcandidat.getValue("Date de naissance", i)
	String ville_naissance = data_createcandidat.getValue("Ville de naissance", i)
	String nationalite = data_createcandidat.getValue("Nationalité", i)
	String email = data_createcandidat.getValue("Email", i)
	String email_confirmation = data_createcandidat.getValue("Email de confirmation", i)
	String telephone_portable = data_createcandidat.getValue("Téléphone portable", i)
	String telephone_fixe = data_createcandidat.getValue("Téléphone fixe", i)
	String numero_voie = data_createcandidat.getValue("Numéro de voie", i)
	String libelle_voie = data_createcandidat.getValue("Libellé de voie", i)
	String complement_adresse = data_createcandidat.getValue("Complément d'adresse", i)
	String codepostal = data_createcandidat.getValue("Code postal", i)
	String ville = data_createcandidat.getValue("Ville", i)
	String bac_bp_obtenu = data_createcandidat.getValue("BAC,BP ou BT obtenu ?", i)
	String rais_conn_epide = data_createcandidat.getValue("Comment avez-vous connu l'EPIDE ?", i)
	String centre_epide_souhaite = data_createcandidat.getValue("Centre EPIDE souhaité", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_Nom'), nom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_Prenom'), prenom)
	
	def radioGenreMap = [
		"Homme" : 'Object Repository/formulaire_internet_creation_candidat/radio_Homme_Genre',
		"Femme" : 'Object Repository/formulaire_internet_creation_candidat/radio_Femme_Genre'
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(genre, radioGenreMap)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_DateNaissance'), date_naissance)	

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_VilleNaissance'), ville_naissance)

	def radioNatioMap = [
		"Nationalité Française" : 'Object Repository/formulaire_internet_creation_candidat/radio_Nationalite_NationaliteFranaise',
		"Union Européenne (UE)" : 'Object Repository/formulaire_internet_creation_candidat/radio_Nationalite_UnionEuropeenne',
		"Hors Union Européenne (UE)" : 'Object Repository/formulaire_internet_creation_candidat/radio_Nationalite_HorsUnionEuropeenne',
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(nationalite, radioNatioMap)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_Email'), email)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_EmailConfirmation'), email_confirmation)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_TelephonePortable'), telephone_portable)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_TelephoneFixe'), telephone_fixe)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_NumeroDeVoie'), numero_voie)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_LibelleDeVoie'), libelle_voie)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_ComplementAdresse'), complement_adresse)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_CodePostal'), codepostal)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/formulaire_internet_creation_candidat/input_Ville'), ville)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/formulaire_internet_creation_candidat/button_VerifierAdresse'))
	CustomKeywords.'Keywords.commun.UtilsEpide.selectAddressInDialog'(libelle_voie)
	
	
	//Formation du candidat
	def radiodiplomeMap = [
		"Oui" : 'Object Repository/formulaire_internet_creation_candidat/radio_OUI_BacBPBT',
		"Non" : 'Object Repository/formulaire_internet_creation_candidat/radio_NON_BacBPBT'
	]
	CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'(bac_bp_obtenu, radiodiplomeMap)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.SelectMatOptionByLabel'(
				"Object Repository/formulaire_internet_creation_candidat/select_CommentAvezVousConnuEpide",
				rais_conn_epide
			)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.SelectMatOptionByLabel'(
				"Object Repository/formulaire_internet_creation_candidat/select_CentreEPIDESouhaite",
				centre_epide_souhaite
			)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/formulaire_internet_creation_candidat/Checkbox_AcceptRGPD'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.fillRobotQuestions'()
			
	
}
WebUI.closeBrowser()