# User Guide

## Introduction
## FinanceProPlus: Your Client Command Center
Stop Clicking. Start Commanding.

Are you tired of slow, mouse-heavy CRMs(Client Relation Management) that can't keep up with your train of thought? 
Do you find yourself losing precious minutes to context-switching, navigating endless menus, and waiting for pages to load just to find a single piece of client information?

**FinanceProPlus** was built for you. We believe that for a true power user, the keyboard is the ultimate tool for speed and precision. This is a keyboard-first client management system designed to be as fast as your fingers can fly. No GUIs, no pop-ups, no distractions—just a direct line from your command to your data.

It's a CRM that works at your pace, not the other way around.
## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `FinanceProPlus.jar` from [here](https://github.com/AY2526S1-CS2113-W12-2/tp/releases).
3. Copy the file to the folder you want to use as the home folder of FinanceProPlus
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar [CS2113-W12-2][FinanceProPlus].jar` command to run the application
5. Refer to the [Features](#features) or [Command Summary](#command-summary)
## Features

### Client Features

This guide provides a complete reference for all commands related to the `client` entity. The commands are designed for rapid data entry and retrieval, allowing you to manage your client portfolio entirely from the keyboard.

### Command Syntax Legend

Before we begin, here is the syntax used in this guide:
*   `<VALUE>`: A placeholder for a value you must provide (e.g., `<NRIC>`).
*   `[...]`: Denotes an optional argument or group of arguments.

#### **1. Adding a New Client**

This command creates a new client record. The policy is optional and, if included, will be created as a "placeholder" that you can detail later using the `updatepolicy` command.

*   **Command:** `client add n/<NAME> c/<CONTACT> id/<NRIC> [p/<POLICY_NAME>]`

*   **Arguments:**
    *   `n/<NAME>`: **Required.** The full name of the client.
    *   `c/<CONTACT>`: **Required.** The client's phone number. Must be 8 digits long.
    *   `id/<NRIC>`: **Required.** The client's unique NRIC number (acts as a primary key).
    *   `p/<POLICY_NAME>`: *Optional.* The name of a base policy to assign to the client as a placeholder. The policy must already exist in the main policy list. Only 1 policy can be added in this command.

*   **Examples:**
    *   To add a client without an initial policy:
        ```
        client add n/John Doe c/87654321 id/S1234567A
        ```
    *   To add a client and immediately assign a placeholder policy named "HealthShield":
        ```
        client add n/Jane Smith c/11223344 id/G7654321B p/HealthShield
        ```

#### **2. Viewing a Client's Full Details**

This command displays a comprehensive, formatted view of a single client, including their personal details, all associated policy contracts, and all pending to-do items.

*   **Command:** `client view id/<NRIC>`

*   **Arguments:**
    *   `id/<NRIC>`: **Required.** The NRIC of the client you wish to view.

*   **Example:**
    ```
    client view id/S1234567A
    ```

#### **3. Listing All Clients**

This command provides a summary view of all clients currently in your portfolio, listed with a numerical index. This index is essential for deleting clients.

*   **Command:** `list client`

*   **Example:**
    ```
    list client
    ```

#### **4. Deleting a Client**

This command permanently removes a client and all their associated data (policies, to-dos) from the system.

*   **Command:** `client delete <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the client to delete. **You must run `list client` first to find the correct index.**

*   **Workflow Example:**
    1.  First, list all clients to find the target's index:
        ```
        list client
        ```
        *(Output might show "1. Name: John Doe...")*

    2.  Then, use that index to delete the client:
        ```
        client delete 1
        ```

#### **5. Searching for a Client**

This command searches for and displays a specific client's basic information using their NRIC. This is useful for quickly finding a client without viewing their full details.

*   **Command:** `client search <NRIC>`

*   **Arguments:**
    *   `<NRIC>`: **Required.** The NRIC of the client you want to search for.

*   **Examples:**
    *   To search for a client with NRIC "S1234567A":
        ```
        client search S1234567A
        ```
    *   If the client is found, you'll see their basic information:
        ```
        Client found:
        Name: John Doe, Contact: 87654321, NRIC: S1234567A
        ```
    *   If no client is found with that NRIC:
        ```
        No client found with NRIC: S9999999Z
        ```

#### **6. Archiving a Client**

This command moves a client and all their associated data (policies, to-dos) from the active clients list to the archived clients list. This is useful for clients who are no longer active but whose records you want to preserve.

*   **Command:** `client archive <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the client to archive. **You must run `list client` first to find the correct index.**

*   **Workflow Example:**
    1.  First, list all active clients to find the target's index:
        ```
        list client
        ```
        *(Output might show "1. Name: John Doe...")*

    2.  Then, use that index to archive the client:
        ```
        client archive 1
        ```

#### **7. Restoring a Client**

This command moves a previously archived client back to the active clients list.

*   **Command:** `client restore <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the archived client to restore. **You must run `list archived` first to find the correct index.**

*   **Data Restoration Notes:**
    *   **Same session:** If you archive and restore a client within the same application session, all associated data (policies, to-dos) will be fully restored.
    *   **New session:** If you restart the application after archiving, only basic client information (name, NRIC, contact) will be restored. Associated policies and to-dos will not be recovered.

*   **Workflow Example:**
    1.  First, list all archived clients to find the target's index:
        ```
        list archived
        ```
        *(Output might show "1. Name: John Doe...")*

    2.  Then, use that index to restore the client:
        ```
        client restore 1
        ```

#### **8. Listing Archived Clients**

This command displays all archived clients with their basic information, listed with numerical indices for restoration.

*   **Command:** `list archived`

*   **Example:**
    ```
    list archived
    ```

---

### Managing Client Policies

These commands allow you to manage the specific insurance contracts associated with a client.

#### **1. Adding a Detailed Policy to a Client**

This command adds a new, fully detailed insurance contract to an existing client.

*   **Command:** `client addpolicy id/<NRIC> p/<POLICY_NAME> s/<START_DATE> e/<EXPIRY_DATE> m/<PREMIUM>`

*   **Arguments:**
    *   `id/<NRIC>`: **Required.** The NRIC of the client.
    *   `p/<POLICY_NAME>`: **Required.** The name of the base policy (must exist).
    *   `s/<START_DATE>`: **Required.** The contract start date in `dd-MM-yyyy` format.
    *   `e/<EXPIRY_DATE>`: **Required.** The contract expiry date in `dd-MM-yyyy` format.
    *   `m/<PREMIUM>`: **Required.** The monthly premium amount (e.g., `150.75`).

*   **Example:**
    ```
    client addpolicy id/S1234567A p/HealthShield s/14-09-2024 e/14-09-2026 m/250.50
    ```

#### **2. Updating a Client's Policy**

This command modifies the details of an existing policy contract for a client. You only need to provide the fields you wish to change.

*   **Command:** `client updatepolicy id/<NRIC> p/<POLICY_NAME> [s/<NEW_DATE>] [e/<NEW_DATE>] [m/<NEW_PREMIUM>]`

*   **Arguments:**
    *   `id/<NRIC>` & `p/<POLICY_NAME>`: **Required** identifiers.
    *   `s/`, `e/`, `m/`: *Optional.* Provide at least one of these to update. Dates must be in `dd-MM-yyyy` format.

*   **Example:**
    *   To update only the premium and expiry date for Jane's "PremiumLife" policy:
        ```
        client updatepolicy id/G7654321B p/PremiumLife e/21-10-2060 m/375.00
        ```

#### **3. Deleting a Policy from a Client**

This command removes a specific policy contract from a client without deleting the client themselves.

*   **Command:** `client deletepolicy id/<NRIC> i/<INDEX>`

*   **Arguments:**
    *   `id/<NRIC>`: **Required.** The NRIC of the client.
    *   `i/<INDEX>`: **Required.** The numerical index of the policy to delete. **You must run `client view id/<NRIC>` first to see the numbered list of policies and find the correct index.**

*   **Workflow Example:**
    1.  First, view the client to see their policies:
        ```
        client view id/G7654321B
        ```        *(Output might show "1. Policy Name: HealthShield", "2. Policy Name: PremiumLife")*

    2.  Then, use the policy's index to delete it:
        ```
        client deletepolicy id/G7654321B i/1
        ```

---

### Managing Client Tasks (To-Dos)

These commands help you track actionable follow-ups for each client.

#### **1. Adding a To-Do for a Client**

This command creates a new task and links it directly to a client.

*   **Command:** `client addtodo id/<NRIC> d/<DESCRIPTION> by/<DUE_DATE>`

*   **Arguments:**
    *   `id/<NRIC>`: **Required.** The NRIC of the client.
    *   `d/<DESCRIPTION>`: **Required.** The description of the task.
    *   `by/<DUE_DATE>`: **Required.** The due date in `dd-MM-yyyy` format.

*   **Example:**
    ```
    client addtodo id/S1234567A d/Follow up on claim submission by/30-11-2025
    ```

#### **2. Listing a Client's To-Dos**

This command provides a focused view of all pending tasks for a single client.

*   **Command:** `client listtodos id/<NRIC>`

*   **Arguments:**
    *   `id/<NRIC>`: **Required.** The NRIC of the client whose tasks you want to see.

*   **Example:**
    ```
    client listtodos id/S1234567A
    ```
### Meeting Features

This guide provides a complete reference for all commands related to the `meeting` entity. These commands help you schedule, track, and manage client meetings efficiently from the keyboard.

#### **1. Adding a New Meeting**

This command creates a new meeting record with the specified details. All meetings require a title, client name, date, and start time. End time is optional.

*   **Command:** `meeting add t/<TITLE> c/<CLIENT> d/<DATE> from/<START_TIME> [to/<END_TIME>]`

*   **Arguments:**
    *   `t/<TITLE>`: **Required.** The title or subject of the meeting.
    *   `c/<CLIENT>`: **Required.** The name of the client attending the meeting.
    *   `d/<DATE>`: **Required.** The meeting date in `dd-MM-yyyy` format.
    *   `from/<START_TIME>`: **Required.** The meeting start time in `HH:mm` format (24-hour).
    *   `to/<END_TIME>`: *Optional.* The meeting end time in `HH:mm` format (24-hour). **Note: If provided, the end time must be after the start time.**

*   **Examples:**
    *   To add a meeting with start and end time:
        ```
        meeting add t/Policy Review c/John Doe d/30-10-2025 from/14:00 to/16:00
        ```
    *   To add a meeting with only start time:
        ```
        meeting add t/Initial Consultation c/Jane Smith d/05-11-2025 from/10:30
        ```

#### **2. Listing All Meetings**

This command displays all scheduled meetings with their details, listed with numerical indices for easy reference.

*   **Command:** `list meeting`

*   **Example:**

    ```
    Here are the meetings in your list:
    1. Title: Policy Review, Client: John Doe, Date: 30-10-2025, Time: 14:00 to 16:00
    2. Title: Initial Consultation, Client: Jane Smith, Date: 05-11-2025, Start Time: 10:30
    ```
    
#### **3. Deleting a Meeting**

This command permanently removes a meeting from your schedule.

*   **Command:** `meeting delete <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the meeting to delete. **You must run `meeting list` first to find the correct index.**

*   **Workflow Example:**
    1.  First, list all meetings to find the target's index:
        ```
        list meeting
        ```
        *(Output might show "1. Title: Policy Review, Client: John Doe...")*

    2.  Then, use that index to delete the meeting:
        ```
        meeting delete 1
        ```

#### **4. Viewing Upcoming Meetings (7-Day Forecast)**

This command shows all meetings scheduled within the next 7 days, helping you prepare for upcoming appointments.

*   **Command:** `meeting forecast`

*   **Example:**
    ```
    Meetings in the next 7 days:
    1. Title: Policy Review, Client: John Doe, Date: 30-10-2025, Time: 14:00 to 16:00
    2. Title: Initial Consultation, Client: Jane Smith, Date: 05-11-2025, Start Time: 10:30
    ```

---


### Policy Features

This guide provides commands for managing **base policies** (insurance product templates) in your system. These base policies serve as templates that can be assigned to clients when adding detailed policy contracts.

#### **1. Adding a New Base Policy**

This command creates a new policy template that can later be assigned to clients. Base policies define the type and general details of insurance products available.

*   **Command:** `policy add n/<NAME> d/<DETAILS>`

*   **Arguments:**
    *   `n/<NAME>`: **Required.** The name of the policy (e.g., "HealthShield", "PremiumLife").
    *   `d/<DETAILS>`: **Required.** A brief description of the policy coverage and features.

*   **Example:**
    ```
    policy add n/HealthShield d/Comprehensive health insurance covering hospitalization and outpatient care
    ```

#### **2. Listing All Base Policies**

This command displays all policy templates currently available in your system, listed with numerical indices. This index is essential for deleting policies.

*   **Command:** `list policy`

*   **Example:**
    ```
    list policy
    ```

#### **3. Deleting a Base Policy**

This command permanently removes a policy template from the system. Note that this does not affect policies already assigned to clients.

*   **Command:** `policy delete <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the policy to delete. 

*   **Workflow Example:**
    1.  First, list all policies to find the target's index:
        ```
        list policy
        ```
        *(Output might show "1. Name: HealthShield, Details: ...")*

    2.  Then, use that index to delete the policy:
        ```
        policy delete 1
        ```

---

### Task Features

This guide provides commands for managing **standalone tasks** in your system. These tasks are independent action items with due dates that help you track general follow-ups, deadlines, and to-do items that may not be tied to a specific client.

#### **1. Adding a New Task**

This command creates a new standalone task with a description and due date.

*   **Command:** `task add d/<DESCRIPTION> by/<DUE_DATE>`

*   **Arguments:**
    *   `d/<DESCRIPTION>`: **Required.** The description of the task or action item.
    *   `by/<DUE_DATE>`: **Required.** The due date in `dd-MM-yyyy` format.

*   **Example:**
    ```
    task add d/Review quarterly insurance report by/15-12-2025
    ```

#### **2. Listing All Tasks**

This command displays all tasks in your system, listed with numerical indices. This index is essential for deleting tasks.

*   **Command:** `list task`

*   **Example:**
    ```
    list task
    ```

#### **3. Deleting a Task**

This command permanently removes a task from the system.

*   **Command:** `task delete <INDEX>`

*   **Arguments:**
    *   `<INDEX>`: **Required.** The numerical index of the task to delete. 

*   **Workflow Example:**
    1.  First, list all tasks to find the target's index:
        ```
        list task
        ```
        *(Output might show "1. Review quarterly insurance report (by: 15-12-2025)")*

    2.  Then, use that index to delete the task:
        ```
        task delete 1
        ```

---

### User Features

The User feature allows financial advisors to store their own profile information (e.g., name, email, contact number, and representative number).
This information is optional and the program can be used without adding a user profile.

---

#### **1. Adding the Active User**

This command registers the Financial Advisor using the system.  
Only one user can exist at any time attempting to add another will result in an error.

* **Command:** `user add n/<NAME> e/<EMAIL> c/<CONTACT> r/<REP_NUMBER>`

* **Arguments:**
    * `n/<NAME>` — **Required.** Advisor’s full name.
    * `e/<EMAIL>` — **Required.** Contact email.
    * `c/<CONTACT>` — **Required.** Contact number (digits only).
    * `r/<REP_NUMBER>` — **Required.** Representative or license number.

* **Example:**
```
user add n/Alex Tan e/alex@example.com c/91234567 r/FA-001
```
#### **2. Viewing the Current User**

Displays the currently registered advisor’s details.  
If no user exists, a message will indicate that the user profile is empty.

* **Example:** `user view`
```
  Here is the current user information:
  User Details:
  Name: Alex Tan
  Email: alex@example.com
  Contact: 91234567
  Representative No.: FA-001
```

#### **3. Editing the Current User**

Updates the existing user profile with new details. 

* Command: `user edit`


#### **Exiting the Application**

Exits the Application
* Command `exit`

---
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: You Can Transfer Files From ./data/ directory to your new computer.

**Q**: Can I directly modify my data in the ./data/ directory?

**A**: Do not modify the data directly. If files get corrupted or the problem fails unexpectedly due to modifications of the data file it is of your own risk.

## Command Summary


#### Client Management

| Command          | Description | Syntax / Arguments                                            | Example                                |
|:-----------------| :--- |:--------------------------------------------------------------|:---------------------------------------|
| `client add`     | Creates a new client record. | `client add n/<NAME> c/<CONTACT> id/<NRIC> [p/<POLICY_NAME>]` | `client add n/John Doe c/123 id/S123A` |
| `client view`    | Displays full details for one client (policies, to-dos). | `client view id/<NRIC>`                                       | `client view id/S123A`                 |
| `list client`    | Shows a summary of all clients with their index numbers. | `list client`                                                 | `list client`                          |
| `client delete`  | Removes a client by index (use `list client` first). | `client delete <INDEX>`                                       | `client delete 1`                      |
| `client search`  | Searches for a client by NRIC and displays basic info. | `client search <NRIC>`                                        | `client search S123A`                  |
| `client archive` | Moves a client to archived list by index. | `client archive <INDEX>`                                      | `client archive 1`                     |
| `client restore` | Restores an archived client by index. | `client restore <INDEX>`                                      | `client restore 1`                     |
| `list archived`  | Shows all archived clients with indices. | `list archived`                                               | `list archived`                        |

#### Client Policy Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `client addpolicy` | Adds a new, detailed policy contract to a client. | `client addpolicy id/<NRIC> p/<POLICY> s/<dd-MM-yyyy> e/<dd-MM-yyyy> m/<PREMIUM>` | `client addpolicy id/S123A p/Health s/14-09-2024 e/14-09-2026 m/250.50` |
| `client updatepolicy` | Modifies an existing policy contract for a client. | `client updatepolicy id/<NRIC> p/<POLICY> [s/...] [e/...] [m/...]` | `client updatepolicy id/S123A p/Health m/275.00` |
| `client deletepolicy` | Removes a policy from a client by index (use `client view` first). | `client deletepolicy id/<NRIC> i/<INDEX>` | `client deletepolicy id/S123A i/1` |

#### Client Task (To-Do) Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `client addtodo` | Adds a new to-do task for a specific client. | `client addtodo id/<NRIC> d/<DESC> by/<dd-MM-yyyy>` | `client addtodo id/S123A d/Follow up claim by/30-11-2025` |
| `client listtodos` | Lists all to-do tasks for a single client. | `client listtodos id/<NRIC>` | `client listtodos id/S123A` |

#### Meeting Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `meeting add` | Creates a new meeting record. | `meeting add t/<TITLE> c/<CLIENT> d/<dd-MM-yyyy> from/<HH:mm> [to/<HH:mm>]` | `meeting add t/Policy Review c/John Doe d/30-10-2025 from/14:00 to/16:00` |
| `list meeting` | Shows all scheduled meetings with indices. | `list meeting` | `list meeting` |
| `meeting delete` | Removes a meeting by index (use `meeting list` first). | `meeting delete <INDEX>` | `meeting delete 1` |
| `meeting forecast` | Shows meetings in the next 7 days. | `meeting forecast` | `meeting forecast` |

#### User Management

| Command     | Description                                                 | Syntax / Arguments                                       | Example                                                          |
|:------------|:------------------------------------------------------------|:---------------------------------------------------------|:-----------------------------------------------------------------|
| `user add`  | Registers the current financial advisor (only one allowed). | `user add n/<NAME> e/<EMAIL> c/<CONTACT> r/<REP_NUMBER>` | `user add n/Alex Tan e/alex@example.com c/91234567 r/FA-001`     |
| `user view` | Displays details of the current user.                       | `user view`                                              | `user view`                                                      |
| `user edit` | Edits the existing user record.                             | `user edit`                                              | `user edit n/Alex Tan e/alex123@example.com c/91234576 r/FA-001` |

#### Policy Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `policy add` | Creates a new base policy template. | `policy add n/<NAME> d/<DETAILS>` | `policy add n/HealthShield d/Comprehensive health insurance` |
| `list policy` | Shows all base policy templates with their index numbers. | `list policy` | `list policy` |
| `policy delete` | Removes a base policy by index (use `list policy` first). | `policy delete <INDEX>` | `policy delete 1` |

#### Task Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `task add` | Creates a new standalone task with a due date. | `task add d/<DESCRIPTION> by/<dd-MM-yyyy>` | `task add d/Review quarterly report by/15-12-2025` |
| `list task` | Shows all tasks with their index numbers. | `list task` | `list task` |
| `task delete` | Removes a task by index (use `list task` first). | `task delete <INDEX>` | `task delete 1` |


#### Miscellaneous

| Command | Description | Syntax / Arguments | Example |
|:--------| :--- |:-------------------| :--- |
| `exit`  |Exits the application| `exit`             |`exit`|