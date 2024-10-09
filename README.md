# OWT Boat Challenge

## Description

OWT Boat Challenge is a project designed to manage and display information about various boats. It includes a backend and frontend component to handle data storage, user authentication, and presentation.

## Backend

Backend in Java/SpringBoot using a H2 database for simplicity, saved to a file that is committed to provide some basic data for testing. 
Decided to implement the authentication using JWT tokens, never had used it so wanted to try. Thing to note, HTTPS is not implemented which means that passwords are visible in the network.

env.properties is commited as well, just to have the proper setup and testing, although normally would be ignored an not added to the repo.

## Frontend
Frontend in React.TS, and decided to try ChakraUI for components and styling. Has both a Login and Signup page, fairly simple, just to play around a bit. npm run dev to start

A default user already exists: owt / HelloWorld42

The token validity is one hour, and the UI has a naive approach to refreshing it (asks the user to login again)
