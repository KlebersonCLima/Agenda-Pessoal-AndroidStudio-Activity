# Agenda Pessoal – App Android

**Desenvolvido por:** Kleberson Crystyan de Lima  
**Stack:** Android + Java + SQLite  
**Arquitetura:** MVC (Model-View-Controller)

---

## Visão Geral

Aplicativo Android nativo para gerenciamento de compromissos pessoais. Projetado com foco em modularidade, clareza de código e experiência do usuário, seguindo rigorosamente o padrão de arquitetura MVC e boas práticas de desenvolvimento mobile.

---

## Funcionalidades

- Cadastro de compromissos com data, hora e descrição.
- Listagem de compromissos com filtros automáticos por data.
- Interface modular baseada em `Fragments`.
- Persistência local com SQLite.
- CRUD completo (Create, Read, Update, Delete).
- Uso de DatePicker e TimePicker nativos do Android.

---

## Estrutura do Projeto

**Model:**
- `Compromisso.java`: Classe de entidade com atributos, getters/setters e encapsulamento.
- `CompromissosDB.java`: Operações de acesso ao banco.
- `CompromissosDBHelper.java`: Criação e versionamento do banco.

**View:**
- `MainActivity.java`
- `FragmentoCadastro.java`
- `FragmentoVisualizacao.java`

**Controller:**
- Lógica integrada às views.
- Controle de fluxo entre os fragments.
- Validação de entrada e comunicação com a camada de dados.

---

## Stack Tecnológica

| Tecnologia     | Função                |
|----------------|-----------------------|
| Java           | Lógica e estrutura    |
| Android SDK    | Framework de interface|
| SQLite         | Persistência local    |
| Fragments      | Modularidade de UI    |

---

## Arquitetura Visual

- `MainActivity`: Responsável por carregar os fragments de cadastro e visualização.
- `FragmentoCadastro`: Formulário para inserção de compromissos.
- `FragmentoVisualizacao`: Lista de compromissos com filtros por data.
- `SQLite`: Banco de dados local com armazenamento persistente.

---

## Métricas do Projeto

- Linhas de Código: ~800  
- Classes Criadas: 6  
- Padrão Arquitetural: MVC  
- Compatibilidade: Android 5.0+
