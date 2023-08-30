### Encurtador de links!
Afins de aprimorar meu conhecimento, vou tentar resolver o desafio proposto pelo repositório [BackEnd Brasil](https://github.com/backend-br)

### Qual a metodologia?
O sistema é composto por 2 *ENDPOINTS* principais, são eles:
- *localhost:8080/dri/encurtador/encurtar:* (Responsável pelo encurtamento da *URL* fornecida, criando a chave, para acesso do *URL* principal (*ShortKey*).
- *localhost:8080/dri/encurtador/acessar:* (Responsável pelo acesso e redirecionamento ao site desejado, por meio da *ShortKey*).

### Como funciona a camada de service?
A lógica aplicada, é simples, ao fornece o *URL* desejado para o encurtamento, é gerado um *UUID* unico, o que eu chamo de *ShortKey*, após isso, um objeto *UrlEntity* é criado, afins de persistir dados como, *urlBase*, *urlShortener*, *dateCreatedUrlShortener*, *expiredDate* e *expired*.

### Validação e tempo de expiração
O tempo definido em que a URL encurtada e salva no database, chegue a expirar, é de *2 HRS*, caso utilize a mesma, é disparada um exceção *"UrlShortenerExpired"*



