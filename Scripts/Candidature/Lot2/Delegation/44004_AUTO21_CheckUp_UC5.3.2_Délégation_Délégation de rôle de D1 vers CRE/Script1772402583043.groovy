import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'Keywords.commun.UtilsEpide.Access_epide'('Admin_National')

CustomKeywords.'Keywords.commun.UtilsEpide.searchMandataireDelagation'("nom cre")

boolean rowExists = CustomKeywords.'Keywords.commun.UtilsEpide.delegationRowExists'(
	"nom d1",
	"prénom d1",
	"Directeur(trice) de centre",
	"prénom cre",
	"nom cre"
)

if (rowExists) {

    WebUI.comment("Ligne trouvée → Suppression en cours")

    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
        findTestObject('Object Repository/Delegation/Page_Simplicit/checkbox_delegationRole')
    )

    CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
        findTestObject('Object Repository/Delegation/Page_Simplicit/button_Supprimer')
    )

    TestObject popup = findTestObject('Object Repository/Delegation/Page_Simplicit/Text_popup_Confirmer')

    if (WebUI.waitForElementPresent(popup, 10, FailureHandling.OPTIONAL)) {

        CustomKeywords.'Keywords.commun.UtilsEpide.waitAndClick'(
            findTestObject('Object Repository/Delegation/Page_Simplicit/button_Ok')
        )

        WebUI.comment("Suppression confirmée avec succès")

    } else {
        WebUI.comment("Popup de confirmation non détectée")
    }

} else {

    WebUI.comment("Aucune ligne correspondante trouvée - aucune suppression nécessaire")
}
WebUI.closeBrowser()