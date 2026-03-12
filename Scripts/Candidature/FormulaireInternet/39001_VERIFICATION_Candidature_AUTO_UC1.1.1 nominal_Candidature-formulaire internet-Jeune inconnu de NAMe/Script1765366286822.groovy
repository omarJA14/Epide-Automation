import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Files
import java.nio.file.Paths

import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonOutput


def data_createcandidat = TestDataFactory.findTestData("FormulaireInternet/creation_candidat")
def totalRows = data_createcandidat.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CRV-Bordeaux')


for (int i = 1; i <= totalRows; i++) {
	String nom = data_createcandidat.getValue("Nom", i)
	String prenom = data_createcandidat.getValue("Prénom", i)
	String libelle_voie = data_createcandidat.getValue("Libellé de voie", i)
	String codepostal = data_createcandidat.getValue("Code postal", i)
	String ville = data_createcandidat.getValue("Ville", i)
	String centre_epide_souhaite = data_createcandidat.getValue("Centre EPIDE souhaité", i)
		
	
	CustomKeywords.'Keywords.commun.UtilsEpide.searchJeune'(prenom)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
		findTestObject('Object Repository/CAS/Elements_CAS/table_infos_jeune'),
		"Statut du jeune",
		"Dépôt de candidature"
	)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires'))
	
	def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("area1")
	
	def jsonString = JsonOutput.prettyPrint(JsonOutput.toJson(form))
	
	def outputPath = "C:\\Users\\A689947\\Katalon Studio\\EPIDE-Automation\\Data Files\\formData.json"
	Files.write(Paths.get(outputPath), jsonString.getBytes("UTF-8"))
	println "Données JSON enregistrées dans : $outputPath"
	
	form.each { k, v ->
		println "${k} = ${v}"
	}
	
	def expectedValues = [
		"namStatut"                 : "DEPOTCAN",
		"namJenCntId__namNomCentre" : centre_epide_souhaite,
		"namLibelleVoieAdr"         : libelle_voie,
		"namVilleAdr"               : ville,
		"namQpv"                    : "OUI",
		"namNomQpv"                 : "Basseau - Grande Garenne"
	]
	
	CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form, expectedValues)
		

}
WebUI.closeBrowser()


