# Wow Completionist

## Presentation

This is an application I built to get my hands on Blizzard's APIs, specifically World of Warcraft APIs.

## How to deploy

### OAuth2 configuration

You need to provide a `client id` and a `client secret` that will authenticate the application with Blizzard's API. 

You can [generate credentials on Blizzard's API Portal](https://develop.battle.net/access/clients), provided you have
a Blizzard account. There are [detailed instructions](https://develop.battle.net/documentation/guides/getting-started) 
to guide you through the process.

### Packaging

```
mvnw clean package
```

## How to run

### Environment variables

Several configuration variables are read from the environment and the application will fail to start if they're not
present. The choice of environment variables over simple properties files allows easy configuration of cloud
deployments, as this application was first meant to be hosted on Heroku.

The required variables are the following : 

* `WC_BLIZZARD_API_CLIENT_ID` Blizzard API Client ID
* `WC_BLIZZARD_API_CLIENT_SECRET` Blizzard API Client secret
* `WC_ADMIN_CREDENTIALS_USERNAME` Admin area username
* `WC_ADMIN_CREDENTIALS_PASSWORD` Admine area password

If you're deploying to Heroku, you can drop a `.env` file in the `KEY=VALUE` format to set the variable. There are also
plugins that can use the `.env` file to load the variables automatically when you run the application from your IDE.

For IntelliJ, you can use [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile/).

Lastly, if you don't want to use environment variables, you can write directly the values you want in the various
properties files of the application where they are read. Keed in mind this will make deployment on multiple environments
more complicated, but it's more than enough for local use and toying around.

### Running from the command line

```
java -jar webapp/target/wow-completionist.jar
```

### Running from the IDE

If you want to activate live reload for Thymeleaf templates, run the application with `--spring.profiles.active=dev`.

### Activating 

The application by default will run on port 8080 and will use whichever port is defined in the `PORT` environment variable.

## How to use

Open the application at `http://localhost:8080/`, this will open character selection page.

From here, you can navigate to existing application modules (currently only one module is supported): 

* Battle and vanity pets (`http://localhost:8080/<region>/<realm>/<character>/pets`)
