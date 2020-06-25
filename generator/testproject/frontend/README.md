# FrontEnd

The FrontEnd program for front-back decoupled architecture.

## Intro

FrontEnd By Angular!

## Docker build

Using the latest nginx:alpine for containing this Anuglar FrontEnd. Port 80 is exposed.

Go into the ./dist folder:

    docker build -t frontend-zh-hans -f Dockerfile-zh-Hans .
    docker run -d  frontend-zh-hans:latest

    docker build -t frontend-zh -f Dockerfile-en-US .
    docker run -d  frontend-en-us:latest