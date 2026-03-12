import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('Admin_National')

CustomKeywords.'Keywords.commun.UtilsEpide.AccessTypesAbsence'()

def table = findTestObject('Object Repository/Absence/Page_Simplicit/table_types_absence')
def inputDelai = findTestObject('Object Repository/Absence/Page_Simplicit/input_Delai_pour_Transmettre')

def typesAbsence = [
	'Pédagogique',
	'Renvoi temporaire à titre conservatoire',
	'Congés maternité',
	'Hors Cursus',
	'Maladie',
	'Accident du travail',
	'Congé paternité'
]

typesAbsence.each { libelle ->

	CustomKeywords.'Keywords.commun.UtilsEpide.verifyTableFieldAndClick'(
		table,
		'Libellé',
		libelle
	)

	CustomKeywords.'Keywords.commun.UtilsEpide.clearAndSetText'(
		inputDelai,
		"1"
	)
	CustomKeywords.'Keywords.commun.UtilsEpide.saveCloseAndCheckConfirmation'()
	WebUI.waitForElementVisible(table, 10)
	
}


WebUI.closeBrowser()