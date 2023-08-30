# Encurtador de Links

Este projeto foi desenvolvido com o objetivo de aprimorar meus conhecimentos, abraçando o desafio proposto pelo repositório [BackEnd Brasil](https://github.com/backend-br).

## Metodologia

O sistema é composto por 2 *endpoints* principais:

- **localhost:8080/dri/encurtador/encurtar:** Responsável por encurtar a URL fornecida, gerando uma chave (*ShortKey*) para acessar a URL original.
- **localhost:8080/dri/encurtador/acessar:** Responsável por redirecionar o usuário para o site desejado, utilizando a *ShortKey*.

## Camada de Serviço

A lógica aplicada é simples: ao fornecer a URL para encurtamento, um identificador único (UUID) é gerado, sendo denominado de *ShortKey*. Com esse valor, uma instância da classe *UrlEntity* é criada. Essa classe armazena informações como URL base, URL encurtada, data de criação, data de expiração e status de expiração.

## Validação e Tempo de Expiração

A URL encurtada é armazenada no banco de dados com um tempo de expiração de 2 horas. Caso a URL expirada seja utilizada, será lançada uma exceção denominada *"UrlShortenerExpired"*.
