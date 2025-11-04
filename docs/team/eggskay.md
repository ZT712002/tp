# Ong Xiang Kai - Project Portfolio Page

## Overview

FinanceProPlus is a Command Line Interface(CLI) application designed to aid financial agents to track their clients,
tasks, meetings and their Companies' policies. It can also show them their upcoming forecast of things to do for the
week.

### Summary of Contributions

**Code Contribution**:
[Code Dashboard Link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=eggskay&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### Enhancements Added
- User Command Methods
    - **Add:** Enables financial advisors to create their personal profile in the system.
    - **Edit:** Updates existing user information dynamically.
    - **View:** Displays current stored user details in a consistent format using the shared `listItems()` interface.  
      *Highlights:* Added email and 8-digit contact validation.

- Storage System (Autosave)
    * What it does: Automatically saves all major data lists after every command is executed.
    * Justification: Prevents data loss and ensures that every update made during runtime is immediately persisted to
      disk.
    * Highlights:
        * Utilized `StorageManager` to handle all read/write operations to text-based files.
        * Integrated per-client task storage — every client’s personal to-do list and  policy list is saved in the `/data/client_tasks/` and `/data/client_policies/`
          directory under their NRIC filename (e.g., `S1234567A.txt`).
        * Added **storage helper functions** to each list class (`toStorageFormat()`, `toCSVFormat()`,
          `loadFromStorage()`).
        * Implemented consistent `Logger.info()` messages for each successful save to simplify debugging and verify
          autosave performance.

- Storage System (Startup Loading)
    * What it does: Loads previously saved data from disk when the program starts so users can resume work without
      losing progress.
    * Justification: Ensures a seamless startup experience where all user, client, meeting, and policy data are restored
      automatically.
    * Highlights:
        * Added a `loadFromFiles()` function in `FinanceProPlus` that calls each list’s `loadFromStorage()` method.
        * Implemented automatic loading of client-specific to-do lists and policy listsby reading from each file in the
          `/data/client_tasks/` directory and `/data/client_policies/`.
        * Added robust error handling to continue loading other data even if one file is missing or corrupted.

- CSV Exporting
    * What it does: Exports all main data lists (User, Client, Policy, Meeting) into structured `.csv` files for use in
      spreadsheet tools like Excel.
    * Justification: Allows external viewing, editing, and analysis of data outside the CLI environment.
    * Highlights: Implemented standardized CSV formatting in `StorageManager` with data sanitization for commas and
      quotes to ensure clean export compatibility.

- User Parser Implementation
    * What it does: Handles all user-specific commands such as `add`, `edit`, and `view` for the User entity.

* Unit Tests and Assertions
    * Added automated testing and defensive programming practices across all implemented components.  

**Contributions to UG**

- Documented the **User Management** section, detailing `user add`, `user edit`, and `user view` commands with syntax and examples.

**Contributions to DG**

- Documented the **Storage Component**, including autosave, startup loading, and per-client to-do list storage design.
- Created the **Storage Sequence Diagrams** (“Autosave Triggered by FinanceProPlus” and “Storage Loading Sequence”).

**Team-Based Tasks**
- Set up GitHub team organisation and repository.
- Maintained issue tracker 
- Reviewed and merged team pull requests to maintain code quality and ensure consistency across components.

**Community**

-TP PR review:[#26](https://github.com/nus-cs2113-AY2526S1/tp/pull/26/files/ac4d09eadf8845a6909f0a0efb9025823e1144a1)

-PE-D review:[Link](https://github.com/nus-cs2113-AY2526S1/ped-EggsKay/issues)
