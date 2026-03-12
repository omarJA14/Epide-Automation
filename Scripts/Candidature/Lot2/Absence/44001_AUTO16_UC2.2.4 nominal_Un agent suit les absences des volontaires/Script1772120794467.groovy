import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('GRH-Bordeaux')
	
CustomKeywords.'Keywords.commun.UtilsEpide.AccessFeuillePresence'()
		
	def sections = [
    'Bordeaux - Section 1',
    'Bordeaux - Section 2',
    'Bordeaux - Section 3',
    'Bordeaux - Section 4',
    'Bordeaux - Section 5'
]

sections.each { sectionName ->

    WebUI.comment("Traitement : " + sectionName)

    TestObject expanderIcon = new TestObject("expander_${sectionName}")
    expanderIcon.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//th[.//span[normalize-space(text())='${sectionName}']]//span[contains(@class,'fc-icon')]"
    )

    WebUI.waitForElementClickable(expanderIcon, 10)

    WebElement element = WebUiCommonHelper.findWebElement(expanderIcon, 10)
    String initialClass = element.getAttribute("class")

    element.click()

    WebDriver driver = DriverFactory.getWebDriver()
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

    wait.until {
        element.getAttribute("class") != initialClass
    }

    String afterClickClass = element.getAttribute("class")

    WebUI.comment("Classe initiale : " + initialClass)
    WebUI.comment("Classe après clic : " + afterClickClass)

    assert initialClass != afterClickClass
}

CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
	findTestObject('Object Repository/Absence/Page_Simplicit/Rub_FeuilledePresence')
)

LocalDate startDate = LocalDate.of(2026, 3, 5)
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

def valeurs_libelle = ["Pédagogique", "Accident du travail", "Congés maternité", "Maladie", "Hors Cursus",
	"Congé paternité","Renvoi temporaire à titre conservatoire" ]

for (int i = 0; i < valeurs_libelle.size(); i++) {

	String dateCible = startDate.plusDays(i).format(formatter)
	String valeurCourante = valeurs_libelle[i]

	WebUI.comment("Date : ${dateCible} | Valeur : ${valeurCourante}")

	TestObject dateCell = new TestObject("dateCell_" + dateCible)
	dateCell.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//td[@data-date='${dateCible}']"
	)

	WebUI.waitForElementVisible(dateCell, 10)
	WebUI.waitForElementClickable(dateCell, 10)
	WebUI.click(dateCell)

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
		findTestObject('Object Repository/Absence/Page_Simplicit/input_libelle_abrege_type_absence')
	)

	TestObject tableLibelle = findTestObject(
		'Object Repository/Absence/Page_Simplicit/table_libelle'
	)
	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
		tableLibelle,
		'Libellé',
		valeurCourante
	)

	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
		findTestObject('Object Repository/Absence/Page_Simplicit/radio_Non_Justificatif_fourni')
	)

	WebUI.delay(2) 

	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	WebUI.back()
	WebUI.waitForElementVisible(dateCell, 10)
}
	
//WebUI.closeBrowser()