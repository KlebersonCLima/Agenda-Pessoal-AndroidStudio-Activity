# 📅 Agenda Pessoal – Aplicativo Android

Este projeto é fruto do esforço colaborativo de quatro desenvolvedores comprometidos com a entrega de uma solução funcional, objetiva e tecnicamente aderente às especificações propostas. Trata-se de um **aplicativo de agenda pessoal**, desenvolvido utilizando a arquitetura **MVC (Model-View-Controller)** em **Java**, com persistência local via **SQLite**, e interface construída com **Activity** e **Fragments**.

---

##  Desenvolvedores

- **Kleberson Crystyan de Lima**
- **Vinicius Fernandes Betti**
- **Gabriel Henrique Custódio**
- **Vitor Hugo Granato Moreira do Prado**

---

## Objetivo

Desenvolver um app que permita o **cadastro e visualização de compromissos diários**, estruturado exatamente conforme os critérios definidos na proposta da disciplina.

---

## ✅ Funcionalidades Implementadas

###  Modelo 
- Implementação da classe `Compromisso` com atributos: `data`, `hora`, `descrição` e `id`.
- Métodos **getters** e **setters** conforme o padrão MVC.

### Interface do Usuário (View + Controller)
- Tela dividida em dois **Fragments**:
  - **Fragmento superior**: cadastro de compromisso com seleção de data, hora e descrição.
  - **Fragmento inferior**: visualização de compromissos por data com rolagem e ordenação.
- Botões funcionais:
  - `Data`: abre DatePicker.
  - `Hora`: abre TimePicker.
  - `OK`: salva compromisso.
  - `Hoje`: mostra compromissos do dia atual.
  - `Outra Data`: abre seletor para consultar compromissos de outra data.

### Persistência (SQLite)
- Banco de dados local criado via `SQLiteOpenHelper`.
- Classes auxiliares:
  - `CompromissosDBHelper`
  - `CompromissosDB`
  - `CompromissosDBSchema`
- CRUD funcional com busca filtrada por data e ordenação por horário.

---

## Tecnologias Utilizadas

- **Java**
- **Android SDK**
- **Fragments**
- **DatePickerDialog / TimePickerDialog**
- **SQLite**
- **Git & GitHub**

---

## Observação

Todos os requisitos do projeto foram **seguros integralmente**, com atenção especial às estruturas de código, organização modular e aplicação fiel da arquitetura solicitada. O resultado é um aplicativo funcional, coeso e pronto para uso acadêmico e evoluções futuras.

---

## Prints
![image](https://github.com/user-attachments/assets/a287116a-a626-4472-adf6-94c033222664)
![image](https://github.com/user-attachments/assets/134001b9-75ab-44bb-85b7-afce1341ef5f)



