##Hello there!
This is repository for my test assignment "Kalah Game".

You can find the rules of the game by the link https://en.wikipedia.org/wiki/Kalah

The application exposes the public API and provides a JWT authentication for access to some endpoints.

###Authentication API
#### Signup
To sign up to the application use endpoint:
```
POST
http://HOST:8080/auth/signup
```
The payload should look like this:
```
{
    "username": "user",
    "password": "pass"
}
```
#### Login
Once you had registered your account - you can feel free to login into it. The authentication mechanism
is based on JWT and consists of such steps:
* You login with a separate endpoint ``/auth/login``. Here is an example: 
```
POST
http://HOST:8080/auth/login
```
* If login was success, you should receive your token and expiration date with the payload from your response.
Example of response:
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTc0ODUyMzEwLCJleHAiOjE1NzU0NTcxMTB9.O-5mEdwtVcceSSeainHCSfV7Q-ujNR-wEHqvxNHnGvh-er71iQW34VjRMYuIVz2Sp5-hzvbx8bYsWUIWczfMoQ",
    "expirationData": "2019-12-04T10:58:30.463+0000"
}
```
* Further, you can use this token to access to other endpoints of the application. All you need to do is copy token and
pass it as Bearer authorization token in HTTP header.
```
Headers
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTc0ODUyMzEwLCJleHAiOjE1NzU0NTcxMTB9.O-5mEdwtVcceSSeainHCSfV7Q-ujNR-wEHqvxNHnGvh-er71iQW34VjRMYuIVz2Sp5-hzvbx8bYsWUIWczfMoQ 
```

### Kalah game API
#### Create game
To create your own game and automatically join to this game as first player you should use this endpoint: 
```
Authenticated EP
POST
/games
```
It should be used without specifying any request body.
The response should look like this:
```
{
    "id": "1",
    "url": "http://localhost:8080/games/1"
}
```
#### Join game
To join in any game created by another user, you can perform the PUT request on this endpoint:
```
Authenticated EP
PUT
/games/{gameId}
```
where gameId is ID, you have received in ``POST /game`` method.
#### Make movement
To make movement in your game, you should perform the PUT request on an endpoint:
```
Authenticated EP
PUT
/games/{gameId}/{pitId}

```
where gameId is ID of the game and pitId is ID of the pits, numerated from 1 to 14
The response will include all meta information about games and its statuses
```
{
            "id": 1,
            "status": "{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6,\"7\":0,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0}",
            "firstUserId": 1,
            "secondUserId": null
}
```
#### Fetch all your games
To fetch all games you're playing now, use endpoint:
```
GET
/games
```
The response should be look like this one:
```
{
    "games": [
        {
            "id": 1,
            "status": "{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6,\"7\":0,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0}",
            "firstUserId": 1,
            "secondUserId": null
        }
    ]
}
```