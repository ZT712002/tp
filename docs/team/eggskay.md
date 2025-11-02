# Ong Xiang Kai - Project Portfolio Page

## Overview

FinanceProPlus is a Command Line Interface(CLI) application designed to aid financial agents to track their clients,
tasks, meetings and their Companies' policies. It can also show them their upcoming forecast of things to do for the
week.

### Summary of Contributions

**Code Contribution**:
Link [Here!](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=eggskay&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### Enhancements Added

- User Command Methods
    * Add
        * What it does: Allows the user (financial advisor) to add their personal profile into the system.
        * Justification: The application is designed for single-user use. This ensures that the system is personalized
          for one financial advisor at a time, storing their name, email, contact number, and representative number.
        * Highlights: Implementation required validation to prevent multiple user profiles. The logic ensures that only
          one active user can exist and that the user must delete their current profile before creating a new one.

    * Edit
        * What it does: Allows the user to update their existing profile from the system.
        * Justification: Ensures the user can replace their information if needed.

    * View
        * What it does: Displays the stored user profile and details such as name, email, contact number, and
          representative number.
        * Justification: Provides a quick way for the financial advisor to verify their stored information in the
          system.
        * Highlights: Although not part of the shared `ListCommand` logic used for other entities, it utilizes the
          existing `listItems()` method in `UserList`. This ensures consistent structure and formatting with minimal
          duplication of logic.

- Storage System (Autosave)
    * What it does: Automatically saves all major data lists (User, Client, Policy, Meeting, Archived Clients, and Task)
      after every command is executed.
    * Justification: Prevents data loss and ensures that every update made during runtime is immediately persisted to
      disk.
    * Highlights:
        * Added autosave logic inside `FinanceProPlus.saveAllData()` which is triggered at the end of each command
          cycle.
        * Utilized `StorageManager` to handle all read/write operations to text-based files.
        * Integrated per-client task storage — every client’s personal to-do list is saved in the `/data/client_tasks/`
          directory under their NRIC filename (e.g., `S1234567A.txt`).
        * Added **storage helper functions** to each list class (`toStorageFormat()`, `toCSVFormat()`,
          `loadFromStorage()`) to provide standardized serialization and deserialization of data. This ensures that
          every class can independently save and restore its state.
        * Implemented consistent `Logger.info()` messages for each successful save to simplify debugging and verify
          autosave performance.

- Storage System (Startup Loading)
    * What it does: Loads previously saved data from disk when the program starts so users can resume work without
      losing progress.
    * Justification: Ensures a seamless startup experience where all user, client, meeting, and policy data are restored
      automatically.
    * Highlights:
        * Added a `loadFromFiles()` function in `FinanceProPlus` that calls each list’s `loadFromStorage()` method.
        * Implemented automatic loading of client-specific to-do lists by reading from each file in the
          `/data/client_tasks/` directory.
        * Added robust error handling to continue loading other data even if one file is missing or corrupted.
        * Startup events are logged with `Logger.info()` for transparency and easier troubleshooting.

- CSV Exporting
    * What it does: Exports all main data lists (User, Client, Policy, Meeting) into structured `.csv` files for use in
      spreadsheet tools like Excel.
    * Justification: Allows external viewing, editing, and analysis of data outside the CLI environment.
    * Highlights: Implemented standardized CSV formatting in `StorageManager` with data sanitization for commas and
      quotes to ensure clean export compatibility.

- User Parser Implementation
    * What it does: Handles all user-specific commands such as `add`, `edit`, and `view` for the User entity.
    * Justification: Separates the logic of parsing user commands from other entities, ensuring modularity and better
      scalability.
    * Highlights: Implemented within `UserParser` to integrate smoothly with the main parser system while maintaining
      entity-level independence. Required careful design to ensure consistency with the command execution flow and error
      handling.
* Unit Tests and Assertions
    * Added automated testing and defensive programming practices across all implemented components.  


**Contributions to UG**

- Documented the **User Management** section, detailing `user add`, `user edit`, and `user view` commands with syntax and examples.

**Contributions to DG**

- Documented the **Storage Component**, including autosave, startup loading, and per-client to-do list storage design.
- Created the **Storage Sequence Diagrams** (“Autosave Triggered by FinanceProPlus” and “Storage Loading Sequence”).
- Added implementation details for `loadFromFiles()` and storage helper methods across all list classes.

**Team-Based Tasks**
- Reviewed and merged team pull requests to maintain code quality and ensure consistency across components.

**Community**

-TP PR review:[#26](https://github.com/nus-cs2113-AY2526S1/tp/pull/26/files/ac4d09eadf8845a6909f0a0efb9025823e1144a1)
