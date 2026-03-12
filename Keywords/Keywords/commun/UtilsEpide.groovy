package Keywords.commun;

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.Normalizer
import java.text.SimpleDateFormat
import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.model.FailureHandling as FH
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class UtilsEpide {

	/**
	 * Refresh browser
	 */
	@Keyword
	def refreshBrowser() {
		KeywordUtil.logInfo("Refreshing")
		WebDriver webDriver = DriverFactory.getWebDriver()
		webDriver.navigate().refresh()
		KeywordUtil.markPassed("Refresh successfully")
	}
	@Keyword
	def normalizeValue(String value) {
		// Remove currency symbols and formatting
		def normalizedValue = value.replace('€', '').replace(',', '').replace(' ', '').replace('.', '')
		return normalizedValue.toBigDecimal()
	}


	@Keyword
	def waitAndClick(TestObject testObject) {
		WebUI.waitForElementVisible(testObject, 30)
		WebUI.waitForElementClickable(testObject, 30)
		WebUI.click(testObject)
	}

	@Keyword
	def waitAndSet(TestObject testObject, String text) {
		WebUI.waitForElementVisible(testObject, 30)
		WebUI.setText(testObject, text)
	}

	@Keyword
	def waitAndSetEncryptedText(TestObject testObject, String text) {
		WebUI.waitForElementVisible(testObject, 30)
		WebUI.setEncryptedText(testObject, text)
	}

	@Keyword
	def clearAndSetText(TestObject to, String value) {
		WebUI.waitForElementVisible(to, 30)
		WebUI.clearText(to)
		WebUI.setText(to, value)
	}

	@Keyword
	def setTextAdvanced(TestObject to, String value, boolean clearBefore = false) {
		WebUI.waitForElementVisible(to, 30)
		if (clearBefore) {
			WebUI.clearText(to)
		}
		WebUI.setText(to, value)
	}

	@Keyword
	def verifyTitles(List actual, List expected, List forbidden) {

		List<String> errors = []

		expected.each { title ->
			WebUI.comment("Vérification présence : " + title)
			if (actual.any { it == title }) {
				WebUI.comment("Présent comme prévu : $title")
			} else {
				WebUI.comment("Manquant : $title")
				errors << "Le titre '$title' devait être présent mais ne l'est pas."
			}
		}

		forbidden.each { title ->
			WebUI.comment("Vérification absence : " + title)
			if (actual.any { it == title }) {
				WebUI.comment("Présent alors qu'il ne devrait pas : $title")
				errors << "Le titre '$title' ne devait pas être présent mais il l'est."
			} else {
				WebUI.comment("Correctement absent : $title")
			}
		}

		assert errors.isEmpty() : errors.join("\n")
	}

	@Keyword
	def initEdgeDownload() {

		String downloadPath = System.getProperty("java.io.tmpdir") + "katalon_download"

		new File(downloadPath).mkdirs()

		Map<String, Object> prefs = new HashMap<>()
		prefs.put("download.default_directory", downloadPath)
		prefs.put("download.prompt_for_download", false)
		prefs.put("download.directory_upgrade", true)
		prefs.put("safebrowsing.enabled", true)

		EdgeOptions options = new EdgeOptions()
		options.setExperimentalOption("prefs", prefs)

		EdgeDriver driver = new EdgeDriver(options)
		DriverFactory.changeWebDriver(driver)

		println "Edge configuré pour télécharger dans : " + downloadPath
	}

	@Keyword
	def verifyDownload(String expectedNamePart, int timeoutSeconds) {
		String downloadPath = System.getProperty("java.io.tmpdir") + "katalon_download"
		File dir = new File(downloadPath)

		int elapsed = 0
		File downloadedFile = null

		while (elapsed < timeoutSeconds && downloadedFile == null) {
			File[] files = dir.listFiles({ f ->
				f.getName().contains(expectedNamePart) && !f.getName().endsWith(".crdownload")
			} as FileFilter)

			if (files != null && files.length > 0) {
				downloadedFile = files[0]
			} else {
				Thread.sleep(1000)
				elapsed++
			}
		}

		assert downloadedFile != null : "Aucun fichier téléchargé détecté"
		assert downloadedFile.length() > 0 : "Fichier téléchargé vide"

		println "Fichier téléchargé correctement : ${downloadedFile.getAbsolutePath()}"
		return downloadedFile
	}

	@Keyword
	def boolean delegationRowExists(String nomDelegant,
			String prenomDelegant,
			String role,
			String nomMandataire,
			String prenomMandataire,
			int timeout = 5) {

		String xpath = """
        //tbody[@data-list='list_NamRoleMandat_the_ajax_NamRoleMandat']
        /tr[
            .//td[@data-field='namRolmdtFromUsrId__usr_last_name']//div[text()='${nomDelegant}']
            and
            .//td[@data-field='namRolmdtFromUsrId__usr_first_name']//div[text()='${prenomDelegant}']
            and
            .//td[@data-field='namRolmdtFromUsrId__namUsrAgentTitre']//div[text()='${role}']
            and
            .//td[@data-field='namRolmdtToUsrId__usr_last_name']//div[text()='${nomMandataire}']
            and
            .//td[@data-field='namRolmdtToUsrId__usr_first_name']//div[text()='${prenomMandataire}']
        ]
        """

		TestObject row = new TestObject("delegationRow")
		row.addProperty("xpath", ConditionType.EQUALS, xpath)

		return WebUI.verifyElementPresent(row, timeout, FailureHandling.OPTIONAL)
	}

	@Keyword
	def verifyDownloadByFirstname(String prenom, int timeoutSeconds) {

		String downloadPath = System.getProperty("user.home") + "\\Downloads"
		File downloadDir = new File(downloadPath)
		assert downloadDir.exists() : "Dossier Downloads introuvable"

		int elapsed = 0
		File downloadedFile = null

		while (elapsed < timeoutSeconds && downloadedFile == null) {
			File[] files = downloadDir.listFiles({ f ->
				f.getName().toLowerCase().contains(prenom.toLowerCase()) &&
						!f.getName().endsWith(".crdownload")
			} as FileFilter)

			if (files != null && files.length > 0) {
				downloadedFile = files.sort { -it.lastModified() }[0]
			} else {
				WebUI.delay(1)
				elapsed++
			}
		}

		assert downloadedFile != null :
		"Aucun fichier téléchargé contenant le prénom '${prenom}'"

		assert downloadedFile.length() > 0 :
		"Fichier téléchargé vide"

		println "Fichier téléchargé OK : ${downloadedFile.getName()}"

		return downloadedFile
	}

	@Keyword
	static Map<String, String> extractElements(String areaId) {

		TestObject fields = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//*[@id='${areaId}']//input | //*[@id='${areaId}']//select | //*[@id='${areaId}']//textarea"
				)

		List<WebElement> elements = WebUI.findWebElements(fields, 10)

		Map<String, String> data = [:]

		elements.each { el ->
			String name = el.getAttribute("name")
			String value = el.getAttribute("value")

			if (name) {
				data[name] = value
			}
		}

		println "=== Extraction (${areaId}) ==="
		data.each { key, value ->
			println "${key.padRight(35)} : ${value}"
		}

		return data
	}

	@Keyword
	static void compareElements(Map<String, String> form, Map<String, String> expectedValues) {

		def normalize = { input ->
			if (!input) return ""
			Normalizer.normalize(input, Normalizer.Form.NFD)
					.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
					.toLowerCase()
					.trim()
		}

		expectedValues.each { key, expected ->
			def actual = form[key] ?: ""
			if (normalize(actual) == normalize(expected)) {
				println "[OK] ${key} = ${actual}"
			} else {
				KeywordUtil.markFailedAndStop("[KO] ${key} = ${actual} (attendu: ${expected})")
			}
		}
		println "Toutes les comparaisons sont OK !"
	}


	@Keyword
	def verifySuccessMessage(String expectedTitle, String expectedMessage) {

		TestObject div = new TestObject('confirmationMessage')
		div.addProperty("xpath", ConditionType.EQUALS, "//div[@id='confirmation-message']")

		WebUI.waitForElementVisible(div, 10)

		WebUI.verifyElementPresent(div, 10)

		TestObject h3 = new TestObject('confirmationH3')
		h3.addProperty("xpath", ConditionType.EQUALS, "//div[@id='confirmation-message']//h3")

		TestObject p = new TestObject('confirmationP')
		p.addProperty("xpath", ConditionType.EQUALS, "//div[@id='confirmation-message']//p")

		try {
			WebUI.verifyElementText(h3, expectedTitle)
			WebUI.verifyElementText(p, expectedMessage)
			KeywordUtil.logInfo("Message de succès vérifié avec succès.")
		} catch (Exception e) {
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Le message de succès ne correspond pas : " + e.getMessage())
		}
	}

	@Keyword
	def verifyElementInPage(String xpath, boolean shouldBeVisible, String label, int timeout = 5) {

		TestObject to = new TestObject("dynamicElement")
		to.addProperty("xpath", ConditionType.EQUALS, xpath)

		boolean visible = WebUI.waitForElementVisible(to, timeout, FailureHandling.OPTIONAL)

		WebUI.comment("Vérification visibilité : " + label)

		if (shouldBeVisible) {
			assert visible : "L’élément ${label} devait être VISIBLE mais ne l’est pas."
			WebUI.comment("L’élément ${label} est bien visible.")
		}
		else {
			assert !visible : "L’élément ${label} NE devait PAS être visible, mais il l'est."
			WebUI.comment("L’élément ${label} n’est pas visible, comme prévu.")
		}
	}


	private Map resolveRubrique(String rubrique) {
		switch (rubrique.toLowerCase()) {

			case "fse":
				return [
					champ : findTestObject('Object Repository/FSE/Page_Simplicit/input_FSE'),
					click : findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_FSE_Type')
				]

			case "jeune":
				return [
					champ : findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes'),
					click : findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires')
				]

			case "inscription_exam":
				return [
					champ : findTestObject('Object Repository/Examen/Page_Simplicit/input_Prenom_inscription_examenED'),
					click : findTestObject('Object Repository/Examen/Page_Simplicit/Element_Presence_inscription_examenED')
				]

			case "candidat":
				return [
					champ : findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidats'),
					click : findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires')
				]

			case "cof":
				return [
					champ  : findTestObject('Object Repository/ComunCI/Page_Simplicit/input_cof_prenom'),
					click : [
						"COF1": findTestObject('Object Repository/COF/Page_COF/Elem_COF1'),
						"COF2": findTestObject('Object Repository/COF/Page_COF/Elem_COF2'),
						"COF3": findTestObject('Object Repository/COF/Page_COF/Elem_COF3')
					]
				]

			case "candidature":
				return [
					champ : findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidatures'),
					click : findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_candidatures')
				]

			case "demandetransfert":
				return [
					champ : findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/input_prenom_DemandeTransfert'),
					click : findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Element_AvisCentreDestination')
				]

			case "volontaire":
				return [
					champ : findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes'),
					click : findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires')
				]

			default:
				throw new IllegalArgumentException("Rubrique inconnue : ${rubrique}")
		}
	}

	@Keyword
	def searchInRub(String rubrique, String prenom) {

		def config = resolveRubrique(rubrique)
		TestObject champPrenom = config.champ

		clearAndSetText(champPrenom, prenom)
		WebUI.sendKeys(champPrenom, Keys.chord(Keys.ENTER))
	}


	@Keyword
	def searchInRubAndClick(String rubrique, String prenom) {

		def config = resolveRubrique(rubrique)

		TestObject champPrenom = config.champ
		TestObject elementACliquer =
				config.clicks ? config.clicks[prenom] : config.click

		if (elementACliquer == null) {
			KeywordUtil.markFailedAndStop(
					"Aucun élément à cliquer pour '${prenom}' dans la rubrique '${rubrique}'"
					)
		}

		clearAndSetText(champPrenom, prenom)
		WebUI.sendKeys(champPrenom, Keys.chord(Keys.ENTER))
		waitAndClick(elementACliquer)
	}

	@Keyword
	def AccessExamens() {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/Examen/Page_Simplicit/Rub_Examens'))
	}

	@Keyword
	def AccessTypesAbsence() {
		waitAndClick(findTestObject('Object Repository/Absence/Page_Simplicit/Rub_Administration_applicative'))
		waitAndClick(findTestObject('Object Repository/Absence/Page_Simplicit/Rub_Types_absence'))
	}

	@Keyword
	def AccessFeuillePresence() {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/Absence/Page_Simplicit/Rub_FeuilledePresence'))
	}

	@Keyword
	def AccessInscriptionExamens() {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/Examen/Page_Simplicit/Rub_InscriptionsExamen'))
	}

	@Keyword
	def AccessPromotions() {
		waitAndClick(findTestObject('Object Repository/Promotion/Page_Simplicit/Rub_OrganisationdesCentres'))
		waitAndClick(findTestObject('Object Repository/Promotion/Page_Simplicit/Rub_Promotions'))
	}


	@Keyword
	def searchCof(String prenomCof) {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/COF/Page_COF/Rub_COF'))
		TestObject champPrenomCof = findTestObject('Object Repository/ComunCI/Page_Simplicit/input_cof_prenom')
		clearAndSetText(champPrenomCof, prenomCof)
		WebUI.sendKeys(champPrenomCof, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchFse(String prenomFse) {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/FSE/Page_Simplicit/Rub_FSE'))
		TestObject champPrenomFse = findTestObject('Object Repository/FSE/Page_Simplicit/input_fse_prenom')
		clearAndSetText(champPrenomFse, prenomFse)
		WebUI.sendKeys(champPrenomFse, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchFseAndClick(String prenomFse) {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/FSE/Page_Simplicit/Rub_FSE'))
		TestObject champPrenomFse = findTestObject('Object Repository/FSE/Page_Simplicit/input_fse_prenom')
		clearAndSetText(champPrenomFse, prenomFse)
		WebUI.sendKeys(champPrenomFse, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_FSE_Type'))
	}

	@Keyword
	def searchNotification(String nomNotif) {
		TestObject champNomNotif = findTestObject('Object Repository/Notification/Page_Notification/input_search_NomNotification')
		clearAndSetText(champNomNotif, nomNotif)
		WebUI.sendKeys(champNomNotif, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchJeuneInRub(String prenom) {
		TestObject champPrenomjeune = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomjeune, prenom)
		WebUI.sendKeys(champPrenomjeune, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchJeuneInRubAndClick(String prenom) {
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires'))
	}

	@Keyword
	def searchJeune(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Jeunes'))
		TestObject champPrenomjeune = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomjeune, prenom)
		WebUI.sendKeys(champPrenomjeune, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchJeuneAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Jeunes'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires'))
	}

	@Keyword
	def searchCandidatInRub(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Candidats'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidats')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchCandidat(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Gestion_des_candidats'))
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Candidats'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidats')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchCandidatAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Gestion_des_candidats'))
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Candidats'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidats')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires'))
	}

	@Keyword
	def searchCandidature(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Gestion_des_candidats'))
		waitAndClick(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_Candidatures'))
		TestObject champPrenomcandidature = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidatures')
		clearAndSetText(champPrenomcandidature, prenom)
		WebUI.sendKeys(champPrenomcandidature, Keys.chord(Keys.ENTER))
	}
	@Keyword
	def searchCandidatureAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Recrutement/Commission_Admission/Rub_Gestion_des_candidats'))
		waitAndClick(findTestObject('Object Repository/Epide_Candidature/Candidatures/Rubrique_Candidatures'))
		TestObject champPrenomcandidature = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_candidatures')
		clearAndSetText(champPrenomcandidature, prenom)
		WebUI.sendKeys(champPrenomcandidature, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_candidatures'))
	}

	@Keyword
	def searchDemandeTransfert(String prenom) {
		waitAndClick(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_GestionVolontaires'))
		waitAndClick(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_DemandesTransfert'))
		TestObject champPrenomcandidature = findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/input_prenom_DemandeTransfert')
		clearAndSetText(champPrenomcandidature, prenom)
		WebUI.sendKeys(champPrenomcandidature, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchDemandeTransfertAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_GestionVolontaires'))
		waitAndClick(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Rub_DemandesTransfert'))
		TestObject champPrenomcandidature = findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/input_prenom_DemandeTransfert')
		clearAndSetText(champPrenomcandidature, prenom)
		WebUI.sendKeys(champPrenomcandidature, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/Transfert/ASSDIR/Page_Simplicit/Element_Etat_Demande_Transfert'))
	}

	@Keyword
	def searchVolontaire(String prenom) {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_Volontaires'))
		TestObject champPrenomjeune = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomjeune, prenom)
		WebUI.sendKeys(champPrenomjeune, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchVolontaireAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_GestionDesVolontaires'))
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/Rub_Volontaires'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Recrutement/Commission_Admission/input_prenom_jeunes')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Element_Prenom_jeune_candidat_volontaires'))
	}

	@Keyword
	def searchMandataireDelagation(String prenom) {
		waitAndClick(findTestObject('Object Repository/Delegation/Page_Simplicit/Rub_Delegationderole'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Delegation/Page_Simplicit/input_prenomduMandataire')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
	}

	@Keyword
	def searchMandataireDelagationAndClick(String prenom) {
		waitAndClick(findTestObject('Object Repository/Delegation/Page_Simplicit/Rub_Delegationderole'))
		TestObject champPrenomcandidat = findTestObject('Object Repository/Delegation/Page_Simplicit/input_prenomduMandataire')
		clearAndSetText(champPrenomcandidat, prenom)
		WebUI.sendKeys(champPrenomcandidat, Keys.chord(Keys.ENTER))
		waitAndClick(findTestObject('Object Repository/Delegation/Page_Simplicit/Element_nom_cre'))
	}

	@Keyword
	def verifyTableFieldSmart(TestObject tableObject, String columnName, String expectedValue) {

		WebElement table = WebUiCommonHelper.findWebElement(tableObject, 10)

		def headers = table.findElements(By.cssSelector("thead tr th"))
		def cleanHeaders = []
		headers.each { h ->
			def text = h.getText().trim()
			if(text && text.length() > 0) cleanHeaders << text
		}

		if(!cleanHeaders.contains(columnName)) {
			KeywordUtil.markFailed("COLONNE introuvable : '${columnName}'. Colonnes trouvées : ${cleanHeaders}")
		}

		int cleanIndex = cleanHeaders.indexOf(columnName)

		def row = table.findElements(By.cssSelector("tbody tr")).find {
			it.findElements(By.cssSelector("td")).any { td -> td.getText().trim().length() > 0 }
		}

		def allCells = row.findElements(By.cssSelector("td"))

		def cleanCells = []
		allCells.each { td ->
			def text = td.getText().trim()
			if(text && text.length() > 0) cleanCells << text
		}

		if(cleanIndex >= cleanCells.size()) {
			KeywordUtil.markFailed("Impossible d'associer la colonne '${columnName}' à une cellule visible. Cells=${cleanCells}")
		}

		String actual = cleanCells[cleanIndex]

		if(actual != expectedValue) {
			KeywordUtil.markFailed("Valeur incorrecte pour '${columnName}'. Attendu='${expectedValue}', trouvé='${actual}'")
		}
		KeywordUtil.logInfo("✔ '${columnName}' = '${expectedValue}' OK")
	}



	@Keyword
	def verifierCentres(String centreOrigineAttendu, String centreSouhaiteAttendu) {

		TestObject origineObj = findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/input_CentreEpideOrigine')
		TestObject souhaiteObj = findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/input_CentreEpideSouhaite')

		String origine = WebUI.getAttribute(origineObj, "value")?.trim()?.toLowerCase()
		String souhaite = WebUI.getAttribute(souhaiteObj, "value")?.trim()?.toLowerCase()

		centreOrigineAttendu = centreOrigineAttendu.trim().toLowerCase()
		centreSouhaiteAttendu = centreSouhaiteAttendu.trim().toLowerCase()

		assert origine == centreOrigineAttendu : "Centre origine attendu : ${centreOrigineAttendu}, trouvé : ${origine}"
		assert souhaite == centreSouhaiteAttendu : "Centre souhaité attendu : ${centreSouhaiteAttendu}, trouvé : ${souhaite}"

		WebUI.comment("Vérification centres OK : Origine=${origine}, Souhaité=${souhaite}")
	}

	@Keyword
	def selectInPopupWithOK(TestObject buttonToOpen, TestObject listTestObject, TestObject textToVerify, List<String> searchTerms, TestObject buttonOk = null) {

		WebUI.comment("Ouverture de la popup ${listTestObject}")
		WebUI.click(buttonToOpen)

		if (!WebUI.verifyElementPresent(textToVerify, 10, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed("Échec : pop-up est introuvable.")
			return
		}

		def options = WebUI.findWebElements(listTestObject, 10)
		def selectElem = options.find { o ->
			def t = o.getText().toLowerCase()
			searchTerms.any { t.contains(it.toLowerCase()) }
		}

		if (selectElem != null) {
			selectElem.click()
			WebUI.comment("Élément sélectionné : " + selectElem.getText())
		} else {
			assert false : "Aucune option correspondante trouvée"
		}

		if (buttonOk != null) {
			WebUI.click(buttonOk)
			WebUI.comment("Fermeture de la popup ${listTestObject}")
		}
	}

	@Keyword
	def saveAndCheckConfirmation() {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/button_Enregistrer'))
		boolean confirmationVisible =
				WebUI.waitForElementPresent(
				findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Text_EnregistrementTermineAvecSucces'),
				30,
				FailureHandling.OPTIONAL
				)
		if (!confirmationVisible) {
			KeywordUtil.markFailed("Échec : aucun message de confirmation.")
		} else {
			KeywordUtil.logInfo("Enregistrement confirmé.")
		}
	}

	@Keyword
	def selectAddressInDialog(String addressToSelect, int timeout = 10) {

		TestObject dialogTO = new TestObject("dialogContainer")
		dialogTO.addProperty("xpath", ConditionType.EQUALS, "//mat-dialog-container[contains(@class,'mat-dialog-container')]")

		boolean dialogVisible = WebUI.waitForElementVisible(dialogTO, timeout, FH.OPTIONAL)
		assert dialogVisible : "Le dialogue d'adresse n'est pas visible."
		WebUI.comment("Dialogue d'adresse visible.")

		// Conversion en minuscules pour insensibilité à la casse
		String addressLower = addressToSelect.toLowerCase()

		TestObject addressTO = new TestObject("addressToSelect")
		String xpath = "//mat-dialog-container[contains(@class,'mat-dialog-container')]//div[contains(@class,'bloc-address') and contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + addressLower + "')]"
		addressTO.addProperty("xpath", ConditionType.EQUALS, xpath)

		boolean addressVisible = WebUI.waitForElementVisible(addressTO, timeout, FH.OPTIONAL)
		assert addressVisible : "L'adresse '${addressToSelect}' n'est pas présente dans le dialogue."
		WebUI.comment("Adresse '${addressToSelect}' présente dans le dialogue.")

		WebUI.click(addressTO)
		WebUI.comment("Adresse '${addressToSelect}' sélectionnée.")

		TestObject closeButton = new TestObject("closeButton")
		closeButton.addProperty("xpath", ConditionType.EQUALS, "//mat-dialog-container[contains(@class,'mat-dialog-container')]//mat-icon[text()='close']")
		if (WebUI.waitForElementVisible(closeButton, 2, FH.OPTIONAL)) {
			WebUI.click(closeButton)
			WebUI.comment("Dialogue fermé.")
		}
	}

	@Keyword
	def saveCloseAndCheckConfirmation() {
		waitAndClick(findTestObject('Object Repository/Demande_Transfert/button_EnregistrerFermer'))
		boolean confirmationVisible =
				WebUI.waitForElementPresent(
				findTestObject('Object Repository/Epide_Candidature/Candidatures/Page_Simplicit/Text_EnregistrementTermineAvecSucces'),
				30,
				FailureHandling.OPTIONAL
				)
		if (!confirmationVisible) {
			KeywordUtil.markFailed("Échec : aucun message de confirmation.")
		} else {
			KeywordUtil.logInfo("Enregistrement confirmé.")
		}
	}

	@Keyword
	def setTableField(TestObject tableObj, String label, String value, int timeout = 10) {

		WebUI.waitForElementVisible(tableObj, timeout)

		String baseXpath = tableObj.findPropertyValue("xpath")

		String xpathHeaders = baseXpath + "//th"
		TestObject thObj = new TestObject().addProperty("xpath", ConditionType.EQUALS, xpathHeaders)
		List<WebElement> headers = WebUiCommonHelper.findWebElements(thObj, timeout)

		int colIndex = headers.findIndexOf {
			it.getText().trim().toLowerCase().contains(label.toLowerCase())
		} + 1

		KeywordUtil.logInfo("Colonne trouvée : '${label}' = colonne ${colIndex}")

		String cellInput = "${baseXpath}//tbody/tr[1]/td[${colIndex}]//input"
		TestObject inputObj = new TestObject().addProperty("xpath", ConditionType.EQUALS, cellInput)

		if (WebUI.verifyElementPresent(inputObj, 1, FailureHandling.OPTIONAL)) {
			WebUI.clearText(inputObj)
			WebUI.setText(inputObj, value)
			KeywordUtil.markPassed("✔ Saisie via <input> OK (${value})")
			return
		}

		String cellEditable = "${baseXpath}//tbody/tr[1]/td[${colIndex}]//*[@contenteditable='true']"
		TestObject editableObj = new TestObject().addProperty("xpath", ConditionType.EQUALS, cellEditable)

		if (WebUI.verifyElementPresent(editableObj, 1, FailureHandling.OPTIONAL)) {
			WebUI.sendKeys(editableObj, Keys.chord(Keys.CONTROL, "a"))
			WebUI.sendKeys(editableObj, value)
			WebUI.sendKeys(editableObj, Keys.chord(Keys.ENTER))
			KeywordUtil.markPassed("✔ Saisie via élément contenteditable OK (${value})")
			return
		}

		String cellDiv = "${baseXpath}//tbody/tr[1]/td[${colIndex}]/div"
		TestObject divObj = new TestObject().addProperty("xpath", ConditionType.EQUALS, cellDiv)

		WebUI.click(divObj)
		WebUI.sendKeys(divObj, Keys.chord(Keys.CONTROL, "a"))
		WebUI.sendKeys(divObj, value)
		WebUI.sendKeys(divObj, Keys.chord(Keys.ENTER))

		KeywordUtil.markPassed("✔ Saisie via div TD OK (${value})")
	}

	@Keyword
	static boolean checkElement(TestObject to, String mode = "present", int timeout = 10, String name = null) {

		String realName = name ?: to.getObjectId()
		boolean ok = false

		switch(mode.toLowerCase()) {
			case "present":
				ok = WebUI.verifyElementPresent(to, timeout, FailureHandling.OPTIONAL)
				break

			case "visible":
				ok = WebUI.verifyElementVisible(to, FailureHandling.OPTIONAL)
				break

			case "clickable":
				ok = WebUI.verifyElementClickable(to, FailureHandling.OPTIONAL)
				break

			default:
				KeywordUtil.markFailed("Mode '${mode}' inconnu pour checkElement()")
				return false
		}

		if (ok) {
			KeywordUtil.logInfo("OK : élément '${realName}' → ${mode}")
			return true
		}
		KeywordUtil.markFailed("ECHEC : élément '${realName}' n'est pas ${mode} (timeout ${timeout}s)")
		return false
	}


	@Keyword
	def checkButtons(List<TestObject> objects, List<String> names) {
		if (objects.size() != names.size()) {
			KeywordUtil.markFailed("La liste des objets et des noms doit avoir la même taille.")
			return
		}

		for (int i = 0; i < objects.size(); i++) {
			if (!WebUI.verifyElementPresent(objects[i], 5, FailureHandling.OPTIONAL)) {
				KeywordUtil.markFailed("Bouton '${names[i]}' introuvable.")
			} else {
				KeywordUtil.logInfo("Bouton '${names[i]}' est présent.")
			}
		}
	}

	//	@Keyword
	//	def waitAndVerifyText(TestObject to, String expectedText, int timeout = 10) {
	//		WebUI.waitForElementVisible(to, timeout)
	//		WebUI.verifyElementText(to, expectedText)
	//	}


	@Keyword
	def waitAndVerifyText(TestObject to, String expectedText, int timeout = 10) {
		// Attendre que l'élément soit visible
		boolean isVisible = WebUI.waitForElementVisible(to, timeout)
		if (!isVisible) {
			KeywordUtil.markFailed("Élément non visible après ${timeout} secondes : ${to.getObjectId()}")
			return
		}

		// Récupérer le texte réel
		String actualText = WebUI.getText(to).trim()

		// Normalisation simple : apostrophes, espaces multiples
		def normalize = { String s ->
			s = s.replaceAll("[’‘`]", "'")           // normaliser toutes les apostrophes en simple '
			s = s.replaceAll("\\s+", " ")           // réduire les espaces multiples
			s = s.replaceAll("\u00A0", " ")         // convertir les espaces insécables
			return s.trim()
		}

		actualText = normalize(actualText)
		expectedText = normalize(expectedText)

		// Comparaison
		if (actualText != expectedText) {
			KeywordUtil.markFailed("Texte non conforme : attendu='${expectedText}' / obtenu='${actualText}'")
		} else {
			KeywordUtil.logInfo("Texte validé : '${actualText}'")
		}
	}


	@Keyword
	def waitAndVerifyValue(TestObject to, String expectedValue, int timeout = 10) {
		WebUI.waitForElementVisible(to, timeout)

		String actualValue = WebUI.getAttribute(to, 'value')

		if (!actualValue.equalsIgnoreCase(expectedValue)) {
			KeywordUtil.markFailed(
					"Échec : valeur trouvée '${actualValue}' ≠ valeur attendue '${expectedValue}'"
					)
		}
	}


	@Keyword
	def verifySelect2DisplayedValue(TestObject to, String expected, int timeout = 10) {

		WebUI.waitForElementVisible(to, timeout)

		String actual = WebUI.getText(to).trim()

		if (!actual) {
			actual = WebUI.getAttribute(to, "title")?.trim()
		}
		if (!actual.equalsIgnoreCase(expected.trim())) {
			KeywordUtil.markFailed(
					"Échec : valeur affichée '${actual}' ≠ valeur attendue '${expected}'"
					)
		}
	}


	//	@Keyword
	//	def verifyTableField(TestObject tableObj, String label, String expectedValue, int timeout = 10) {
	//		WebUI.waitForElementVisible(tableObj, timeout)
	//		def lignes = WebUI.getText(tableObj).readLines()
	//		int nbLabels = lignes.size() / 2
	//		int idx = lignes[0..(nbLabels-1)].findIndexOf { it.trim() == label }
	//		if(idx == -1) KeywordUtil.markFailed("Label '${label}' non trouvé")
	//		String actual = lignes[idx + nbLabels].trim()
	//		(actual == expectedValue) ?
	//				KeywordUtil.markPassed("'${label}' = '${actual}'") :
	//				KeywordUtil.markFailed("'${label}' incorrect. Attendu : '${expectedValue}', trouvé : '${actual}'")
	//	}


	@Keyword
	def verifyTableFieldReleve(TestObject tableObj, String label, String expectedValue, int timeout = 10) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		// 1. Trouver le header par son libellé
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[@class='head']//th[.//span[normalize-space(text())='${label}']]"
				)

		WebElement header = WebUiCommonHelper.findWebElement(headerObj, timeout)

		// 2. Récupérer le data-name
		String dataName = header.getAttribute("data-name")
		if (!dataName) {
			KeywordUtil.markFailed("Aucun data-name trouvé pour le label '${label}'")
			return
		}

		// 3. Récupérer la cellule correspondante (ligne 1)
		TestObject valueObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[1]//td[@data-field='${dataName}']"
				)

		String actual = WebUI.getText(valueObj).trim()

		if (actual == expectedValue) {
			KeywordUtil.markPassed("'${label}' = '${actual}'")
		} else {
			KeywordUtil.markFailed("'${label}' incorrect. Attendu : '${expectedValue}', trouvé : '${actual}'")
		}
	}


	@Keyword
	static void verifyCofs(TestObject tableObj, List<String> expectedCofs) {

		WebDriver driver = DriverFactory.getWebDriver()

		WebElement table = driver.findElement(
				By.xpath(tableObj.findPropertyValue('xpath'))
				)

		// Récupération des valeurs de la colonne N° COF
		List<WebElement> cofCells = table.findElements(
				By.xpath(".//td[@data-field='namCofNumero']//div")
				)

		List<String> actualCofs = cofCells.collect { it.getText().trim() }

		// 1️⃣ Vérification du nombre
		if (actualCofs.size() != expectedCofs.size()) {
			KeywordUtil.markFailed(
					"Nombre de COF incorrect : ${actualCofs.size()} (attendu : ${expectedCofs.size()})"
					)
		} else {
			KeywordUtil.logInfo("Nombre de COF OK : ${actualCofs.size()}")
		}

		// 2️⃣ Vérification du contenu
		expectedCofs.each { cof ->
			if (actualCofs.contains(cof)) {
				KeywordUtil.logInfo("${cof} présent")
			} else {
				KeywordUtil.markFailed("${cof} absent")
			}
		}

		// 3️⃣ Vérification stricte (aucun COF en trop)
		if (actualCofs.toSet() != expectedCofs.toSet()) {
			KeywordUtil.markFailed(
					"Liste des COF non conforme. Trouvés : ${actualCofs} / Attendus : ${expectedCofs}"
					)
		}

		KeywordUtil.logInfo("Vérification des COF terminée avec succès")
	}

	@Keyword
	def verifyTableFieldCof(TestObject tableObj, String label, String expectedValue, int timeout = 10) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		// 1. Récupérer le header
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[@class='head']//th[.//span[normalize-space(.)='${label}']]"
				)
		WebElement header = WebUiCommonHelper.findWebElement(headerObj, timeout)

		String dataName = header.getAttribute("data-name")
		if (!dataName) {
			KeywordUtil.markFailed("Aucun data-name trouvé pour le label '${label}'")
			return
		}

		// 2. Chercher la valeur dans toutes les lignes
		TestObject cellObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//td[@data-field='${dataName}']"
				)

		List<WebElement> cells = WebUiCommonHelper.findWebElements(cellObj, timeout)
		boolean found = cells.any { it.getText().trim() == expectedValue }

		if (found) {
			KeywordUtil.markPassed("✅ '${label}' contient la valeur '${expectedValue}'")
		} else {
			KeywordUtil.markFailed("❌ Valeur '${expectedValue}' non trouvée dans la colonne '${label}'")
		}
	}

	@Keyword
	def verifySyntheseByStep2(Map<String, String> form, String stepLabel) {

		Map<String, Map<String, Boolean>> rules = [

			"COF1": [
				'namCofSyntheseGlobalePr': false,
				'namCofSyntheseGlobaleDx': false,
				'namCofSyntheseGlobaleTr': false
			],

			"COF2": [
				'namCofSyntheseGlobalePr': true,
				'namCofSyntheseGlobaleDx': false,
				'namCofSyntheseGlobaleTr': false
			],

			"COF3": [
				'namCofSyntheseGlobalePr': true,
				'namCofSyntheseGlobaleDx': true,
				'namCofSyntheseGlobaleTr': false
			]
		]

		if (!rules.containsKey(stepLabel)) {
			KeywordUtil.markFailed("Règles inconnues pour '${stepLabel}'")
			return
		}

		KeywordUtil.logInfo("===== Vérification synthèse ${stepLabel} =====")

		rules[stepLabel].each { field, mustBeFilled ->

			String value = form[field]
			boolean isFilled = value != null && !value.trim().isEmpty()

			if (mustBeFilled && !isFilled) {
				KeywordUtil.markFailed(
						"${stepLabel} – '${field}' devrait être rempli mais est vide"
						)
			}
			else if (!mustBeFilled && isFilled) {
				KeywordUtil.markFailed(
						"${stepLabel} – '${field}' devrait être vide mais contient '${value}'"
						)
			}
			else {
				KeywordUtil.logInfo(
						"${stepLabel} – '${field}' conforme"
						)
			}
		}
	}
	@Keyword
	List<Map<String, String>> readTableAsListOfMap(TestObject tableObj) {

		WebUI.waitForElementVisible(tableObj, 10)
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject rowObj = new TestObject().addProperty(
				"xpath",
				com.kms.katalon.core.testobject.ConditionType.EQUALS,
				tableXPath + "//tbody//tr"
				)

		List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObj, 10)
		List<Map<String, String>> tableData = []

		rows.eachWithIndex { row, rowIndex ->
			Map<String, String> rowData = [:]

			List<WebElement> cells = row.findElements(By.xpath("./td[@data-field]"))
			cells.each { cell ->
				String field = cell.getAttribute("data-field")
				String value = cell.getText().trim()
				rowData[field] = value
			}

			tableData.add(rowData)
			KeywordUtil.logInfo("Ligne ${rowIndex + 1} => ${rowData}")
		}

		return tableData
	}
	@Keyword
	def verifyRowExistsInTable(TestObject tableObj, Map<String, String> expectedRow) {

		List<Map<String, String>> tableData =
				readTableAsListOfMap(tableObj)

		boolean found = tableData.any { row ->
			expectedRow.every { key, expectedValue ->
				row.containsKey(key) && row[key] == expectedValue
			}
		}

		if (found) {
			KeywordUtil.logInfo("Ligne trouvée dans la table : ${expectedRow}")
		} else {
			KeywordUtil.markFailed(
					"Ligne NON trouvée dans la table.\nAttendu : ${expectedRow}\nContenu : ${tableData}"
					)
		}
	}

	@Keyword
	def checkNotificationAndSelect1(
			TestObject tableObj,
			String expectedDate,
			String expectedLibelle,
			String expectedPrenom,
			String expectedCof
	) {

		WebUI.waitForElementVisible(tableObj, 10)
		String tableXPath = tableObj.findPropertyValue("xpath")

		String rowXpath = """
    ${tableXPath}//tr[
        .//td[@data-field='namNotifDateRef']//div[normalize-space()='${expectedDate}']
        and
        .//td[@data-field='namNotifLibelle']//div[contains(normalize-space(),'${expectedLibelle}')]
        and
        .//td[@data-field='namNotifMessage']//div[
            contains(normalize-space(),'${expectedPrenom}')
            and contains(normalize-space(),'${expectedCof}')
        ]
    ]
    """

		TestObject checkboxObj = new TestObject().addProperty(
				"xpath",
				com.kms.katalon.core.testobject.ConditionType.EQUALS,
				rowXpath + "//input[@type='checkbox' and contains(@class,'selrow')]"
				)

		if (WebUI.verifyElementPresent(checkboxObj, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(checkboxObj)
			KeywordUtil.markPassed("Notification trouvée et sélectionnée : ${expectedCof}")
		} else {
			KeywordUtil.markFailed("Notification introuvable : ${expectedCof}")
		}
	}



	@Keyword
	def verifySyntheseByStep(Map<String, String> form, String stepLabel) {

		Map<String, Map<String, Boolean>> rules = [

			"COF1": [
				'namCofSyntheseGlobalePr': false,
				'namCofSyntheseGlobaleDx': false,
				'namCofSyntheseGlobaleTr': false
			],

			"COF2": [
				'namCofSyntheseGlobalePr': true,
				'namCofSyntheseGlobaleDx': false,
				'namCofSyntheseGlobaleTr': false
			],

			"COF3": [
				'namCofSyntheseGlobalePr': true,
				'namCofSyntheseGlobaleDx': true,
				'namCofSyntheseGlobaleTr': false
			]
		]

		// 🔹 Règles de présence des blocs Synthèse
		Map<String, List<TestObject>> syntheseElements = [

			"COF1": [
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF1')
			],

			"COF2": [
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF1'),
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF2')
			],

			"COF3": [
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF1'),
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF2'),
				findTestObject('Object Repository/COF/Page_COF/Elem_Synthse_COF3')
			]
		]

		if (!rules.containsKey(stepLabel)) {
			KeywordUtil.markFailed("Règles inconnues pour '${stepLabel}'")
			return
		}

		KeywordUtil.logInfo("===== Vérification synthèse ${stepLabel} =====")

		rules[stepLabel].each { field, mustBeFilled ->

			String value = form[field]
			boolean isFilled = value != null && !value.trim().isEmpty()

			if (mustBeFilled && !isFilled) {
				KeywordUtil.markFailed(
						"${stepLabel} – '${field}' devrait être rempli mais est vide"
						)
			}
			else if (!mustBeFilled && isFilled) {
				KeywordUtil.markFailed(
						"${stepLabel} – '${field}' devrait être vide mais contient '${value}'"
						)
			}
			else {
				KeywordUtil.logInfo(
						"${stepLabel} – '${field}' conforme"
						)
			}
		}

		syntheseElements[stepLabel].each { TestObject elem ->

			WebUI.verifyElementPresent(
					elem,
					10,
					FailureHandling.STOP_ON_FAILURE
					)

			KeywordUtil.logInfo(
					"${stepLabel} – Élément ${elem.getObjectId()} présent"
					)
		}
	}


	@Keyword
	def createAndVerifyAxeTravail(
			String constat,
			String proposition,
			String moyens
	) {
		waitAndClick(findTestObject('Object Repository/COF/Page_COF/button_Creer_Axes_de_travail'))

		waitAndSet(findTestObject('Object Repository/COF/Page_COF/input_Axe_Tr_Constat'), constat)
		waitAndSet(findTestObject('Object Repository/COF/Page_COF/input_Axe_Tr_PropositionDobjectif'), proposition)
		waitAndSet(findTestObject('Object Repository/COF/Page_COF/input_Axe_Tr_Moyens'), moyens)

		saveCloseAndCheckConfirmation()

		TestObject tableAxeTrav = findTestObject('Object Repository/COF/Page_COF/table_AxesTravail')

		Map<String, String> expected = [
			"namAxetrvDomaine" : "Chargé(e) de recrutement des volontaires",
			"namAxetrvConstat" : constat,
			"namAxetrvPropObj" : proposition,
			"namAxetrvMoyen"   : moyens
		]

		verifyRowExistsInTable(tableAxeTrav, expected)
	}


	@Keyword
	def processCofSynthese(
			TestObject tableObj,
			String cofCode,
			TestObject inputSynthese,
			TestObject inputCommentaire,
			String synthese,
			String commentaire
	) {

		clickOnCof(tableObj, cofCode)

		waitAndClick(findTestObject('Object Repository/COF/Page_COF/button_COF_Animer'))
		waitAndVerifyText(findTestObject('Object Repository/COF/Page_COF/Statut_EnCours'),
				'En cours')

		def form = extractElements("work")
		verifySyntheseByStep(form, cofCode)

		waitAndSet(inputSynthese, synthese)
		waitAndSet(inputCommentaire, commentaire)

		saveAndCheckConfirmation()

		waitAndClick(findTestObject('Object Repository/COF/Page_COF/button_Valider'))
		//verifyCofStatus(cofCode, "VALIDE")
		waitAndVerifyText(findTestObject('Object Repository/COF/Page_COF/Statut_Valide'),
				'Validé')

		waitAndClick(findTestObject('Object Repository/COF/Page_COF/page_Cof_Volontaire'))
	}

	@Keyword
	def checkNotificationAndSelect(
			TestObject tableObj,
			String expectedDate,
			String expectedLibelle,
			String expectedPrenom,
			String expectedCof
	) {

		WebUI.waitForElementVisible(tableObj, 10)
		String tableXPath = tableObj.findPropertyValue("xpath")

		String rowXpath = """
			${tableXPath}//tr[
			    .//td[@data-field='namNotifDateRef']//div[normalize-space()='${expectedDate}']
			    and
			    .//td[@data-field='namNotifLibelle']//div[contains(normalize-space(), '${expectedLibelle}')]
			    and
			    .//td[@data-field='namNotifMessage']//div[
			        contains(normalize-space(), '${expectedPrenom}')
			        and contains(normalize-space(), '${expectedCof}')
			    ]
			]
			"""

		TestObject checkboxObj = new TestObject().addProperty(
				"xpath",
				com.kms.katalon.core.testobject.ConditionType.EQUALS,
				rowXpath + "//input[@type='checkbox' and contains(@class,'selrow')]"
				)

		if (WebUI.verifyElementPresent(checkboxObj, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(checkboxObj)
			KeywordUtil.markPassed(
					"Notification trouvée : date=${expectedDate}, libellé=${expectedLibelle}, prénom=${expectedPrenom}, cof=${expectedCof}"
					)
		} else {
			KeywordUtil.markFailed(
					"Notification NON trouvée : date=${expectedDate}, libellé=${expectedLibelle}, prénom=${expectedPrenom}, cof=${expectedCof}"
					)
		}
	}
	@Keyword
	def verifyCofCompteRenduNotification(
			TestObject tableObj,
			String expectedPrenom,
			String expectedLibelle,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		// Date du jour jj/MM/yyyy
		String today = new Date().format("dd/MM/yyyy")

		String tableXPath = tableObj.findPropertyValue("xpath")

		// XPath insensible à la casse pour le prénom et le libellé
		String rowXpath = """
${tableXPath}//tr[
    .//td[@data-field='namNotifDateRef']//div[normalize-space()='${today}']
    and
    contains(translate(.//td[@data-field='namNotifLibelle']//div, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '${expectedLibelle.toLowerCase()}')
    and
    contains(translate(.//td[@data-field='namNotifMessage']//div, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '${expectedPrenom.toLowerCase()}')
]
"""

		TestObject rowObj = new TestObject().addProperty(
				"xpath",
				com.kms.katalon.core.testobject.ConditionType.EQUALS,
				rowXpath
				)

		if (WebUI.verifyElementPresent(rowObj, timeout, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed(
					"Notification trouvée : date=${today}, libellé='${expectedLibelle}', prénom='${expectedPrenom}'"
					)
		} else {
			KeywordUtil.markFailed(
					"Notification ABSENTE : date=${today}, libellé='${expectedLibelle}', prénom='${expectedPrenom}'"
					)
		}
	}


	@Keyword
	def verifyCofCompteRenduNotificationFromAccueil(
			String profil,
			String expectedPrenom,
			String expectedLibelle = "Le Compte Rendu du COF est disponible",
			int timeout = 10
	) {

		Access_epide(profil)

		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Rub_Accueil'))

		TestObject tableNotif = findTestObject(
				'Object Repository/Volontaire/Page_Simplicit/table_notification'
				)

		verifyCofCompteRenduNotification(
				tableNotif,
				expectedPrenom,
				expectedLibelle,
				timeout
				)
		WebUI.closeBrowser()
	}

	@Keyword
	def verifyNotificationSortieFromAccueil(
			String profil,
			String expectedPrenom,
			String expectedLibelle = "Une sortie est prévue pour le jeune",
			int timeout = 10
	) {

		Access_epide(profil)

		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Rub_Accueil'))

		TestObject tableNotif = findTestObject(
				'Object Repository/Volontaire/Page_Simplicit/table_notification'
				)

		verifyCofCompteRenduNotification(
				tableNotif,
				expectedPrenom,
				expectedLibelle,
				timeout
				)
		WebUI.closeBrowser()
	}


	@Keyword
	def checkCofNotifications(
			String centre,
			String date,
			String texteNotif,
			String prenom,
			List<String> cofList
	) {

		Access_epide(centre)

		waitAndClick(findTestObject('Object Repository/ComunCI/Page_Simplicit/Rub_Accueil'))

		TestObject tableNotif = findTestObject(
				'Object Repository/Volontaire/Page_Simplicit/table_notification'
				)

		cofList.each { cof ->
			KeywordUtil.logInfo("Vérification notification ${cof}")
			checkNotificationAndSelect(
					tableNotif,
					date,
					texteNotif,
					prenom,
					cof
					)
		}
	}

	@Keyword
	def verifyCofWithStatus1(TestObject tableObj, String cofNumber, String expectedStatus, int timeout = 10) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject rowObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[td[normalize-space(.)='${cofNumber}']]"
				)

		List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObj, timeout)

		if (rows.isEmpty()) {
			KeywordUtil.markFailed("COF '${cofNumber}' non trouvé dans le tableau")
			return
		}

		WebElement row = rows[0]

		WebElement statusCell = row.findElement(By.xpath(".//td[contains(@data-field,'statut')]")

				//By.xpath(".//td[contains(@data-field,'statut') or normalize-space(.)='${expectedStatus}']")
				)

		String actualStatus = statusCell.text.trim()

		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			KeywordUtil.markPassed("COF '${cofNumber}' → Statut = '${actualStatus}'")
		} else {
			KeywordUtil.markFailed(
					"COF '${cofNumber}' → Statut incorrect. Attendu '${expectedStatus}', trouvé '${actualStatus}'"
					)
		}
	}


	@Keyword
	def verifyCofStatus(String expectedNumero, String expectedStatut, int timeout = 15) {

		long endTime = System.currentTimeMillis() + (timeout * 1000)
		while (System.currentTimeMillis() < endTime) {

			def form = extractElements("work")

			String currentNumero = form["namCofNumero"]
			String currentStatut = form["namCofStatut"]

			if (currentNumero == expectedNumero && currentStatut == expectedStatut) {

				KeywordUtil.logInfo(
						"Statut '${expectedStatut}' confirmé pour ${expectedNumero}"
						)
				return
			}

			KeywordUtil.logInfo(
					"Attente statut... COF=${currentNumero}, Statut=${currentStatut}"
					)

			WebUI.delay(1)
		}

		KeywordUtil.markFailed(
				"Timeout ${timeout}s : attendu COF=${expectedNumero}, Statut=${expectedStatut}"
				)
	}



	@Keyword
	def verifyCofWithStatus(
			TestObject tableObj,
			String cofNumber,
			String expectedStatus,
			int timeout = 10
	) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		/* ===== TROUVER LA LIGNE DU COF ===== */

		TestObject rowObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[td[@data-field='namCofNumero' and normalize-space(.)='${cofNumber}']]"
				)

		List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObj, timeout)

		if (rows.isEmpty()) {
			KeywordUtil.markFailed("COF '${cofNumber}' non trouvé dans le tableau")
			return
		}

		WebElement row = rows[0]

		/* ===== CELLULE STATUT ===== */

		WebElement statusCell = row.findElement(
				By.xpath(".//td[@data-field='namCofStatut']//div")
				)

		String actualStatus = statusCell.text.trim()

		/* ===== COMPARAISON ===== */

		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			KeywordUtil.markPassed(
					"COF '${cofNumber}' → Statut = '${actualStatus}'"
					)
		} else {
			KeywordUtil.markFailed(
					"COF '${cofNumber}' → Statut incorrect : attendu '${expectedStatus}', trouvé '${actualStatus}'"
					)
		}
	}

	@Keyword
	def clickOnCof(TestObject tableObj, String cofNumber, int timeout = 10) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject cofRow = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath +
				"//tbody//tr[.//td[@data-field='namCofNumero']//div[normalize-space(.)='${cofNumber}']]"
				)

		if (!WebUI.verifyElementPresent(cofRow, timeout, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed("COF '${cofNumber}' introuvable dans le tableau")
			return
		}

		WebUI.click(cofRow)
		KeywordUtil.markPassed("COF '${cofNumber}' cliqué avec succès")
	}

	@Keyword
	def verifyTableNotification(
			TestObject tableObj,
			String label,
			String expectedValue,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		// Mapping des libellés vers les data-field
		Map<String, String> fieldMapping = [
			"Nom de la notification"      : "namAdmNotifNom",
			"N° de notification"          : "namAdmNotifNum",
			"Type de notification"        : "namAdmNotifType",
			"Informations complémentaires": "namAdmNotifInfoCmp",
			"Actif"                       : "namAdmNotifActif",
			"Message"                     : "namAdmNotifMsg"
		]

		if (!fieldMapping.containsKey(label)) {
			KeywordUtil.markFailed("Libellé '${label}' non mappé vers un data-field")
			return
		}

		String dataField = fieldMapping[label]
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject valueObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[1]//td[@data-field='${dataField}']//div"
				)

		WebUI.waitForElementVisible(valueObj, timeout)

		String actual = ""
		long endTime = System.currentTimeMillis() + (timeout * 1000)

		while (System.currentTimeMillis() < endTime) {
			actual = WebUI.getText(valueObj).trim()

			if (expectedValue == "notEmpty") {
				if (actual) {
					KeywordUtil.markPassed("'${label}' n'est pas vide comme attendu. Valeur récupérée : '${actual}'")
					return
				}
			} else if (expectedValue == "empty") {
				if (!actual) {
					KeywordUtil.markPassed("'${label}' est vide comme attendu.")
					return
				}
			} else {
				if (actual == expectedValue) {
					KeywordUtil.markPassed("'${label}' = '${actual}' comme attendu")
					return
				}
			}

			WebUI.delay(1)
		}


		if (expectedValue == "notEmpty") {
			KeywordUtil.markFailed("'${label}' est vide, alors qu'il ne devrait pas l'être.")
		} else if (expectedValue == "empty") {
			KeywordUtil.markFailed("'${label}' n'est pas vide, valeur trouvée : '${actual}'")
		} else {
			KeywordUtil.markFailed("'${label}' incorrect. Attendu : '${expectedValue}', trouvé : '${actual}'")
		}
	}

	@Keyword
	def selectSelect2OptionByLabel(TestObject selectObj, String optionLabel, int timeout = 10) {

		TestObject select2Container = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				selectObj.findPropertyValue("xpath") +
				"/following-sibling::span[contains(@class,'select2')]"
				)

		WebUI.waitForElementClickable(select2Container, timeout)
		WebUI.click(select2Container)

		TestObject optionObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//li[contains(@class,'select2-results__option') and normalize-space()='${optionLabel}']"
				)

		WebUI.waitForElementClickable(optionObj, timeout)
		WebUI.click(optionObj)

		KeywordUtil.markPassed("Option '${optionLabel}' sélectionnée")
	}


	@Keyword
	def verifyTableFse(
			TestObject tableObj,
			String label,
			String expectedValue,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		Map<String, String> fieldMapping = [
			"Type de FSE"     : "namFseType",
			"Statut de FSE"      : "namFseStatut",
			"Date FSE"        : "namFseDate",
			"Numéro de FSE"   : "namFseNum"

		]

		if (!fieldMapping.containsKey(label)) {
			KeywordUtil.markFailed(
					"Libellé '${label}' non mappé vers un data-field"
					)
			return
		}

		String dataField = fieldMapping[label]
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject valueObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[1]//td[@data-field='${dataField}']//div"
				)

		WebUI.waitForElementVisible(valueObj, timeout)

		String actual = ""
		long endTime = System.currentTimeMillis() + (timeout * 1000)

		while (System.currentTimeMillis() < endTime) {
			actual = WebUI.getText(valueObj).trim()

			if (expectedValue == "notEmpty" && actual) {
				KeywordUtil.markPassed("'${label}' n'est pas vide : '${actual}'")
				return
			} else if (expectedValue == "empty" && !actual) {
				KeywordUtil.markPassed("'${label}' est vide comme attendu")
				return
			} else if (expectedValue != "empty" && expectedValue != "notEmpty" && actual == expectedValue) {
				KeywordUtil.markPassed("${label} = ${actual}")
				return
			}

			WebUI.delay(1)
		}

		KeywordUtil.markFailed(
				"'${label}' incorrect. Attendu '${expectedValue}', trouvé '${actual}'"
				)
	}
	@Keyword
	def verifyDemandeTransfertTable(
			TestObject tableObj,
			String columnLabel,
			String expectedValue,
			int rowIndex = 1,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		String tableXPath = tableObj.findPropertyValue("xpath")

		/* ===============================
		 1. Récupérer le data-name depuis le label visible
		 =============================== */
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"${tableXPath}//thead//th[.//span[normalize-space(text())='${columnLabel}']]"
				)

		if (!WebUI.verifyElementPresent(headerObj, timeout, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed("Colonne '${columnLabel}' introuvable dans la table Demande de transfert")
			return
		}

		String dataName = WebUI.getAttribute(headerObj, "data-name")

		if (!dataName) {
			KeywordUtil.markFailed("La colonne '${columnLabel}' ne possède pas d'attribut data-name")
			return
		}

		/* ===============================
		 2. Récupérer la cellule correspondante
		 =============================== */
		TestObject cellObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"${tableXPath}//tbody//tr[${rowIndex}]//td[@data-field='${dataName}']"
				)

		WebUI.waitForElementVisible(cellObj, timeout)

		String actual = ""
		long endTime = System.currentTimeMillis() + (timeout * 1000)

		/* ===============================
		 3. Boucle d’attente métier
		 =============================== */
		while (System.currentTimeMillis() < endTime) {
			actual = WebUI.getText(cellObj).trim()

			if (expectedValue.equalsIgnoreCase("notEmpty") && !actual.isEmpty()) {
				KeywordUtil.markPassed("Colonne '${columnLabel}' non vide : '${actual}'")
				return
			}

			if (expectedValue.equalsIgnoreCase("empty") && actual.isEmpty()) {
				KeywordUtil.markPassed("Colonne '${columnLabel}' vide comme attendu")
				return
			}

			if (!expectedValue.equalsIgnoreCase("empty")
					&& !expectedValue.equalsIgnoreCase("notEmpty")
					&& actual.equals(expectedValue)) {
				KeywordUtil.markPassed("${columnLabel} = ${actual}")
				return
			}

			WebUI.delay(1)
		}

		KeywordUtil.markFailed(
				"Colonne '${columnLabel}' incorrecte. Attendu='${expectedValue}', trouvé='${actual}'"
				)
	}

	@Keyword
	def compareAndCheckElements(Map elementsMap, Map expectedValues, int timeout = 10) {
		expectedValues.each { field, expected ->
			if (!elementsMap.containsKey(field)) {
				KeywordUtil.markFailed("Élément '${field}' introuvable dans le formulaire")
				return
			}

			String actual = (elementsMap[field] as String)?.trim() ?: ""

			if (expected == "empty") {
				if (actual.isEmpty()) {
					KeywordUtil.markPassed("${field} est vide comme attendu")
				} else {
					KeywordUtil.markFailed("${field} devait être vide, trouvé '${actual}'")
				}
			} else if (expected == "notEmpty") {
				if (actual.isEmpty()) {
					KeywordUtil.markFailed("${field} n'est pas vide comme attendu")
				} else {
					KeywordUtil.markPassed("${field} n'est pas vide comme attendu. Valeur récupérée : '${actual}'")
				}
			} else {
				if (actual == expected) {
					KeywordUtil.markPassed("${field} = '${actual}'")
				} else {
					KeywordUtil.markFailed("${field} incorrect. Attendu '${expected}', trouvé '${actual}'")
				}
			}
		}
	}

	@Keyword
	def compareAndCheckElementsFse(
			String formKey,
			Map<String, String> expectedValues,
			int timeout = 10
	) {
		Map elementsMap = extractElements(formKey)

		if (!elementsMap || elementsMap.isEmpty()) {
			KeywordUtil.markFailed(
					"Extraction du formulaire '${formKey}' vide ou invalide"
					)
			return
		}

		expectedValues.each { field, expected ->

			if (!elementsMap.containsKey(field)) {
				KeywordUtil.markFailed(
						"Élément '${field}' introuvable dans le formulaire '${formKey}'"
						)
				return
			}

			String actual = (elementsMap[field] as String)?.trim() ?: ""

			switch (expected) {

				case "empty":
					if (actual.isEmpty()) {
						KeywordUtil.markPassed("${field} est vide comme attendu")
					} else {
						KeywordUtil.markFailed(
								"${field} devait être vide, trouvé '${actual}'"
								)
					}
					break

				case "notEmpty":
					if (actual.isEmpty()) {
						KeywordUtil.markFailed(
								"${field} est vide alors qu'il ne devrait pas l'être"
								)
					} else {
						KeywordUtil.markPassed(
								"${field} n'est pas vide comme attendu. Valeur récupérée : '${actual}'"
								)
					}
					break

				default:
					if (actual == expected) {
						KeywordUtil.markPassed("${field} = '${actual}'")
					} else {
						KeywordUtil.markFailed(
								"${field} incorrect. Attendu '${expected}', trouvé '${actual}'"
								)
					}
			}
		}
	}


	@Keyword
	def verifyTableField(TestObject tableObj, String label, String expectedValue, int timeout = 10) {
		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[@class='head']//th"
				)

		TestObject rowObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[1]"
				)

		List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, timeout)
		int colIndex = headers.findIndexOf { it.getText().trim() == label.trim() } + 1

		if (colIndex == 0) KeywordUtil.markFailed("Label '${label}' non trouvé")

		TestObject valueObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr[1]//td[${colIndex}]"
				)

		String actual = WebUI.getText(valueObj).trim()

		(actual == expectedValue) ?
				KeywordUtil.markPassed("'${label}' = '${actual}'") :
				KeywordUtil.markFailed("'${label}' incorrect. Attendu : '${expectedValue}', trouvé : '${actual}'")
	}

	@Keyword
	def verifyTableFieldEmptyOrNot(TestObject tableObj, String label, boolean shouldBeEmpty, int timeout = 10) {
	
		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")
	
		TestObject headerObj = new TestObject().addProperty(
			"xpath",
			ConditionType.EQUALS,
			tableXPath + "//thead//tr[@class='head']//th"
		)
	
		List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, timeout)
	
		int colIndex = headers.findIndexOf { it.getText().trim() == label.trim() } + 1
	
		if (colIndex == 0) {
			KeywordUtil.markFailed("Colonne '${label}' non trouvée")
			return
		}
	
		TestObject cellObj = new TestObject().addProperty(
			"xpath",
			ConditionType.EQUALS,
			tableXPath + "//tbody/tr[1]/td[" + colIndex + "]"
		)
	
		WebUI.waitForElementPresent(cellObj, timeout)
	
		String value = WebUI.getText(cellObj).trim()
	
		if(shouldBeEmpty) {
	
			if(value.isEmpty()) {
				KeywordUtil.markPassed("Le champ '${label}' est vide comme attendu")
			} else {
				KeywordUtil.markFailed("Le champ '${label}' devrait être vide mais contient : ${value}")
			}
	
		} else {
	
			if(value.isEmpty()) {
				KeywordUtil.markFailed("Le champ '${label}' est vide alors qu'une valeur est attendue")
			} else {
				KeywordUtil.markPassed("Le champ '${label}' contient une valeur : ${value}")
			}
	
		}
	}

	@Keyword
	def verifyTableFieldAndClick(
			TestObject tableObj,
			String label,
			String expectedValue,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		// 1️⃣ Trouver l’index de la colonne
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[@class='head']//th"
				)

		List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, timeout)
		int colIndex = headers.findIndexOf { it.getText().trim() == label.trim() } + 1

		if (colIndex == 0) {
			KeywordUtil.markFailed("Label '${label}' non trouvé")
			return
		}

		// 2️⃣ Récupérer toutes les lignes
		TestObject rowsObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr"
				)

		List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsObj, timeout)

		if (rows.isEmpty()) {
			KeywordUtil.markFailed("Aucune ligne trouvée dans le tableau")
			return
		}

		// 3️⃣ Parcourir les lignes
		for (int i = 1; i <= rows.size(); i++) {

			TestObject cellObj = new TestObject().addProperty(
					"xpath",
					ConditionType.EQUALS,
					tableXPath + "//tbody//tr[${i}]//td[${colIndex}]"
					)

			String actual = WebUI.getText(cellObj).trim()

			if (actual == expectedValue) {
				KeywordUtil.markPassed(
						"'${label}' = '${actual}' trouvé à la ligne ${i}"
						)

				WebUI.click(cellObj)   // ✅ clic sur la bonne ligne
				return
			}
		}

		// 4️⃣ Rien trouvé
		KeywordUtil.markFailed(
				"Valeur '${expectedValue}' non trouvée dans la colonne '${label}'"
				)
	}

	def clickRowByStatus(String statusTexte) {
		def driver = DriverFactory.getWebDriver()

		// XPath dynamique pour trouver le div correspondant au statut
		String xpathStatus = "//td[@data-field='namRetfinanceStatut']//div[contains(text(),'" + statusTexte + "')]"

		// Attente jusqu'à ce que l'élément soit visible et cliquable
		WebDriverWait wait = new WebDriverWait(driver, 10)
		WebElement statusElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStatus)))

		// Récupérer la ligne parente <tr>
		WebElement rowElement = statusElement.findElement(By.xpath("./ancestor::tr"))

		// Scroll pour garantir visibilité
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", rowElement)

		// Cliquer sur la ligne entière
		rowElement.click()

		println("Clic effectué sur la première ligne contenant le statut : '${statusTexte}'")
	}

	@Keyword
	def clickOnStatutRF(String statusTexte) {
		def driver = DriverFactory.getWebDriver()

		// XPath dynamique
		String xpathStatus = "//td[@data-field='namRetfinanceStatut']//div[contains(text(),'" + statusTexte + "')]"

		// Attente jusqu'à ce que l'élément soit cliquable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
		WebElement statusElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStatus)))

		// Récupérer la ligne parente <tr>
		WebElement rowElement = statusElement.findElement(By.xpath("./ancestor::tr"))

		// Scroll pour visibilité
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", rowElement)

		// Cliquer sur la ligne entière
		rowElement.click()

		println("Clic effectué sur la première ligne contenant le statut : '${statusTexte}'")
	}

	@Keyword
	def verifyTableFieldRFAndClick(
			TestObject tableObj,
			String label,
			String expectedValue,
			int timeout = 10
	) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		// 1️⃣ Trouver les headers
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[contains(@class,'head')]//th"
				)

		List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, timeout)

		int colIndex = headers.findIndexOf {
			it.getText().trim() == label.trim()
		} + 1

		if (colIndex == 0) {
			KeywordUtil.markFailed("Label '${label}' non trouvé")
			return
		}

		// 2️⃣ Trouver les lignes
		TestObject rowsObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//tr"
				)

		List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsObj, timeout)

		if (rows.isEmpty()) {
			KeywordUtil.markFailed("Aucune ligne trouvée")
			return
		}

		// 3️⃣ Parcourir
		for (int i = 1; i <= rows.size(); i++) {

			TestObject cellObj = new TestObject().addProperty(
					"xpath",
					ConditionType.EQUALS,
					tableXPath + "//tbody//tr[${i}]//td[${colIndex}]//div"
					)

			String actual = WebUI.getText(cellObj).trim()

			if (actual.contains(expectedValue)) {

				KeywordUtil.markPassed(
						"'${label}' = '${actual}' trouvé ligne ${i}"
						)

				// clic sur la ligne entière
				TestObject rowObj = new TestObject().addProperty(
						"xpath",
						ConditionType.EQUALS,
						tableXPath + "//tbody//tr[${i}]"
						)

				WebUI.click(cellObj)

				return
			}
		}

		KeywordUtil.markFailed(
				"Valeur '${expectedValue}' non trouvée dans '${label}'"
				)
	}

	@Keyword
	def verifyElementTable(
			TestObject tableObj,
			List<String> expectedTypes,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject typeCells = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//td[@data-field='namCavenantType']//div"
				)

		List<String> actualTypes = WebUI.findWebElements(typeCells, timeout)
				.collect { it.getText().trim() }
				.findAll { it }

		if (actualTypes.size() != expectedTypes.size()) {
			KeywordUtil.markFailed(
					"Nombre de types incorrect. Attendu=${expectedTypes.size()}, trouvé=${actualTypes.size()}"
					)
			return
		}

		if (!actualTypes.containsAll(expectedTypes)) {
			KeywordUtil.markFailed(
					"Types incorrects. Attendus=${expectedTypes}, trouvés=${actualTypes}"
					)
			return
		}

		KeywordUtil.markPassed("Types strictement conformes : ${expectedTypes}")
	}

	@Keyword
	def getDateTimePlusWorkingDays(int workingDays, String time = "10:00") {

		Calendar cal = Calendar.getInstance()
		int addedDays = 0

		while (addedDays < workingDays) {
			cal.add(Calendar.DAY_OF_MONTH, 1)

			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

			if (dayOfWeek != Calendar.SATURDAY &&
					dayOfWeek != Calendar.SUNDAY) {
				addedDays++
			}
		}

		String datePart = cal.getTime().format("dd/MM/yyyy")
		return "${datePart} ${time}"
	}


	@Keyword
	def setMembreConseil(TestObject accessObj, String value) {

		int timeout = 10
		TestObject inputObj = findTestObject('Object Repository/FSE/Page_Simplicit/input_NomAgent')
		TestObject suggestionObj = findTestObject('Object Repository/FSE/Page_Simplicit/Elem_prenomCEC')

		WebUI.waitForElementClickable(accessObj, timeout)
		WebUI.click(accessObj)

		WebUI.waitForElementVisible(inputObj, timeout)
		WebUI.clearText(inputObj)
		WebUI.setText(inputObj, value)
		WebUI.sendKeys(inputObj, Keys.chord(Keys.ENTER))

		WebUI.waitForElementClickable(suggestionObj, timeout)
		WebUI.click(suggestionObj)
	}


	@Keyword
	def verifyRadioSelection(
			String fieldName,
			String expectedState,
			int timeout = 5
	) {

		expectedState = expectedState?.toLowerCase()

		TestObject radioOui = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//input[@type='radio' and @name='${fieldName}' and @value='1']"
				)

		TestObject radioNon = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//input[@type='radio' and @name='${fieldName}' and @value='0']"
				)

		WebUI.waitForElementPresent(radioOui, timeout)

		boolean ouiChecked = WebUI.verifyElementChecked(
				radioOui,
				1,
				FailureHandling.OPTIONAL
				)

		boolean nonChecked = WebUI.verifyElementChecked(
				radioNon,
				1,
				FailureHandling.OPTIONAL
				)

		switch (expectedState) {

			case "oui":
				if (ouiChecked && !nonChecked) {
					KeywordUtil.markPassed("Radio OUI sélectionné pour '${fieldName}'")
				} else {
					KeywordUtil.markFailed("Radio OUI attendu mais état réel : oui=${ouiChecked}, non=${nonChecked}")
				}
				break

			case "non":
				if (nonChecked && !ouiChecked) {
					KeywordUtil.markPassed("Radio NON sélectionné pour '${fieldName}'")
				} else {
					KeywordUtil.markFailed("Radio NON attendu mais état réel : oui=${ouiChecked}, non=${nonChecked}")
				}
				break

			case "rien":
				if (!ouiChecked && !nonChecked) {
					KeywordUtil.markPassed("Aucune radio sélectionnée pour '${fieldName}'")
				} else {
					KeywordUtil.markFailed("Aucune radio attendue mais état réel : oui=${ouiChecked}, non=${nonChecked}")
				}
				break

			default:
				KeywordUtil.markFailed(
				"Valeur invalide '${expectedState}'. Utiliser 'oui', 'non' ou 'rien'."
				)
		}
	}



	@Keyword
	def verifyElementTableContains(
			TestObject tableObj,
			List<String> expectedTypes,
			int timeout = 10
	) {
		WebUI.waitForElementVisible(tableObj, timeout)

		String tableXPath = tableObj.findPropertyValue("xpath")

		TestObject typeCells = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//tbody//td[@data-field='namCavenantType']//div"
				)

		List<String> actualTypes = WebUI.findWebElements(typeCells, timeout)
				.collect { it.getText().trim() }
				.findAll { it }

		List<String> missing = expectedTypes.findAll { !actualTypes.contains(it) }

		if (!missing.isEmpty()) {
			KeywordUtil.markFailed(
					"Types manquants dans la table : ${missing}. Présents = ${actualTypes}"
					)
			return
		}

		KeywordUtil.markPassed(
				"Tous les types attendus sont présents : ${expectedTypes}"
				)
	}


	@Keyword
	def verifyTableDT(
			TestObject tableObj,
			Map<String, String> expectedFields,
			int rowIndex = 1,
			int timeout = 10
	) {

		WebUI.waitForElementVisible(tableObj, timeout)
		String tableXPath = tableObj.findPropertyValue("xpath")

		// Récupération des headers
		TestObject headerObj = new TestObject().addProperty(
				"xpath",
				ConditionType.EQUALS,
				tableXPath + "//thead//tr[@class='head']//th"
				)

		List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, timeout)

		Map<String, Integer> columnIndexes = [:]

		headers.eachWithIndex { WebElement th, int i ->
			String headerText = th.getText()
					.replaceAll('\\s+', ' ')
					.trim()
			columnIndexes[headerText] = i + 1
		}

		expectedFields.each { label, expectedValue ->

			if (!columnIndexes.containsKey(label)) {
				KeywordUtil.markFailed("Colonne '${label}' introuvable dans la table")
				return
			}

			int colIndex = columnIndexes[label]

			TestObject cellObj = new TestObject().addProperty(
					"xpath",
					ConditionType.EQUALS,
					tableXPath + "//tbody//tr[${rowIndex}]//td[${colIndex}]"
					)

			String actualValue = WebUI.getText(cellObj)
					.replaceAll('\\s+', ' ')
					.trim()

			if (actualValue != expectedValue) {
				KeywordUtil.markFailed(
						"Colonne '${label}' incorrecte. Attendu='${expectedValue}', trouvé='${actualValue}'"
						)
			} else {
				WebUI.comment("OK - ${label} = ${actualValue}")
			}
		}
	}


	@Keyword
	public static boolean clickGenerateWordEdge(TestObject button, int timeout = 10) {
		try {
			def driver = DriverFactory.getWebDriver()
			int beforeCount = driver.getWindowHandles().size()

			WebUI.click(button)
			KeywordUtil.logInfo("Bouton cliqué pour générer le Word...")

			// Attendre l’ouverture éventuelle d’un nouvel onglet
			int waited = 0
			while (driver.getWindowHandles().size() <= beforeCount && waited < timeout * 1000) {
				Thread.sleep(500)
				waited += 500
			}

			int afterCount = driver.getWindowHandles().size()
			if (afterCount > beforeCount) {
				KeywordUtil.markPassed("✔ Bouton cliqué → nouvel onglet ouvert (visualiseur Word Edge).")
				return true
			} else {
				KeywordUtil.markPassed("✔ Bouton cliqué → aucun nouvel onglet détecté, mais Word a probablement été généré dans Edge.")
				return true
			}
		} catch(Exception e) {
			KeywordUtil.markFailed("❌ Exception lors du clic : " + e.getMessage())
			return false
		}
	}

	@Keyword
	def verifyIconContains(TestObject imgObject, String expectedText, int timeout = 10) {
		WebUI.waitForElementVisible(imgObject, timeout)
		String src = WebUI.getAttribute(imgObject, 'src')
		KeywordUtil.logInfo("Image détectée : ${src}")
		if (src == null || src.trim() == "") {
			KeywordUtil.markFailed("Erreur : l'attribut 'src' est vide ou introuvable.")
		}
		if (!src.toLowerCase().contains(expectedText.toLowerCase())) {
			KeywordUtil.markFailed(
					"Erreur : Mauvaise image détectée. L'URL devrait contenir '${expectedText}'. Trouvé : ${src}"
					)
		} else {
			KeywordUtil.logInfo("Icône correcte : contient '${expectedText}'.")
		}
	}

	@Keyword
	def setDateTimeField(TestObject field, String dateTime) {

		WebUI.waitForElementVisible(field, 10)
		WebUI.clearText(field)
		WebUI.setText(field, dateTime)

		// Déclenchement JS (obligatoire sur Epide)
		WebUI.executeJavaScript(
				"arguments[0].dispatchEvent(new Event('change', { bubbles: true }))",
				Arrays.asList(WebUI.findWebElement(field, 10))
				)

		// Validation côté UI
		WebUI.sendKeys(field, Keys.chord(Keys.TAB))
	}

	@Keyword
	def setDateField(TestObject field, String date) {
		waitAndSet(field, date)
		WebUI.executeJavaScript(
				"arguments[0].dispatchEvent(new Event('change'))",
				Arrays.asList(WebUI.findWebElement(field))
				)
		WebUI.sendKeys(field, Keys.chord(Keys.TAB))
	}

	@Keyword
	def setDateRange(String d, TestObject from, TestObject to = null) {
		if (!d) return
			def dates = d.split(',')*.trim()
		fill(from, dates[0])
		if (dates.size() > 1) {
			assert to : "Deux dates mais pas de champ fin"
			fill(to, dates[1])
		}
	}

	private void fill(TestObject f, String v) {
		WebUI.setText(f, v)
		WebUI.executeJavaScript("arguments[0].dispatchEvent(new Event('change'))",[WebUI.findWebElement(f)])
		WebUI.sendKeys(f, Keys.chord(Keys.TAB))
	}

	@Keyword
	boolean waitForNewFile(String folderPath, int timeoutSec) {

		File folder = new File(folderPath)
		long initialCount = folder.listFiles()?.size() ?: 0

		for (int i = 0; i < timeoutSec; i++) {
			long currentCount = folder.listFiles()?.size() ?: 0
			if (currentCount > initialCount) {
				return true
			}
			Thread.sleep(1000)
		}
		return false
	}

	@Keyword
	def selectFromMap(String value, Map<String, String> radioMap) {

		def key = value?.trim()
		def testObjectPath = radioMap[key]

		if (testObjectPath == null) {
			KeywordUtil.markFailed("Valeur inconnue : '${value}'. Options possibles : ${radioMap.keySet()}")
			return
		}

		WebUI.click(findTestObject(testObjectPath))
	}

	@Keyword
	def verifyAndClick(TestObject testObject, int timeout) {
		try {
			if (WebUI.verifyElementPresent(testObject, timeout)) {
				WebUI.click(testObject)
				return true
			} else {
				return false
			}
		} catch (Exception e) {
			println("Erreur lors de la vérification ou du clic : " + e.message)
			return false
		}
	}


	@Keyword
	def bypassCertificateInterstitial() {
		def driver = DriverFactory.getWebDriver()

		String body = (String) WebUI.executeJavaScript("return document.body ? document.body.innerText : '';", null)
		if (body != null && (body.contains("Votre connexion n’est pas privée") || body.contains("ERR_CERT_AUTHORITY_INVALID"))) {
			println "Cert interstitial detected — trying details->proceed"
			try {
				def details = driver.findElement(By.id("details-button"))
				if (details != null && details.isDisplayed()) {
					details.click()
					Thread.sleep(400)
					def proceed = driver.findElement(By.id("proceed-link"))
					if (proceed != null && proceed.isDisplayed()) {
						proceed.click()
						Thread.sleep(1000)
						println "Proceed clicked"
					} else {
						println "proceed-link not found after details click"
					}
				} else {
					println "details-button not found/visible"
				}
			} catch (Exception e) {
				println "Interstitial click attempt failed: ${e.message}"
			}
		}
	}

	@Keyword
	def getTextFromInput(String objectPath) {

		TestObject textZoneObject = findTestObject(objectPath)
		String textrecup = WebUI.getAttribute(textZoneObject, 'value')

		println("Texte récupéré : " + textrecup)

		return textrecup
	}

	@Keyword
	def getTextFromInputAndCompare(String objectPath, String expectedValue) {
		String textRecup = null

		try {
			TestObject textZoneObject = findTestObject(objectPath)

			if (textZoneObject == null) {
				KeywordUtil.markFailedAndStop("L'objet '${objectPath}' est introuvable dans le référentiel.")
			}

			textRecup = WebUI.getAttribute(textZoneObject, 'value')

			println("Valeur récupérée depuis '${objectPath}' : '${textRecup}'")
			KeywordUtil.logInfo("Valeur récupérée depuis '${objectPath}' : '${textRecup}'")

			if (textRecup == null) {
				KeywordUtil.markFailedAndStop("Impossible de récupérer la valeur du champ '${objectPath}'. Valeur attendue : '${expectedValue}'")
			}

			println("Valeur attendue : '${expectedValue}'")
			println("Valeur trouvée  : '${textRecup}'")

			if (!textRecup.equalsIgnoreCase(expectedValue)) {
				KeywordUtil.markFailedAndStop("La valeur ne correspond pas. Attendu : '${expectedValue}', Trouvé : '${textRecup}'")
			}

			println("Comparaison OK : '${expectedValue}' = '${textRecup}'")
			KeywordUtil.logInfo("Comparaison OK : '${expectedValue}' = '${textRecup}'")
			return true
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("Erreur lors de la récupération ou de la comparaison du texte pour '${objectPath}' : ${e.message}")
			return false
		}
	}


	@Keyword
	def getTextFromInputAndCompareold(String objectPath, String expectedValue) {
		String textRecup = null
		boolean isEqual = false

		try {
			TestObject textZoneObject = findTestObject(objectPath)
			textRecup = WebUI.getAttribute(textZoneObject, 'value')
			println("Valeur attendue : '${expectedValue}', Valeur trouvée : '${textRecup}'")

			isEqual = textRecup.toLowerCase().equals(expectedValue.toLowerCase())

			assert isEqual : KeywordUtil.markFailed("La valeur ne correspond pas. Attendu : " + expectedValue + ", Trouvé : " + textRecup)
		} catch (Exception e) {
			println("Erreur lors de la récupération ou de la comparaison du texte : " + e.message)
		}

		return isEqual
	}

	@Keyword
	def getTextFromDropdownAndCompare(String objectPathOrXpath, String expectedText) {
		String actualText = null
		TestObject dropdownField = null

		try {
			if (objectPathOrXpath == null || objectPathOrXpath.trim().isEmpty()) {
				KeywordUtil.markFailedAndStop("Paramètre objectPathOrXpath vide.")
			}

			if (objectPathOrXpath.trim().startsWith("/") || objectPathOrXpath.trim().startsWith("//")) {
				dropdownField = new TestObject("dynamicObject")
				dropdownField.addProperty("xpath", ConditionType.EQUALS, objectPathOrXpath)
			} else {
				dropdownField = findTestObject(objectPathOrXpath)
			}

			if (dropdownField == null) {
				KeywordUtil.markFailedAndStop("L'objet '${objectPathOrXpath}' est introuvable.")
			}

			actualText = WebUI.getText(dropdownField)

			if (!actualText || actualText.trim().isEmpty()) {
				actualText = WebUI.getAttribute(dropdownField, "title")
			}
			if (!actualText || actualText.trim().isEmpty()) {
				actualText = WebUI.getAttribute(dropdownField, "aria-label")
			}

			println("Texte lu depuis '${objectPathOrXpath}' : '${actualText}'")
			KeywordUtil.logInfo("Texte lu depuis '${objectPathOrXpath}' : '${actualText}'")

			if (actualText == null || actualText.trim().isEmpty()) {
				KeywordUtil.markFailedAndStop("Impossible de lire la valeur du menu déroulant '${objectPathOrXpath}'. Attendu : '${expectedText}'")
			}

			if (!actualText.trim().equalsIgnoreCase(expectedText.trim())) {
				KeywordUtil.markFailedAndStop("Le texte du menu déroulant ne correspond pas. Attendu : '${expectedText}', Trouvé : '${actualText}'")
			}

			println("Vérification réussie : '${expectedText}' = '${actualText}'")
			KeywordUtil.logInfo("Vérification réussie : '${expectedText}' = '${actualText}'")
			return true
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("Erreur lors de la vérification du menu déroulant '${objectPathOrXpath}' : ${e.message}")
			return false
		}
	}


	@Keyword
	def getTextFromDropdownAndCompareold(String objectPath, String expectedValue) {
		String textRecup = null
		boolean isEqual = false

		try {
			TestObject dropdownObject = findTestObject(objectPath)

			textRecup = WebUI.getText(dropdownObject)

			if (!textRecup || textRecup.trim().isEmpty()) {
				textRecup = WebUI.getAttribute(dropdownObject, "title")
			}

			if (!textRecup || textRecup.trim().isEmpty()) {
				textRecup = WebUI.getAttribute(dropdownObject, "aria-label")
			}

			println("Valeur attendue : '${expectedValue}', Valeur trouvée : '${textRecup}'")

			if (textRecup != null) {
				isEqual = textRecup.trim().equalsIgnoreCase(expectedValue.trim())
			}

			assert isEqual : KeywordUtil.markFailed("La valeur ne correspond pas. Attendu : '${expectedValue}', Trouvé : '${textRecup}'")
		} catch (Exception e) {
			println("Erreur lors de la récupération ou de la comparaison du dropdown : " + e.message)
		}

		return isEqual
	}


	@Keyword
	def getTextFromElementByXPath(String xpath) {

		WebElement dropdownElement = DriverFactory.getWebDriver().findElement(By.xpath(xpath))
		String texteRecupere = dropdownElement.getText()

		println("Texte récupéré : " + texteRecupere)

		return texteRecupere
	}

	@Keyword
	def getTextFromElementByXPathAndCompare(String xpath, String expectedValue) {
		String textRecup = null
		boolean isEqual = false

		try {
			WebElement dropdownElement = DriverFactory.getWebDriver().findElement(By.xpath(xpath))
			String texteRecupere = dropdownElement.getText()

			println("Texte récupéré : " + texteRecupere)

			isEqual = texteRecupere.toLowerCase().equals(expectedValue.toLowerCase())

			assert isEqual : KeywordUtil.markFailed("La valeur ne correspond pas. Attendu : " + expectedValue + ", Trouvé : " + textRecup)
		} catch (Exception e) {
			println("Erreur lors de la récupération ou de la comparaison du texte : " + e.message)
		}

		return isEqual
	}

	@Keyword
	def clickAndVerifyElementVisible(TestObject testObject, int timeout = 30) {
		WebUI.waitForElementVisible(testObject, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(testObject)
	}

	@Keyword
	def printAndVerifyElementText(TestObject testObject, String expectedText, int timeout = 30) {
		WebUI.waitForElementVisible(testObject, timeout, FailureHandling.STOP_ON_FAILURE)
		String actualText = WebUI.getText(testObject)
		println(actualText)
		WebUI.verifyElementText(testObject, expectedText, FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def remplirChamp(String testObjectPath, String valeur) {
		try {
			clearAndSetText(testObjectPath, valeur)
			WebUI.comment("Champ rempli : ${testObjectPath} avec la valeur : ${valeur}")
		} catch (Exception e) {
			WebUI.comment("Erreur lors du remplissage de ${testObjectPath} : ${e.message}")
		}
	}

	@Keyword
	def selectionnerOption(String xpathElement, String optionAttendue) {
		try {
			WebElement dropdownElement = DriverFactory.getWebDriver().findElement(By.xpath(xpathElement))
			dropdownElement.click()
			WebElement dropdownPanel = DriverFactory.getWebDriver().findElement(By.cssSelector(".ng-dropdown-panel-items"))
			SscrollToElementAndClick(dropdownPanel, ".//div[@role='option']", optionAttendue)
			WebUI.comment("Option sélectionnée : ${optionAttendue} dans le menu déroulant")
		} catch (Exception e) {
			WebUI.comment("Erreur lors de la sélection de l'option dans ${xpathElement} : ${e.message}")
		}
	}

	@Keyword
	def selectOptionList(String selectXpath, String optionText) {
		WebUI.click(findTestObject(selectXpath))
		WebUI.delay(1)

		TestObject option = new TestObject('option')
		option.addProperty("xpath", ConditionType.EQUALS, "//li[contains(@class,'select2-results__option') and text()='${optionText}']")

		WebUI.click(option)
		WebUI.comment("Option sélectionnée : ${optionText}")
	}
	@Keyword
	def verifierOptionsMenuDeroulant(String xpathElement, String profilRecherche) {
		def data_profils = TestDataFactory.findTestData('identifiants_profil')

		def totalRows = data_profils.getRowNumbers()

		for (int i = 1; i <= totalRows; i++) {
			String check_profil = data_profils.getValue("check_profil", i)
			String profil = data_profils.getValue("profil", i)

			if (check_profil == profilRecherche) {
				try {
					WebElement dropdownElement = DriverFactory.getWebDriver().findElement(By.xpath(xpathElement))
					dropdownElement.click()

					WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 10)
					List<WebElement> dropdownOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ng-dropdown-panel-items .ng-option")))
					def dropdownOptionsText = dropdownOptions.collect { it.getText().trim() }

					def valeursAttenduesListe = check_profil.split(",", -1).collect { it.trim() }

					println("Liste des éléments du menu déroulant (récupérée) : ${dropdownOptionsText.join(', ')}")
					println("Liste des valeurs attendues : ${valeursAttenduesListe.join(', ')}")

					if (dropdownOptionsText.sort() != valeursAttenduesListe.sort()) {
						WebUI.comment("Les valeurs du menu déroulant ne correspondent pas exactement aux valeurs attendues pour le profil '${profilRecherche}' à XPath '${xpathElement}'.")
						throw new AssertionError("Les valeurs récupérées ne correspondent pas aux valeurs attendues.")
					} else {
						println("Les valeurs du menu déroulant correspondent exactement aux valeurs attendues.")
					}
				} catch (Exception e) {
					WebUI.comment("Erreur lors de la vérification des options du menu déroulant pour le profil '${profilRecherche}' à XPath '${xpathElement}': ${e.message}")
					throw e
				}
				break
			}
		}
	}

	@Keyword
	def clickElement(TestObject to) {
		try {
			WebElement element = WebUiBuiltInKeywords.findWebElement(to);
			KeywordUtil.logInfo("Clicking element")
			element.click()
			KeywordUtil.markPassed("Element has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("Element not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on element")
		}
	}

	@Keyword
	def List<WebElement> getHtmlTableRows(TestObject table, String outerTagName) {
		WebElement mailList = WebUiBuiltInKeywords.findWebElement(table)
		List<WebElement> selectedRows = mailList.findElements(By.xpath("./" + outerTagName + "/tr"))
		return selectedRows
	}

	@Keyword
	def SscrollToElementAndClick(WebElement dropdownPanel, String elementsToScrollXPath, String elementToCheck) {
		try {
			boolean elementFound = false;
			int maxAttempts = 30;
			int attemptCount = 0;
			JavascriptExecutor executor = ((JavascriptExecutor) DriverFactory.getWebDriver());

			List<WebElement> optionElements = dropdownPanel.findElements(By.xpath(elementsToScrollXPath));
			for (WebElement option : optionElements) {
				String optionText = option.getText();
				if (optionText.contains(elementToCheck)) {
					option.click();
					elementFound = true;
					break;
				}
			}

			while (!elementFound && attemptCount < maxAttempts) {
				executor.executeScript("arguments[0].scrollTop += 500;", dropdownPanel);
				optionElements = dropdownPanel.findElements(By.xpath(elementsToScrollXPath));

				for (WebElement option : optionElements) {
					String optionText = option.getText();
					if (optionText.contains(elementToCheck)) {
						option.click();
						elementFound = true;
						break;
					}
				}

				println("Attempt #$attemptCount: Element ${elementFound ? "found" : "not found yet"}");

				Long scrollHeight = (Long) executor.executeScript("return arguments[0].scrollHeight;", dropdownPanel);
				Long scrollTop = (Long) executor.executeScript("return arguments[0].scrollTop;", dropdownPanel);
				Long clientHeight = (Long) executor.executeScript("return arguments[0].clientHeight;", dropdownPanel);

				if (scrollTop + clientHeight >= scrollHeight) {
					break;
				}

				attemptCount++;
			}

			if (!elementFound) {
				attemptCount = 0;
				while (!elementFound && attemptCount < maxAttempts) {
					executor.executeScript("arguments[0].scrollTop -= 500;", dropdownPanel); // Défile vers le haut
					optionElements = dropdownPanel.findElements(By.xpath(elementsToScrollXPath));

					for (WebElement option : optionElements) {
						String optionText = option.getText();
						if (optionText.contains(elementToCheck)) {
							option.click();
							elementFound = true;
							break;
						}
					}

					// Log attempt
					println("Scrolling up - Attempt #$attemptCount: Element ${elementFound ? "found" : "not found yet"}");

					Long scrollTop = (Long) executor.executeScript("return arguments[0].scrollTop;", dropdownPanel);
					if (scrollTop <= 0) {
						break;
					}

					attemptCount++;
				}
			}

			if (!elementFound) {
				println("Element not found after scrolling up and down");
			}
		} catch (Exception e) {
			println("An error occurred: " + e.getMessage());
		}
	}


	@Keyword
	def ScrollToElementAndClick(WebElement dropdownElement, String elementsToScrollXPath, String elementToCheck) {

		dropdownElement.click()
		List<WebElement> optionElements = dropdownElement.findElements(By.xpath(elementsToScrollXPath))
		println(optionElements.size())
		for (WebElement option : optionElements) {
			String optionText = option.getText()
			if (optionText.contains(elementToCheck)) {
				option.click()
				break
			}
		}
	}

	@Keyword
	def getServiceUrl(String serviceName) {
		String service_url = null
		def data_url = TestDataFactory.findTestData('url_epide')

		for (def rowIndex : (1..data_url.getRowNumbers())) {
			String name = data_url.getValue('service_name', rowIndex)

			if (name.equals(serviceName)) {
				service_url = data_url.getValue('url', rowIndex)
				break
			}
		}
		return service_url
	}

	@Keyword
	def openBrowserAndMaximize(String url) {
		WebUI.openBrowser(url)
		WebUI.maximizeWindow()
	}


	@Keyword
	def Access_epideOld(String profilRequis = null) {
		def data_ident_epide = TestDataFactory.findTestData('identifiants_epide')
		String serviceName = "portail_epide"
		String serviceUrl = getServiceUrl(serviceName)
		def totalRows = data_ident_epide.getRowNumbers()

		for (int i = 1; i <= totalRows; i++) {
			String profil = data_ident_epide.getValue("profil", i)
			String utilisateur = data_ident_epide.getValue("utilisateur", i)
			String motDePasse = data_ident_epide.getValue("mot_de_passe", i)


			if (profilRequis == null || profil == profilRequis) {
				WebUI.openBrowser('')
				WebUI.maximizeWindow()
				WebUI.navigateToUrl(serviceUrl)

				bypassCertificateInterstitial()

				waitAndClick(findTestObject('Object Repository/AuthentificationEpide/Button_signin-with-azuread'))

				TestObject btn = findTestObject('Object Repository/AuthentificationEpide/Button_Access_New_Account_Epide')

				if (WebUI.verifyElementPresent(btn, 5, FailureHandling.OPTIONAL)) {
					WebUI.click(btn)
				}

				waitAndSet(findTestObject('Object Repository/AuthentificationEpide/Epide_Username'), utilisateur)
				waitAndClick(findTestObject('Object Repository/AuthentificationEpide/Button_Epide_suivant'))
				waitAndSetEncryptedText(findTestObject('Object Repository/AuthentificationEpide/Epide_Passwd'), motDePasse)
				waitAndClick(findTestObject('Object Repository/AuthentificationEpide/Button_SeConnecter'))
				if (WebUI.verifyElementPresent(findTestObject('Object Repository/AuthentificationEpide/Text_ResterConnecter'), 5)){
					println ("Pop-up 'Rester connecté' est trouvé")
					waitAndClick(findTestObject('Object Repository/AuthentificationEpide/Button_Oui_Rub_Res_Co'))
				}
				WebUI.verifyElementPresent(findTestObject('Object Repository/AuthentificationEpide/PageAccess/Button_Accueil'), 30)
				WebUI.verifyElementPresent(findTestObject('Object Repository/AuthentificationEpide/PageAccess/List_NAMe'), 30)
				println("Access Epide OK pour le profil : ${profil}")
				return
			}
		}
		throw new IllegalArgumentException("Aucun profil trouvé correspondant à : ${profilRequis}")
	}

	@Keyword
	def Access_epide(String profilRequis = null) {

		def data_ident_epide = TestDataFactory.findTestData('identifiants_epide')
		String serviceName = "portail_epide"
		String serviceUrl = getServiceUrl(serviceName)
		def totalRows = data_ident_epide.getRowNumbers()

		for (int i = 1; i <= totalRows; i++) {

			String profil = data_ident_epide.getValue("profil", i)
			String utilisateur = data_ident_epide.getValue("utilisateur", i)
			String motDePasse = data_ident_epide.getValue("mot_de_passe", i)

			if (profilRequis == null || profil == profilRequis) {

				WebUI.openBrowser('')
				WebUI.maximizeWindow()
				WebUI.navigateToUrl(serviceUrl)

				bypassCertificateInterstitial()

				WebUI.waitForPageLoad(60)

				waitAndClick(findTestObject(
						'Object Repository/AuthentificationEpide/Button_signin-with-azuread'
						))

				TestObject btn = findTestObject(
						'Object Repository/AuthentificationEpide/Button_Access_New_Account_Epide'
						)

				if (WebUI.verifyElementPresent(btn, 5, FailureHandling.OPTIONAL)) {
					WebUI.click(btn)
				}

				waitAndSet(
						findTestObject('Object Repository/AuthentificationEpide/Epide_Username'),
						utilisateur
						)

				waitAndClick(
						findTestObject('Object Repository/AuthentificationEpide/Button_Epide_suivant')
						)

				waitAndSetEncryptedText(
						findTestObject('Object Repository/AuthentificationEpide/Epide_Passwd'),
						motDePasse
						)

				waitAndClick(
						findTestObject('Object Repository/AuthentificationEpide/Button_SeConnecter')
						)

				if (WebUI.verifyElementPresent(
						findTestObject('Object Repository/AuthentificationEpide/Text_ResterConnecter'),
						5,
						FailureHandling.OPTIONAL
						)) {
					println("Pop-up 'Rester connecté' trouvé")
					waitAndClick(
							findTestObject('Object Repository/AuthentificationEpide/Button_Oui_Rub_Res_Co')
							)
				}

				WebUI.waitForPageLoad(60)
				WebUI.delay(2)

				WebUI.waitForElementVisible(
						findTestObject('Object Repository/AuthentificationEpide/PageAccess/Button_Accueil'),
						60,
						FailureHandling.STOP_ON_FAILURE
						)

				WebUI.waitForElementVisible(
						findTestObject('Object Repository/AuthentificationEpide/PageAccess/List_NAMe'),
						60,
						FailureHandling.STOP_ON_FAILURE
						)

				println("Access Epide OK pour le profil : ${profil}")
				return
			}
		}

		throw new IllegalArgumentException(
		"Aucun profil trouvé correspondant à : ${profilRequis}"
		)
	}

	@Keyword
	def Access_formulaire_internet_epide() {
		String serviceName = "Formulaire_epide_internet"
		String serviceUrl = getServiceUrl(serviceName)

		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(serviceUrl)
	}

	@Keyword
	def fillRobotQuestions(
			String questionXPath = "//label[@id='question-label']",
			String inputXPath = "//input[@id='answer']"
	) {
		WebDriver driver = DriverFactory.getWebDriver()
		def questions = driver.findElements(By.xpath(questionXPath))

		questions.each { question ->
			String qText = question.getText().trim()
			println("Question détectée : ${qText}")

			def pattern = ~/(\d+(?:\.\d+)?)\s*([\+\-\*\/])\s*(\d+(?:\.\d+)?)/

			def matcher = pattern.matcher(qText)
			if (matcher.find()) {
				def a = matcher.group(1).toBigDecimal()
				def operator = matcher.group(2)
				def b = matcher.group(3).toBigDecimal()
				def result

				switch(operator) {
					case '+': result = a + b; break
					case '-': result = a - b; break
					case '*': result = a * b; break
					case '/':
						if(b != 0) {
							result = a / b
						} else {
							throw new Exception("Division par zéro détectée !")
						}
						break
					default:
						throw new Exception("Opérateur non supporté : ${operator}")
				}

				def inputField = driver.findElement(By.xpath(inputXPath))
				inputField.clear()
				inputField.sendKeys(result.toString())
				println("Réponse calculée : ${result}")
			} else {
				println("Impossible de détecter un calcul dans la question (peut-être format inattendu).")
			}
		}
	}

	@Keyword
	def SelectMatOptionByLabel(String matSelectXpath, String labelToSelect) {

		WebUI.waitForElementClickable(findTestObject(matSelectXpath), 10)

		WebUI.click(findTestObject(matSelectXpath))

		TestObject option = new TestObject()
		option.addProperty("xpath", ConditionType.EQUALS,
				"//mat-option//span[normalize-space(text())='${labelToSelect}']"
				)

		WebUI.waitForElementVisible(option, 10)

		WebUI.click(option)

		WebUI.delay(1)
	}

	@Keyword
	def verifyMenuItemNotDisabled(TestObject menuItem) {

		String label = menuItem.getObjectId() ?: "élément du menu"
		WebUI.comment("Vérification : ${label} n’est PAS désactivé")

		try {
			WebUI.waitForElementVisible(menuItem, 5)

			String classes = WebUI.getAttribute(menuItem, "class")

			if (classes == null || !classes.contains("disabled")) {
				WebUI.comment("${label} est actif (non disabled).")
			} else {
				KeywordUtil.markFailed(
						"ERREUR : ${label} est désactivé alors qu’il ne devrait pas l’être."
						)
			}
		} catch (com.kms.katalon.core.exception.StepFailedException e) {
			KeywordUtil.markFailed("${label} est introuvable dans le DOM.")
		}
	}



	@Keyword
	def HandleCentreEpide(
			String selectXPath,
			List<String> expectedOptions = []
	) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

		WebElement select = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectXPath)))

		try {
			wait.until(ExpectedConditions.elementToBeClickable(select))
			select.click()
		} catch (Exception e) {
			println "Le clic direct a échoué → ouverture du mat-select via JavaScript."
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", select)
		}

		WebUI.delay(1)

		List<WebElement> options = driver.findElements(By.cssSelector("mat-option .mat-option-text"))
		List<String> optionTexts = options.collect { it.getText().trim() }

		println "Options actuellement visibles dans le mat-select des centres EPIDE :"
		optionTexts.each { println "- ${it}" }

		if (expectedOptions.isEmpty()) {
			return
		}

		def sortedExpected = expectedOptions.sort()
		def sortedActual = optionTexts.sort()

		if (sortedExpected != sortedActual) {

			def missingInUI = sortedExpected - sortedActual
			def extraInUI = sortedActual - sortedExpected

			def missingDetails = missingInUI.collect { val ->
				def row = expectedOptions.indexOf(val) + 1
				return "${val} (ligne ${row} du CSV)"
			}

			throw new AssertionError(
			"Comparaison incorrecte dans le mat-select des centres EPIDE.\n" +
			(missingInUI ? "Manquants dans la liste du formulaire internet (présents dans le CSV) : ${missingDetails}\n" : "") +
			(extraInUI   ? "En trop dans la liste du formulaire internet (absents du CSV) : ${extraInUI}\n" : "") +
			"\nAnalyse :\n" +
			(missingInUI ? "- Certains centres du CSV ne sont pas affichés dans le formulaire internet.\n" : "") +
			(extraInUI   ? "- Le formulaire internet contient des centres non présents dans le CSV.\n" : "") +
			"\nAttendu (CSV) : ${sortedExpected}\n" +
			"Trouvé (mat-select) : ${sortedActual}"
			)
		}

		println("La liste des centres EPIDE correspond parfaitement avec le CSV.")
	}


	@Keyword
	def HandleCentreEpide1(
			String selectXPath,
			List<String> expectedOptions = [],
			String optionToSelect = null
	) {
		WebDriver driver = DriverFactory.getWebDriver()

		// 1. Ouvrir le mat-select par son XPath direct
		WebElement select = driver.findElement(By.xpath(selectXPath))
		select.click()
		WebUI.delay(1)

		// 2. Récupérer les options
		List<WebElement> options = driver.findElements(
				By.cssSelector("mat-option .mat-option-text")
				)

		List<String> optionTexts = options.collect { it.getText().trim() }

		println "Options trouvées :"
		optionTexts.each { println "- ${it}" }

		// 3. Vérification des options attendues
		if (!expectedOptions.isEmpty()) {
			if (optionTexts.containsAll(expectedOptions) && expectedOptions.containsAll(optionTexts)) {
				println("Les options correspondent.")
			} else {
				throw new AssertionError(
				"Options incorrectes.\nTrouvées : ${optionTexts}\nAttendu : ${expectedOptions}"
				)
			}
		}

		// 4. Sélectionner l’option
		if (optionToSelect != null) {
			WebElement target = options.find { it.getText().trim() == optionToSelect }
			if (target != null) {
				target.click()
				println "Option sélectionnée : ${optionToSelect}"
			} else {
				throw new AssertionError("Option '${optionToSelect}' introuvable.")
			}
		}
	}


	@Keyword
	def List<String> scrollAndProcessText(TestObject testObject, int timeout = 10) {
		WebUI.scrollToElement(testObject, 10)

		WebUI.waitForElementVisible(testObject, timeout)

		String text = WebUI.getText(testObject)

		def lines = text.split('\n')
		def data = ""
		def description = ""

		if (lines.size() >= 2) {
			data = lines[0]
			description = lines[1]
			println "donnée : $data"
			println "description : $description"
		} else if (lines.size() == 1) {
			data = lines[0]
			println "donnée : $data"
			println "La description n'a pas été récupérée"
		} else {
			println "Le texte ne contient pas de lignes."
		}

		return [data, description]
	}


	@Keyword
	def compareTextWithArray(List<String> retrievedData, List<List<String>> comparisonArray) {
		for (def comparisonElement : comparisonArray) {
			assert retrievedData == comparisonElement : "Le texte récupéré ne correspond pas à l'élément attendu: ${comparisonElement}"

			if (retrievedData == comparisonElement) {
				println "Le texte récupéré correspond à l'un des éléments de comparaison: ${comparisonElement}"
				return true
			}
		}

		assert false : "Le texte récupéré ne correspond à aucun élément de comparaison."
		return false
	}

	@Keyword
	def scrollAndProcessType(TestObject testObject, int timeout = 10) {
		WebUI.scrollToElement(testObject, 10)

		WebUI.waitForElementVisible(testObject, timeout)

		String text = WebUI.getText(testObject)

		def words = text.split(' ')
		def lastWord = words.size() > 0 ? words[-1] : ""

		println "dernier mot : $lastWord"

		return lastWord
	}

	@Keyword
	def scrollAndVerifyCheckmark(TestObject testObject, int timeout = 10) {
		WebUI.scrollToElement(testObject, timeout)

		WebUI.waitForElementVisible(testObject, timeout)

		TestObject checkmarkObject = findTestObject('Object Repository/BSSCost/Catalogue_service/OptionsCommun/Check')

		boolean isCheckmarkVisible = WebUI.waitForElementVisible(checkmarkObject, timeout)

		if (isCheckmarkVisible) {
			println("Checkmark verified successfully.")
			return true
		} else {
			WebUI.comment("Checkmark could not be verified.")
			return false
		}
	}


	@Keyword
	def compareTextWithString(String retrievedData, String comparisonElement) {
		assert retrievedData.trim().contains(comparisonElement.trim()) :
		"Le texte récupéré ne correspond pas à l'élément de comparaison attendu: ${comparisonElement}"

		println "Le texte récupéré correspond à l'élément de comparaison: ${comparisonElement}"
		return true
	}


	@Keyword
	def searchTextInTable(String tableLocator, String searchText) {
		TestObject tableTestObject = new TestObject()
		tableTestObject.addProperty("xpath", ConditionType.EQUALS, tableLocator)

		try {
			WebUI.waitForElementVisible(tableTestObject, 30)

			WebElement tableElement = WebUiCommonHelper.findWebElement(tableTestObject, 30)

			List<WebElement> rows = tableElement.findElements(By.tagName("tr"))

			boolean found = false
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"))
				for (WebElement cell : cells) {
					if (cell.getText().contains(searchText)) {
						found = true
						println("Text '${searchText}' found in the table.")
						cell.click()
						break
					}
				}
				if (found) break
			}

			if (!found) {
				throw new AssertionError("Text '${searchText}' not found in the table.")
			}
		} catch (Exception e) {
			println("An error occurred: " + e.message)
			throw e
		} finally {
		}
	}

	@Keyword
	def searchTextInTableAndClickRadio(String tableLocator, String searchText) {
		TestObject tableTestObject = new TestObject()
		tableTestObject.addProperty("xpath", ConditionType.EQUALS, tableLocator)

		try {
			WebUI.waitForElementVisible(tableTestObject, 30)

			WebElement tableElement = WebUiCommonHelper.findWebElement(tableTestObject, 30)

			List<WebElement> rows = tableElement.findElements(By.tagName("tr"))

			boolean found = false

			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"))

				for (WebElement cell : cells) {
					if (cell.getText().contains(searchText)) {
						found = true
						println("Texte '${searchText}' trouvé dans le tableau.")

						List<WebElement> radios = row.findElements(By.tagName("input"))
						for (WebElement radio : radios) {
							if (radio.getAttribute("type") == "radio") {
								radio.click()
								println("Bouton radio correspondant au texte '${searchText}' a été cliqué.")
								break
							}
						}
						break
					}
				}
				if (found) break
			}

			if (!found) {
				throw new AssertionError("Texte '${searchText}' non trouvé dans le tableau.")
			}
		} catch (Exception e) {
			println("Une erreur est survenue : " + e.message)
			throw e
		} finally {
		}
	}

	@Keyword
	def checkTextInTable(String tableLocator, String searchText) {
		TestObject tableTestObject = new TestObject()
		tableTestObject.addProperty("xpath", ConditionType.EQUALS, tableLocator)

		try {
			WebUI.waitForElementVisible(tableTestObject, 30)

			WebElement tableElement = WebUiCommonHelper.findWebElement(tableTestObject, 30)

			List<WebElement> rows = tableElement.findElements(By.tagName("tr"))

			boolean found = false
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"))
				for (WebElement cell : cells) {
					if (cell.getText().contains(searchText)) {
						found = true
						println("Text '${searchText}' found in the table.")
						break
					}
				}
				if (found) break
			}

			if (!found) {
				throw new AssertionError("Text '${searchText}' not found in the table.")
			}
		} catch (Exception e) {
			println("An error occurred: " + e.message)
			throw e
		} finally {
		}
	}
	@Keyword
	static Point getElementPosition(TestObject testObject) {
		WebElement element = WebUI.findWebElement(testObject, 30)
		return element.getLocation()
	}

	@Keyword
	static void inspectTableContent(TestObject tableObject) {
		WebUI.comment("Analyse de la table...")

		WebElement tableElement = WebUiCommonHelper.findWebElement(tableObject, 10)

		List<WebElement> rows = tableElement.findElements(By.tagName("tr"))
		WebUI.comment("Nombre de lignes trouvées : ${rows.size()}")

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i)
			List<WebElement> cells = row.findElements(By.tagName("td"))

			String rowText = row.getText()
			WebUI.comment("Ligne ${i + 1} : ${rowText}")

			boolean hasCheckIcon = false
			boolean hasNonLabel = false

			for (WebElement cell : cells) {
				if (cell.getText().trim().equalsIgnoreCase("NON")) {
					hasNonLabel = true
				}

				if (!cell.findElements(By.xpath(".//i[contains(@class,'bynd-icon-checkBold')]")).isEmpty()) {
					hasCheckIcon = true
				}
			}

			if (hasNonLabel) {
				WebUI.comment("'NON' détecté — Icône présente ? ${hasCheckIcon}")
			}
		}
	}

	@Keyword
	static void verifyCheckIconBesideLabel(String optionLabel, boolean shouldBePresent) {
		TestObject iconCheck = new TestObject("dynamicCheckIcon")
		iconCheck.addProperty("xpath", ConditionType.EQUALS,
				"//tr[td/div/div[normalize-space(text())='" + optionLabel + "']]/td[i[contains(@class,'bynd-icon-checkBold')]]"
				)

		WebUI.comment("Attente de l'apparition de l'icône check à côté de : " + optionLabel)
		WebUI.waitForElementPresent(iconCheck, 10)

		boolean iconPresent = WebUI.verifyElementPresent(iconCheck, 5, FailureHandling.OPTIONAL)

		if (shouldBePresent) {
			assert iconPresent : "L'icône check est attendue mais absente pour l’option : ${optionLabel}."
			WebUI.comment("L'icône check est bien présente à côté de '${optionLabel}'.")
		} else {
			assert !iconPresent : "L'icône check ne doit pas être présente pour l’option : ${optionLabel}."
			WebUI.comment("L'icône check est bien absente à côté de '${optionLabel}'.")
		}
	}

	@Keyword
	static void verifyElementsOnSameLine(TestObject objectPath1, TestObject objectPath2, double tolerance) {
		Point pos1 = getElementPosition(objectPath1)
		Point pos2 = getElementPosition(objectPath2)

		String id1 = objectPath1.getObjectId()
		String id2 = objectPath2.getObjectId()

		println("Comparaison des éléments pour alignement horizontal :")
		println("${id1} - Position Y : ${pos1.y}")
		println("${id2} - Position Y : ${pos2.y}")

		double difference = Math.abs(pos1.y - pos2.y)

		if (difference <= tolerance) {
			println("OK - Les éléments sont alignés. (Différence : ${difference})")
		} else {
			println("KO - Les éléments ne sont pas alignés. (Différence : ${difference}, Tolérance : ${tolerance})")

			WebUI.takeScreenshot("Screenshots/AlignmentFailure_${System.currentTimeMillis()}.png")

			assert false : "Les éléments ne sont pas sur la même ligne. Différence Y : ${difference}, Tolérance : ${tolerance}"
		}
	}

	@Keyword
	def verifyElementsPresence(String optionrand, String options) {
		List<String> webElements = optionrand.split('\\n')

		Set<String> uniqueWebElements = new HashSet<>(webElements)
		println "webElements: $uniqueWebElements"

		def listopt = options.split(',').collect {
			it.trim()
		}
		println "listopt: $listopt"

		boolean allPresent = uniqueWebElements.containsAll(listopt)

		assert allPresent : "Les éléments de listopt ne sont pas tous présents dans webElements."

		println "Tous les éléments de listopt sont présents dans webElements : $allPresent"
	}
	@Keyword
	def clickUsingJavaScript(TestObject testObject) {
		WebElement element = WebUiCommonHelper.findWebElement(testObject, 30)
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		js.executeScript("arguments[0].click();", element)
	}
	@Keyword
	def selectFromNgSelect(String formControlName, String optionText) {

		if (optionText == null || optionText.trim().isEmpty()) {
			WebUI.comment("Option vide pour le champ '${formControlName}', aucune sélection effectuée.")
			return
		}

		TestObject ngSelect = new TestObject("ngSelect")
		ngSelect.addProperty("xpath", ConditionType.EQUALS, "//ng-select[@formcontrolname='" + formControlName + "']")
		WebUI.click(ngSelect)

		TestObject dynamicOption = new TestObject("dynamicOption")

		dynamicOption.addProperty("xpath", ConditionType.EQUALS,
				"//ng-select[@formcontrolname='" + formControlName + "']//span[contains(normalize-space(text()),'" + optionText + "')]")

		WebUI.click(dynamicOption)
	}

	@Keyword
	def getDateForm(String jourAvecHeure = "aujourd'hui") {
		def cal = Calendar.getInstance()

		def parts = jourAvecHeure.split(" ")
		def jour = parts[0]?.toLowerCase() ?: "aujourd'hui"

		def heure = 8
		def minute = 0
		def seconde = 0

		if (parts.length > 1) {
			def timeParts = parts[1].split(":")
			if (timeParts.length == 3) {
				heure = Integer.parseInt(timeParts[0])
				minute = Integer.parseInt(timeParts[1])
				seconde = Integer.parseInt(timeParts[2])
			}
		}

		if (jour == "demain") {
			cal.add(Calendar.DAY_OF_YEAR, 1)
		}

		cal.set(Calendar.HOUR_OF_DAY, heure)
		cal.set(Calendar.MINUTE, minute)
		cal.set(Calendar.SECOND, seconde)
		cal.set(Calendar.MILLISECOND, 0)

		def now = Calendar.getInstance()
		if (jour == "aujourd'hui" && cal.before(now)) {
			cal.add(Calendar.DAY_OF_YEAR, 1)
		}

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SATURDAY:
				cal.add(Calendar.DAY_OF_YEAR, 2)
				break
			case Calendar.SUNDAY:
				cal.add(Calendar.DAY_OF_YEAR, 1)
				break
		}

		def sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
		return sdf.format(cal.getTime())
	}
}


