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

public class vibePage {
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
    public void VisibleVideoInPreview(String xpath){
        WebDriver driver  = WebDriverRunner.getWebDriver();
        try{
            driver.switchTo().frame("webviewIframeA");
            LogHelper.info(log,"Switch to webviewIframeA");
            WebElement element = driver.findElement(By.xpath(xpath));
            if (element.isDisplayed()){
                assert true;
                LogHelper.info(log,"Can see video in preview");
                driver.switchTo().parentFrame();
            }
        }catch (Exception e){
            LogHelper.info(log,"Cannot see video in weviewIframeA, Check in weviewIframeB");
            driver.switchTo().parentFrame();
            try{
            driver.switchTo().frame("webviewIframeB");
            WebElement element = driver.findElement(By.xpath(xpath));
            if (element.isDisplayed()){
                assert true;
                LogHelper.info(log,"Can see video in preview");
                driver.switchTo().parentFrame();
            }}
            catch(NoSuchElementException e2){
                driver.switchTo().parentFrame();
                assert false;
                LogHelper.info(log,"Cannot see video in preview");
            }
        }
    }
    public void hiddentest(String elemenet) throws Exception {

        try {
            if ($(Selectors.byXpath(elemenet)).is(Condition.exist)){
                LogHelper.info(log, "can see element " + elemenet);
            throw new Exception("Elemenet does exist " + elemenet);}
            else {
                LogHelper.info(log, "can't see  element " + elemenet);
            }
        } catch (NoSuchElementException var5) {
            assert false: "does not exist element";
        }

    }
    public void closeBrowser(){
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.close();
    }
    public void switchFrameXpath(String xpath) {
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();
            driver.manage().timeouts().implicitlyWait(15L, TimeUnit.SECONDS);
            Selenide.switchTo().frame($(By.xpath(xpath)));
            LogHelper.info(log, "switched to frame " + xpath);
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "couldn't find frame " + xpath);

            assert false;
        }

    }
    public void enddates(String text, String xpath,int future,int time) throws Exception {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+time));
            calendar.set(Calendar.HOUR,(calendar.get(Calendar.HOUR)+future));
            String dateString = dateFormat.format(calendar.getTime());
            $(Selectors.byXpath(xpath)).clear();
            LogHelper.info(log,  dateString);
            $(Selectors.byXpath(xpath)).setValue(dateString);
            LogHelper.info(log, "filled " + text + " with " + dateString);
        } catch (
                org.openqa.selenium.NoSuchElementException var5) {
            LogHelper.error(log, "couldn't fill " + text );
            assert false;
        }
    }
    public void endTime(String text, String xpath,int future,int time) throws Exception {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+time));
            calendar.set(Calendar.MINUTE,(calendar.get(Calendar.MINUTE)+future));
            String dateString = dateFormat.format(calendar.getTime());
            $(Selectors.byXpath(xpath)).clear();
            LogHelper.info(log,  dateString);
            $(Selectors.byXpath(xpath)).setValue(dateString);
            LogHelper.info(log, "filled " + text + " with " + dateString);
        } catch (
                org.openqa.selenium.NoSuchElementException var5) {
            LogHelper.error(log, "couldn't fill " + text );
            assert false;
        }
    }
    public void todaysdate(String text, String xpath,int future) throws Exception {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+future));
            String dateString = dateFormat.format(calendar.getTime());
            $(Selectors.byXpath(xpath)).clear();
            $(Selectors.byXpath(xpath)).setValue(dateString);
            LogHelper.info(log, "filled " + text + " with " + dateString);
        } catch (
                org.openqa.selenium.NoSuchElementException var5) {
            LogHelper.error(log, "couldn't fill " + text );
            assert false;
        }
    }
    public void dates(String text, String xpath,int future) throws Exception {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+future));
            calendar.set(Calendar.HOUR,(calendar.get(Calendar.HOUR)));
            String dateString = dateFormat.format(calendar.getTime());
            $(Selectors.byXpath(xpath)).clear();
            LogHelper.info(log,  dateString);
            $(Selectors.byXpath(xpath)).setValue(dateString);
            LogHelper.info(log, "filled " + text + " with " + dateString);
        } catch (
                org.openqa.selenium.NoSuchElementException var5) {
            LogHelper.error(log, "couldn't fill " + text );
            assert false;
        }
    }

    public void ifVisiblecheackAnotherElement(String conditionValueXapth, String clickValueXpath) throws Exception {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.visible)) {
                if ($(Selectors.byXpath(clickValueXpath)).is(Condition.exist)){
                    LogHelper.info(log, "can see both element " + clickValueXpath + conditionValueXapth);}
                else{
                    throw new Exception("Elemenet doesn't exist " + clickValueXpath);
                }
            }

            LogHelper.info(log, "not found elemenet" + conditionValueXapth);
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't find" + conditionValueXapth);

            assert false;
        }

    }
    public boolean VisibleClick(String xpath) {
        try {
            if ($(Selectors.byXpath(xpath)).exists()) {
                LogHelper.info(log, "found element " + xpath);
                return true;
            } else {
                LogHelper.error(log, "couldn't find element " + xpath);
                assert false: "cant see element";
                return  false;
            }
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "couldn't find element " + xpath);
            return false;
        }
    }
    public void ifnotVisibleClick1(String conditionValueXapth, String clickValueXpath, String clickValueXpath2) {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.hidden)) {
                $(Selectors.byXpath(clickValueXpath)).click();
                LogHelper.info(log, "Clicked " + clickValueXpath + " because  " + conditionValueXapth + " appeared");
            }
            else{


                $(Selectors.byXpath(clickValueXpath2)).click();
                LogHelper.info(log, "Clicked " + clickValueXpath2 + " because  " + conditionValueXapth + " not appeared");
            }


        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't click" + clickValueXpath);

            assert false;
        }

    }
    public boolean canSeeAttributeforElement(String text, String xpath, String attributeName, String attributeValue) {
        try {
            boolean attributeValuExist = $(Selectors.byXpath(xpath)).shouldHave(new Condition[]{Condition.attribute(attributeName, attributeValue)}).exists();
            LogHelper.info(log, "Can see attribute " + attributeName + " with value " + attributeValue);
            return attributeValuExist;
        } catch (org.openqa.selenium.NoSuchElementException var6) {
            LogHelper.error(log, "Couldn't found" + text);
            return false;
        }
    }


    public void clearInputByLocator(String locatorPath) {
        try {
            String xpath = this.dataHelper.getLocatorsData(locatorPath);
            $(Selectors.byXpath(xpath)).clear();
            LogHelper.info(log, "Cleared input field with locator " + locatorPath + ".");
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "Could not find element with locator " + locatorPath + ".");

            assert false;
        } catch (IOException var4) {
            LogHelper.error(log, locatorPath + " not found in file.");

            assert false;
        }

    }
    public void clearElementXpath(String xpath) {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            LogHelper.info(log, "cleared " + xpath);
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "couldn't clear " + xpath);

            assert false;
        }

    }
    public void fillFieldWithXpathclearText(String text, String xpath, String value) {
        try {
            $(Selectors.byXpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            $(Selectors.byXpath(xpath)).sendKeys(Keys.DELETE);
            value = this.dataHelper.generateRandomString(value);
            $(Selectors.byXpath(xpath)).setValue(value);
            LogHelper.info(log, "filled " + text + " with " + value);
        } catch (org.openqa.selenium.NoSuchElementException var5) {
            LogHelper.error(log, "couldn't fill " + text + " with " + value);

            assert false;
        }

    }
    public void tests(String value) throws Exception  {
        String bgColor = $(byXpath("//div[@class='bg-overlay']")).getCssValue("background-color");
        if (value.equals(bgColor)) {

            LogHelper.info(log, "filled value " + bgColor);
        }
        else
        {
            LogHelper.info(log,    bgColor);
            throw new Exception("could not find " + value);
        }

    }
    public void get_url() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String number = urlSplit[l-1];
        String orginalUrl = "https://automation.vibe.fyi/OutputSlideRepeatSettings.ashx?slideId="+number;
        LogHelper.info(log,"your url is " + orginalUrl );
        driver.get(orginalUrl);
    }
    public void get_CopyurlURL() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String number = urlSplit[l-1];
        String orginalUrl = "https://azurestaging.vibe.fyi/Modules/DigitalSignage/FrontEnd/PreviewUrl.aspx?slideId="+number;
        LogHelper.info(log,"your url is " + orginalUrl );
        driver.get(orginalUrl);
    }

    public void get_CopyurlURL_auto() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String number = urlSplit[l-1];
        String orginalUrl = "https://automation.vibe.fyi/portal/playlists/edit-playlist/"+number;
        LogHelper.info(log,"your url is " + orginalUrl );
        driver.get(orginalUrl);
    }

    public void open_currenturl_inNewtab() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", url);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        LogHelper.info(log,"open "+url+" and switch to new tab" );
    }

    public void switch_original_tab() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        LogHelper.info(log,"switch to original tab" );
    }

    public void get_CopylinkURL() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String copylink = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                ;
        if (hasTransferableText) {


            try {
                copylink = (String)contents.getTransferData(DataFlavor.stringFlavor);
                LogHelper.info(log,"your url is " + copylink );
                driver.get(copylink);;
            }
            catch (UnsupportedFlavorException | IOException ex){
                ex.printStackTrace();
                LogHelper.error(log, "No copy link");
                assert false : "No copy link" ;
            }
        }

    }
//    public void refresh_page(){
//        WebDriver driver = WebDriverRunner.getWebDriver();
//        Actions act = new Actions(driver);
//        act.sendKeys(Keys.F5).perform();
//        LogHelper.info(log,"Refresh page by F5");
//    }
    public void refresh_page(){
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.navigate().refresh();
        LogHelper.info(log,"Refresh page by F5");
    }
    public void get_CopylinkURL_Incognito() {
        String copylink = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                ;
        if (hasTransferableText) {


            try {
                copylink = (String)contents.getTransferData(DataFlavor.stringFlavor);
                LogHelper.info(log,"your url is " + copylink );
                WebDriverManager.chromedriver().setup();
                ChromeOptions o= new ChromeOptions();
                // add Incognito parameter
                o.addArguments("--incognito");
                // DesiredCapabilities object
                DesiredCapabilities c = new  DesiredCapabilities();
                //set capability to browser
                c.setCapability(ChromeOptions.CAPABILITY, o);
                o.merge(c);
                WebDriver driver = new ChromeDriver(o);
                LogHelper.info(log,"Press Ctr Shift N keys");
                driver.get(copylink);
                LogHelper.info(log,"Open Copy link in new Incognito window");
            }
            catch (UnsupportedFlavorException | IOException ex){
                ex.printStackTrace();
                LogHelper.error(log, "No copy link");
                assert false : "No copy link" ;
            }
        }

    }
    public void get_url_dstest() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String number = urlSplit[l-1];
        String site = urlSplit[l-5];
        String orginalUrl = "https://"+site+"/OutputSlideRepeatSettings.ashx?slideId="+number;
        LogHelper.info(log,"your url is " + orginalUrl );
        driver.get(orginalUrl);
    }
    public void Everyweekonday() throws Exception {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+1));
//            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));
//            String dateString = dateFormat.format(calendar.getTime());
//            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));
//            String dateString1 = dateFormat.format(calendar.getTime());
//            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));
//            String dateString2 = dateFormat.format(calendar.getTime());
//            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));
//            String dateString3 = dateFormat.format(calendar.getTime());
//            calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));
//            String dateString4 = dateFormat.format(calendar.getTime());
//            LogHelper.info(log, "updated " +  " with " + dateString);
//            LogHelper.info(log, "updated " +  " with " + dateString1);
//            LogHelper.info(log, "updated " +  " with " + dateString2);
//            LogHelper.info(log, "updated " +  " with " + dateString3);
//            LogHelper.info(log, "updated " +  " with " + dateString4);
//                    Selenide.$(Selectors.byText(dateString)).shouldHave(Condition.text(dateString));
//            LogHelper.info(log, "can see text " + dateString);
//                    Selenide.$(Selectors.byText(dateString1)).shouldHave(Condition.text(dateString1));
//                    LogHelper.info(log, "can see text " + dateString1);
//            Selenide.$(Selectors.byText(dateString2)).shouldHave(Condition.text(dateString2));
//            LogHelper.info(log, "can see text " + dateString2);
//            Selenide.$(Selectors.byText(dateString3)).shouldHave(Condition.text(dateString3));
//            LogHelper.info(log, "can see text " + dateString3);
            for (int i =0;i<3;i++)
            {
                String dateString = dateFormat.format(calendar.getTime());
                LogHelper.info(log, "updated " +  " with " + dateString);
                $(Selectors.byText(dateString)).shouldHave(Condition.text(dateString));
                LogHelper.info(log, "can see text " + dateString);
                calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+7));

            }
                } catch (org.openqa.selenium.NoSuchElementException var3) {
                    LogHelper.error(log, "couldn't see text");
                    assert false : "Couldn't see date" ;
                }

            }
    public void open_url() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String text = urlSplit[2];
        String Equal = "azurestaging.vibe.fyi";
        if(text.compareTo(Equal) != 0) {

            String Urlad ="https://dstest.vibe.fyi/Admin/Console/Default.aspx";
            driver.get(Urlad);
        }
            else {
            String Urlad = "https://azurestaging.vibe.fyi/Admin/Console/Default.aspx";
            driver.get(Urlad);
        }


    }

    public void open_url2() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String text = urlSplit[2];
        String Equal = "automation.vibe.fyi";
        if(text.compareTo(Equal) != 0) {

            String Urlad ="https://automationds.vibe.fyi/Admin/Console/Default.aspx";
            driver.get(Urlad);
        }
        else {
            String Urlad = "https://automation.vibe.fyi/Admin/Console/Default.aspx";
            driver.get(Urlad);
        }


    }
    public void open_url_For_deltion_of_account() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = driver.getCurrentUrl();
        String[] urlSplit=url.split("/");
        Integer l = urlSplit.length;
        String text = urlSplit[2];
        String Equal = "azurestaging.vibe.fyi";
        if(text.compareTo(Equal) != 0) {

            String Urlad ="https://dstest.vibe.fyi/_deleteAutomatedTestingData.aspx";
            driver.get(Urlad);
        }
        else {
            String Urlad = "https://azurestaging.vibe.fyi/_deleteAutomatedTestingData.aspx";
            driver.get(Urlad);
        }


    }
    public void CopyAppLogs(String locatorPath){
        try {
            String xpath = this.dataHelper.getLocatorsData(locatorPath);
            String logs = $(Selectors.byXpath(xpath)).getValue();
            LogHelper.info(log, "App logs: " + logs);
        } catch (Exception ex) {
            LogHelper.error(log, "Failed to copy app logs: " + ex.getMessage());

            assert false;
        }
    }
    public void i_Refresh(String elemenet) throws Exception{
        try {
            if (!$(Selectors.byXpath(elemenet)).is(Condition.exist)) {
                LogHelper.info(log, "can't see element " + elemenet + ", about to refresh the page...");
                Selenide.refresh();
            }
        } catch (NoSuchElementException var5) {
            LogHelper.info(log, "can't see element " + elemenet + ", about to refresh the page...");
            Selenide.refresh();
        }
    }
    public void i_click_element_on_seing(String conditionValueXapth,String clickValueXpath ) throws Exception{
        try {
            if (!$(Selectors.byXpath(conditionValueXapth)).is(Condition.exist)) {
                LogHelper.info(log, "can't see element " + conditionValueXapth + ", going to click element ");
                $(Selectors.byXpath(clickValueXpath)).click();
            }
            LogHelper.info(log, "not found elemenet" + conditionValueXapth);
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't find" + conditionValueXapth);

            assert false;
        }
        }

    public void i_click_element_on_seeingText(String conditionText,String clickValueXpath ) throws Exception{
        try {
            if (!$(Selectors.byText(conditionText)).isDisplayed()) {
                LogHelper.info(log, "can't see text " + conditionText + ", going to click element ");
                $(Selectors.byXpath(clickValueXpath)).click();
            }
            LogHelper.info(log, "not found text " + conditionText);
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't find " + conditionText);

            assert false;
        }
    }

    public void i_swipe_element_left(String locatorPath) throws Exception{
        try{
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions action = new Actions(driver);
            action.dragAndDropBy($(byXpath(locatorPath)), -200,0).perform();
            LogHelper.info(log, "Swipe element to left");
        } catch (org.openqa.selenium.NoSuchElementException var4){
            LogHelper.error(log, "couldn't find "+locatorPath);
            assert false;
        }
    }

    public void i_swipe_element_right(String locatorPath) throws Exception{
        try{
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions action = new Actions(driver);
            action.dragAndDropBy($(byXpath(locatorPath)), 200,0).perform();
            LogHelper.info(log, "Swipe element to right");
        } catch (org.openqa.selenium.NoSuchElementException var4){
            LogHelper.error(log, "couldn't find "+locatorPath);
            assert false;
        }
    }

    public void RefreshPage(){
        Selenide.refresh();
        LogHelper.info(log, "Refreshed the Page");
    }
    public void doubleclickText(String text) {
        try {
            $(Selectors.byText(text)).doubleClick();
            LogHelper.info(log, "clicked " + text);
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "couldn't click" + text);

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
    public void cantseeText(String text) {
        try {
            $(Selectors.byText(text)).shouldNotHave(new Condition[]{Condition.text(text)});

            LogHelper.info(log, "couldn't see text " + text);
        } catch (org.openqa.selenium.NoSuchElementException var3) {
            LogHelper.error(log, "can see text " + text);
            assert false : "Could see" + text;
        }

    }

    public void cantseeTextInList(String text, String xpath) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        List<WebElement> li = driver.findElements(By.xpath(xpath));
        Assert.assertNotEquals("Cannot see elements list "+xpath,0,li.size());
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getText().equals(text)) {
                Assert.assertNotEquals("Can see text "+text,text, li.get(i).getText());
                LogHelper.error(log, "can see text " + text + " in list");
                break;
            }
        }
        LogHelper.error(log, "cannot see text " + text + " in list");
    }
    public void seeElementNotExist(String xpath) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (driver.findElements(By.xpath(xpath)).isEmpty()){
            LogHelper.info(log,"Element "+xpath+" does not exist");
        } else{
            assertFalse(driver.findElements(By.xpath(xpath)).isEmpty());
            LogHelper.error(log,"Element exist");
        }
    }

    public void Visibletest(String elemenet) throws Exception {

        try {
            if ($(Selectors.byXpath(elemenet)).is(Condition.empty)){
                LogHelper.info(log, "can cant see value in element " + elemenet);}
            else{
                throw new Exception("can see value in element " + elemenet);
            }
        } catch (NoSuchElementException var5) {
            assert false: "does not exist element";
        }

    }
    public void fillFieldWithxpathforLogin(String text, String xpath, String value) {
        try {
            if ($(Selectors.byXpath(xpath)).is(Condition.visible)) {
                value = this.dataHelper.generateRandomString(value);
                $(Selectors.byXpath(xpath)).setValue(value);
                LogHelper.info(log, "filled " + text + " with " + value);
            }
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't fill " + text + " with " + value);

            assert false;
        }


    }

    public void ifVisibleClickLogin(String conditionValueXapth, String clickValueXpath) {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.visible)) {
                $(Selectors.byXpath(clickValueXpath)).click();
            }

            LogHelper.info(log, "Clicked " + clickValueXpath + " because  " + conditionValueXapth + " appeared");
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't click" + clickValueXpath);

            assert false;
        }

    }
    public void ifVisibleClick2(String conditionValueXapth, String clickValueXpath) {
        try {
            if ($(Selectors.byXpath(conditionValueXapth)).is(Condition.visible)) {
                $(Selectors.byXpath(clickValueXpath)).doubleClick();
            }

            LogHelper.info(log, "Clicked " + clickValueXpath + " because  " + conditionValueXapth + " appeared");
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't click" + clickValueXpath);

            assert false;
        }

    }
    public void jSexecutorClickElement(String xpath){
        WebDriver driver = WebDriverRunner.getWebDriver();
        try{
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", $(Selectors.byXpath(xpath)));
            LogHelper.info(log, "Use JSexecutor click "+xpath);
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't click" + xpath);
            assert false;
        }
    }
    public void waitSeeingByLocator(String xpath){
        WebDriver driver = WebDriverRunner.getWebDriver();
        try{
            WebDriverWait wait = new WebDriverWait(driver, 40);
            By locatorXpath = By.xpath(xpath);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locatorXpath));
            LogHelper.info(log, "Can see "+xpath);
        } catch (org.openqa.selenium.NoSuchElementException var4) {
            LogHelper.error(log, "couldn't see" + xpath);
            assert false;
        }
    }

        }

