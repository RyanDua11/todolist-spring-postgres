# 📝 To-Do List API

Uma API REST de gerenciamento de tarefas, construída do zero como projeto prático para aplicar tudo o que aprendi nos cursos (Bradesco, FIAP, IFRS) e na faculdade (ADS) — mas com um propósito real: ser um facilitador de verdade no meu dia a dia, não só um exercício acadêmico.

## 💡 A ideia

Vi a inspiração inicial para um to-do list em um post no Instagram, mas o que estava lá era bem cru: um backend simples com banco de dados temporário (H2, que reseta os dados toda vez que a aplicação reinicia), sem CSS, sem HTML trabalhado — só a funcionalidade básica de CRUD.

Decidi pegar essa ideia como ponto de partida e transformá-la em um projeto de verdade. Queria algo que eu realmente usasse no dia a dia para organizar tarefas, e por isso fui além do básico:

- Troquei o banco temporário por **PostgreSQL persistente**
- **Containerizei tudo com Docker**, de forma reproduzível
- Construí uma **interface visual do zero**, com identidade própria

## 🛠️ Stack utilizada

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 + Spring Boot 4.1.0 |
| ORM | Hibernate ORM 7.4.1 |
| Banco de dados | PostgreSQL (via Docker) |
| Build | Maven |
| Frontend | HTML + CSS + JavaScript (vanilla) |
| Testes de API | Cliente HTTP do IntelliJ (`testes.http`) |
| Containerização | Docker / Docker Compose |

## 📊 Comparativo: ponto de partida vs. versão final

| Aspecto | Inspiração inicial (Instagram) | Versão final (este projeto) |
|---|---|---|
| Banco de dados | H2 em memória — dados somem a cada restart | PostgreSQL persistente, em container Docker |
| Infraestrutura | Nenhuma, só execução local | `docker-compose.yml` reproduzível com um único comando |
| Front-end | Inexistente | HTML + CSS customizado, tema dark, animações de hover, feedback visual de tarefa concluída |
| Persistência | Não se aplica (H2 não persiste por natureza) | Testada na prática: container removido e recriado, dados confirmados intactos |
| CRUD | Básico (assumido) | Completo: POST, GET, PUT, DELETE — todos testados manualmente |

## 🚀 Como rodar o projeto

### Pré-requisitos
- Java 21 (JDK)
- Docker e Docker Compose
- Maven (ou usar o `mvnw` incluso no projeto)

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/RyanDua11/todolist-spring-postgres.git
cd todolist-spring-postgres
```

2. Suba o banco de dados PostgreSQL via Docker:
```bash
docker-compose up -d
```

3. Rode a aplicação Spring Boot:
```bash
./mvnw spring-boot:run
```

4. A API estará disponível em `http://localhost:8080/tarefas`, e a interface visual em `http://localhost:8080`.

### Testando os endpoints

O arquivo `testes.http` contém requisições prontas para testar todos os endpoints (POST, GET, PUT, DELETE) diretamente pelo IntelliJ ou outro cliente HTTP compatível.

## 🧩 Desafios técnicos enfrentados

Documentando aqui os principais bugs reais que encontrei durante o desenvolvimento, com causa raiz e solução — porque acho que processo de debugging conta tanto quanto o resultado final:

### 1. Maven não baixava o driver do PostgreSQL

Passei um bom tempo investigando: testei conectividade, troquei versão do driver, usei `-U` e `-X` no Maven, isolei o problema via PowerShell, tentei instalação manual no repositório local `.m2`.

**Causa raiz:** um typo no `pom.xml` — estava escrito `org.postgreql` em vez de `org.postgresql`.

**Lição:** um erro de digitação minúsculo pode gerar sintomas que parecem totalmente desconectados da causa real.

### 2. Migração para Docker Compose quebrou o container (loop de restart)

Ao migrar para Docker Compose, reaproveitei um volume de dados já existente (`external: true`), mas o caminho de montagem (`/var/lib/postgresql/data`) estava incompatível com o formato de dados do PostgreSQL 18+, que organiza os dados em uma subpasta por versão (via `pg_ctlcluster`).

**Diagnóstico:** inspeção dos logs via `docker logs` e investigação do volume usando um container Alpine descartável.

**Correção:** ajustei o caminho de montagem para `/var/lib/postgresql`.

**Lição:** bug de versionamento/compatibilidade de infraestrutura é um tipo de problema bem diferente de debugar lógica de código — exige outra abordagem de investigação.

### 3. Edição manual do CSS quebrou o HTML inteiro (85 erros no editor)

O auto-fechamento de chaves do IntelliJ duplicava fechamentos durante a digitação manual, gerando uma cascata de erros de sintaxe.

**Solução:** em vez de tentar corrigir trecho por trecho, substituí o arquivo inteiro de uma vez (selecionar tudo, apagar, colar o conteúdo completo e correto).

**Lição:** às vezes recomeçar limpo é mais rápido do que tentar consertar uma cascata de erros já instaurada.

## 📂 Estrutura do projeto

```
src/
├── main/
│   ├── java/com/example/todolist/
│   │   ├── Todo.java               # Entidade
│   │   ├── TodoController.java     # Endpoints REST
│   │   ├── TodoRepository.java     # Acesso a dados
│   │   └── TodoListApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/index.html       # Interface visual
└── test/
    └── java/com/example/todolist/
        └── TodoListApplicationTests.java
```

## 🔭 Próximos passos / evoluções planejadas

- Adicionar camada de **Service**, separando regra de negócio do Controller (boa prática que pretendo aplicar na próxima iteração)
- Implementar autenticação de usuário
- Deploy em ambiente gratuito (Render/Railway)

## 👤 Autor

Ryan Duarte Quintão — estudante de Análise e Desenvolvimento de Sistemas, em transição para a área de tecnologia.

[LinkedIn](https://www.linkedin.com/in/ryan-duarte-39028a2a1?utm_source=share_via&utm_content=profile&utm_medium=member_ios) · [GitHub](https://github.com/RyanDua11)