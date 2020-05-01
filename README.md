# Energy price calculator API
Price calculation service and API for average energy price calculation in selected period.

## Running

This is a dockerized REST service. To run it simply copy the provided .env.example file to an actual .env file, change the variables inside and run it
as a regular docker compose image, e.g. `docker-compose up`. This should be enough to start the MariaDB database and the REST service itself.

## Querying 

To query the service send an HTTP POST request to `/api/price-calculations` with the following parameters as a body of the request in JSON format:

`periodStart` - query period start date in any format parseable by Jackson

`periodEnd` - query period end date in any format parseable by Jackson

`customerType` - type of the customer, this can by any of `MINING`, `INDUSTRIAL` or `COMMERCIAL`

`productTypes` - array with the products to query. Possible products are: `ENERGY`, `LGC`.

To get the historical log with the last 10 queries send HTTP GET request to `/api/price-calculations`.
 The result will be a list of `HistoricQuery` objects.
