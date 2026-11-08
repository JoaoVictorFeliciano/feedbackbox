# FeedbackBox API

API REST para gerenciamento de feedbacks, desenvolvida com **Spring Boot 4**, **Java 21** e **Couchbase** como banco de dados NoSQL. A documentação interativa é gerada automaticamente com **SpringDoc OpenAPI (Swagger UI)**.

Permite cadastrar usuários, registrar feedbacks (classificados por categoria e com controle de status) e responder aos feedbacks.

## Sumário

- [Tecnologias](#tecnologias)
- [Modelo de domínio](#modelo-de-domínio)
- [Endpoints](#endpoints)
- [Pré-requisitos](#pré-requisitos)
- [Guia de instalação](#guia-de-instalação)
- [Configuração](#configuração)
- [Exemplos de uso](#exemplos-de-uso)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Tratamento de erros](#tratamento-de-erros)
- [Solução de problemas](#solução-de-problemas)

## Tecnologias

| Tecnologia | Versão |
| --- | --- |
| Java | 21 |
| Spring Boot | 4.0.9 |
| Spring Data Couchbase | 6.0.x |
| SpringDoc OpenAPI (Swagger UI) | 3.0.3 |
| Couchbase Server | Community (Docker) |
| Lombok | gerenciado pelo Spring Boot |
| Maven | via wrapper (`mvnw`) |

## Modelo de domínio

```
Usuario (1) ──── (N) Feedback (1) ──── (N) Resposta
```

- **Usuario**: `id`, `nome`, `email`, `dataCadastro`.
- **Feedback**: `id`, `usuarioId`, `titulo`, `mensagem`, `categoria`, `status`, `dataCriacao`.
- **Resposta**: `id`, `feedbackId`, `mensagem`, `responsavel`, `dataResposta`.

As entidades ficam no bucket `feedbackbox`, scope `app` e collections:

| Entidade | Collection |
| --- | --- |
| Usuario | `app.usuarios` |
| Feedback | `app.feedbacks` |
| Resposta | `app.respostas` |

### Enums

- **CategoriaFeedback**: `SUGESTAO`, `RECLAMACAO`, `ELOGIO`.
- **StatusFeedback**: `PENDENTE`, `EM_ANALISE`, `RESOLVIDO`.

> Regra de negócio: um feedback `RESOLVIDO` não pode voltar para `PENDENTE`.

> Observação: não há integridade referencial/cascata. Excluir um usuário **não** remove seus feedbacks, e excluir um feedback **não** remove suas respostas (eles ficam órfãos).

## Endpoints

Base URL: `http://localhost:8083`

### Usuários — `/api/usuarios`

| Método | Rota | Descrição | Sucesso |
| --- | --- | --- | --- |
| `POST` | `/api/usuarios` | Cadastra um usuário | `201` |
| `GET` | `/api/usuarios` | Lista todos os usuários | `200` |
| `GET` | `/api/usuarios/{id}` | Busca usuário por ID | `200` |
| `DELETE` | `/api/usuarios/{id}` | Remove um usuário | `204` |

### Feedbacks — `/api/feedbacks`

| Método | Rota | Descrição | Sucesso |
| --- | --- | --- | --- |
| `POST` | `/api/feedbacks` | Cadastra um feedback | `201` |
| `GET` | `/api/feedbacks` | Lista todos os feedbacks | `200` |
| `GET` | `/api/feedbacks/{id}` | Busca feedback por ID | `200` |
| `GET` | `/api/feedbacks/categoria/{categoria}` | Lista por categoria | `200` |
| `GET` | `/api/feedbacks/status/{status}` | Lista por status | `200` |
| `PATCH` | `/api/feedbacks/{id}/status?novoStatus={status}` | Atualiza o status | `200` |
| `DELETE` | `/api/feedbacks/{id}` | Remove um feedback | `204` |

### Respostas — `/api/respostas`

| Método | Rota | Descrição | Sucesso |
| --- | --- | --- | --- |
| `POST` | `/api/respostas` | Cadastra uma resposta | `201` |
| `GET` | `/api/respostas` | Lista todas as respostas | `200` |
| `GET` | `/api/respostas/{id}` | Busca resposta por ID | `200` |
| `GET` | `/api/respostas/feedback/{feedbackId}` | Lista respostas de um feedback | `200` |
| `DELETE` | `/api/respostas/{id}` | Remove uma resposta | `204` |

### Documentação (Swagger)

| Recurso | URL |
| --- | --- |
| Swagger UI | http://localhost:8083/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8083/v3/api-docs |

## Pré-requisitos

- [JDK 21](https://adoptium.net/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (com o engine em execução)
- Maven **não** é obrigatório — o projeto já inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`)

## Guia de instalação

### 1. Clonar o repositório

```bash
git clone https://github.com/JoaoVictorFeliciano/feedbackbox.git
cd feedbackbox
```

### 2. Criar o volume externo do Couchbase

O `compose.yaml` usa um volume **externo** (`feedbackbox_couchbase_data`). Ele precisa existir antes de subir o container:

```bash
docker volume create feedbackbox_couchbase_data
```

### 3. Subir o Couchbase

```bash
docker compose up -d
```

> O `docker compose up` também é executado automaticamente pela aplicação (ver [Configuração](#configuração)), mas na primeira execução recomenda-se subir manualmente para inicializar o cluster.

Aguarde o container ficar saudável:

```bash
docker ps
```

### 4. Inicializar o Couchbase

Acesse a interface web em http://localhost:8091 e:

1. Conclua o **Setup New Cluster**:
   - Usuário: `Administrator`
   - Senha: `feedbackbox123`
   - (Esses valores estão em `src/main/resources/application.properties`.)
2. Crie o bucket **`feedbackbox`** (tipo Couchbase, cota de memória de pelo menos 256 MB).
3. Crie o scope **`app`** dentro do bucket.
4. Crie as collections no scope `app`:
   - `usuarios`
   - `feedbacks`
   - `respostas`

> Sem o scope/collections, a API falha com `Keyspace not found ... app.usuarios`.

**Alternativa via REST** (após criar bucket e scope pela UI):

```bash
# criar scope
curl -u Administrator:feedbackbox123 -X POST \
  "http://127.0.0.1:8091/pools/default/buckets/feedbackbox/scopes" \
  -d "name=app"

# criar collections
curl -u Administrator:feedbackbox123 -X POST \
  "http://127.0.0.1:8091/pools/default/buckets/feedbackbox/scopes/app/collections" \
  -d "name=usuarios"

curl -u Administrator:feedbackbox123 -X POST \
  "http://127.0.0.1:8091/pools/default/buckets/feedbackbox/scopes/app/collections" \
  -d "name=feedbacks"

curl -u Administrator:feedbackbox123 -X POST \
  "http://127.0.0.1:8091/pools/default/buckets/feedbackbox/scopes/app/collections" \
  -d "name=respostas"
```

### 5. Executar a aplicação

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Ou execute a classe `FeedbackboxApplication` diretamente na sua IDE.

A aplicação sobe na porta **8083**. Acesse o Swagger em:

```
http://localhost:8083/swagger-ui/index.html
```

### 6. Build e testes

```bash
# empacotar
./mvnw clean package

# rodar os testes (requer Docker + Couchbase disponíveis)
./mvnw test
```

## Configuração

Arquivo: `src/main/resources/application.properties`

```properties
spring.application.name=feedbackbox

# Conexão com Couchbase
spring.couchbase.connection-string=127.0.0.1
spring.couchbase.username=Administrator
spring.couchbase.password=feedbackbox123

# Bucket
spring.data.couchbase.bucket-name=feedbackbox

# Docker: sobe o compose ao iniciar e para ao encerrar
spring.docker.compose.lifecycle-management=start-and-stop

# Porta
server.port=8083
springdoc.swagger-ui.path=/swagger-ui.html
```

- **Ciclo de vida do Docker**: com `start-and-stop`, o container é iniciado junto com a aplicação e **parado** no shutdown normal. Se o processo for encerrado de forma abrupta, o container pode permanecer em execução.
- **Portas publicadas**: o `compose.yaml` expõe apenas as portas realmente utilizadas pelo Couchbase (`8091-8094` e `11210`). Isso evita que a verificação de readiness do Spring fique presa em portas que o Couchbase abre e fecha imediatamente.

## Exemplos de uso

### Criar usuário

```bash
curl -X POST http://localhost:8083/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Victor","email":"joao@exemplo.com"}'
```

### Criar feedback

```bash
curl -X POST http://localhost:8083/api/feedbacks \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": "<ID_DO_USUARIO>",
    "titulo": "Melhorar o app",
    "mensagem": "Gostaria de sugerir uma melhoria na navegação.",
    "categoria": "SUGESTAO"
  }'
```

### Atualizar status do feedback

```bash
curl -X PATCH "http://localhost:8083/api/feedbacks/<ID_DO_FEEDBACK>/status?novoStatus=EM_ANALISE"
```

### Responder um feedback

```bash
curl -X POST http://localhost:8083/api/respostas \
  -H "Content-Type: application/json" \
  -d '{
    "feedbackId": "<ID_DO_FEEDBACK>",
    "mensagem": "Obrigado pelo feedback, já estamos analisando.",
    "responsavel": "Equipe de Suporte"
  }'
```

## Estrutura do projeto

```
src/main/java/com/joaovictor/feedbackbox/
├── config/         # Configuração do OpenAPI (SwaggerConfig)
├── controller/     # Controllers REST (Usuário, Feedback, Resposta)
├── dto/            # DTOs de request/response
├── enumerate/      # Enums (CategoriaFeedback, StatusFeedback)
├── exception/      # Exceções personalizadas
├── model/          # Entidades do Couchbase
├── repository/     # Repositórios Spring Data Couchbase
└── service/        # Regras de negócio
```

## Tratamento de erros

Exceções personalizadas (pacote `exception`):

| Exceção | Quando ocorre |
| --- | --- |
| `UsuarioNaoEncontradoException` | Usuário inexistente |
| `EmailJaCadastradoException` | E-mail já cadastrado |
| `FeedbackNaoEncontradoException` | Feedback inexistente |
| `RespostaNaoEncontradaException` | Resposta inexistente |
| `TransicaoStatusInvalidaException` | Tentativa de voltar feedback `RESOLVIDO` para `PENDENTE` |

Atualmente essas exceções são lançadas nos services e não há um handler global mapeando para códigos HTTP específicos — requisições que caem nelas retornam erro genérico (`500`) por padrão.

## Solução de problemas

| Sintoma | Causa provável | Solução |
| --- | --- | --- |
| `Port 8083 was already in use` | Já existe uma instância da aplicação rodando | Pare a instância anterior (IDE/terminal) antes de subir outra |
| App trava em `Using Docker Compose file` | Docker Desktop/engine não terminou de subir | Aguarde o Docker ficar "running" e inicie novamente |
| `Keyspace not found ... app.usuarios` | Scope `app` ou collections não existem | Crie o scope e as collections no Couchbase |
| `external volume ... not found` | Volume externo não criado | `docker volume create feedbackbox_couchbase_data` |
| `No index available` (consultas) | Falta índice N1QL | Crie um índice primário na collection (UI do Couchbase ou N1QL) |
| Container não para ao encerrar a app | Processo encerrado de forma abrupta | `docker compose stop` manualmente |
