# API de Gerenciamento de Tarefas

API REST desenvolvida em Java com Spring Boot, Spring Web MVC e Spring Data JDBC para gerenciamento de tarefas, utilizando MySQL como banco de dados.

## Tecnologias utilizadas
- Java
- Spring Boot
- Spring Web MVC
- Spring Data JDBC
- MySQL
- Maven

## Como executar o projeto

### 1. Clone o repositório
```bash
git clone https://github.com/ZZItalo/api-proj.git
cd api-proj
```

### 2. Configure o banco de dados MySQL
Crie o banco de dados no MySQL e execute o script de criação da tabela:

```sql
CREATE DATABASE IF NOT EXISTS nome_do_banco;
USE nome_do_banco;

CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT UNSIGNED AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    body VARCHAR(600),
    status ENUM('IN_PROGRESS', 'PENDING', 'COMPLETE', 'CANCELLED', 'EXPIRED') NOT NULL,
    zone_offset VARCHAR(6) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update_date DATETIME NOT NULL,
    due_date DATETIME,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);
```

### 3. Configure a conexão da aplicação
No arquivo `task-api/src/main/resources/application.properties`, ajuste os dados de conexão com o seu banco local (ou defina as variáveis de ambiente correspondentes):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco?useSSL=true&serverTimezone=UTC
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Execute a API
Rode a aplicação pela sua IDE ou pelo terminal (dentro da pasta `task-api`):

```bash
./mvnw spring-boot:run
```

## Endpoints principais
- `GET /task/{id}` — Recupera uma tarefa por ID
- `GET /task` — Lista tarefas com filtros opcionais via *query parameters*:
	- `year` — Ano da tarefa
	- `dayOfWeek` — Dia da semana
	- `date1` e `date2` — Intervalo entre duas datas
	- `taskStatus` — Status da tarefa (`IN_PROGRESS`, `PENDING`, `COMPLETE`, `CANCELLED`, `EXPIRED`)
- `POST /task` — Cria uma nova tarefa
- `PATCH /task` — Atualiza uma tarefa existente
- `DELETE /task/{id}` — Remove uma tarefa

## Propósito do Projeto
Desenvolvido para praticar a criação de APIs REST com Spring Boot, Spring Data JDBC e modelagem relacional no MySQL.