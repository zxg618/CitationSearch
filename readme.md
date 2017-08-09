# Citation search

## Overview
The program can find all patents and citations from PATSTAT database 2016 Autumn edition given a list of company names in Chinese.

## Local Environment
1. Java SE 1.8
2. Microsoft SQL server 2012 developer edition
3. Internet access
4. mac osx

## Setup guide 
1. Modify Constants.SERVER_ADDR to your local server address
2. Modify Constants.FILE_PATH to you input excel file path
3. May need to change other file path while using windows os*
4. mvn exec:java -Dexec.mainClass="citationsearch.Main"

## Features
1. Read an excel file containing a list of company names in Chinese
2. Read WIPO web page to get a list of patents' publication numbers
3. Search EPO database using each publication number to get a list of person ids
    * Some patents may not be found in EPO db
    * Person ids are retrieved from both directions i.e. 211(->201)>207->206 and 211->227->206
4. Use name exact match to find more person ids and store all person ids into database
5. Use these person ids to find all patents
6. Search each patent's citations from table 212
7. Output excel files

## Known issues
1. Number of citations is inconsistent with the result on EPO online

## Resources:
1. PATSTAT database 2016 Autumn edition
2. WIPO https://patentscope.wipo.int/search/en/search.jsf
3. EPO https://worldwide.espacenet.com/?locale=en_EP
4. EPO forum https://forums.epo.org//
