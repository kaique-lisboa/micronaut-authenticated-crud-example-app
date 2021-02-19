# Micronaut Simple Authenticated Todo CRUD Project

This project is made using micronaut and implements simple CRUD operations with authentication from micronaut-security


## Getting started
```bash
    git clone https://github.com/kaique-lisboa/micronaut-authenticated-crud-example-app.git
    cd micronaut-authenticated-crud-example-app
    ./gradlew clean run
```

then login to obtain bearer token using initial root user:

```bash
  curl --request POST \
  --url http://localhost:8080/login \
  --header 'Content-Type: application/json' \
  --data '{
	"username": "root@root.com",
	"password": "root"
  }'
```

initial users and roles can be configured at `application.yml` file in users and roles section (respectively)

h2-console can be accessed by `localhost:8082`

## Endpoints

| method | Endpoint    | Descricao                                 | params                           |
|--------|-------------|-------------------------------------------|----------------------------------|
| POST   | /login      | SignIn in the application                 |                                  |
| GET    | /users/me   | retrieves logged user infos               |                                  |
| GET    | /users      | retrieves information about all the users |                                  |
| GET    | /users/{cpf}| retrieves information of an user          | cpf: user's cpf
| GET    | /todos      | get all todos visible by role             | userCpf: todo owner's cpf        |
| POST   | /todos      | add a new todo                            |                                  |
| PUT    | /todos/{id} | modifies a existing todo                  | id: id of the corresponding todo |
| DELETE | /todos/{id} | deletes a existing todo                   | id: id of the corresponding todo |