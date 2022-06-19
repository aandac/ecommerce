# Overview

Ecommerce application is simple ecommerce application POC built with Spring boot backend and VueJs frontend.
# Table of Contents
1-[Design](#1-design)<br>
2-[Installing](#2-installing)

## 1. Design
### 1.1 Considerations
#### 1.1.1 Assumptions
<p>This project is designed for simple ecommerce application that run on any docker container.</p>

#### 1.1.2 Constraints
Amazon S3 and Image transformation API is required to run application.
#### 1.1.3 System Environment
Application backend components
* Spring Boot
* PostgresSQL
* Redis
* [TransformImgs](https://github.com/Pixboost/transformimgs) -> Open source image transformation library uses ImageMagick
* Amazon S3

### 1.2 Architecture
#### 1.2.1 Overview
*Provide here a descriptive overview of the software/system/application architecture.*
#### 1.2.2 Component Diagrams
*Provide here the diagram and a detailed description of its most valuable parts. There may be multiple diagrams. Include a description for each diagram. Subsections can be used to list components and their descriptions.*
#### 1.2.3 Class Diagrams
*Provide here any class diagrams needed to illustrate the application. These can be ordered by which component they construct or contribute to. If there is any ambiguity in the diagram or if any piece needs more description provide it here as well in a subsection.*
#### 1.2.4 Sequence Diagrams
*Provide here any sequence diagrams. If possible list the use case they contribute to or solve. Provide descriptions if possible.*
#### 1.2.5 Deployment Diagrams
*Provide here the deployment diagram for the system including any information needed to describe it. Also, include any information needed to describe future scaling of the system.*
#### 1.2.6 Other Diagrams
*Provide here any additional diagrams and their descriptions in subsections.*
### 1.3 User Interface Design
*Provide here any user interface mock-ups or templates. Include explanations to describe the screen flow or progression.*

In ecommerce application has 3 roles.<br>
* `ADMIN` - > Admin user to manage/browse API swagger documentation. 
* `MERCHANT` - > User can add/modify products 
* `CUSTOMER` - > Regular user type who can purchase products



## 2. Installing

Browse in `setup` folder and run
```
docker-compose up --build -d
```

To stop application
```
docker-compose down
```
### 2.1 Delete Data
[Localstack](https://localstack.cloud/) is using `localstack-data` and delete folder to reset all images.<br>
[PostgreSQL](https://www.postgresql.org/) is using `postgres-data` and delete folder to erase database.



