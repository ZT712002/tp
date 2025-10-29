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
2. Down the latest version of `FinanceProPlus.jar` from [here](https://github.com/AY2526S1-CS2113-W12-2/tp/releases).
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
    *   `c/<CONTACT>`: **Required.** The client's phone number.
    *   `id/<NRIC>`: **Required.** The client's unique NRIC number (acts as a primary key).
    *   `p/<POLICY_NAME>`: *Optional.* The name of a base policy to assign to the client as a placeholder. The policy must already exist in the main policy list.

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


### Policy Features

### Task Features

### User Features



## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: You Can Transfer Files From ./data/ directory to your new computer.

## Command Summary

Of course. Here is a concise cheatsheet for all `client` commands in Markdown table format, perfect for a quick reference.

---

### **FinanceProPlus Client Command Cheatsheet**

#### Client Management

| Command | Description | Syntax / Arguments | Example |
| :--- | :--- | :--- | :--- |
| `client add` | Creates a new client record. | `client add n/<NAME> c/<CONTACT> id/<NRIC> [p/<POLICY_NAME>]` | `client add n/John Doe c/123 id/S123A` |
| `client view` | Displays full details for one client (policies, to-dos). | `client view id/<NRIC>` | `client view id/S123A` |
| `list client` | Shows a summary of all clients with their index numbers. | `list client` | `list client` |
| `client delete`| Removes a client by index (use `list client` first). | `client delete <INDEX>` | `client delete 1` |

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
