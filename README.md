[![Java CI with Maven](https://github.com/lealceldeiro/backend-coding-test/actions/workflows/maven.yml/badge.svg)](https://github.com/lealceldeiro/backend-coding-test/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/lealceldeiro/backend-coding-test/branch/master/graph/badge.svg?token=F1EF4JTFBU)](https://codecov.io/gh/lealceldeiro/backend-coding-test)
# Disclaimer

If you arrived here because you're being evaluated, and you're expected to complete this technical task (originally
forked from https://github.com/RatedPower/backend-coding-test) on your own, please **refrain** from copying the solution
implemented here (let along present it as your own).

If you'd like to (maybe) explore an approach other than your own, and/or only for studying, or any other purpose which
is not to get a benefit from the content presented in this repo (such as a good result in a technical interview which
evaluates this task) you're free to clone it and explore it.

This code contains a mix from the original repo and code of my own, but neither I am nor I claim to be the author of it.

If, for any reason, I'm asked by the original author (https://github.com/RatedPower) to remove this fork from my GitHub
repositories, I'll do so without any previous warning to any third party.

## Addendum to below content

An alternative to the required tools mentioned below, is to have installed [docker](https://docs.docker.com/) and
[docker-compose](https://docs.docker.com/compose/). If that's the case you can run
`docker-compose up -d rp_mysql_db rp_springbootapp` from the project root directory. In this case there's no need to
configure any property (like DB connection parameters).

To *test* the application endpoints, you can use as a reference the provided
[API Sample HTTP Requests](./api_sample_requests.http).

---

# Spring Boot to-do app challenge

With this challenge we want to see your skills and the quality of the code, we will take into account the use of SOLID principles. You can use all the tools and libraries you want!


## Required tools

1. [Java 11](https://adoptopenjdk.net/)
2. [MySQL](https://dev.mysql.com/downloads/mysql/)

## Objectives

##### Principal

Develop the necessary functionalities for the application to be able to perform the following requests:

- **GET** http request that returns a list of all tasks stored in the database.
- **GET** http request that returns a specific task by their ID.
- **POST** http request that stores a new task in the database.
- **PUT** http request that updates a specific task by their ID.
- **DELETE** http request that deletes a task in the database.

##### Optional

1. Improve the request that returns a list of tasks by adding the possibility to order and filter the results, for example:

    - Order results by priority or creation date.
    - Filter results by priority and/or completion.
    
2. Create a new entity called *SubtaskEntity* that allows one task to have multiple subtasks.
