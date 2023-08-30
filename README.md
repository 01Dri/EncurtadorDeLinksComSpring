# Encurtador de Links

Este projeto foi desenvolvido com o objetivo de aprimorar meus conhecimentos, abraçando o desafio proposto pelo repositório [BackEnd Brasil](https://github.com/backend-br).

### Pré-requisitos

- Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina. Se você ainda não os possui, pode baixá-los e instalá-los seguindo as instruções nos sites oficiais do [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/).

### Passos

1. **Clone este repositório para o seu ambiente local:**

   ```bash
   git clone https://github.com/01Dri/EncurtadorDeLinksComSpring.git
2. **cd encurtador-de-links:**
   ```bash
   cd EncurtadorDeLinksComSpring
3. **Abra o arquivo docker-compose.yml e verifique as configurações do serviço PostgreSQL**:
   ```bash
   docker-compose up -d
4. **Parar e remover os contêineres:**
   ```bash
   docker-compose down

## Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

- [Spring Boot](https://spring.io/projects/spring-boot): Um framework para facilitar a criação de aplicativos Java.
- [PostgreSQL](https://www.postgresql.org/): Um sistema de gerenciamento de banco de dados relacional de código aberto.
- [JUnit](https://junit.org/junit5/): Um framework para testes unitários em Java.
- [Mockito](https://site.mockito.org/): Uma biblioteca para criação de mocks e simulação de objetos para testes.

## Metodologia

O sistema é composto por 2 *endpoints* principais:

- **localhost:8080/dri/encurtador/encurtar:** Responsável por encurtar a URL fornecida, gerando uma chave (*ShortKey*) para acessar a URL original.
- **localhost:8080/dri/encurtador/acessar:** Responsável por redirecionar o usuário para o site desejado, utilizando a *ShortKey*.

## Camada de Serviço

A lógica aplicada é simples: ao fornecer a URL para encurtamento, um identificador único (UUID) é gerado, sendo denominado de *ShortKey*. Com esse valor, uma instância da classe *UrlEntity* é criada. Essa classe armazena informações como URL base, URL encurtada, data de criação, data de expiração e status de expiração.

## Validação e Tempo de Expiração

A URL encurtada é armazenada no banco de dados com um tempo de expiração de 2 horas. Caso a URL expirada seja utilizada, será lançada uma exceção denominada *"UrlShortenerExpired"*.
