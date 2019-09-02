# circle-service
The beginning of a new social network type thing that's more like Slack than Facebook. 

## Badges
The most important part of a readme of course

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/931d0f85c1c84e93bf022313d8a7143c)](https://www.codacy.com/app/peavers/circle-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=peavers/circle-service&amp;utm_campaign=Badge_Grade)

## Why
I wanted a pet project to get stuck into after hours and I liked the idea of building something similar to a social network. It has many complex
moving parts and would be a great thing to spend the weekends on. 

This service is responsible for creating what I've named "circles". A circle is a private group in which you must invite someone to join. 
Everything is always private amongst only those members.

### Webflux
The future of REST endpoints and APIs. This project makes heavy use of `Flux` and `Mono` implementations. They might not be perfect but I think 
it's coming along.  

### MongoDB
Who doesn't love a good NoSQL datastore? 

### RabbitMQ
Implemented as more of a websocket broker than anything. A frontend client subscribes to the queue and listens for new posts/messages. This service
will send anything that it saved to the database to RabbitMQ. I was more interested in playing with RabbitMQ than anything. 

### Okta
Auth is hard. Don't reinvent the wheel, use Okta! 

## Running
The `circle-service.yml` should be used via docker-compose due to the number of required dependence's. Make sure to 
setup environment variables to match those in the `application.yml` file. Just start the application the good old fashion way 
and you should be good to go.    

## W.I.P
This may never get another commit, or it might get many... 
