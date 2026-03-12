import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_volontaire = TestDataFactory.findTestData("Cof/anime_cof")
def totalRows = data_volontaire.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')

for (int i = 1; i <= totalRows; i++) {
	
	String prenom = data_volontaire.getValue("Prénom du jeune", i)
	String nom = data_volontaire.getValue("Nom du jeune", i)
	String constat = data_volontaire.getValue("Constat", i)
	String propos_objec = data_volontaire.getValue("Proposition d’objectif", i)
	String moyens = data_volontaire.getValue("Moyens", i)
	String synt_cof1 = data_volontaire.getValue("Synthèse globale 1", i)
	String com_cof1 = data_volontaire.getValue("Commentaire COF1", i)
	String synt_cof2 = data_volontaire.getValue("Synthèse globale 2", i)
	String com_cof2 = data_volontaire.getValue("Commentaire COF2", i)
	String synt_cof3 = data_volontaire.getValue("Synthèse globale 3", i)
	String com_cof3 = data_volontaire.getValue("Commentaire COF3", i)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'(prenom)
	
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
	
	// RETOUR OBLIGATOIRE À LA LISTE COF
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/COF/Page_COF/page_Cof_Volontaire'))
	
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
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofCompteRenduNotificationFromAccueil'(
		"GRH-Bordeaux",
		prenom,
		"Le Compte Rendu du COF est disponible",
		10
	)

}
WebUI.closeBrowser()