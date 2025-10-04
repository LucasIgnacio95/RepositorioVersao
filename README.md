<img width="1913" height="1012" alt="image" src="https://github.com/user-attachments/assets/8d948107-da10-4df2-a7e8-c570eb776423" /><img width="942" height="737" alt="image" src="https://github.com/user-attachments/assets/49d91137-8dc5-4bdd-bf64-05294f5e784e" /># Repositório de Versões

![Java](https://img.shields.io/badge/Java-11-blue?style=for-the-badge&logo=openjdk)
![SQL Server](https://img.shields.io/badge/SQL%20Server-CC2927?style=for-the-badge&logo=microsoft-sql-server)
![Apache NetBeans IDE](https://img.shields.io/badge/NetBeans-12.0%2B-blue?style=for-the-badge&logo=apache-netbeans-ide)

Aplicação desktop desenvolvida em Java Swing para gerenciamento e versionamento de arquivos (APKs) associados a chamados de clientes.

---

## 📖 Sobre o Projeto

O **Repositório de Versões** é uma ferramenta interna criada para centralizar e organizar as diferentes versões de softwares (arquivos `.apk`) liberadas para clientes. A aplicação permite que um usuário faça o upload de uma nova versão, vinculando-a a um cliente, cidade e número de chamado específico, e também permite a busca e o download dessas versões de forma rápida e organizada.

O sistema possui uma interface gráfica intuitiva e se conecta a um banco de dados SQL Server para persistir todas as informações.

---

## ✨ Funcionalidades

-   **Upload de Versões:** Permite selecionar um arquivo `.apk` local, associá-lo a um cliente, cidade e chamado, e salvá-lo em um diretório organizado no servidor.
-   **Download por Chamado:** Busca e download de uma versão específica através do número do chamado.
-   **Consulta e Download por Cidade:** Exibe em uma tabela todos os arquivos e chamados associados a uma cidade, permitindo a seleção e o download.
-   **Gerenciamento de Registros:**
    -   **Editar:** Altera a observação de um chamado já existente.
    -   **Excluir:** Remove o registro do banco de dados e também o arquivo e a pasta correspondente no diretório físico.
-   **Interface Amigável:** Navegação entre telas de upload e download com uma experiência de usuário focada na simplicidade.

---

## 🖼️ Demonstração

*(Dica: Tire prints das suas telas principais e coloque aqui! Isso ajuda muito a entender o projeto visualmente).*

**Tela de Upload de Versão**
<img width="942" height="737" alt="image" src="https://github.com/user-attachments/assets/b5717cb6-55cd-4411-a561-b2f0ad690e73" />



**Tela de Download por Cidade com a Tabela**
<img width="1913" height="1012" alt="image" src="https://github.com/user-attachments/assets/b1faeb0b-6365-485c-b165-3d6590ab15ba" />


**Tela de Download por Chamado**
<img width="936" height="737" alt="image" src="https://github.com/user-attachments/assets/08ab5cda-8d5e-47e3-af83-9efdcaa73d72" />

---

## 🛠️ Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias:

-   **Linguagem:** [Java](https://www.java.com/) (utilizando JRE 11)
-   **Interface Gráfica:** [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
-   **Banco de Dados:** [Microsoft SQL Server](https://www.microsoft.com/pt-br/sql-server/)
-   **Conectividade com BD:** JDBC (Java Database Connectivity)
-   **IDE de Desenvolvimento:** [Apache NetBeans](https://netbeans.apache.org/)

### Bibliotecas Externas

-   **Layout Manager:** [MigLayout](http://www.miglayout.com/) - Para criação de layouts complexos e flexíveis.
-   **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) - Para modernizar a aparência dos componentes Swing.

---

## ⚙️ Pré-requisitos e Instalação

Para executar este projeto em um ambiente de desenvolvimento, você precisará de:

1.  **JDK 11 ou superior:** [OpenJDK](https://jdk.java.net/) ou outra distribuição.
2.  **Microsoft SQL Server:** Uma instância ativa do banco de dados.
3.  **IDE Java:** Apache NetBeans, IntelliJ IDEA ou Eclipse.

### Passos para Execução

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/LucasIgnacio95/RepositorioVesao.git](https://github.com/LucasIgnacio95/RepositorioVesao.git)
    ```

2.  **Configure o Banco de Dados:**
    -   Crie um banco de dados no seu SQL Server (ex: `repositorioareatec`).
    -   Execute o script SQL abaixo para criar as tabelas necessárias. *(Este é um modelo baseado no seu código, ajuste se necessário)*.
    ```sql
    -- Tabela de Clientes
    CREATE TABLE CLIENTES (
        id INT PRIMARY KEY IDENTITY(1,1),
        cliente VARCHAR(255) NOT NULL UNIQUE
    );

    -- Tabela de Cidades
    CREATE TABLE CIDADES (
        id INT PRIMARY KEY IDENTITY(1,1),
        cidade VARCHAR(255) NOT NULL,
        id_cliente INT,
        FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id)
    );

    -- Tabela de Chamados
    CREATE TABLE CHAMADOS (
        id INT PRIMARY KEY IDENTITY(1,1),
        chamado VARCHAR(50) NOT NULL,
        observacao TEXT,
        id_cidade INT,
        id_cliente INT,
        FOREIGN KEY (id_cidade) REFERENCES CIDADES(id),
        FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id)
    );

    -- Tabela de Arquivos
    CREATE TABLE ARQUIVOS (
        id INT PRIMARY KEY IDENTITY(1,1),
        nome_arquivo VARCHAR(255) NOT NULL,
        caminho_arquivo VARCHAR(MAX) NOT NULL,
        id_chamado INT,
        id_cidade INT,
        id_cliente INT,
        FOREIGN KEY (id_chamado) REFERENCES CHAMADOS(id) ON DELETE CASCADE,
        FOREIGN KEY (id_cidade) REFERENCES CIDADES(id),
        FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id)
    );
    ```

3.  **Atualize a Conexão no Código:**
    -   Abra o arquivo `src/main/java/com/lucasignacio/repositorioversao/ConexaoSQL.java`.
    -   Altere as constantes `URL`, `USUARIO` e `SENHA` com as credenciais do seu banco de dados.

4.  **Adicione as Bibliotecas (JARs):**
    -   No NetBeans, clique com o botão direito em "Bibliotecas" > "Adicionar JAR/Pasta".
    -   Adicione os arquivos `.jar` do **MigLayout** e do **FlatLaf**.
    -   Adicione o driver **JDBC para SQL Server**.

5.  **Execute o Projeto:**
    -   Encontre a classe `Main.java` e execute-a.

---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
