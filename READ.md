# Site Outage Program
System post new outages belongs a site which provided by User. KrakenFlex API occasionally return 500 status codes. In such cases, Feign will repeat the request. In such cases, Feign will retry the request. This retry is 5 times per request.

Uses three endpoint provided by krakenflex api.

`getOutages()` uses for getting outages.

`getSiteInfo` uses for getting site Info provided by user when application starts.

`postOutagesForSite` uses for posting outages for provided site.

## Tech Stack
* Java
* Spring Boot
* Maven
* JUnit
* Lombok
* Mockito
* Docker


## How to Build Docker
`docker build -t site-outage .`

## How to Run Docker with Default Environments Variables, Default variables located in application yml
`docker run -it site-outage`

## How to Run Docker with Environments Variables
`docker run -it --env API_KEY=EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23 --env SITE_ID=norwich-pear-tree site-outage
`

