# Andrew Cai - Project Portfolio Page

## Overview
FinanceProPlus is a Command Line Interface(CLI) application designed to aid financial agents to track their clients,
tasks, meetings and their Companies' policies. It can also show them their upcoming forecast of things to do for the week.

### Summary of Contributions

**Code Contribution**: Link [Here!](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=andrewcai8&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=andrewcai8&tabRepo=AY2526S1-CS2113-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

**Enhancements Added**:

- **Task Management Features**
  * Add
    * What it does: Allows users to create standalone tasks with descriptions and due dates.
    * Justification: Financial advisors need to track action items and deadlines independently of clients or policies, providing a comprehensive task management system.
    * Highlights: Implementation was challenging as I had to design the task command structure and ensure proper date validation. The task feature needed to integrate seamlessly with the existing parser and storage architecture.
  * List
    * What it does: Displays all tasks in a formatted, numbered list.
    * Justification: Users need a high-level overview of all tasks to manage their workload effectively.
    * Highlights: Required implementing consistent formatting with other list features in the application.
  * Delete
    * What it does: Removes tasks from the task list by index.
    * Justification: Users need the ability to remove completed or obsolete tasks.
    * Highlights: Ensured proper error handling for invalid indices and confirmation of successful deletion.

- **Client Todo Features**
  * Add Todo
    * What it does: Creates task items specifically linked to individual clients.
    * Justification: Financial advisors need to track client-specific action items, such as follow-ups or claim submissions.
    * Highlights: This required careful integration with the Client class to maintain the relationship between clients and their to-do items. Implementation was challenging as it needed to work alongside both the general task system and client management.
  * List Todos
    * What it does: Displays all to-do items for a specific client.
    * Justification: Provides a focused view of pending tasks for individual clients, improving workflow efficiency.
    * Highlights: Designed the output format to clearly show task descriptions and due dates in an organized manner.
  * Delete Todo
    * What it does: Removes specific to-do items from a client's task list.
    * Justification: Users need to maintain clean, current task lists for each client.

- **Policy Management Features**
  * Add Policy
    * What it does: Creates new policy records with policy names and descriptions.
    * Justification: Financial advisors need to maintain a centralized list of available policies to assign to clients.
    * Highlights: Implemented validation to prevent duplicate policy entries and ensure data integrity.
  * List Policies
    * What it does: Displays all available policies in the system.
    * Justification: Provides users with a quick reference of all policy options available for assignment to clients.
  * Delete Policy
    * What it does: Removes policies from the system by index.
    * Justification: Allows administrators to maintain an up-to-date policy catalog by removing discontinued policies.

- **Welcome Message**
  * What it does: Displays a formatted welcome message when the application starts.
  * Justification: Improves user experience by providing a clear indication that the application has started successfully and gives users context about the application.
  * Highlights: Designed the message to be informative and aligned with the application's professional tone.

- **Bug Fixes and Issue Resolution**
  * Client Todo Issues
    * Fixed edge cases in client todo deletion where invalid indices would cause unexpected behavior.
    * Resolved issues with todo persistence in storage, ensuring todos are correctly saved and loaded across sessions.
    * Fixed formatting inconsistencies in todo list display to improve readability.
  * Task Management Issues
    * Addressed bugs in task date validation to handle invalid date formats gracefully.
    * Fixed task deletion index out of bounds errors and improved error messaging.
    * Resolved conflicts between task storage and client storage mechanisms.
  * Policy Management Issues
    * Fixed duplicate policy detection logic to prevent adding policies with identical names.
    * Resolved policy deletion issues that could leave orphaned references in client records.
    * Addressed index synchronization problems in policy list management.

- **Unit Tests**
  * Wrote comprehensive unit tests for all the above features to ensure code reliability and maintainability.
  * Added test cases specifically targeting edge cases and bug scenarios discovered during development.

**Contributions to UG**

- Documented the Task Management features section, including add, list, and delete commands with examples.
- Documented the Policy Management features section, including add, list, and delete commands with examples.
- Added detailed command reference entries for task and policy commands in the Quick Reference table.
- Created comprehensive examples demonstrating typical workflows for financial advisors.
- Updated error message documentation to help users understand and resolve common issues.
- Added notes and warnings for edge cases in task and policy management.
- Improved formatting and consistency across all feature documentation sections.

**Contributions to DG**

- Primary author of the Task Management Feature section, explaining the design considerations and implementation details.
- Created the Task Add sequence diagram (`tasksequence.puml`) showing the flow from user input through parser to storage.
- Documented the interaction between TaskCommand, TaskList, and Storage components.
- Authored the Policy Management architecture documentation, including class relationships and data flow.
- Added implementation details for client todo integration with the existing Client class structure.
- Documented design decisions for separating standalone tasks from client-specific todos.
- Created detailed explanations of storage mechanisms for tasks, todos, and policies.
- Updated architecture diagrams to reflect the addition of task and policy management components.
- Documented error handling strategies and validation logic for all implemented features.
- Added user stories and use cases specific to task and policy management features.

**Team Based Tasks**

- Managed Releases `v1.0`-`v2.0` on GitHub, including preparing release notes and ensuring proper tagging.
- Contributed to overall product design discussions and feature planning.

**Review/Mentoring Contributions**

- Reviewed team members' pull requests and provided constructive feedback to maintain code quality.
- Assisted teammates with debugging and implementation challenges during development.

**Community**

- Participated in peer code reviews during the tP tutorials.
- Contributed to team discussions on design patterns and best practices.

