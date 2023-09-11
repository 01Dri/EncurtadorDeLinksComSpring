# Encurtador de Links

Este projeto foi desenvolvido com o objetivo de aprimorar meus conhecimentos, abraçando o desafio proposto pelo repositório [BackEnd Brasil](https://github.com/backend-br).

## Teste o Encurtador de Links

Você pode testar o Encurtador de Links diretamente em um servidor AWS EC2 Amazon. Acesse os seguintes endpoints:

- **Frontend Básico:** [http://54.224.59.14:8080/page](http://54.224.59.14:8080/page)
  - Este endpoint contém um frontend básico com HTML, JavaScript e CSS.

- **Listar URLs Encurtadas em JSON:** [http://54.224.59.14:8080/encurtador/all](http://54.224.59.14:8080/encurtador/all)
  - Este endpoint exibe em formato JSON todas as URLs encurtadas armazenadas no banco de dados.

### Pré-requisitos

- Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina. Se você ainda não os possui, pode baixá-los e instalá-los seguindo as instruções nos sites oficiais do [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/).
- O projeto foi compilado utilizando Java SDK 17.

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

### Testando a Aplicação

Você pode testar a aplicação executando testes unitários e de integração. Siga os passos abaixo:

### Testes Unitários

1. **Navegue até o diretório raiz do projeto:**
    ```bash
     cd EncurtadorDeLinksComSpring
2. **Após isso inicie os testes com o comando abaixo:**
   ```bash
   /mvnw clean test
   
Isso executará todos os testes unitários no projeto.

### Testes de Integração
Para executar os testes de integração, você precisará ter o Docker e o Docker Compose instalados e ter iniciado o projeto com o Docker Compose (consulte os Passos no README para iniciar o projeto).

### Execute os testes unitários com o comando Maven:**
1. **Navegue até o diretório raiz do projeto:**
    ```bash
     cd EncurtadorDeLinksComSpring
2. **Após isso inicie os testes com o comando abaixo:**
   ```bash
   ./mvnw clean verify -Pintegration-test



## Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

- [Spring Boot](https://spring.io/projects/spring-boot): Um framework para facilitar a criação de aplicativos Java.
- [PostgreSQL](https://www.postgresql.org/): Um sistema de gerenciamento de banco de dados relacional de código aberto.
- [JUnit](https://junit.org/junit5/): Um framework para testes unitários em Java.
- [Mockito](https://site.mockito.org/): Uma biblioteca para criação de mocks e simulação de objetos para testes.
- [Testcontainers](https://java.testcontainers.org/): Uma biblioteca para configurar o ambiente Docker para teste de integração.
- [RestAssured](https://rest-assured.io/): Uma biblioteca para testes de integração.

## Metodologia

O sistema é composto por 3 *endpoints* principais:

- **localhost:8080/encurtador/encurtar Responsável por encurtar a URL fornecida, gerando uma chave (*ShortKey*) para acessar a URL original.
- **localhost:8080/enc/{shortKey}:** Responsável por redirecionar o usuário para o site desejado, utilizando a *ShortKey*.
- **localhost:8080/encurtador/all:** Responsável por exibir todos os URLS encurtados em formato JSON*.

## Camada de Serviço

A lógica aplicada é simples: ao fornecer a URL para encurtamento, um identificador único (UUID) é gerado, sendo denominado de *ShortKey*. Com esse valor, uma instância da classe *UrlEntity* é criada. Essa classe armazena informações como URL base, URL encurtada, data de criação, data de expiração e status de expiração.

### Validação e Tempo de Expiração

A URL encurtada é armazenada no banco de dados com um tempo de expiração de 2 horas. Caso a URL expirada seja utilizada, será lançada uma exceção denominada *"UrlShortenerExpired"*.
