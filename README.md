# Wow Completionist

## Presentation

This is an application I built to get my hands on Blizzard's APIs, specifically World of Warcraft APIs.

## How to build

### OAuth2 configuration

You will need to provide a `credentials.properties` file in the `src/main/resources/configuration` directory.

This file should define two properties : `oauth2.clientId` and `oauth2.clientSecret`. These are required for the application
to authenticate itself with Blizzard's API. You can [generate credentials here](https://develop.battle.net/access/clients).

### Packaging

```
mvnw clean package
```

## How to run

```
java -jar target/wow-completionist-0.0.1-SNAPSHOT.jar
```

The application will run on port 8080.

## How to use

Open the application at `http://localhost:8080/` (or whatever host you have configured), this will open character selection page.

From here, you can navigate to all application modules : 

* Battle and vanity pets (`http://localhost:8080/<region>/<realm>/<character>/pets`)

