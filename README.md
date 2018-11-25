# Wow Completionist

## Presentation

This is an application I built to get my hands on Blizzard's APIs, specifically World of Warcraft APIs.

## How to deploy

### OAuth2 configuration

You need to provide a `client id` and a `client secret` that will authenticate the application with Blizzard's API. 
The will be read from the environment variables `WC_BLIZZARD_API_CLIENT_ID` and `WC_BLIZZARD_API_CLIENT_SECRET`.

You can [generate credentials here](https://develop.battle.net/access/clients) if you need to.

### Packaging

```
mvnw clean package
```

## How to run

```
java -jar webapp/target/webapp-0.0.1-SNAPSHOT.jar
```

The application will run on port 8080.

## How to use

Open the application at `http://localhost:8080/` (or whatever host you have configured), this will open character selection page.

From here, you can navigate to existing application modules (currently only one module is supported): 

* Battle and vanity pets (`http://localhost:8080/<region>/<realm>/<character>/pets`)
