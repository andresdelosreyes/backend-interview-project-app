# Andres de los Reyes - NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that can be volatile. It is storing the ./database directory

## Starting the Application

Run the `BackendInterviewProjectApplication` class


## Accesing the documentation
Go to:
* http://localhost:8080/swagger-ui/#/

You will see the swagger endpoint exposing all the controllers and its methods

## H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:

```yml
url: jdbc:h2:file:./database/localdb
username: sa 
password: password
```

You should be able to see a db console now that has the Sample Repository in it.

## Notes
The application will execute the data.sql script located in src/main/resources every time it starts, this file contains the basic set of data to run the cost endpoint to get the example result of the project, to do so you will need to hit this endpoint:

* URL: http://localhost:8080/cost (POST)
* Method: Post
* Header: Content-Type: application/json
* Payload: 
```json
{
    "devices": [
        {
            "deviceId": 1,
            "numberOfDevices": 2
        },
        {
            "deviceId": 3,
            "numberOfDevices": 3
        }
    ],
    "serviceIds": [
        1,
        2,
        4
    ]
}
```
