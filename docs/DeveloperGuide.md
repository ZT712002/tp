# Developer Guide

## Acknowledgements
This was heavily inspired by the AB3 reference DG found [here](https://github.com/se-edu/addressbook-level3/blob/master/docs/DeveloperGuide.md)

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation
### Architecture
![Figure of High Level Architecure Diagram](./umldiagrams/architecture.png)

Here is a quick Overview of Main Components and how they interact with each other:

#### Main Components of the architecture
`Main` is in charge of the app launch and shut down. 
* At app launch, it initializes all other components.
* At shutdown, it shuts down the other components and invoked the java garbage collector where necessary

The App is consisted of the following four components:
* UI: The UI of the app
* Storage: Holds the Data of the App in Memory
* Parser: Finds the appropriate command and returns it to UI
* Commons: Consists of various classes that interact with other components
* LookUpTable: Consists a Look Up Table of various lists that store data
* Command: The command executer

### Storage Component

The **StorageManager** class is responsible for all file operations in the application, including reading, writing, and exporting data.  
It ensures that user data, client data, archived clients, tasks, policies, and meeting records are automatically persisted to disk after every command execution  minimizing data loss risks even during unexpected shutdowns.

![Storage Component Autosave Sequence](./umldiagrams/Storage.png)

#### Design Overview
The `FinanceProPlus` class manages the autosave feature through its `saveAllData()` method, which is triggered after each executed command.  
This guarantees that all updates made during runtime  including new tasks, meetings, or clients — are immediately written to disk without requiring explicit user action to save.

Each dataset (User, Client, ArchivedClient, Policy, Meeting, and Task) is stored in both:
- **Text format** (`.txt`) – for reliable internal persistence and reloading.
- **CSV format** (`.csv`) – for user-friendly export and analysis.

All saved data resides within two primary directories:
- `data/` — used for internal save and load operations.
- `exports/` — used for external CSV exports (e.g., viewing in Excel).

#### Key Data Files
| Data Type | Text File | CSV File |
|------------|------------|----------|
| User | `user.txt` | `user.csv` |
| Client | `client.txt` | `client.csv` |
| Archived Clients | `archived_clients.txt` | `archived_clients.csv` |
| Policy | `policy.txt` | `policy.csv` |
| Meeting | `meeting.txt` | `meeting.csv` |
| Task | `task.txt` | `task.csv` |
#### Workflow Description (Autosave)
1. After the user enters a command, `Parser.parse()` creates a `Command` object.
2. The command executes and updates the respective data list(s).
3. `FinanceProPlus` automatically invokes `saveAllData()`.
4. Each list’s data is written to `.txt` and `.csv` files via `StorageManager`.
5. The `StorageManager` logs a success message (`logger.info("Data saved successfully.")`).

Errors during saving are logged internally and not printed to the user — keeping the UI clean but traceable through logs.

#### Rationale for Design
- **Autosave per command** guarantees maximum reliability — no manual save needed.
- **Centralized save logic** inside `FinanceProPlus` keeps design modular and testable.
- **Logging instead of printing** ensures users aren’t spammed with system messages.

---

### Storage Initialization on Startup

When the program launches, previously saved data is automatically loaded into memory.  
This allows users to seamlessly continue from where they left off without manually restoring files.

![Storage Loading Sequence](./umldiagrams/Storage_Loading.png)

#### Design Overview
On startup, the `FinanceProPlus` constructor initializes the `StorageManager` and sequentially loads data from disk into each component:
- **PolicyList**
- **ClientList**
- **UserList**
- **MeetingList**
- **ArchivedClientList**
- **TaskList**

If any file is missing or unreadable, the app logs the issue but continues loading other data, ensuring robust operation even with partial data.

#### Workflow Description (Startup Loading)
1. `FinanceProPlus` is instantiated.
2. `StorageManager` is initialized, creating necessary folders.
3. The following load sequence occurs:
    - `policies.loadFromStorage(storage.loadFromFile("policy.txt"));`
    - `clients.loadFromStorage(storage.loadFromFile("client.txt"), policies);`
    - `user.loadFromStorage(storage.loadFromFile("user.txt"));`
    - `meetings.loadFromStorage(storage.loadFromFile("meeting.txt"));`
    - `archivedClients.loadFromStorage(storage.loadFromFile("archived_clients.txt"));`
    - `tasks.loadFromStorage(storage.loadFromFile("task.txt"));`
4. Each list reconstructs its objects from text lines.
5. `Logger.info("Data loaded successfully.")` confirms successful initialization.

If no files exist (first launch), blank lists are created and files will be generated automatically upon the first autosave.

#### Rationale for Design
- Guarantees users always resume with consistent data.
- Prevents startup failure even if a single data file is missing.
- Keeps all load logic centralized within `FinanceProPlus` for easier debugging and maintenance.

---

### LookUpTable Component
API: [LookUpTable](./seedu/duke/container/LookUpTable.java)
![Figure of LookUpTable](./umldiagrams/lookuptable.png "Class Diagram of LookUpTable")

In the LookUptable Component,
* It stores the various lists(ClientList,PolicyList,MeetingList,UserList) that implements the ListContainer Interface.
* Once instantiated, the constructor creates the list objects and stores it as a Hashmap with the appropriate key-value pairs
* The LookUpTable is composed of the lists. If deleted, the list objects will be destroyed

### Client Features

When a command with the prefix "client" is invoked by the user, here is a sequence diagram depicting the overall flow of the program.

![Figure of Client Add Command SQ](./umldiagrams/clientsequence-Sequence_Diagram__Add_New_Client.png)

To explain  an example of the command. The user(Financial Advisor), first issues a command, this command is passed into the UI. Class, the UI calls the parse command to pass the data into an instantiated parser for processing.
In the parser, it then further breaks it down into respective child classes, in this case, it is the ClientParser Child class. This class splits the processed string into further substrings and then calls the constructor for AddCommand. AddCommand calls itself to instantiate some methods
and hands back the control signal to Client Parser. ClientParser hands back signal to Parser and Parser hands back signal to UI. UI then calls the execute method from Add Command. Add Command calls Client List to addItem.
In the Additem function it creates a new Client Object. In the Client Object Constructor it does the verification and instantiates the attributes. The client object then returns the control signal back to ClientList.
ClientList then does a callback method AddClient to add the client into the clientlist object  and then the additem callback is finished. ClientList then hands back control to Add Command and Add Command adds gives back the control to UI.
UI then calls the method printExecutionMessage() to AddCommand and AddCommand returns control back to UI with data. UI then displays the success message to the user.

### Task Management Feature

The Task Management feature allows financial advisors to create and manage standalone tasks with due dates. This feature enables users to track action items, deadlines, and follow-ups independently of clients or policies, providing a comprehensive task management system within FinanceProPlus.

#### Implementation

The task management feature is implemented through several key components that work together to parse user input, validate task details, and persist task data. The implementation follows the existing architecture pattern established for other entities (clients, policies, meetings) in the system.

##### Architecture-Level Design

At the architecture level, the task feature integrates seamlessly with the existing components:

1. UI Component: Receives the user's task command (e.g., task add d/Report by/15-01-2024)
2. Parser Component: Routes the command to TaskParser, which interprets the command subtype and extracts arguments
3. Command Component: Creates and executes an AddCommand with the task subtype
4. LookUpTable Component: Retrieves the appropriate TaskList to handle the operation
5. Model Component: Task objects encapsulate task data including description and due date

##### Task Creation Process

When a user creates a new task, the following sequence of operations occurs:

![Task Add Sequence Diagram](./umldiagrams/tasksequence.png)

### List Features

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
