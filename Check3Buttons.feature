Feature: ViewCart
  Scenario:PORTAL -- Validation of three Buttons in cart
    Given I open Url www.saucedemo.com 

    ## we can configure to open a url easly in the background
    
    And If I see element loginPage.userName then I fill value standard_user
    And If I see element loginPage.password then I fill value secret_sauce
    And If I see element loginPage.loginButton then I click element loginPage.loginButton

    ##if needed we can hide the username and password behind code

    And If I see element shopping-cart.shopping-cart-button then I click element shopping-cart.shopping-cart-button

    And If I see element shopping-cart.ItemRemove then I click element shopping-cart.itemRemove
    And If I see element shopping-cart.ItemRemove then I click element shopping-cart.itemRemove

    ## we can add this in a if statement if needed to repeat or pass a specific c

    And I can see element shopping-cart.Checkout
    And I can see element shopping-cart.ContinueShopping
