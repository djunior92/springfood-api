<img src="https://img.shields.io/badge/Java-11-orange"/>

# SpringFood API
**Especialista Spring REST**\
Desenvolvimento de um MVP (Minimum Viable Product) da API de uma empresa de delivery de comida.

## Tecnologias utilizadas
### Visão geral
| Tecnologia         | Descrição                    |
|--------------------|------------------------------|
| Core Framework     | Spring Boot 2.5.6            |
| Security Framework | Spring Security, JWT         |
| Persistência       | Spring Data JPA              |
| Armazenamento      | Spring Data Redis, MySQL 8.0 |


### Data

| Tecnologia                                 | Descrição                                             |
|--------------------------------------------|-------------------------------------------------------|
| <a href="https://flywaydb.org/">Flyway</a> | Controle de versão para banco de dados                |
| <a href="https://www.mysql.com/">MySQL</a> | Sistema de gerenciamento de banco de dados relacional |
| <a href="https://redis.io/">Redis</a>      | Armazenamento de estrutura de dados em memória        |

### Client - Frontend/UI

| Tecnologia                                                 | Descrição                                                                      |
|------------------------------------------------------------|--------------------------------------------------------------------------------|
| <a href="https://www.thymeleaf.org/">Thymeleaf</a>         | Mecanismo de modelo Java do lado do servidor para ambientes da Web e autônomos |

### Servidor - Backend

| Tecnologia                                                       | Descrição                                                               |
|------------------------------------------------------------------|-------------------------------------------------------------------------|
| <a href="https://openjdk.java.net/">JDK</a>                      | Java™ Platform, Standard Edition Development Kit                        |
| <a href="https://spring.io/projects/spring-boot">Spring Boot</a> | Framework para facilitar o desenvolvimento de novos aplicativos Spring  |
| <a href="https://maven.apache.org/">Maven</a>                    | Gerenciamento de Dependências                                                  |
| <a href="https://jwt.io/">JSON Web Token</a>                     | Codifique ou decodifique JWTs                                                 |

###  Bibliotecas e Plugins

| Tecnologia                                                                 | Descrição                                                                                                                            |
|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| <a href="https://projectlombok.org/">Lombok</a>                            | Uma biblioteca java que se conecta automaticamente ao seu editor e cria ferramenta, facilitando o desenvolvimento                    |
| <a href="https://swagger.io/">Swagger</a>                                  | Framework apoiado por um grande ecossistema de ferramentas que ajuda os desenvolvedores a projetar, construir e documentar projetos. |
| <a href="https://community.jaspersoft.com/">Jasper Reports</a>             | Framework apoiado por um grande ecossistema de ferramentas que ajuda os desenvolvedores a projetar, construir e documentar projetos. |
| <a href="https://github.com/spotify/dockerfile-maven">Dockerfile Maven</a> | Este plugin Maven integra o Maven com o Docker                                                                                       |

### Ferramentas e serviços externos

| Tecnologia                                          | Descrição                                                                                                                                                         |
|-----------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <a href="https://www.getpostman.com/">Postman</a>   | API Client (Testar documentação)                                                                                                                                  |
| <a href="https://sendgrid.com/">SendGrid</a>        | Plataforma para envio de e-mails transacionais                                                                                                                    |
| <a href="https://aws.amazon.com/pt/s3/">AWS S3</a>  | Serviço oferecido pela Amazon Web Services que fornece armazenamento de objetos por meio de uma interface de serviço da web.                                      |
| <a href="https://www.nginx.com/">Nginx</a>          | Servidor HTTP utilizado como proxy reverso                                                                                                                        |
| <a href="https://www.loggly.com/">Loggly</a>        | Provedor de serviços de análise e gerenciamento de logs baseado em nuvem                                                                                          |
| <a href="https://git-scm.com/">git</a>              | Sistema de controle de versão distribuído gratuito e de código aberto                                                                                             |
| <a href="https://www.docker.com/">Docker</a>        | Um conjunto de produtos de plataforma como serviço que usam virtualização no nível do sistema operacional para entregar software em pacotes chamados contêineres. |

## Construindo e executando a aplicação
Para obter o projeto é necessário realizar o "clone" (GitHub):
```text
https://github.com/djunior92/springfood-api.git
```

Para gerar uma imagem docker, execute o seguinte comando no terminal de comandos (no diretório raiz do projeto):
```text
./mvnw package -Pdocker
```

Para executar e escalar a aplicação execute o seguinte comando (no diretório raiz do projeto):
```text
docker-compose up --scale springfood-api=NUMERO_DE_INSTANCIAS
```

## Testes de integração
A aplicação utiliza JUnit 5 e REST Assured para a realização do testes de integração. Os testes são executados em um banco de dados separado (springfood_test). Por padrão, os testes são desabilitados pelo Maven Failsafe Plugin. Para executar os testes é necessário executar o seguinte comando (no diretório raiz do projeto):
```text
./mvnw verify
```

## Endpoints
```text
- Ponto de entrada raiz: /v1
- Cidades: /v1/cidades
- Cozinhas: /v1/cozinhas
- Estados: /v1/estados
- Estatísticas: /v1/estatisticas
- Formas de Pagamento: /v1/formas-pagamento
- Fotos de Produtos: /v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto
- Grupos de Usuários: /v1/grupos
- Pedidos: /v1/pedidos
- Permissões: /v1/permissoes
- Produtos: /v1/restaurantes/{restauranteId}/produtos
- Restaurantes: /v1/restaurantes
- Usuários: /v1/usuarios
 ```
Obs: É possível importar o arquivo do Postman "springfood.postman_collection.json" (localizado no diretório raiz do projeto) que contém todos os  endpoints do projeto.

## Profiles
A aplicação aceita dois tipos de perfis:
- development
    - Armazenamento de imagem (foto do produto) local
    - Envio de e-mail fake (exibe o e-mail no console)
    - Armazenamento da sessão local


- production
    - Armazenamento de imagem com AWS S3
    - Envio de e-mail real com SMTP (SendGrid)
    - Armazenamento da sessão com Redis
    - Gerenciamento de logs com o serviço (em nuvem) Loggly

## Segurança
A aplicação contém um servidor de autorização (Authorization Server). Para acessar os endpoints é necessário que o usuário realize autenticação por algum dos fluxos de autorização:
- Password Flow Grant Type:
```text
É necessário realizar uma requisição POST para o endpoint "/oauth/token" contendo um header "Contet-Type" com "application/x-www-form-urlencoded" e informar os seguintes parâmetros no body:
    - username: Usuário registrado na plataforma;
    - password: Senha do usuário registrado na plataforma;
    - grant_type: Valor fixo com "password".
 ```
Obs: Requer o header "Authorization" com a autenticação do Client da requisição.

- Authorization Code Grant Type:
```text
- Para realizar a autenticação, primeiramente é necessário gerar um novo "code" (que será utilizado posteriormente para obter um access token) contendo as autorizações dos escopos. Para isso, é necessário realizar uma requisição (GET pelo navegador ou uma aplicação HTTP) contendo os seguintes parâmetros:
    - response_type: Valor fixo com "code".
    - client_id: Informando a "id do client".
    - state: Uma string aleatória enviada pelo client e no momento de retorno é possível validar se houve inconsistência.
    - redirect_uri: Contendo a URL que a requisição será redirecionada após obter o código.

Ex: http://localhost/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://aplicacao-cliente

- Para gerar um novo access token (após obter o "code"), é necessário realizar uma requisição POST para a URI "/oauth/token".

A requisição deve ter Basic Auth (informando as credenciais do Client) e o Body da requisição deve ser do tipo "x-www-form-urlencoded" e possuir os parâmetros: 
    - code: Informando qual o código recebido.
    - grant_type: Valor fixo com authorization_code".
    - redirect_uri: Contendo o link de redirecionamento para o client
 ```

- Authorization Code Grant Type com suporte a PKCE:
```text
- A autenticação é semelhante ao método do Authorization Code porém é necessário informar mais 2 parâmetros no momento de obtenção do "code":
    - code_challenge: Uma string aleatória de no mínimo 43 caracteres e no máximo 128. É a String gerada no CODE VERIFIER, passada pelo algoritmo SHA-256 (ou pelo método PLAIN) e o resultado combinado com Base64Url.
    - code_challenge_method: Informando o método s256 (SHA-256) ou PLAIN.
    
- Para gerar um novo access token (após obter o "code"), é necessário realizar uma requisição POST para a URI "/oauth/token". Porém agora não é necessário utilizar a autenticação básica (Basic Auth) mas sim informar o client_id e o "code_verifier" (código de desafio do code challenge).    
 ```

- Client Credentials Grant Type:
```text
Para gerar um novo access token, é necessário realizar uma requisição POST para a URI "/oauth/token". O Body da requisição deve ser do tipo "x-www-form-urlencoded" e possuir um parâmetro chamado "grant_type" informando o valor fixo "client_credentials".
 ```
Obs: Requer o header "Authorization" com a autenticação do Client da requisição.