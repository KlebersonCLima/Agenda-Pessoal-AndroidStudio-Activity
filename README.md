# Agenda Pessoal

Aplicativo Android nativo para gerenciamento de compromissos pessoais, com arquitetura MVC e persistência local em SQLite.

## Funcionalidades

- CRUD completo de compromissos (data, hora e descrição)
- Listagem com filtro por data
- Interface modular com `Fragments`
- `DatePicker` e `TimePicker` nativos

## Tecnologias

- Java
- Android SDK (compatível com Android 5.0+)
- SQLite

## Arquitetura (MVC)

| Camada | Componentes |
|---|---|
| Model | `Compromisso`, `CompromissosDB`, `CompromissosDBHelper` |
| View | `MainActivity`, `FragmentoCadastro`, `FragmentoVisualizacao` |
| Controller | Fluxo entre fragments, validação de entrada e acesso aos dados |

## Executar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Execute em um emulador ou dispositivo (Android 5.0+).

## Autor

Kleberson Crystyan de Lima
