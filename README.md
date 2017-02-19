# ComparingPricesviaAmazonAPI
  We can check the price (converts to US dollars) of an item sold in 6 Amazons(.com/.co.uk/.de/.fr/.jp) at once when you buy one from japan.
  CurrencyAPI, which is called CurrencylayerAPI (https://currencylayer.com/), has been used to get currencies as well.
  
# How To Use
  Build it via Eclipse, and execute it.(maybe you need to import some libs.)
  There are some parameters in order to search. See "ItemData.java".
  - The ASIN code of the product(See https://www.amazon.com/gp/help/customer/display.html?nodeId=200202190#find_asins).
  - The weight(kg) of the product(Default: under 1kg).
  - The category of the product(See ShipmentCharge-com.csv and Select one of them).
  
# Things you need
 - Amazon associates tags for each Amazons.
 - 2 Amazon AWS_Keys and AWs_Secret_Keys (For Japan and the others).
 - CurrencyAPI(Currencylayer API) Key
 - By Setting parameters

Please set your keys in AmazonConstants class  of "SearchAnItem.java".

# Sample
 - Prices of The big bang theory Blu-ray <br/>
![Prices of The big bang theory Blu-ray](https://qiita-image-store.s3.amazonaws.com/0/153487/a685d994-7992-dd7f-54f8-e4b33f78e468.png
 "sample")

# Comments
 - This is my first time to write in Java, so if you have some questions or suggestions, please tell me.
 - It sometimes clashes when the HTTP request is sent (I'm still working for fixing).
 - Future Work: Making more functions. For example, "Checking the price of multiple items", "Converting URL to ASIN number", "Making it available to select a destination country (since Japan is only available right now)", and so on...

# Links
 - http://qiita.com/alpacatom/items/f02233f0feea7cad9244 (In japanese)
