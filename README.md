# KrazyOne

A simple web application to manage users.

## Pre-Requisites

Following softwares are required before proceeding:

- Java 17
- Maven 3.9.6
- Eclipse IDE or any other equivalent IDEs like IntelliJ
- Stable internet connection for finding and downloading required libraries
- For project lombok (getter setter generation) in eclipse / sts / other ides:
	[Lombok configuration](https://stackoverflow.com/a/45217235/3247644)

## Build the application

Once the module is checked out / cloned from git / unzipped as raw application
- Navigate to module location
- Issue the following command to perform compilation and packaging but not installing / copying to any repo location

```
	mvn clean package
```
## Before running the application
Make sure to add aws credentials in the application properties. GitHub policy will not allow any credentials to be committed to repository
```
/krazyone/src/main/resources/application.properties

aws.access.key=<<access key>>

aws.secret.key=<<secret>>

aws.region=<<region>>
```