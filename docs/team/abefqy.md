# Abe Foo - Project Portfolio Page

## Overview
FinanceProPlus is a Command Line Interface(CLI) application designed to aid financial agents to track their clients,
tasks, meetings and their Companies' policies. It can also show them their upcoming forecast of things to do for the week.

### Summary of Contributions

**Code Contribution**: Link [Here!](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=abefqy&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=abefqy&tabRepo=AY2526S1-CS2113-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

**Enhancements Added**:

- **Client Management Features**
    * Search
        * What it does: Allows users to search for clients by NRIC using keyword matching.
        * Justification: Financial advisors often need to quickly locate specific clients from a large database without scrolling through the entire list.
        * Highlights: Built upon the existing NRIC lookup functionality to extend search capabilities for NRIC matching.

      * Archive
        * What it does: Moves inactive clients to an archived list, removing them from the main client view.
        * Justification: Users need to maintain a clean active client list while preserving historical data for compliance and reference purposes in the future.
        * Highlights: Required careful design to ensure archived clients maintain their policy and meeting associations without cluttering the main workspace.
    * Restore
        * What it does: Restores archived clients back to the active client list.
        * Justification: Clients may become active again, requiring quick restoration without data loss or re-entry.
        * Highlights: Ensured data integrity during the restore process and proper validation to prevent duplicate entries.
    * List Archived
        * What it does: Displays all archived clients in a formatted list.
        * Justification: Users need visibility into archived clients for auditing and potential restoration.
        * Highlights: Implemented consistent formatting with the main client list for a seamless user experience.


- **Meeting Management Features**
    * Add
        * What it does: Creates new meeting entries with title, client, date, and time information.
        * Justification: Financial advisors need to schedule and track client meetings to manage their appointments effectively.
        * Highlights: Implementation was challenging as I had to design robust date and time validation to prevent invalid entries like February 30th or 25:00 hours. Used LocalDate and LocalTime parsing with format verification to ensure data integrity.
    * List
        * What it does: Displays all scheduled meetings in a formatted, numbered list.
        * Justification: Users need a comprehensive view of all meetings to manage their schedule.
        * Highlights: Required implementing consistent formatting with other list features in the application.
    * Delete
        * What it does: Removes meetings from the meeting list by index.
        * Justification: Users need the ability to cancel or remove completed meetings.
        * Highlights: Ensured proper error handling for invalid indices and confirmation of successful deletion.
    * Forecast
        * What it does: Displays all meetings scheduled within the next 7 days from the current date.
        * Justification: Financial advisors need to see their upcoming weekly schedule to plan their time effectively and prepare for client meetings.
        * Highlights: Implementation required working with LocalDate to handle date comparisons across month and year boundaries automatically. The feature filters meetings dynamically based on the current system date, ensuring accurate weekly forecasts.


- **Logger Implementation**
    * What it does: Integrated logging functionality into MeetingList class to track meeting operations.
    * Justification: Provides developers with debugging capabilities and audit trails for meeting-related operations.
    * Highlights: Implemented consistent logging patterns for add, delete, and forecast operations to maintain code standards across the application.


- **Unit Tests**
    * Wrote comprehensive unit tests for meeting parser functionality to ensure code reliability and maintainability.
    * Added test coverage for forecast command validation and meeting command parsing.
    * Added test coverage for client archive, restore and list archive.

    
**Contributions to UG**
- Documented the Client Search, Archive, and Restore features with detailed examples and use cases.
- Documented the Meeting Management features section, including add, list, delete, and forecast commands with examples.
- Added detailed command reference entries for client search and archive operations and meeting commands in the Quick Reference table.


**Contributions to DG**
- Primary author of the Meeting Features section, explaining the implementation details for meeting forecast feature.
- Created sequence diagrams showing the flow of meeting forecast functionality.


**Team Based Tasks**
- Contributed to overall product design discussions and feature planning.
- Assisted in maintaining code quality standards across the team.
- Participated in release preparation and testing.


**Review/Mentoring Contributions**
- Reviewed team members' pull requests and provided constructive feedback to maintain code quality.
- Assisted teammates with debugging date/time validation issues during development.

**Community**
TP PR Review: [#36](https://github.com/nus-cs2113-AY2526S1/tp/pull/36/files/46eaea8971ebc26e3240ff8103eeb416d64d5e3a)
