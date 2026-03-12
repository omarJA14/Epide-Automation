import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.openqa.selenium.Keys

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject(
//    'Object Repository/RetenueFinanciere/Page_Simplicit/table_retenue_financiere'
//),
//	'Statut',
//	"A valider DIORRE"
//)

CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldEmptyOrNot'(
	findTestObject('Object Repository/Volontaire/Page_Simplicit/table_RelevesMensuels'),
	"Code Tiers",
	false   // false = on attend une valeur, true = on attend vide
)

//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnStatutRF'("A valider SEF")

//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldRFAndClick1'(
//	findTestObject(
//	'Object Repository/RetenueFinanciere/Page_Simplicit/table_retenue_financiere'),'Statut',"A valider SEF")

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
//        findTestObject('Object Repository/Absence/Page_Simplicit/Elem_Pedagogique')
//    )

//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Pédagogique'
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Renvoi temporaire à titre conservatoire'
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Congés maternité'
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Hors Cursus'
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Maladie'
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Accident du travail'
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(findTestObject("Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre"), "2")
//
//WebUI.back()
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence'),
//	'Libellé',
//	'Congé paternité'
//)
//






//String resourceId = "48107"
//String dateCible = "2026-03-03"
//
//// Fonction JS pour scroll et trouver la cellule
//String jsFindCell = """
//return new Promise((resolve) => {
//    function tryFind() {
//        var row = document.querySelector('tr[data-resource-id="${resourceId}"]');
//        if(row){
//            row.scrollIntoView({behavior:'auto', block:'center'});
//            var cell = row.querySelector('td[data-date="${dateCible}"]');
//            if(cell){
//                resolve(cell);
//                return;
//            }
//        }
//        resolve(null);
//    }
//    tryFind();
//});
//"""
//
//// On attend jusqu'à 30 secondes que la cellule apparaisse
//boolean found = false
//int tries = 0
//while(!found && tries < 60){  // 60 x 0.5s = 30s
//    Object cell = WebUI.executeJavaScript(jsFindCell, null)
//    if(cell != null){
//        // Scroll et clique via JS
//        WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'}); arguments[0].click();", Arrays.asList(cell))
//        WebUI.comment("✅ Cellule du jeune ${resourceId} pour la date ${dateCible} cliquée")
//        found = true
//        break
//    } else {
//        WebUI.delay(0.5)
//        tries++
//    }
//}
//
//if(!found){
//    WebUI.comment("❌ Impossible de trouver la cellule du jeune ${resourceId} pour la date ${dateCible}")
//}
//// --- CONFIGURATION ---
//def jeunes = ["48107", "48108", "48109"]   // IDs des jeunes
//def dates = ["2026-03-03", "2026-03-04"]   // Dates ciblées
//
//// Temps d'attente par défaut
//int timeoutVisible = 30
//int timeoutClickable = 10
//
//// Conteneur du calendrier (utile si scroll global nécessaire)
//String calendarContainerSelector = "div.fc-timeline-body"
//
//// --- SCRIPT ---
//for (String resourceId : jeunes) {
//	for (String dateCible : dates) {
//
//		// TestObject dynamique pour la cellule du jeune à la date
//		TestObject cellJeune = new TestObject("cell_${resourceId}_${dateCible}")
//		cellJeune.addProperty(
//			"xpath",
//			ConditionType.EQUALS,
//			"//tr[@data-resource-id='${resourceId}']//td[@data-date='${dateCible}']"
//		)
//
//		// Scroll vertical pour s'assurer que la ligne du jeune est visible
//		WebUI.executeJavaScript(
//			"var row = document.querySelector('tr[data-resource-id=\"${resourceId}\"]');" +
//			"if(row){ row.scrollIntoView({behavior:'smooth', block:'center'}); }",
//			null
//		)
//
//		// Attendre que la cellule soit visible et cliquable
//		if (WebUI.waitForElementVisible(cellJeune, timeoutVisible)) {
//			WebUI.scrollToElement(cellJeune, 5)  // scroll horizontal sur la date
//			if (WebUI.waitForElementClickable(cellJeune, timeoutClickable)) {
//				WebUI.click(cellJeune)
//				WebUI.comment("Cellule du ${dateCible} pour le jeune ${resourceId} cliquée avec succès")
//			} else {
//				WebUI.comment("Cellule cliquable non trouvée pour ${dateCible}, jeune ${resourceId}")
//			}
//		} else {
//			WebUI.comment("Cellule visible non trouvée pour ${dateCible}, jeune ${resourceId}")
//		}
//	}
//}
// Date de départ

//LocalDate startDate = LocalDate.of(2026, 3, 5)
//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//
//// Les valeurs à injecter
//def valeurs_libelle = ["Pédagogique", "Accident du travail", "Congés maternité", "Maladie", "Hors Cursus",
//	"Congé paternité","Renvoi temporaire à titre conservatoire" ]
//
//for (int i = 0; i < valeurs_libelle.size(); i++) {
//
//    String dateCible = startDate.plusDays(i).format(formatter)
//    String valeurCourante = valeurs_libelle[i]
//
//    WebUI.comment("Date : ${dateCible} | Valeur : ${valeurCourante}")
//
//    TestObject dateCell = new TestObject("dateCell_" + dateCible)
//    dateCell.addProperty(
//        "xpath",
//        ConditionType.EQUALS,
//        "//td[@data-date='${dateCible}']"
//    )
//
//    WebUI.waitForElementVisible(dateCell, 10)
//    WebUI.waitForElementClickable(dateCell, 10)
//    WebUI.click(dateCell)
//
//    // Ouverture champ
//    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
//        findTestObject('Object Repository/Absence/Page_Simplicit/input_libelle_abrege_type_absence')
//    )
//
//    TestObject lib_select = findTestObject('Object Repository/Absence/Page_Simplicit/input_libelle_selec_type_absence')
//
//    // Injection dynamique A → B → C → D → E
//    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(lib_select, valeurCourante)
//    WebUI.sendKeys(lib_select, Keys.chord(Keys.ENTER))
//
//    // Si l'objet dynamique dépend de la valeur
//    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
//        findTestObject(valeurCourante)
//    )
//
//    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
//        findTestObject('Object Repository/Absence/Page_Simplicit/radio_Non_Justificatif_fourni')
//    )
//
//    WebUI.delay(2) // évite 7s fixes si possible
//
//    // 🔁 Retour
//    WebUI.back()
//    WebUI.waitForElementVisible(dateCell, 10)
//}
//// La date ciblée
//String dateCible = "2026-03-03"
//
//// Création du TestObject dynamique pour la cellule
//TestObject dateCell = new TestObject("dateCell")
//dateCell.addProperty(
//	"xpath",
//	ConditionType.EQUALS,
//	"//td[@data-date='${dateCible}']"
//)
//
//// Attendre que la cellule soit visible
//WebUI.waitForElementVisible(dateCell, 5)
//
//// Cliquer sur la cellule
//WebUI.click(dateCell)
//
//WebUI.comment("Cellule du ${dateCible} cliquée avec succès")
//WebDriver driver = DriverFactory.getWebDriver()
//
//String resourceId = "48107"
//String dateStr = "2026-03-03"
//
//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30))
//
//// 1️⃣ Attendre que la ligne du jeune soit visible
//WebElement resourceRow = wait.until {
//    driver.findElements(By.cssSelector("td.fc-timeline-lane.fc-resource"))
//          .find { it.getAttribute("data-resource-id") == resourceId }
//}
//
//if (resourceRow == null) {
//    WebUI.comment("Impossible de trouver la ligne pour le jeune $resourceId")
//    return
//}
//
//// Scroll vertical pour centrer la ligne
//((JavascriptExecutor)driver).executeScript(
//    "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", 
//    resourceRow
//)
//
//// 2️⃣ Scroll horizontal et attendre que la cellule du jour soit présente
//FluentWait<WebElement> cellWait = new FluentWait(resourceRow)
//    .withTimeout(Duration.ofSeconds(30))
//    .pollingEvery(Duration.ofMillis(500))
//    .ignoring(org.openqa.selenium.NoSuchElementException)
//
//WebElement dayCell = null
//cellWait.until {
//    // Récupérer toutes les cellules de la ligne
//    List<WebElement> cells = resourceRow.findElements(By.cssSelector("[data-date]"))
//
//    // Scroll horizontal pour forcer génération des cellules
//    cells.each { cell ->
//        String cellDate = cell.getAttribute("data-date")
//        if (cellDate < dateStr) {
//            ((JavascriptExecutor)driver).executeScript(
//                "arguments[0].scrollIntoView({behavior:'auto', block:'center', inline:'center'});", 
//                cell
//            )
//        }
//    }
//
//    // Trouver la cellule correspondant à la date
//    dayCell = cells.find { it.getAttribute("data-date") == dateStr }
//    return dayCell != null
//}
//
//if (dayCell == null) {
//    WebUI.comment("Aucune cellule trouvée pour la date $dateStr dans la ligne du jeune $resourceId")
//} else {
//    WebUI.comment("Cellule trouvée pour la date $dateStr !")
//}
//String dataResourceId = "49620" // ABI leon
//String dataDate = "2026-03-03" // Date cible
//
//// XPath pour l'intersection jeune/date
//String intersectionXpath = """
////tr[@role='row']//td[@data-resource-id='${dataResourceId}']/ancestor::tr
///following-sibling::tr/td[@class='fc-timeline-slot' and @data-date='${dataDate}']
//"""
//
//TestObject cell = new TestObject("cell_ABDOU_François_2026-03-03")
//cell.addProperty("xpath", ConditionType.EQUALS, intersectionXpath)
//
//// Clique sur la cellule exacte
//WebUI.click(cell)



//TestObject expanderIcon = new TestObject("expanderIcon")
//expanderIcon.addProperty(
//	"xpath",
//	ConditionType.EQUALS,
//	"//th[.//span[text()='Bordeaux - Section 1']]//span[contains(@class,'fc-icon')]"
//)
//
///* 1️⃣ Vérifier état initial */
//String initialClass = WebUI.getAttribute(expanderIcon, "class")
//WebUI.comment("Classe initiale : " + initialClass)
//
///* 2️⃣ Cliquer */
//WebUI.click(expanderIcon)
//
///* 3️⃣ Attendre changement */
//WebUI.delay(1)
//
//String afterClickClass = WebUI.getAttribute(expanderIcon, "class")
//WebUI.comment("Classe après clic : " + afterClickClass)
//
///* 4️⃣ Vérifier que l'état a changé */
//assert initialClass != afterClickClass

//TestObject boutonCreer = findTestObject('Object Repository/Delegation/Page_Simplicit/button_Creer_candidat')
//WebUI.verifyElementVisible(boutonCreer, FailureHandling.STOP_ON_FAILURE)
//String nomDelegant = "nom d1"
//String prenomDelegant = "prénom d1"
//String role = "Directeur(trice) de centre"
//String nomMandataire = "prénom cre"
//String prenomMandataire = "nom cre"
//
//String xpath = """
////tbody[@data-list='list_NamRoleMandat_the_ajax_NamRoleMandat']
///tr[
//    .//td[@data-field='namRolmdtFromUsrId__usr_last_name']//div[text()='${nomDelegant}']
//    and
//    .//td[@data-field='namRolmdtFromUsrId__usr_first_name']//div[text()='${prenomDelegant}']
//    and
//    .//td[@data-field='namRolmdtFromUsrId__namUsrAgentTitre']//div[text()='${role}']
//    and
//    .//td[@data-field='namRolmdtToUsrId__usr_last_name']//div[text()='${nomMandataire}']
//    and
//    .//td[@data-field='namRolmdtToUsrId__usr_first_name']//div[text()='${prenomMandataire}']
//]
//"""
//
//TestObject row = new TestObject()
//row.addProperty("xpath", ConditionType.EQUALS, xpath)
//
//// Vérifie que la ligne existe
//WebUI.verifyElementPresent(row, 10)

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Sortie/Page_Simplicit/statut_Encours'),
//	'En cours', 20)
//
//WebUI.selectOptionByLabel(findTestObject('Object Repository/Sortie/Page_Simplicit/select_TypedeSortie'), "Contrat court", false)
//
//WebUI.selectOptionByLabel(findTestObject('Object Repository/Sortie/Page_Simplicit/select_MotifSortieAnticipee'), "Problème de santé, maladie", false)
//
//def radioMaprechercheemploi = [
//	"Oui" : 'Object Repository/Sortie/Page_Simplicit/radio_Oui_enRechecheEmploi',
//	"Non" : 'Object Repository/Sortie/Page_Simplicit/radio_Non_enRechecheEmploi'
//]
//
//CustomKeywords.'Keywords.commun.UtilsEpide.selectFromMap'("Non", radioMaprechercheemploi)
//TestObject resource = new TestObject()
//resource.addProperty(
//  "xpath",
//  ConditionType.EQUALS,
//  "//span[text()='AHAMADI Zayasse']/ancestor::td[@data-resource-id]"
//)
//
//WebUI.click(resource)
//
//TestObject dateCell = new TestObject()
//dateCell.addProperty(
//  "css",
//  ConditionType.EQUALS,
//  "td[data-date='2026-02-26']"
//)
//
//WebUI.click(dateCell)

// Nom de la ressource et date
//
//String resourceName = "AHAMADI Zayasse"  // Nom exact
//String date = "2026-02-26"      // Date exacte
//
//// XPath pour sélectionner la cellule correspondante
//String xpath = """
////td[contains(@class,'fc-datagrid-cell-main') and text()='${resourceName}']
///ancestor::tr
///following-sibling::tr[1]//td[@data-date='${date}']
//"""
//
//TestObject cellObj = new TestObject()
//cellObj.addProperty("xpath", ConditionType.EQUALS, xpath)
//
//WebUI.click(cellObj)

// Scroll en haut de la page pour forcer le rendu du bouton
// Récupérer le bouton via TestObject
//TestObject convoc_conseil_discip = findTestObject('Object Repository/FSE/Page_Simplicit/input_lesFaits')
//	WebElement element = WebUI.findWebElement(convoc_conseil_discip)
//	WebUI.executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", Arrays.asList(element))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(convoc_conseil_discip,
//		'Convocation conseil de discipline')
//		
//	TestObject btnConvoquer = findTestObject('Object Repository/FSE/Page_Simplicit/button_Convoquer')
//	WebUI.waitForElementVisible(btnConvoquer, 10)
//CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('CEC-Bordeaux')
//CustomKeywords.'Keywords.commun.UtilsEpide.searchFseAndClick'('TRATKatalonPrenom6')
//TestObject btn_exporter = findTestObject('Object Repository/FSE/Page_Simplicit/button_Exporter')
//CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_exporter, 15)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_popup_Exporter'))
//WebUI.delay(5)
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyDownloadByFirstname'('TRATKatalonPrenom6', 30)


//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
//	findTestObject('Object Repository/Convention_Stage/Page_Simplicit/table_ConventionsdeStage'),
//	"Statut de la convention de stage",
//	"Signé"
//)


//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldSmart'(
//	findTestObject('Object Repository/Examen/Page_Simplicit/table_inscrip_exam'),
//	"Prénom",
//	"TRATKatalonPrenom6"
//)

//CustomKeywords.'Keywords.commun.UtilsEpide.searchInRub'("inscription_exam","Ayan")
//		
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Examen/Page_Simplicit/button_EditerlaListe'))
//	
//	TestObject element_sais = findTestObject('Object Repository/Examen/Page_Simplicit/input_NbModValid_InscriptionExam')
//	
//	WebUI.verifyElementNotHasAttribute(element_sais, "readonly", 5)
//		
//def data_entretien_fse = TestDataFactory.findTestData("Fse/validg_fse")
//def totalRows = data_entretien_fse.getRowNumbers()
//
//for (int i = 1; i <= totalRows; i++) {
//	
//	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
//	String ref_oblig_enfre = data_entretien_fse.getValue("Références aux obligations enfreintes - conseil discipline", i)
//	String avis_conseil_discip = data_entretien_fse.getValue("Avis du conseil de discipline", i)
//	String propos_sanct = data_entretien_fse.getValue("Proposition de sanction", i)
////CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
////	'Conseil de discipline', 20)
////
////CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_ConsDisp_ReferencesauxObligationsEnfreintes'), ref_oblig_enfre)
////
////CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_AvisduConseildeDiscipline'), avis_conseil_discip)
////
////WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_proposition_sanction'), propos_sanct, true)
//	
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ValidationDG'),
//		'Validation DG', 20)
//	
//	TestObject btn_exporter = findTestObject('Object Repository/FSE/Page_Simplicit/button_Exporter')
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_exporter, 15)
//	}

//TestObject btn_edit_convoc = findTestObject('Object Repository/FSE/Page_Simplicit/button_EditerConvocation')
//CustomKeywords.'Keywords.commun.UtilsEpide.clickGenerateWordEdge'(btn_edit_convoc, 15)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Convoquer'))
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
//	'Conseil de discipline', 20)

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
//	'Conseil de discipline', 20)
//WebUI.executeJavaScript("arguments[0].scrollIntoView(true);", Arrays.asList(element))
//TestObject monBoutonObj = findTestObject('Object Repository/FSE/Page_Simplicit/button_Convoquer')
//
//// Puis attendre et cliquer
//WebUI.waitForElementVisible(monBoutonObj, 10)
//def data_entretien_fse = TestDataFactory.findTestData("Fse/prog_d1_fse")
//def totalRows = data_entretien_fse.getRowNumbers()
//
//for (int i = 1; i <= totalRows; i++) {
//	
//	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
//	String directeur = data_entretien_fse.getValue("Nom du directeur du centre ou son représentant", i)
//	String secretaire = data_entretien_fse.getValue("Nom du secrétaire", i)
//	String rapporteur = data_entretien_fse.getValue("Nom du rapporteur", i)
//
//	TestObject dateField =
//	findTestObject('Object Repository/FSE/Page_Simplicit/input_DatedeConvocation')
//	
//	String dateTime =
//	CustomKeywords.'Keywords.commun.UtilsEpide.getDateTimePlusWorkingDays'(4, "10:00")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.setDateTimeField'(
//	dateField,
//	dateTime
//	)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduDirecteurduCentre'),
//		directeur
//	)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduSecretaire'),
//		secretaire
//	)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduRapporteur'),
//		rapporteur
//	)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_Convoquer'))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_ConseildeDiscipline'),
//		'Conseil de discipline')
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
//	
//}
//WebUI.closeBrowser()
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//
//import org.openqa.selenium.By
//import org.openqa.selenium.WebElement
//
//import com.kms.katalon.core.testobject.ConditionType
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.util.KeywordUtil
//import com.kms.katalon.core.webui.common.WebUiCommonHelper
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//
//import org.openqa.selenium.Keys
//
//import com.kms.katalon.core.testdata.TestDataFactory
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//
//def data_entretien_fse = TestDataFactory.findTestData("Fse/prog_fse")
//def totalRows = data_entretien_fse.getRowNumbers()
//
//for (int i = 1; i <= totalRows; i++) {
//	
//	String prenom = data_entretien_fse.getValue("Prénom du jeune", i)
//	String sanctions = data_entretien_fse.getValue("Sanction(s) disciplinaire(s) demandée(s)", i)
//	String prenom_agent = data_entretien_fse.getValue("Prénom de l'agent", i)
//
//
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_AssocierAgents'))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/FSE/Page_Simplicit/input_NomAgent'), prenom_agent)
//	
//	TestObject elem_prenom_agent = findTestObject('Object Repository/FSE/Page_Simplicit/input_NomAgent')
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(elem_prenom_agent, prenom_agent)
//	WebUI.sendKeys(elem_prenom_agent, Keys.chord(Keys.ENTER))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/checkbox_selectionner_agent'))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/FSE/Page_Simplicit/button_selectionner'))
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/table_agent_suivi_sanction'), "Nom de l'agent", prenom_agent)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'), "Statut de FSE", "Validation CSECi")
//	
//}
//
// Oui sélectionné
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyRadioSelection'(
//	"namFseValidCseciValidation",
//	"oui"
//)
//
//// Non sélectionné
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyRadioSelection'(
//	"namFseValidCseciValidation",
//	"non"
//)
//String datePlusWorkingDays =
//CustomKeywords.'Keywords.commun.UtilsEpide.getDateTimePlusWorkingDays'(4, "10:00")
//
//
//println(datePlusWorkingDays)
//
//TestObject dateField =
//findTestObject('Object Repository/FSE/Page_Simplicit/input_DatedeConvocation')
//
//String dateTime =
//CustomKeywords.'Keywords.commun.UtilsEpide.getDateTimePlusWorkingDays'(4, "10:00")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.setDateTimeField'(
//dateField,
//dateTime
//)

//CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//	findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduDirecteurduCentre'),
//	"prénom cec"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//	findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduSecretaire'),
//	"prénom cec"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.setMembreConseil'(
//	findTestObject('Object Repository/FSE/Page_Simplicit/input_NomduRapporteur'),
//	"prénom cec"
//)



// Aucune sélection
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyRadioSelection'(
//	"namFseValidCseciValidation",
//	"oui"
//)
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//def expectedValues = [
//	"namFseNum" : "notEmpty",
//	"namFseDateEvenement"   : "19/12/2025",
//	"namFseType"   : "DETERIORATION"
//]
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElements'(form, expectedValues)

//CustomKeywords.'Keywords.commun.UtilsEpide.checkCofNotifications'(
//	'GRH-Bordeaux',
//	'19/12/2025',
//	'Compte Rendu du COF',
//	'Yasmina',
//	['COF1', 'COF2', 'COF3']
//)
//namFseNum                           : 41 179
//namFseDateEvenement                 : 19/12/2025
//namFseType                          : DETERIORATION
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//		
//	form.each { k, v ->
//		println "${k} = ${v}"
//	}
//TestObject btnAdmettre = findTestObject('Recrutement/Commission_Admission/button_Admettre')
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyMenuItemNotDisabled'(btnAdmettre)

//CustomKeywords.'Keywords.commun.UtilsEpide.searchVolontaireAndClick'('tratkatalonprenom')
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Demande_Transfert/Check_AvisduCentredorigine'), "Avis du centre d'origine")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/button_SoumettreCentreDestination'))

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Rub_DemandesTransfert'))
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyDemandeTransfertTable'(
//	findTestObject('Object Repository/Demande_Transfert/table_demande_transfert'),
//	"Etat",
//	"Avis du centre de destination"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Demande_Transfert/Element_AvisCentreDestination'))
//	
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Demande_Transfert/Check_AvisduCentreDestination'),
//	'Avis du centre de destination')
//CustomKeywords.'Keywords.commun.UtilsEpide.verifierCentres'('Bordeaux', 'Lanrodec')
//TestObject tableCOF = findTestObject('Object Repository/COF/Page_COF/Table_COF')
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofs'(
//	tableCOF,
//	['COF1', 'COF2', 'COF3']
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/Element_Etat_EnCoursDeCreation'),
//	'En cours de création')

//CustomKeywords.'Keywords.commun.UtilsEpide.verifyElementTableContains'(
//		findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/tab_contrat'),
//		[
//			"Avenant de transfert de centre"
//		]
//	)
// Check dans Notification
//CustomKeywords.'Keywords.commun.UtilsEpide.checkCofNotifications'(
//	'GRH-Bordeaux',
//	'19/12/2025',
//	'Compte Rendu du COF',
//	"Abraham",
//	['COF1', 'COF2', 'COF3']
//)

//TestObject tableNotif = findTestObject(
//	'Object Repository/Volontaire/Page_Simplicit/table_notification'
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofCompteRenduNotification'(
//	tableNotif,
//	"Abraham",
//	"Le Compte Rendu du COF est disponible"
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofCompteRenduNotificationFromAccueil'(
//	"Abraham",
//	"Le Compte Rendu du COF est disponible"
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofCompteRenduNotificationFromAccueil'(
//	"GRH-Bordeaux",
//	"dayan",
//	"Le Compte Rendu du COF est disponible",
//	10
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/COF/Page_COF/Statut_Valide'),
//	'Validé')

//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_CreationFSE'),
//	'Création FSE')
//
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndVerifyText'(findTestObject('Object Repository/FSE/Page_Simplicit/Elem_EntretienCEC'),
//	'Entretien CEC')

//WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_TypedeFSE'), 'TUG', true)

//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableField'(
//	findTestObject('Object Repository/Transfert/Centre_GRH/Page_Simplicit/tab_contrat'),
//	"Type",
//	"Avenant de transfert de centre"
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyDemandeTransfertTable'(
//	findTestObject('Object Repository/Demande_Transfert/table_demande_transfert'),
//	"Etat",
//	"Avis du centre de destination"
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.searchInRubAndClick'("fse", "Lina")
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
//		findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'),
//		"Statut de FSE",
//		"Validation CSECi"
//	)
	
//	WebUI.selectOptionByLabel(findTestObject('Object Repository/FSE/Page_Simplicit/select_SanctionsDisciplinairesDemandes'), 'TUG', true);
	
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
//	findTestObject('Object Repository/FSE/Page_Simplicit/table_FSE'),
//	"Numéro de FSE",
//	"empty"
//)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFse'(
//		findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//		"Message",
//		"Détérioration"
//	)

//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
//	findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//	"N° de notification",
//	"notEmpty"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
//	findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//	"Type de notification",
//	"Alerte"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
//	findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//	"Nom de la notification",
//	"Nouvelle FSE"
//)
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
//	findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//	"Message",
//	"Une nouvelle FSE a été créée pour le jeune"
//)
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Notification/Page_Notification/Rub_AdministrationDesNotifications'))
//
//CustomKeywords.'Keywords.commun.UtilsEpide.searchNotification'('Nouvelle FSE')
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableNotification'(
//	findTestObject('Object Repository/Notification/Page_Notification/table_notification'),
//	"Message",
//	"Une nouvelle FSE a été créée pour le jeune"
//)

//
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//def expectedValues = [
//	"namFseNum" : "notEmpty",
//	"namFseDateEvenement"   : "19/12/2025",
//	"namFseType"   : "DETERIORATION"
//]
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareAndCheckElements'(form, expectedValues)


//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF1')
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/COF/Page_COF/page_Cof_Volontaire'))
//
//
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF2')
//CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/COF/Page_COF/page_Cof_Volontaire'))
//	
//	
//		CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF3')
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofStatus'("COF1", "ENCOURS",)

//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_AxesTravail')
//
//Map<String, String> expectedRow = [
//    "namAxetrvDomaine" : "Chargé(e) de recrutement des volontaires",
//    "namAxetrvConstat" : "TRA",
//    "namAxetrvPropObj" : "dd",
//    "namAxetrvMoyen"   : "dd"
//]
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifyRowExistsInTable'(tableObj, expectedRow)

//		TestObject tableObjs = findTestObject('Object Repository/COF/Page_COF/Table_COF')
	
//		WebUI.waitForElementVisible(tableObjs, 10)
//	
//		String tableXPaths = tableObjs.findPropertyValue("xpath")
//	
//		// Récupérer toutes les lignes
//		TestObject headerObjs = new TestObject().addProperty(
//		"xpath",
//		ConditionType.EQUALS,
//		tableXPaths + "//thead//tr[@class='head']//th//span"
//	)
//	
//	List<WebElement> headerss = WebUiCommonHelper.findWebElements(headerObjs, 10)
//	
//	KeywordUtil.logInfo("===== EN-TÊTES DU TABLEAU =====")
//	headerss.eachWithIndex { h, i ->
//		KeywordUtil.logInfo("Colonne ${i + 1} = ${h.getText().trim()}")
//	}
//	
//	
//	['COF1','COF2','COF3'].each { cof ->
//		TestObject tof = new TestObject()
//		tof.addProperty(
//			"xpath",
//			com.kms.katalon.core.testobject.ConditionType.EQUALS,
//			"//tbody//td[@data-field='namCofNumero']//div[normalize-space()='${cof}']"
//		)
//	
//		if (WebUI.verifyElementPresent(tof, 5, FailureHandling.OPTIONAL)) {
//			KeywordUtil.logInfo("${cof} présent")
//		} else {
//			KeywordUtil.markFailed("${cof} absent")
//		}
//	}
//	
//	
	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofs'(
//		tableObjs,
//		['COF1', 'COF2', 'COF3']
//	)
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF1')
//	TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//	def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form, "COF1")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form, [
//		"namCofNumero": "COF1"
//	])
//	
//	WebUI.back()
//	
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF2')
//	
//	def form1 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form1, "COF2")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form1, [
//		"namCofNumero": "COF2"
//	])
//	
//	WebUI.back()
//		
//	CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF3')
//	
//	def form2 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form2, "COF3")
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form2, [
//		"namCofNumero": "COF3"
//	])
	//*[@id="list_NamCof_the_ajax_NamCof"]/div/div[2]/form/div/table
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldReleve'(
//		findTestObject('Object Repository/Volontaire/Page_Simplicit/table_RelevesMensuels'),
//		"Code Tiers",
//		"0085221"
//	)
//	
//	TestObject tableObj = findTestObject('Object Repository/Volontaire/Page_Simplicit/table_notification')
//	
//	WebUI.waitForElementVisible(tableObj, 10)
//	
//	String tableXPath = tableObj.findPropertyValue("xpath")
//	
//	// Récupérer toutes les lignes
//	TestObject rowObj = new TestObject().addProperty(
//		"xpath",
//		ConditionType.EQUALS,
//		tableXPath + "//tbody//tr"
//	)
//	
//	List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObj, 10)
//	// Affichage simple
//	KeywordUtil.logInfo("===== CONTENU DU TABLEAU =====")
//	
//	rows.eachWithIndex { row, rowIndex ->
//		List<WebElement> cells = row.findElements(By.tagName("td"))
//		cells.eachWithIndex { cell, cellIndex ->
//			KeywordUtil.logInfo("Ligne ${rowIndex + 1} | Colonne ${cellIndex + 1} = ${cell.getText().trim()}")
//		}
//	}
	
//	TestObject tableNotif = findTestObject('Object Repository/Volontaire/Page_Simplicit/table_notification')
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.checkNotificationAndSelect'(
//		tableNotif,
//		'19/12/2025',
//		'Compte Rendu du COF',
//		'Yasmina',
//		'COF1'
//	)
//	CustomKeywords.'Keywords.commun.UtilsEpide.checkNotificationAndSelect'(
//		tableNotif,
//		'19/12/2025',
//		'Compte Rendu du COF',
//		'Yas',
//		'COF2'
//	)
//	
//	CustomKeywords.'Keywords.commun.UtilsEpide.checkNotificationAndSelect'(
//		tableNotif,
//		'19/12/2025',
//		'Compte Rendu du COF',
//		'Yas',
//		'COF3'
//	)
////	
////	
//	TestObject tableObjs = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//	
//	WebUI.waitForElementVisible(tableObjs, 10)
//	
//	String tableXPaths = tableObjs.findPropertyValue("xpath")
//	
//	// Récupérer toutes les lignes
//	TestObject headerObjs = new TestObject().addProperty(
//	"xpath",
//	ConditionType.EQUALS,
//	tableXPaths + "//thead//tr[@class='head']//th//span"
//)
//
//List<WebElement> headerss = WebUiCommonHelper.findWebElements(headerObjs, 10)
//
//KeywordUtil.logInfo("===== EN-TÊTES DU TABLEAU =====")
//headerss.eachWithIndex { h, i ->
//	KeywordUtil.logInfo("Colonne ${i + 1} = ${h.getText().trim()}")
//}
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldCof'(
//		findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF'),
//		"Code Tiers",
//		"0085221"
//	)


// -------------------- TEST OBJECT TABLE --------------------
//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//WebUI.waitForElementVisible(tableObj, 10)
//String tableXPath = tableObj.findPropertyValue("xpath")
//
//// -------------------- HEADERS --------------------
//TestObject headerObj = new TestObject().addProperty(
//    "xpath",
//    ConditionType.EQUALS,
//    tableXPath + "//thead//tr[@class='head']//th//span"
//)
//
//List<WebElement> headers = WebUiCommonHelper.findWebElements(headerObj, 10)
//List<String> headerNames = headers.collect { it.getText().trim() }
//
//KeywordUtil.logInfo("===== EN-TÊTES DU TABLEAU =====")
//headerNames.eachWithIndex { name, i ->
//    KeywordUtil.logInfo("Colonne ${i + 1} = ${name}")
//}
//
//// -------------------- LIGNES --------------------
//TestObject rowObj = new TestObject().addProperty(
//    "xpath",
//    ConditionType.EQUALS,
//    tableXPath + "//tbody//tr"
//)
//
//List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObj, 10)
//KeywordUtil.logInfo("===== VÉRIFICATION COMPLÈTE DU TABLEAU =====")
//
//rows.eachWithIndex { WebElement row, int rowIndex ->
//
//    List<WebElement> cells = row.findElements(By.tagName("td"))
//    boolean isBusinessRow = cells.any { it.getText().trim() }
//
//    if (!isBusinessRow) {
//        return // ignorer les lignes techniques vides
//    }
//
//    // -------------------- CHECKBOX --------------------
//    List<WebElement> checkboxes = row.findElements(By.xpath(".//input[@type='checkbox']"))
//    if (!checkboxes.isEmpty()) {
//        boolean isChecked = checkboxes[0].isSelected()
//        KeywordUtil.logInfo("Ligne ${rowIndex + 1} | Checkbox 1 = " + (isChecked ? "COCHÉE" : "NON COCHÉE"))
//    } else {
//        KeywordUtil.logInfo("Ligne ${rowIndex + 1} | Checkbox 1 = ABSENTE")
//    }
//
//    // -------------------- CELLULES MÉTIER --------------------
//    cells.eachWithIndex { WebElement cell, int colIndex ->
//
//    // Récupérer tout le texte visible même dans les enfants
//    String value = cell.getText().trim()
//
//    String columnName = colIndex < headerNames.size() ? headerNames[colIndex] : "Colonne ${colIndex + 1}"
//
//    if (value.isEmpty()) {
//        KeywordUtil.markFailed("Valeur vide détectée | Ligne ${rowIndex + 1} | ${columnName}")
//    } else {
//        KeywordUtil.logInfo("Ligne ${rowIndex + 1} | ${columnName} = ${value}")
//    }
//}
//}
//List<String> ListCofs = ['COF1', 'COF2', 'COF3']
//List<String> StatutCofs = ['Préparation', 'Préparation', 'Préparation']
//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')

//ListCofs.each { cof ->
//    CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldCof'(tableObj, "N° COF", cof)
//}
//StatutCofs.each { cof ->
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldCof'(tableObj, "Statut du COF", cof)
//}

//Map<String, String> cofStatuts = [
//	'COF1': 'En cours',
//	'COF2': 'Préparation',
//	'COF3': 'Préparation'
//]
//
//cofStatuts.each { cof, statut ->
//	CustomKeywords.'Keywords.commun.UtilsEpide.verifyCofWithStatus'(
//		tableObj,
//		cof,
//		statut
//	)
//}

//
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//form.each { k, v ->
//println "${k} = ${v}"
//}

//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF1')
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//form.each { k, v ->
//println "${k} = ${v}"
//}
//def expValues = [
//	"namCofNumero"                     : "COF1"
//]
//['namCofSyntheseGlobalePr', 'namCofSyntheseGlobaleDx', 'namCofSyntheseGlobaleTr'].each { field ->
//    String value = form[field]
//
//    if (value == null || value.trim().isEmpty()) {
//        KeywordUtil.markFailed("Champ '${field}' vide ou absent")
//    } else {
//        KeywordUtil.logInfo("Champ '${field}' = '${value}'")
//    }
//}
//
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form, expValues)
//
//
//WebUI.back()
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF2')
//def form1 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//form1.each { k, v ->
//println "${k} = ${v}"
//}
//def expecValues = [
//	"namCofNumero"                     : "COF2"
//]
//['namCofSyntheseGlobalePr', 'namCofSyntheseGlobaleDx', 'namCofSyntheseGlobaleTr'].each { field ->
//    String value = form[field]
//
//    if (value == null || value.trim().isEmpty()) {
//        KeywordUtil.markFailed("Champ '${field}' vide ou absent")
//    } else {
//        KeywordUtil.logInfo("Champ '${field}' = '${value}'")
//    }
//}
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form1, expecValues)
//
//WebUI.back()
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF3')
//def form2 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//form2.each { k, v ->
//println "${k} = ${v}"
//}
//
//['namCofSyntheseGlobalePr', 'namCofSyntheseGlobaleDx', 'namCofSyntheseGlobaleTr'].each { field ->
//    String value = form[field]
//
//    if (value == null || value.trim().isEmpty()) {
//        KeywordUtil.markFailed("Champ '${field}' vide ou absent")
//    } else {
//        KeywordUtil.logInfo("Champ '${field}' = '${value}'")
//    }
//}
//def expectedValues = [
//	"namCofNumero"                     : "COF3"
//]
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form2, expectedValues)



//TestObject tableObj = findTestObject('Object Repository/COF/Page_COF/table_Volontaires_COF')
//
//
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF1')
//
//def form = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form, "COF1")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form, [
//	"namCofNumero": "COF1"
//])
//
//WebUI.back()
//
//
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF2')
//
//def form1 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form1, "COF2")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form1, [
//	"namCofNumero": "COF2"
//])
//
//WebUI.back()
//
//
//CustomKeywords.'Keywords.commun.UtilsEpide.clickOnCof'(tableObj, 'COF3')
//
//def form2 = CustomKeywords.'Keywords.commun.UtilsEpide.extractElements'("work")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.verifySyntheseByStep'(form2, "COF3")
//
//CustomKeywords.'Keywords.commun.UtilsEpide.compareElements'(form2, [
//	"namCofNumero": "COF3"
//])
