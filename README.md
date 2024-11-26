# Prerequisites

- Java 21
- Kotlin 2.0.21

I recommend using SDKMAN to install them:
1. Install SDKMAN
2. Run `sdk install java 21.0.5-tem` to install Java 21
3. Run `sdk install kotlin 2.0.21` to install Kotlin 2.0.21

# Running instructions

1. Go to the project directory in the terminal
2. Start the app with `./gradlew bootRun`
3. The app should be available on port 8080

# API

- `GET /customers/{customerId}/rentals` - list rented trailers for a customer, add parameter `trailerType` to filter on trailer type.
- `GET /customers/{customerId}/rentals/{trailerAssetId}` - get a specific rented trailer of a customer.
- `GET /owners/{ownerId}/trailers` - list all owned trailers for an owner, add parameter `vin` to search for a VIN.