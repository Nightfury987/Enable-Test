package steps;

import co.nz.getskills.auto.Base.TestDriver;
import co.nz.getskills.auto.context.TestContext;
import co.nz.getskills.auto.utils.DataHelper;
import com.codeborne.selenide.Selenide;
import cucumber.api.java.en.When;
import pageObjects.InformationBarPage;
import pageObjects.PlaylistsPage;
import pageObjects.SlidesPage;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.page;


public class VibeSteps extends TestDriver 
{
    vibePage vibePage = new vibePage();
    SlidesPage slidePage = new SlidesPage();
    PlaylistsPage playlistsPage = new PlaylistsPage();
    InformationBarPage informationBarPage = new InformationBarPage();
    DataHelper dataHelper = new DataHelper();
    String platform = "web";
    private TestContext testContext;

    public VibeSteps(TestContext testContext) {
        this.testContext = testContext;
    }

   @When("^I can see element (.+)$")
    public void i_can_seeingElement(String conditionLocatorPath) throws Exception {
       this.vibePage = (vibePage) Selenide.page(vibePage.class);
        String conditionLocatorXpath = this.dataHelper.getLocatorsData(conditionLocatorPath);
       this.vibePage.VisibleClicktest(conditionLocatorXpath);
    }
    @When("^If I see element (.+) then I fill value (.+)$")
    public void i_see_element_And_Fill(String conditionLocatorPath, String value) throws Exception {
        this.vibePage = (vibePage)Selenide.page(vibePage.class);
        String conditionLocatorXpath = this.dataHelper.getLocatorsData(conditionLocatorPath);
        this.vibePage.ifVisibleAndFill(conditionLocatorXpath, value);
    }
  
    @When("^If I see element (.+) then I click element (.+)$")
    public void i_click_element_on_seeingElement(String conditionLocatorPath, String locatorPath) throws Exception {
        this.vibePage = (vibePage)Selenide.page(vibePage.class);
        String xpath = this.dataHelper.getLocatorsData(locatorPath);
        String conditionLocatorXpath = this.dataHelper.getLocatorsData(conditionLocatorPath);
        this.vibePage.ifVisibleClick2(conditionLocatorXpath, xpath);
    }
    
}
