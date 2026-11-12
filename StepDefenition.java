package steps;

import co.nz.getskills.auto.pageobjects.CommonPage;
import co.nz.getskills.auto.utils.DataHelper;
import co.nz.getskills.auto.utils.LogHelper;
import com.codeborne.selenide.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class vibePage 
{
    private static final Logger log = LogManager.getLogger(CommonPage.class.getName());
    DataHelper dataHelper = new DataHelper();

    public void checkNumberOfElementsOverTime(int count, String xpath) throws InterruptedException, NullPointerException {
        try {
            WebDriver driver  = WebDriverRunner.getWebDriver();
            ElementsCollection elementsCollection = $$(byXpath(xpath));
            ElementsCollection collectingElements;
            for (int i = 0; i < count*3; i++){
                collectingElements = $$(byXpath(xpath));
                elementsCollection.addAll(collectingElements);
                driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
            }
            List<String> imageName = new ArrayList<>();
            if(!elementsCollection.isEmpty()){
                for (WebElement element: elementsCollection) {
                    String str = element.getAttribute("style").split(";",1)[0].substring(23).replace("\")","");
                    if(!imageName.contains(str)) {
                        imageName.add(str);
                        System.out.println(str);
                    }
                }
            }
            int actualCount = imageName.size();
            if (count == actualCount){
                LogHelper.info(log, "Counted "+actualCount+".");
            } else {
                LogHelper.error(log, "Counted "+actualCount+". Should be "+count);
                assert false;
            }
        }catch (NoSuchElementException e){
            LogHelper.error(log, "Couldn't count elements.");
            assert false;
        }
    }

    public void ifVisibleAndFill(String conditionValueXapth, String value) {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.visible)) {
                value = this.dataHelper.generateRandomString(value);
                $(Selectors.byXpath(conditionValueXapth)).setValue(value);
                LogHelper.info(log, "filled " + value + " because  " + conditionValueXapth + " appeared");
            }
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't fill" + value);

            assert false;
        }

    }

    public void VisibleClicktest(String elemenet) throws Exception {

        try {
            if ($(Selectors.byXpath(elemenet)).is(Condition.exist)){
                LogHelper.info(log, "can see element " + elemenet);}
            else{
                throw new Exception("Elemenet doesn't exist " + elemenet);
            }
        } catch (NoSuchElementException var5) {
            assert false: "does not exist element";
        }

    }
    public void ifVisibleClick2(String conditionValueXapth, String clickValueXpath) {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.visible)) {
                $(Selectors.byXpath(clickValueXpath)).Click();
            }

            LogHelper.info(log, "Clicked " + clickValueXpath + " because  " + conditionValueXapth + " appeared");
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't click" + clickValueXpath);

            assert false;
        }

    }
    

}

