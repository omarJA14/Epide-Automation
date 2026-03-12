import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_volontaire = TestDataFactory.findTestData("anime_cof")
def totalRows = data_volontaire.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_volontaire.getValue("Prénom du jeune", i)
	String constat = data_volontaire.getValue("Constat", i)
	String propos_objec = data_volontaire.getValue("Proposition d’objectif", i)
	String moyens = data_volontaire.getValue("Moyens", i)
	String synt_cof1 = data_volontaire.getValue("Synthèse globale 1", i)
	String com_cof1 = data_volontaire.getValue("Commentaire COF1", i)
	String synt_cof2 = data_volontaire.getValue("Synthèse globale 2", i)
	String com_cof2 = data_volontaire.getValue("Commentaire COF2", i)
	String synt_cof3 = data_volontaire.getValue("Synthèse globale 3", i)
	String com_cof3 = data_volontaire.getValue("Commentaire COF3", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_Volontaires'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeuneInRubAndClick'(prenom)
	
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
		
	// ===== COF1 =====
	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF1')
	
	CustomKeywords.'Keywords.commun.UtilsEpide.createAndVerifyAxeTravail'(
			constat,
			propos_objec,
			moyens
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.processCofSynthese'(
			tableObj,
			"COF1",
			findTestObject('Object Repository/COF/Page_COF/input_SyntheseGlobaleCOF1'),
			findTestObject('Object Repository/COF/Page_COF/input_CommentaireCOF1'),
			synt_cof1,
			com_cof1
	)
	
	// ===== COF2 =====
	CustomKeywords.'Keywords.commun.UtilsEpide.processCofSynthese'(
			tableObj,
			"COF2",
			findTestObject('Object Repository/COF/Page_COF/input_SyntheseGlobaleCOF2'),
			findTestObject('Object Repository/COF/Page_COF/input_CommentaireCOF2'),
			synt_cof2,
			com_cof2
	)
	
	// ===== COF3 =====
	CustomKeywords.'Keywords.commun.UtilsEpide.processCofSynthese'(
			tableObj,
			"COF3",
			findTestObject('Object Repository/COF/Page_COF/input_SyntheseGlobaleCOF3'),
			findTestObject('Object Repository/COF/Page_COF/input_CommentaireCOF3'),
			synt_cof3,
			com_cof3
	)
	
	WebUI.closeBrowser()
	
	// Check dans Notification
	CustomKeywords.'Keywords.commun.UtilsEpide.checkCofNotifications'(
		'GRH-Bordeaux',
		'19/12/2025',
		'Compte Rendu du COF',
		'Yasmina',
		['COF1', 'COF2', 'COF3']
	)

}

// Choisir volontaire 
// Vérifier le tableau des COF1 2 3 sont "en préparation"
// Aller dans COF1 -> Axes de travail -> Créer
// Vérifier le domaine (correspond au profil connecté CRV = chargé de recrutement de volontaires
// remplir les champs constat, proposition d'objectif et moyens
// Enregistrer ou faire "Enregistrer et Fermer"
// Vérifier dans Axes de travail, on a bien un tableau avec les elements saisis
// Vérifier Texte Synthese COF1 + remplir Synthese globale et Commentaire COF1 + Enregistrer et checker le statut du COF1 "En cours"
// Appuyer sur le bouton Valider et checker le statut du COF1 "Validé"
// retour au tableau des COF et vérifier que COF1 est passé au statut "validé"
// Aller dans COF2 -> Appuyer sur Animer
// Vérifier présence Texte Synthese COF1 et Synthese COF2 + remplir Synthese globale et Commentaire COF2 + Enregistrer et checker le statut du COF2 "En cours"
// Appuyer sur le bouton Valider et checker le statut du COF2 "Validé"
// retour au tableau des COF et vérifier que COF1 et COF2 sont passés au statut "validé"
// Aller dans COF3 -> Appuyer sur Animer
// Vérifier présence Texte Synthese COF1 et Synthese COF2 et Synthese COF3 + remplir Synthese globale et Commentaire COF3 + Enregistrer et checker le statut du COF3 "En cours"
// Appuyer sur le bouton Valider et checker le statut du COF3 "Validé"
// retour au tableau des COF et vérifier que COF1 et COF2 et COF3 sont passés au statut "validé"
// Aller dans notification et vérifier 