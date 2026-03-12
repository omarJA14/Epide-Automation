import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def data_promo = TestDataFactory.findTestData("Promotion/creer_promotion")
def totalRows = data_promo.getRowNumbers()

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('Admin_National')

for (int i = 1; i <= totalRows; i++) {
	String centre_epide = data_promo.getValue("Centre EPIDE", i)
	String libelle = data_promo.getValue("Libellé", i)
	String date_arrivee = data_promo.getValue("Date d'arrivée", i)
	String prenom = data_promo.getValue("Prénom du jeune", i)
		
	CustomKeywords.'Keywords.commun.UtilsEpide.AccessPromotions'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/button_Creer'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/input_CentreEPIDE_promo'))
	
	TestObject select_ce = findTestObject('Object Repository/Promotion/Page_Simplicit/input_Select_centreEpide_Ville')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(select_ce, centre_epide)
	WebUI.sendKeys(select_ce, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/Element_Ville_CentreEpide'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(findTestObject('Object Repository/Promotion/Page_Simplicit/input_Libelle_promo'), libelle)
	
	TestObject champ_date = findTestObject('Object Repository/Promotion/Page_Simplicit/input_Date_promo')
	CustomKeywords.'Keywords.commun.UtilsEpide.setDateField'(champ_date, date_arrivee)
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveAndCheckConfirmation'()
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/button_AssocierJeunes'))
	
	TestObject select_jeune = findTestObject('Object Repository/Promotion/Page_Simplicit/input_prenom_select_jeune')
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndSet'(select_jeune, prenom)
	WebUI.sendKeys(select_jeune, Keys.chord(Keys.ENTER))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/Checkbox_select_jeune'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(findTestObject('Object Repository/Promotion/Page_Simplicit/button_Selectionner'))
	
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	
}
WebUI.closeBrowser()