[![Java CI with Maven](https://github.com/lealceldeiro/backend-coding-test/actions/workflows/maven.yml/badge.svg)](https://github.com/lealceldeiro/backend-coding-test/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/lealceldeiro/backend-coding-test/branch/master/graph/badge.svg?token=F1EF4JTFBU)](https://codecov.io/gh/lealceldeiro/backend-coding-test)

# Spring Boot to-do app challenge

With this challenge we want to see your skills and the quality of the code, we will take into account the use of SOLID principles. You can use all the tools and libraries you want!


## Required tools

1. [Java 11](https://adoptopenjdk.net/)
2. [MySQL](https://dev.mysql.com/downloads/mysql/)
(Optionally, if have installed [docker](https://docs.docker.com/) and
[docker-compose](https://docs.docker.com/compose/), you can run `docker-compose up -d rp_mysql_db`)

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
