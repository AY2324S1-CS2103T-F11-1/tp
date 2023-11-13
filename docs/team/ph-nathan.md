---
layout: page
title: Nathan's Project Portfolio Page
---

### Project: CCACommander Ultra Promax Xtra 9000PLUS

CCACommander Ultra Promax Xtra 9000PLUS is the one-stop app for CCA Heads to manage CCA members and events, optimised for CCA Heads who prefer to use command line interface.

Given below is a summary of my contributions to the project. All my contributions can be found at this [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=ph-nathan&breakdown=true#/).

### Features Implemented
* **Designed a new UI for CCACommander** [#114](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/114)
  * **Feature details:** Changing the UI layout and color theme of application.
  * **Justifications:** The change in design is needed because CCACommander's core is in tracking both members and events,
    hence the two respective columns must be presented. With the new design, users are able to see both members and events simultaneously.

* **Edit Event Feature** [#160](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/160)
  * **Feature details:** Allows the user of CCACommander to edit an event in CCACommander's database.
  * **Justifications:** Gives the user the ability to correct any mistakes in the details of the event, or add more information for unfilled fields like tags.

* **View Members of Event Feature** [#181](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/181)
  * **Feature details:** Allows the user of CCACommander to view all the members attending an event.
  * **Justifications:** Gives the user the ability to quickly see the members participation of an event and the contribution of each member (hours and remark).

* **View Events of Member Feature** [#181](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/181)
  * **Feature details:** Allows the user of CCACommander to view all the events a member is involved in.
  * **Justifications:** Gives the user the ability to quickly see the list of events of a member and their contribution in each event (hours and remark).

* **List Feature** [#181](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/181)
  * **Feature details:** Allows the user of CCACommander to view the full list of events and members.
  * **Justifications:** Gives the user the ability to quickly see an overview of every member and event, this is also the default view of the application.

Relevant tests were added for these commands.

### Enhancements to existing features:
* **Fix `editMember` and `editEvent` commands logic to also update enrolment:** [#182](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/182) [#192](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/192)
  * **Enhancement details:** The enrolments' details of a member (event) is updated, if affected by the edit member (event) command.
  * **Justification:** Enrolment links a member and an event based on their names. When the identity (name) of a member/event is edited via the edit commands, the existing name fields for enrolment wasn't updated.
    This would cause inconvenience to users, having to re-track all the attendance linked to a member or event, hence I have updated the logic
    to also modify the enrolment object when the name field is edited.

* **Enhance UI and add toggle light/dark theme feature for UI:** [#194](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/194)
  * **Enhancement details:** Users can toggle between light and dark mode.
  * **Justification:** To adapt to the needs of CCA heads, who may want to work on CCA tasks during both day and night. Dark mode can reduce blue light
    that can interrupt sleep, it also saves energy because less power usage to display darker background.
  * **Credits:** Implementation is inspired by Tutor’s Pet

* **Fix `editEnrolment` command** [#265](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/265)
  * **Enhancement details:** The hours and remarks fields will be reflected in UI when edited via `editEnrolment` command.
  * **Justification:** Previously, the edited enrolment details (hours, remarks) are correctly stored but not updated to UI automatically,
    this causes confusion to users. To reflect the changes in UI, the solution is to re-filter the list twice.

* **Enhance error messages** [#263](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/263)
  * **Enhancement details:** The error message for enrol command is more specific in indicating optional fields.
  * **Justification:** PE-dry run testers reported that the error message could be more specific on what the optional fields for the command are.

### Documentation:
* **User Guide**
  * Updated the following sections: Introduction, About, Quick Start, Navigate UI, Command History, Notes for command format.
  * Added command details and summaries for the following commands: `enrol`, `unenrol` for draft UG.

* **Developer Guide**
  * Added diagram for updated UI.
  * Added class diagram for Member class and alternative design consideration section.
  * Added class diagram for Event class and alternative design consideration section.
  * Updated parts of old diagrams where there were instances of AddressBook.
  * Updated general model class diagram.
  * Added use cases and glossary section:
    * UC09 - Add member to an event.
    * UC10 - Delete member from an event.

### Contributions to team-based tasks:
* Changing the application logo [#114](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/114)
* Updating UG introduction, About, Quick Start, Navigate UI, Command History, Notes for command format sections [#286](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/286)
* Updated parts of old diagrams where there were instances of AddressBook [#174](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/174)

### Community:
* This is the full [list](https://github.com/AY2324S1-CS2103T-F11-1/tp/pulls?q=is%3Apr+reviewed-by%3Aph-nathan) of pull requests which I have reviewed.
* Some non-trivial pull requests which I have reviewed: [#84](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/84), [#99](https://github.com/AY2324S1-CS2103T-F11-1/tp/pull/99).
